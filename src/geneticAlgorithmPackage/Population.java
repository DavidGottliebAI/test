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
	protected EvolutionViewer evolutionViewer;
	private PopulationViewer populationViewer;
	private BestChromosomeViewer bestChromosomeViewer;
	protected EditableViewer editableViewer;
	private FitnessViewer fitnessViewer;

	protected int chromosomeLength;
	protected int populationSize;
	private int truncationPercent;
	private int numberElite;
	protected String fitnessFunction = "";
	private String selectionMethod = "";
	protected Random random;
	private int averageNumMutations;
	private int maxFitness;
	private int extraFitness;
	private int extraSelection;
	private int seed;
	private boolean crossover;

	public Population(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {

		this.editableViewer = editableViewer;
		this.bestChromosomeViewer = bestChromosomeViewer;
		this.populationViewer = populationViewer;
		this.evolutionViewer = evolutionViewer;
		this.fitnessViewer = fitnessViewer;

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

		updateFitnessScores();
		Collections.sort(this.chromosomeList); // Sorts the list based on fitness

		this.bestChromosomeViewer.updateGeneGrid(this.chromosomeList.get(0));
		this.populationViewer.updateChromosomeGrid(this.chromosomeList);
		this.evolutionViewer.lineGraph.addEntry(this.chromosomeList, this.populationSize);
		this.fitnessViewer.scatterGraph.updateChromosomeList(this.chromosomeList);

		if (this.chromosomeList.get(0).getFitness() >= this.maxFitness) {
			return true;
		}

//		System.out.println();
//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.chromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		selection(this.truncationPercent);
		this.chromosomeList = repopulate();
		crossover();
		mutate();
		return false;
	}

	/**
	 * ensures: iterates through each chromosome at the start of each evolutionary
	 * loop to have accurate fitness before sorting
	 */
	public void updateFitnessScores() {
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
		} else if (this.selectionMethod.equals("Ranked")) {
			ArrayList<Chromosome> rankedList = new ArrayList<Chromosome>();
			for (int i = 0; i < this.chromosomeList.size(); i++) {
				Chromosome current = this.chromosomeList.get(i);
				current.calculateFitness("", this.chromosomeList.size() - i, this.evolutionViewer);
				for (int j = 0; j < current.getFitness(); j++) {
					rankedList.add(current);
				}
			}
			Random random = new Random();
			this.chromosomeList.clear();
			for (int i = 0; i < this.populationSize; i++) {
				this.chromosomeList.add(rankedList.get(random.nextInt(rankedList.size() - 1)));
			}
		} else if (this.selectionMethod.equals("Tournament")) {
			Random random = new Random();
			ArrayList<Chromosome> tempChromosome = new ArrayList<Chromosome>();
			ArrayList<Chromosome> newChromosomes = new ArrayList<Chromosome>();
			for (int i = 0; i < this.chromosomeList.size(); i++) {
				tempChromosome.clear();
				for (int j = 0; j < this.extraSelection; j++) {
					tempChromosome.add(this.chromosomeList.get(random.nextInt(this.chromosomeList.size() - 1)));
				}
				Collections.sort(tempChromosome);
				newChromosomes.add(tempChromosome.get(0));
			}
			this.chromosomeList.clear();
			this.chromosomeList = newChromosomes;
		} else if (this.selectionMethod.equals("Steady-State")) {
			ArrayList<Chromosome> bestParents = new ArrayList<Chromosome>();
			for (int i = 0; i < this.extraSelection; i++) {
				bestParents.add(this.chromosomeList.get(i));
			}
			for (int i = 0; i < this.extraSelection; i++) {
				this.chromosomeList.remove(this.chromosomeList.size() - 1);
			}
			for (int i = 0; i < bestParents.size(); i++) {
				bestParents.get(i).mutate(i, this.seed);
			}
			for (int i = 0; i < this.extraSelection; i++) {
				this.chromosomeList.add(bestParents.get(i));
			}
		} else if (this.selectionMethod.equals("SUS")) {
			ArrayList<Chromosome> SUSList = new ArrayList<Chromosome>();
			for (Chromosome chromosome : this.chromosomeList) {
				for (int i = 0; i < chromosome.getFitness(); i++) {
					SUSList.add(chromosome);
				}
			}
			this.chromosomeList.clear();
			for (int i = 0; i < this.populationSize; i++) {
				if (i * this.extraSelection < SUSList.size() - 1) {
					this.chromosomeList.add(SUSList.get(i * this.extraSelection));
				}
			}
		} else if (this.selectionMethod.equals("Boltzmann")) {
			Random random = new Random();
			ArrayList<Chromosome> tempChromosome = new ArrayList<Chromosome>();
			ArrayList<Chromosome> newChromosomes = new ArrayList<Chromosome>();
			int alpha = this.extraSelection;
			double initialTempurature = 50;
			for (int i = 0; i < this.chromosomeList.size(); i++) {
				double k = (1 + 100 * this.evolutionViewer.getNumLoops() / this.evolutionViewer.getMaxGenerations());
				double tempurature = initialTempurature * Math.pow(1 - alpha, k);
				double pressure = Math.exp(-1
						* (this.chromosomeList.get(0).getFitness() - this.evolutionViewer.lineGraph.getFitnesses()[1])
						/ tempurature);
				System.out.println(Math.ceil(100 * pressure));
				tempChromosome.clear();
				for (int j = 0; j < (int) Math.ceil(100 * pressure); j++) {
					tempChromosome.add(this.chromosomeList.get(random.nextInt(this.chromosomeList.size() - 1)));
				}
				Collections.sort(tempChromosome);
				newChromosomes.add(tempChromosome.get(0));
				initialTempurature = tempurature;
			}
			this.chromosomeList.clear();
			this.chromosomeList = newChromosomes;
		}
	}

	private void crossover() {
		if (this.crossover) {
			Random randomCrossover = new Random(this.random.nextLong());
			for (int index = this.numberElite; index < this.populationSize - 1; index += 2) {
				this.chromosomeList.get(index).crossover(this.chromosomeList.get(index + 1),
						randomCrossover.nextLong());
			}
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

	public void setExtraFitness(int extraFitness) {
		this.extraFitness = extraFitness;

	}

	public void setExtraSelection(int extraSelection) {
		this.extraSelection = extraSelection;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public void setCrossover(boolean crossover) {
		this.crossover = crossover;
	}
	
}
