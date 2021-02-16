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
	private String selectionMethod = "";
	private EditableViewer editableViewer;
	private Random random;
	private BestChromosomeViewer bestChromosomeViewer;

	public Population(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer) {
		this.bestChromosomeViewer = bestChromosomeViewer;
		this.editableViewer = editableViewer;
		this.evolutionViewer = evolutionViewer;
		this.populationSize = populationSize;
		this.chromosomeLength = chromosomeLength;
		this.random = new Random();
		this.random.setSeed(seed);
		for (int i = 0; i < this.populationSize; i++) {
			Chromosome chromosome = new Chromosome(random.nextLong(), this.chromosomeLength, this.editableViewer);
			this.chromosomeList.add(chromosome);
		}
	}

	/**
	 * ensures: runs evolutionary loop to simulate biological evolution of a
	 * chromosome
	 * 
	 * @return true if the evolution produced a chromosome with a fitness >= 100,
	 *         else false
	 */
	public boolean evolutionLoop() {

		updateFitessScores();
		Collections.sort(this.chromosomeList); // Sorts the list based on fitness

//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}
//
//		System.out.println(this.chromosomeList.get(0).getFitness() + " "
//				+ this.chromosomeList.get(this.chromosomeList.size() - 1).getFitness() + " "
//				+ this.calculateAverageFitness());

		this.bestChromosomeViewer.updateGeneGrid(this.chromosomeList.get(0));

		this.evolutionViewer.lineGraph.addEntry(this.chromosomeList, this.totalUnique());
		if (this.chromosomeList.get(0).getFitness() >= this.evolutionViewer.maxFitness) {
			return true;
		}

		selection(50);
		this.chromosomeList = repopulate();
		mutate(this.evolutionViewer.getAverageNumMutations());
		return false;
	}

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
	public void selection(int percent) {
		double numberSurvive = Math.ceil((double) percent / 100 * this.chromosomeList.size());
		if (this.selectionMethod.equals("Truncation")) {
			while (this.chromosomeList.size() > numberSurvive) {
				this.chromosomeList.remove(this.chromosomeList.size() - 1);
			}
		} else if (this.selectionMethod.equals("Roulette Wheel")) {
			ArrayList<Chromosome> rouletteList = new ArrayList<Chromosome>();
			for (Chromosome chromosome : this.chromosomeList) {
				for (int i = 0; i < chromosome.getFitness(); i++) {
					rouletteList.add(chromosome);
				}
			}
			Random random = new Random();
			this.chromosomeList.clear();
			for (int i = 0; i < this.populationSize; i++) {
				this.chromosomeList.add(rouletteList.get(random.nextInt(rouletteList.size() - 1)));
			}
		}
	}

	/**
	 * ensures: all chromosomes that are killed (truncated) or parents are replaced
	 * my clones of the surviving parents
	 * 
	 * @return a repopulated list of cloned chromosomes
	 */
	private ArrayList<Chromosome> repopulate() {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();
		int index = 0;
		while (repopulatedChromosomeList.size() < this.populationSize) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			repopulatedChromosomeList.add(newChromosome);

			index++;
		}
		return repopulatedChromosomeList;
	}

	private int totalUnique() {
		int unique = this.populationSize;
		for(int i = 0; i < this.chromosomeList.size() - 1; i++) {
			String current = this.chromosomeList.get(i).getBits();
			for(int j = i + 1; j < this.chromosomeList.size(); j++) {
				String other = this.chromosomeList.get(j).getBits();
				if(current.equals(other)) {
					unique -= 1;
				}
			}
		}
		return unique;
	}

	/**
	 * ensures: the each chromosome in the population is mutated according to the
	 * average number of expected mutations
	 * 
	 * @param averageNumMutations the statistically expected number of mutations
	 */
	private void mutate(int averageNumMutations) {
		Random randomMutate = new Random(this.random.nextLong());
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.mutate(averageNumMutations, randomMutate.nextLong());
		}
	}

	/**
	 * ensures: iterates through each chromosome at the start of each evolutionary
	 * loop to have accurate fitness before sorting
	 */
	private void updateFitessScores() {
		for (Chromosome chromosome : this.chromosomeList) {
			chromosome.calculateFitness(this.fitnessFunction, this.populationSize, this.evolutionViewer);
		}
	}

	/**
	 * ensures: the populations fitness function can be set by the evolution viewer
	 * 
	 * @param fitnessFunction the selected fitness function from the user drop down
	 */
	public void setFitnessFunction(String fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	/**
	 * ensures: the population's selection method can be set by the evolution viewer
	 * 
	 * @param selectionMethod the selected selection method from the user's drop
	 *                        down
	 */
	public void setSelectionMethod(String selectionMethod) {
		this.selectionMethod = selectionMethod;
	}

}
