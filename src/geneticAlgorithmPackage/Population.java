package geneticAlgorithmPackage;

import java.util.ArrayList;

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
	
	/**
	 * ensures: gets chromosome from list
	 * @return chromosome
	 */
//	public Chromosome getChromosome() {
//		return this.chromosomeList.get(0);
//	}
	
	/**
	 * ensures: sorts fitness of all chromosomes in the population, from highest to lowest
	 * @return sortedChromosomes
	 */
	public ArrayList<Chromosome> sortByFitness() {
		ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
		sortedChromosomes.add(this.chromosomeList.get(0));
		for(int i = 0; i < this.chromosomeList.size() - 1; i++) {
			Chromosome current = this.chromosomeList.get(i);
			for(int j = 0; j < sortedChromosomes.size() - 1; j++) {
				if(current.getFitness() >= sortedChromosomes.get(j).getFitness()) {
					sortedChromosomes.add(j, current);
					break;
				}
			}
			sortedChromosomes.add(current);
		}
		return sortedChromosomes;
	}
	
	/**
	 * ensures: truncates chromosomes by removing bottom 50%
	 * @param truncatedChromosomes
	 */
	public ArrayList<Chromosome> truncate(ArrayList<Chromosome> chromosomes) {
		ArrayList<Chromosome> truncatedChromosomes = new ArrayList<Chromosome>();
		for(int i = 0; i < Math.floorDiv(chromosomes.size(), 2); i++) {
			truncatedChromosomes.add(chromosomes.get(i));
		}
		return truncatedChromosomes;
	}
	
	public void evolutionLoop() {
		int end = 0;
		while(end < 20) {
			ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
			for(Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}
			sortedChromosomes = sortByFitness();
			sortedChromosomes = truncate(sortedChromosomes);
			end++;
		}
	}
}
