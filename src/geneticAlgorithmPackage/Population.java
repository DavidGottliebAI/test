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
	private int truncationPercent;
	private PopulationViewer populationViewer;

	public Population(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer,
			PopulationViewer populationViewer) {

		this.editableViewer = editableViewer;
		this.bestChromosomeViewer = bestChromosomeViewer;
		this.populationViewer = populationViewer;
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
	public String evolutionLoop() {

		updateFitessScores();
		Collections.sort(this.chromosomeList); // Sorts the list based on fitness
		
		this.bestChromosomeViewer.updateGeneGrid(this.chromosomeList.get(0));
		this.populationViewer.updateChromsomeGrid(this.chromosomeList);
		this.evolutionViewer.lineGraph.addEntry(this.chromosomeList, this.totalUnique());
		this.evolutionViewer.scatterPlot.addEntry(this.chromosomeList);
		
		if (this.chromosomeList.get(0).getFitness() >= this.evolutionViewer.maxFitness) {
			return "fitness";
		} else if(this.evolutionViewer.getElitismPercent() > 100) {
			return "elitism";
		}
		
		double numElite = Math.ceil(this.evolutionViewer.getElitismPercent() / (double) 100 * this.populationSize);

		ArrayList<Chromosome> elite = new ArrayList<Chromosome>();
		for (int i = 0; i < numElite; i++) {
			elite.add(this.chromosomeList.get(0));
			this.chromosomeList.remove(0);
		}
		
//		if(this.evolutionViewer.crossover) {
//			populationCrossover();
//		}

		// System.out.println(this.chromosomeList.get(98));

//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}
//
//		System.out.println(this.chromosomeList.get(0).getFitness() + " "
//				+ this.chromosomeList.get(this.chromosomeList.size() - 1).getFitness() + " "
//				+ this.calculateAverageFitness());
		
    selection(this.truncationPercent);
		this.chromosomeList = repopulate(numElite);

		mutate(this.evolutionViewer.getAverageNumMutations());

		for (int i = 0; i < numElite; i++) {
			this.chromosomeList.add(0, elite.get((int) (numElite - i - 1)));
		}
		return "";
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
		double numberSurvive = this.chromosomeList.size()
				- Math.ceil((double) percent / 100 * this.chromosomeList.size());
		if (this.selectionMethod.equals("Truncation")) {
			while (this.chromosomeList.size() >= numberSurvive) {
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
	 * by clones of the surviving parents
	 * 
	 * @param elitismPercent
	 * 
	 * @return a repopulated list of cloned chromosomes
	 */
	private ArrayList<Chromosome> repopulate(double numElite) {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();
		int index = 0;
		while (repopulatedChromosomeList.size() < this.populationSize - numElite) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			repopulatedChromosomeList.add(newChromosome);

			index++;
		}
		return repopulatedChromosomeList;
	}

	/**
	 * ensures: counts the number of unique chromosomes in the population
	 * 
	 */
	private int totalUnique() {
		int unique = this.populationSize;
		for (int i = 0; i < this.chromosomeList.size() - 1; i++) {
			String current = this.chromosomeList.get(i).getBits();
			for (int j = i + 1; j < this.chromosomeList.size(); j++) {
				String other = this.chromosomeList.get(j).getBits();
				if (current.equals(other)) {
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
			chromosome.calculateFitness(this.fitnessFunction, this.chromosomeLength, this.evolutionViewer);
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

	public void setTruncationPercent(int truncationPercent) {
		this.truncationPercent = truncationPercent;
	}
}
