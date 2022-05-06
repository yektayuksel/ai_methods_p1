import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		String password = "Deep Learning 2022";
		int totNumOfGeners = 30;
		int populationSize = 500;
		int chromosomeSize = password.length();
		int numberOfElitism = 100;
		double pc = 1;
		double pm = 0;
		ArrayList<Character> characters = new ArrayList<Character>();
		fillCharacters(characters);
		ArrayList<Chromosome> generation = new ArrayList<Chromosome>();
		firstGeneration(generation, characters, chromosomeSize, populationSize, password);
		// printGeneration(generation);

		int generationsCreated = 0;

		while (generationsCreated < totNumOfGeners) {
			ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
			nextGeneration.addAll(pickBest(generation, numberOfElitism));
			while(true) {
				
				if(nextGeneration.size() == populationSize) {
					generationsCreated++;
					generation = nextGeneration;
					break;
				}
				
				ArrayList<Chromosome> selected = rankSelection(generation);
				//printGeneration(selected);
				ArrayList<Chromosome> crossedOver = new ArrayList<Chromosome>();
				crossedOver.add(crossover(selected.get(0), selected.get(1), pc, password));
				crossedOver.add(crossover(selected.get(1), selected.get(0), pc, password));
				//printGeneration(crossedOver);
				nextGeneration.addAll(crossedOver);
				
			}
			
		
			
			
		}
		printGeneration(pickBest(generation,1));
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

	public static ArrayList<Chromosome> pickBest(ArrayList<Chromosome> generation, int numberOfElitism) {

		ArrayList<Chromosome> bests = new ArrayList<Chromosome>();
		Chromosome best = new Chromosome();
		int bestFv;
		for (int i = 0; i < numberOfElitism; i++) {
			bestFv = 0;
			for (int j = 0; j < generation.size(); j++) {
				if (!bests.contains(generation.get(j))
						&& bestFv <= generation.get(j).getFitnessValue()) {
					best = generation.get(j);
					bestFv = generation.get(j).getFitnessValue();				}
			}
			bests.add(best);
		}

		return bests;

	}

	public static ArrayList<Chromosome> rankSelection(ArrayList<Chromosome> generation) {

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
	
	public static Chromosome crossover(Chromosome chr1, Chromosome chr2,  double pc, String password) {
		if(Math.random() > pc) {
			return chr1;
		}
		Chromosome crossedOver = new Chromosome();
		int crossoverPoint = new Random().nextInt(chr1.getGenes().size());
		for(int i = 0; i <  crossoverPoint; i++) {
			crossedOver.getGenes().add(chr1.getGenes().get(i));
		}
		for(int i = crossoverPoint; i < chr2.getGenes().size(); i++) {
			crossedOver.getGenes().add(chr2.getGenes().get(i));
		}
		
		crossedOver.calculateFitnessVal(password);
		return crossedOver;
		
	}
	
	

}
