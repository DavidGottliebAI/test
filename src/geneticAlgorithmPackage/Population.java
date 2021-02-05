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
		for (int i = 0; i < 2; i++) {
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
				System.out.println("Once");
			} else{
				for(int i = 0; i < this.chromosomeList.size(); i++) {
					if(chromosome.getFitness() >= sortedChromosomes.get(i).getFitness()) {
						System.out.println("Bigger");
						sortedChromosomes.add(i, chromosome);
					} else {
						System.out.println("Smaller");
						sortedChromosomes.add(chromosome);
					}
				}
				
			}
		}
		return sortedChromosomes;
	}

	public ArrayList<Chromosome> truncate(ArrayList<Chromosome> chromosomes) {
		ArrayList<Chromosome> truncatedChromosomes = new ArrayList<Chromosome>();
		for(int i = 0; i < Math.floorDiv(chromosomes.size(), 2); i++) {
			truncatedChromosomes.add(chromosomes.get(i));
		}
		return truncatedChromosomes;
	}
	
	public void evolutionLoop() {
		int end = 0;
		for(int i = 0; i < 2; i++) {
			ArrayList<Chromosome> sortedChromosomes = new ArrayList<Chromosome>();
			for(Chromosome chromosome : this.chromosomeList) {
				chromosome.calculateLameFitness();
			}
			sortedChromosomes = sortByFitness();
			System.out.println("!!!");
			sortedChromosomes = truncate(sortedChromosomes);
			System.out.println(sortedChromosomes.size());
			end++;
		}
	}
}
