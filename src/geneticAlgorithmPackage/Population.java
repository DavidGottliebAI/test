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
	private PopulationViewer populationViewer;
	private BestChromosomeViewer bestChromosomeViewer;
	private EditableViewer editableViewer;

	private int chromosomeLength;
	private int populationSize;
	private int truncationPercent;
	private int numberElite;
	private String fitnessFunction = "";
	private String selectionMethod = "";
	private Random random;
	private int averageNumMutations;
	private int maxFitness;

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
		this.evolutionViewer.lineGraph.addEntry(this.chromosomeList, this.populationSize);
		this.evolutionViewer.scatterPlot.addEntry(this.chromosomeList);

		if (this.chromosomeList.get(0).getFitness() >= this.maxFitness) {
			return "fitness";
		}

//		System.out.println();
//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		selection(this.truncationPercent);
		this.chromosomeList = repopulate();
		if (this.evolutionViewer.crossover) {
			crossover();
		}
		mutate();
		return "";
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
	 * ensures: all chromosomes that are killed (truncated) or parents are replaced
	 * by clones of the surviving parents
	 * 
	 * @param elitismPercent
	 * 
	 * @return a repopulated list of cloned chromosomes
	 */
	private ArrayList<Chromosome> repopulate() {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();

		int index = 0;
		while (repopulatedChromosomeList.size() < this.numberElite) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			repopulatedChromosomeList.add(newChromosome);
			index++;
		}
		index = 0;
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

	/**
	 * ensures: truncates chromosomes by leaving the top n%
	 * 
	 * @param percent
	 */
	public void selection(int percent) {
		double numberSurvive = (this.chromosomeList.size()
				- Math.ceil((double) percent / 100 * this.chromosomeList.size()));
		if (this.selectionMethod.equals("Truncation")) {
			while (this.chromosomeList.size() >= numberSurvive & this.chromosomeList.size() > this.numberElite) {
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

	private void crossover() {
		Random randomCrossover = new Random(this.random.nextLong());
		System.out.println("Run Crossover!");
		for (int index = this.numberElite; index < this.populationSize - 1; index += 2) {
			this.chromosomeList.get(index).crossover(this.chromosomeList.get(index + 1), randomCrossover.nextLong());
		}
	}

	/**
	 * ensures: the each chromosome in the population is mutated according to the
	 * average number of expected mutations
	 * 
	 * @param averageNumMutations the statistically expected number of mutations
	 */
	private void mutate() {
		Random randomMutate = new Random(this.random.nextLong());
		for (int index = this.numberElite; index < this.populationSize; index++) {
			this.chromosomeList.get(index).mutate(this.averageNumMutations, randomMutate.nextLong());
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

	public void setNumberElite(int elitismPercent) {
		this.numberElite = (int) Math.ceil(elitismPercent / 100 * this.populationSize);
	}

	public ArrayList<Chromosome> getChromosomeList() {
		return this.chromosomeList;
	}

	public void setAverageNumMutations(int averageNumMutations) {
		this.averageNumMutations = averageNumMutations;
	}

	public void setMaxFitness(int maxFitness) {
		this.maxFitness = maxFitness;
	}
}
