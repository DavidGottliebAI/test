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

	public Population() {
		for (int i = 0; i < 100; i++) {
			Chromosome chromosome = new Chromosome();
			this.chromosomeList.add(chromosome);
		}
		evolutionLoop();
	}

	public ArrayList<Chromosome> getChromosomeList() {
		return this.chromosomeList;
	}

//	public ArrayList<Chromosome> truncate() {
//		ArrayList<Chromosome> truncatedChromosomes = new ArrayList<Chromosome>();
//		for (int i = 0; i < Math.floorDiv(chromosomes.size(), 2); i++) {
//			truncatedChromosomes.add(chromosomes.get(i));
//		}
//		return truncatedChromosomes;
//	}

	public void evolutionLoop() {
		int end = 0;
		for (int i = 0; i < 3; i++) {
			System.out.println("Loop:");
			ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
			for (Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}
			System.out.println("	Before: ");
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.println(chromosome.getFitness());
			}
			System.out.println("	After:");
			Collections.sort(this.chromosomeList); // Sorts the list based on fitness
			for (Chromosome chromosome : this.chromosomeList) {
				System.out.println(chromosome.getFitness());
			}
			end++;
		}
	}
}
