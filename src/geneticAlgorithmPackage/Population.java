package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;

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
	private int originalSize;

	public Population() {
		this.originalSize = 100;
		for (int i = 0; i < this.originalSize; i++) {
			Chromosome chromosome = new Chromosome();
			this.chromosomeList.add(chromosome);
		}
		evolutionLoop();
	}

	public Population(int originalSize) {
		this.originalSize = originalSize;
		for (int i = 0; i < originalSize; i++) {
			Chromosome chromosome = new Chromosome();
			this.chromosomeList.add(chromosome);
		}
		evolutionLoop();
	}

	public ArrayList<Chromosome> getChromosomeList() {
		return this.chromosomeList;
	}

	public ArrayList<Chromosome> truncate() {
		ArrayList<Chromosome> truncatedChromosomeList = new ArrayList<Chromosome>();
		for (int index = 0; index < Math.floorDiv(this.chromosomeList.size(), 2); index++) {
			truncatedChromosomeList.add(this.chromosomeList.get(index));
		}
		return truncatedChromosomeList;
	}

	private ArrayList<Chromosome> repopulate() {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();
		int index = 0;
		while (repopulatedChromosomeList.size() < this.originalSize) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			repopulatedChromosomeList.add(this.chromosomeList.get(index));
			index++;
		}
		return repopulatedChromosomeList;

	}

	private ArrayList<Chromosome> mutate() {
		ArrayList<Chromosome> mutatedChromosomeList = new ArrayList<Chromosome>();
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.mutate();
			mutatedChromosomeList.add(chromosome);
		}
		return mutatedChromosomeList;

	}

	public void evolutionLoop() {
		for (int i = 0; i < 10; i++) {
			System.out.println();
			for (Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}
			System.out.println();
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

			this.chromosomeList = truncate();

			System.out.println();
			System.out.println("After truncate:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			this.chromosomeList = repopulate();

			System.out.println();
			System.out.println("After repop:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			this.chromosomeList = mutate();
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}

			System.out.println();
			System.out.println("After mutate:");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.print(chromosome.getFitness() + ", ");
			}
		}
	}
}
