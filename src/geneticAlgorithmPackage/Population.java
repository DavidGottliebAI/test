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
	private int populationSize;

	public Population() {
		this.populationSize = 100;
		for (int i = 0; i < this.populationSize; i++) {
			Chromosome chromosome = new Chromosome();
			this.chromosomeList.add(chromosome);
		}

		evolutionLoop();
	}

	public Population(int originalSize, long seed) {
		this.populationSize = originalSize;
		for (int i = 0; i < originalSize; i++) {
			Random random = new Random();
			random.setSeed(seed);
			Chromosome chromosome = new Chromosome(random.nextLong());
			this.chromosomeList.add(chromosome);
		}

		evolutionLoop();
	}

	public ArrayList<Chromosome> getChromosomeList() {
		return this.chromosomeList;
	}

	public void truncate(int percent) {
		double numberSurvive = Math.round((double) percent / 100 * this.chromosomeList.size());
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
			repopulatedChromosomeList.add(this.chromosomeList.get(index).deepCopy());
			index++;
		}
		return repopulatedChromosomeList;

	}

	private void mutate() {
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.mutate();
			chromosome.calculateLameFitness();
		}

	}

	public void evolutionLoop() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
			System.out.println();

			System.out.println("Loop:" + i);

			for (Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}

			System.out.println("Before sort:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			Collections.sort(this.chromosomeList); // Sorts the list based on fitness
			System.out.println();
			System.out.println("After sort:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			if (chromosomeList.get(0).getFitness() >= 100) { // Stop evolutionary loop when you reach 100
				break;
			}

			truncate(5);

			System.out.println();
			System.out.println("After truncate:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			this.chromosomeList = repopulate();

			System.out.println();
			System.out.println("After repopulted:");

			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			mutate();

			System.out.println();
			System.out.println("After mutate:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

		}
	}
}
