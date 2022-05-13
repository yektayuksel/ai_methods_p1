import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		String password = "DeepLearning";
		int totNumOfGeners = 50;
		int populationSize = 500;
		int chromosomeSize = password.length();
		int numberOfElitism = 100;
		int recalculateNumber = 3;
		double avgTime = 0;
		double pc = 1;
		double pm = 0.01;
		System.out.println("Çözülecek þifre: " + password);
		System.out.println("Maksimum oluþturulacak nesil sayýsý: " + totNumOfGeners);
		System.out.println("Populasyon büyüklüðü: " + populationSize);
		System.out.println("Kromozom uzunluðu: " + chromosomeSize);
		System.out.println("Elitlizmle seçilecek kromozom sayýsý: " + numberOfElitism);
		System.out.println("Crossver ihtimali: " + pc);
		System.out.println("Mutasyon ihtimali: " + pm);
		System.out.println("------------------------------------");
		
		
		
		
		long startTime = System.nanoTime();
		ArrayList<Character> characters = new ArrayList<Character>();
		fillCharacters(characters);
		double totalGenNum = 0;
		double avgFv = 0;
		for (int i = 0; i < recalculateNumber; i++) {

			ArrayList<Chromosome> generation = new ArrayList<Chromosome>();
			firstGeneration(generation, characters, chromosomeSize, populationSize, password);
			int generationsCreated = 0;
			System.out.printf("%d. Çözüm:\n",i+1);
			while (generationsCreated < totNumOfGeners) {
				ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
				nextGeneration.addAll(pickBest(generation, numberOfElitism));
				while (true) {

					if (nextGeneration.size() == populationSize) {
						generationsCreated++;
						generation = nextGeneration;
						break;
					}
					ArrayList<Chromosome> selected = selection(generation);
					// printGeneration(selected);
					ArrayList<Chromosome> crossedOver = new ArrayList<Chromosome>();
					crossedOver.add(crossover(selected.get(0), selected.get(1), pc, password));
					crossedOver.add(crossover(selected.get(1), selected.get(0), pc, password));
					mutate(crossedOver, pm);
					// printGeneration(crossedOver);
					nextGeneration.addAll(crossedOver);
				}
				
				Chromosome bestInGen = pickBest(generation, 1).get(0);
				System.out.print(generationsCreated + ". nesildeki en iyi kromozom: ");
				printChromosome(bestInGen);
				if (bestInGen.getFitnessValue() == chromosomeSize) {
					
					break;
				}
			}
			totalGenNum += generationsCreated;
			long endTime = System.nanoTime();
			
			long elapsedTime = endTime - startTime;
			System.out.print("En iyi kromozom: ");
			Chromosome bestChr = pickBest(generation, 1).get(0);
			avgFv += bestChr.getFitnessValue();
			printChromosome(bestChr);
			double milliseconds = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
			avgTime += milliseconds;
			System.out.printf("Geçen süre: %23.2f\nToplam yaratýlan nesil: %11d\n------------------------------------\n", milliseconds,
					generationsCreated);
		}
		System.out.printf("Ortalama nesil sayýsý: %12.2f\n", totalGenNum / recalculateNumber);
		System.out.printf("Ortalama fitness value: %11.2f\n", avgFv / recalculateNumber);
		System.out.printf("Ortalama süre: %20.2f", avgTime / recalculateNumber);
	}

	public static void fillCharacters(ArrayList<Character> characters) {
		for (char i = 97; i <= 122; i++) {
			characters.add((Character) i);
		}
		for (char i = 65; i <= 90; i++) {
			characters.add((Character) i);
		}
		for (char i = 48; i <= 57; i++) {
			characters.add((Character) i);
		}
		characters.add(' ');
	}

	public static void firstGeneration(ArrayList<Chromosome> generation, ArrayList<Character> characters,
			int chromosomeSize, int populationSize, String password) {
		for (int i = 0; i < populationSize; i++) {
			Chromosome chromosome = new Chromosome();
			for (int j = 0; j < chromosomeSize; j++) {
				int random = (int) Math.floor(Math.random() * characters.size());

				chromosome.getGenes().add(characters.get(random));
			}
			chromosome.calculateFitnessVal(password);
			generation.add(chromosome);
		}

	}

	public static void printGeneration(ArrayList<Chromosome> generation) {
		for (int i = 0; i < generation.size(); i++) {
			System.out.println("Chromosome: " + i);
			for (int j = 0; j < generation.get(i).getGenes().size(); j++) {
				System.out.print(generation.get(i).getGenes().get(j));
			}
			System.out.println("\nFV: " + generation.get(i).getFitnessValue() + "\n----------------");
		}
	}

	public static void printChromosome(Chromosome chromosome) {
		for (int i = 0; i < chromosome.getGenes().size(); i++) {
			System.out.print(chromosome.getGenes().get(i));
		}
		System.out.println();
	}

	public static ArrayList<Chromosome> pickBest(ArrayList<Chromosome> generation, int numberOfElitism) {

		ArrayList<Chromosome> bests = new ArrayList<Chromosome>();
		Chromosome best = new Chromosome();
		int bestFv;
		for (int i = 0; i < numberOfElitism; i++) {
			bestFv = 0;
			for (int j = 0; j < generation.size(); j++) {
				if (!bests.contains(generation.get(j)) && bestFv <= generation.get(j).getFitnessValue()) {
					best = generation.get(j);
					bestFv = generation.get(j).getFitnessValue();
				}
			}
			bests.add(best);
		}

		return bests;

	}

	public static ArrayList<Chromosome> selection(ArrayList<Chromosome> generation) {

		ArrayList<Integer> ranks = new ArrayList<Integer>();
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>();
		for (int i = 0; i < generation.size(); i++) {
			for (int j = 0; j < generation.get(i).getFitnessValue(); j++) {
				ranks.add(i);
			}
		}

		int r1;
		int r2;
		do {
			r1 = new Random().nextInt(ranks.size());
			r2 = new Random().nextInt(ranks.size());
		} while (r1 == r2);

		selected.add(generation.get(ranks.get(r1)));
		selected.add(generation.get(ranks.get(r2)));

		return selected;
	}

	public static Chromosome crossover(Chromosome chr1, Chromosome chr2, double pc, String password) {
		if (Math.random() > pc) {
			return chr1;
		}
		Chromosome crossedOver = new Chromosome();
		int crossoverPoint = new Random().nextInt(chr1.getGenes().size());
		for (int i = 0; i < crossoverPoint; i++) {
			crossedOver.getGenes().add(chr1.getGenes().get(i));
		}
		for (int i = crossoverPoint; i < chr2.getGenes().size(); i++) {
			crossedOver.getGenes().add(chr2.getGenes().get(i));
		}

		crossedOver.calculateFitnessVal(password);
		return crossedOver;

	}

	public static void mutate(ArrayList<Chromosome> chr, double pm) {
		if (Math.random() <= pm) {
			for (int i = 0; i < chr.size(); i++) {
				int k = new Random().nextInt(chr.get(i).getGenes().size());
				int l = new Random().nextInt(chr.get(i).getGenes().size());
				Collections.swap(chr.get(i).getGenes(), k, l);
			}
		}
	}

}
