package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Visualize chromosome population of 100 Restriction: <br>
 *         Has no functionality beyond representing chromosomes and their
 *         changes over time; cannot be edited For example: <br>
 *         PopulationViewer populationViewer = new PopulationViewer()
 *
 */

public class Population {

	private ArrayList<Chromosome> chromosomeList = new ArrayList<Chromosome>();
	private EvolutionViewer evolutionViewer;

	private int chromosomeLength;
	private int populationSize;
	private String fitnessFunction = "";

	public Population(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize) {
		this.evolutionViewer = evolutionViewer;
		this.populationSize = populationSize;
		this.chromosomeLength = chromosomeLength;
		Random random = new Random();
		random.setSeed(seed);
		for (int i = 0; i < this.populationSize; i++) {
			Chromosome chromosome = new Chromosome(random.nextLong(), this.chromosomeLength);
			this.chromosomeList.add(chromosome);
		}
	}

//	public Population(EvolutionViewer evolutionViewer, int populationSize, long seed) {
//		this.evolutionViewer = evolutionViewer;
//		this.populationSize = populationSize;
//		for (int i = 0; i < this.populationSize; i++) {
//			Random random = new Random();
//			random.setSeed(seed);
//			Chromosome chromosome = new Chromosome(random.nextLong(), this.chromosomeLength);
//			this.chromosomeList.add(chromosome);
//		}
//	}

	/**
	 * ensures: sorts fitness of all chromosomes in the population, from highest to
	 * lowest
	 * 
	 * @return sortedChromosomes
	 */
	public ArrayList<Chromosome> getChromosomeList() {
		return this.chromosomeList;
	}

	/**
	 * ensures: truncates chromosomes by leaving the top n%
	 * 
	 * @param percent
	 */
	public void truncate(int percent) {
		double numberSurvive = Math.ceil((double) percent / 100 * this.chromosomeList.size());
		while (this.chromosomeList.size() > numberSurvive) {
			this.chromosomeList.remove(this.chromosomeList.size() - 1);
		}
	}

	private ArrayList<Chromosome> repopulate() {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();
		int index = 0;
		while (repopulatedChromosomeList.size() < this.populationSize) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			newChromosome.calculateFitness(this.fitnessFunction, this.populationSize);
			repopulatedChromosomeList.add(newChromosome);

			index++;
		}
		return repopulatedChromosomeList;
	}

	private void mutate(int averageNumMutations) {
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.mutate(averageNumMutations);
			chromosome.calculateFitness(this.fitnessFunction, this.populationSize);
		}
	}

	private void updateFitessScores() {
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.calculateFitness(this.fitnessFunction, this.populationSize);
		}
	}

	public void evolutionLoop() {

		updateFitessScores();

		System.out.println("Before sort:");
		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
			System.out.print(chromosome.getUpdatedGeneString() + ", ");
		}

		Collections.sort(this.chromosomeList); // Sorts the list based on fitness

		this.evolutionViewer.lineGraph.addEntry(this.chromosomeList.get(0).getFitness(),
				this.chromosomeList.get(this.chromosomeList.size() - 1).getFitness(), this.calculateAverageFitness(),
				this.calculateAverageHammingDistance());

		System.out.println(this.chromosomeList.get(0).getFitness());

//		System.out.println("After sort:");
//
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		truncate(50);

//		System.out.println();
//		System.out.println("After truncate:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		this.chromosomeList = repopulate();

//		System.out.println();
//		System.out.println("After repopulated:");
//
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		mutate(this.evolutionViewer.getAverageNumMutations());
		updateFitessScores();

//		System.out.println();
//		System.out.println("After mutate:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}
	}

	public void setFitnessFunction(String fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	public double calculateAverageFitness() {
		int sum = 0;
		int count = 0;
		for (int i = 0; i < this.chromosomeList.size(); i++) {
			int current = this.chromosomeList.get(i).getFitness();
			sum += current;
			count++;
		}
		double average = sum / count;
		return average;
	}

	public int calculate1sBinaryAddition(long binary1, long binary2) {
		int[] newBinary = new int[10];
		int i = 0;
		int carry = 0;
		int ones = 0;

		while (binary1 != 0 || binary2 != 0) {
			newBinary[i++] = (int) ((binary1 % 10 + binary2 % 10 + carry) % 2);
			carry = (int) ((binary1 % 10 + binary2 % 10 + carry) / 2);
			binary1 = 10;
			binary2 /= 10;
		}
		if (carry != 0) {
			newBinary[i++] = carry;
		}
		for (int j = 0; j < newBinary.length; j++) {
			if (newBinary[j] == 1) {
				ones++;
			}
		}
		return ones;
	}

	public double calculateAverageHammingDistance() {
		int sum = 0;
		int count = 0;
//		for(int i = 0; i < this.chromosomeList.size(); i++) {
//			Chromosome current = this.chromosomeList.get(i);
//			long firstBinary = Long.parseLong(current.getUpdatedGeneString());
//			for(int j = 0; j < this.chromosomeList.size() - i; j++) {
//				long secondBinary = Long.parseLong(current.getUpdatedGeneString());
//				sum += this.calculate1sBinaryAddition(firstBinary, secondBinary);
//				count++;
//			}
//		}
//		return sum / count;
		return 0;
	}
}
