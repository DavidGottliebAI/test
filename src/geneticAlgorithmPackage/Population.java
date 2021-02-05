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
		this.chromosomeList = chromosomeList;
		for (int i = 0; i < 100; i++) {
			Chromosome chromosome = new Chromosome();
			this.chromosomeList.add(chromosome);
		}
		evolutionLoop();
	}

	public Chromosome getChromosomeList() {
		return this.chromosomeList.get(0);
	}

	public ArrayList<Chromosome> sortByFitness() {
		ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
		for(Chromosome chromosome : this.chromosomeList) {
			if(sortedChromosomes.isEmpty()) {
				sortedChromosomes.add(chromosome);
			} else{
				for(int i = 0; i < sortedChromosomes.size(); i++) {
					if(chromosome.getFitness() >= sortedChromosomes.get(i).getFitness()) {
						sortedChromosomes.add(i, chromosome);
					} else {
						sortedChromosomes.add(chromosome);
					}
				}
				
			}
		}
		return sortedChromosomes;
	}

	public ArrayList<Chromosome> truncate(ArrayList<Chromosome> chromosomes) {
		return (ArrayList<Chromosome>) chromosomes.subList(0, Math.floorDiv(chromosomes.size(), 2));
	}
	
	public void evolutionLoop() {
		int end = 0;
		while (end < 20) {
			ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
			for(Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}
			sortedChromosomes = sortByFitness();
			sortedChromosomes = truncate(sortedChromosomes);
			System.out.println(sortedChromosomes.size());
			end++;
		}
	}
}
