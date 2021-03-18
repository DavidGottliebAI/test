package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Creates chromosome full of genes for EditableViewer to interpret
 *         Restriction: <br>
 *         Only creates array of genes, must pass to EditableViewer to create
 *         the chromosome For example: <br>
 *         Chromosome chromosome = new Chromosome()
 *
 */
public class Chromosome implements Comparable<Chromosome> {
	ArrayList<EditableGene> editableGeneList = new ArrayList<EditableGene>();
	ArrayList<Gene> geneList = new ArrayList<Gene>();
	public String geneString = "";
	protected EditableViewer editableViewer;
	protected int fitness;
	protected int chromosomeLength;
	protected long seed;
	protected Random random;

	/**
	 * ensures: a chromosome a is constructed completely randomly and basically for
	 * the editable chromosome viewer
	 */

	public Chromosome() {
		this.chromosomeLength = 100;
		for (int i = 0; i < this.chromosomeLength; i++) {
			Gene gene = new Gene(); // allow to be seeded or not
			this.geneList.add(gene);
		}
	}

	/**
	 * ensures: a specific chromosome can be created based on user input of a seed
	 * and length
	 * 
	 * @param seed             the user defined seed to make random chromosome
	 *                         creation repeatable
	 * @param chromosomeLength the user defined number of genes in this chromosome
	 * @param editableViewer   the viewer containing the target chromosome for
	 *                         target fitness comparison
	 * @param evolutionViewer  used to acces population level methods
	 */
	public Chromosome(long seed, int chromosomeLength, EditableViewer editableViewer) { // maybe create new chromosome
																						// class
		this.seed = seed;
		this.chromosomeLength = chromosomeLength;
		this.editableViewer = editableViewer;
		this.random = new Random(seed);
		this.chromosomeLength = chromosomeLength;
		for (int i = 0; i < this.chromosomeLength; i++) {
			Gene gene = new Gene(this.random.nextInt(2));
			this.geneList.add(gene);
		}
	}

	/**
	 * ensures: adds genes to a geneList and adds their actionListeners, thereby
	 * creating a chromosome. Also creates a geneString so it can be reproduced
	 * later
	 * 
	 * @param editableViewer
	 */
	public Chromosome(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
		this.chromosomeLength = 100;
		for (int i = 0; i < this.chromosomeLength; i++) {
			EditableGene gene = new EditableGene(i);
			gene.addActionListener(new editableGeneListener(gene, this.editableViewer));
			this.editableGeneList.add(gene);
			this.geneString = this.geneString + gene.getBit();
		}
	}

	/**
	 * ensures: adds genes to geneList based on specified geneString as well as
	 * actionListeners, thereby creating a chromosome.
	 * 
	 * @param editableViewer, geneString
	 */
	public Chromosome(EditableViewer editableViewer, String geneString) {
		this.editableViewer = editableViewer;
		this.geneString = geneString;
		for (int index = 0; index < geneString.length(); index++) {
			int bit = Character.getNumericValue(geneString.charAt(index));
			EditableGene gene = new EditableGene(index, bit);
			gene.addActionListener(new editableGeneListener(gene, this.editableViewer));
			this.editableGeneList.add(gene);
		}
	}

	/**
	 * ensures: calculates fitness of the chromosome based on user chosen fitness
	 * function and normalizes fitness values
	 * 
	 * @param fitnessFunction the user chosen (drop down) fitness function
	 * @param populationSize  the number of chromosomes in this chromsome's
	 *                        populationF
	 * @param evolutionViewer the evolution viewer the population's evolutionary
	 *                        progress is being visualized in
	 * @throws NullPointerException if the target chromosome has not yet been
	 *                              created by the user
	 */
	public void calculateFitness(String fitnessFunction, int populationSize, EvolutionViewer evolutionViewer)
			throws NullPointerException {
		if (fitnessFunction.equals("Absolutely!")) {
			this.fitness = 0;
			for (Gene gene : this.geneList) {
				this.fitness += gene.getBit();
			}
			this.fitness = Math.abs(this.fitness - populationSize / 2) * 2;
		} else if (fitnessFunction.equals("One for All!")) {
			this.fitness = 0;
			for (Gene gene : this.geneList) {
				this.fitness += gene.getBit();
			}
		} else if (fitnessFunction.equals("Target")) {
			try {
				this.fitness = this.chromosomeLength;
				for (int i = 0; i < this.geneList.size(); i++) {
					int currentEditableBit = this.editableViewer.getChromosome().getEditableGeneList().get(i).getBit();
					int currentPopulationBit = this.geneList.get(i).getBit();
					this.fitness -= Math.abs(currentEditableBit - currentPopulationBit);
				}
			} catch (NullPointerException e) {
				evolutionViewer.frame.setTitle(
						evolutionViewer.title + ": Please create a target chromosome in Editable Chromosome Viewer!");
			}
		} else if (fitnessFunction.equals("Novelty")) {
			this.fitness = this.getGeneLength();
			double sum = 0;
			for (int i = 0; i < evolutionViewer.getPopulationSize(); i++) {
				Chromosome current = evolutionViewer.getPopulation().getChromosomeList().get(i);
				sum += this.calculateHamming(current);
			}
			this.fitness = (int) Math.ceil(sum);
			this.normalizeFitness();
		} else if (fitnessFunction.equals("Minimal Criteria Novelty")) {
			this.fitness = 0;
			double sum = 0;
			for (int i = 0; i < evolutionViewer.getPopulationSize(); i++) {
				Chromosome current = evolutionViewer.getPopulation().getChromosomeList().get(i);
				sum += this.calculateHamming(current);
			}
			this.fitness = (int) Math.ceil(sum);
			if (this.fitness < evolutionViewer.getExtraFitness()) {
				this.fitness = 0;
			}
			this.normalizeFitness();
		} else if (fitnessFunction.equals("Local Competition Novelty")) {
			this.fitness = 0;
			double sum = 0;
			ArrayList<Chromosome> competition = new ArrayList<Chromosome>();
			for (int i = 0; i < evolutionViewer.getPopulationSize(); i++) {
				Chromosome current = evolutionViewer.getPopulation().getChromosomeList().get(i);
				sum += this.calculateHamming(current);
				if (this.calculateHamming(current) * 100 < evolutionViewer.getExtraFitness()) {
					competition.add(current);
				}
			}
			Chromosome bestChromosome = null;
			for (int i = 0; i < competition.size() - 1; i++) {
				if (competition.get(i).getFitness() < competition.get(i + 1).getFitness()) {
					bestChromosome = competition.get(i + 1);
				}
			}
			if (bestChromosome != null && competition.get(0).getFitness() > bestChromosome.getFitness()) {
				bestChromosome = competition.get(0);
			}
			this.fitness = (int) Math.ceil(sum);
			if (bestChromosome != null && bestChromosome.getGeneString().equals(this.getGeneString())) {
				this.fitness += 25;
			}
			this.normalizeFitness();
		} else {
			this.fitness = 50;
		}
		// normalizeFitness();
	}

	private double calculateHamming(Chromosome current) {
		double sum = 0;
		String bitstring = current.getGeneString();
		for (int i = 0; i < bitstring.length(); i++) {
			if (bitstring.charAt(i) != this.geneString.charAt(i)) {
				sum += 1;
			}
		}
		sum /= this.geneString.length();
		return sum;
	}

	/**
	 * ensures: sets the fitness proportional to this chromosome's length on a 0 to
	 * 100 scale
	 */
	public void normalizeFitness() {
		this.fitness = 100 * this.fitness / this.chromosomeLength;
	}

	public int getFitness() {
		return this.fitness;
	}

	/**
	 * ensures: creates a new geneString based on editable geneList
	 * 
	 * @return geneString
	 */
	public String getEditableGeneString() {
		this.geneString = "";
		for (EditableGene gene : editableGeneList) {
			this.geneString = this.geneString + gene.getBit();
		}
		return this.geneString;
	}

	public String getGeneString() {
		this.geneString = "";
		for (Gene gene : this.geneList) {
			this.geneString += gene.getBit();
		}
		return this.geneString;
	}

	/**
	 * ensures: gets geneList
	 * 
	 * @return geneList
	 */
	public ArrayList<EditableGene> getEditableGeneList() {
		return this.editableGeneList;
	}

	public ArrayList<Gene> getGeneList() {
		return this.geneList;
	}

	public int getGeneLength() {
		return geneList.size();
	}

	public int getBitAt(int index) {
		return this.geneList.get(index).getBit();
	}

	@Override
	public int compareTo(Chromosome otherChromosome) {
		return otherChromosome.fitness - this.fitness;
	}

	/**
	 * ensures: mutations occur to a random selection of genes in the chromosome
	 * based on user input of mutation rate
	 * 
	 * @param averageNumMutations the statistically expected number of mutations to
	 *                            occur in each chromomosome
	 * @param seed                the random seed fed from chromosome to ensure
	 *                            repeated identically random mutations when an
	 *                            equal seed is selected by a user
	 */
	public void mutate(int averageNumMutations, long seed) {
		Random newRandom = new Random(seed);
		for (Gene gene : this.geneList) {
			if (newRandom.nextInt(this.geneList.size()) + 1 <= averageNumMutations) {
				gene.changeBit();
			}
		}
	}

	/**
	 * ensures: a new chromosome is created based off of the information of this
	 * chromosome
	 * 
	 * @return a cloned chromosome that is new and in a seperate location
	 */
	public Chromosome deepCopy() {
		Chromosome copiedChromosome = new Chromosome(this.seed, this.chromosomeLength, this.editableViewer);
		copiedChromosome.geneList.clear();
		for (Gene gene : this.geneList) {
			Gene newGene = new Gene();
			newGene.setBit(gene.getBit());
			copiedChromosome.geneList.add(newGene);
		}
		return copiedChromosome;
	}

	public void crossover(Chromosome otherChromosome, long seed) {
		Random newRandom = new Random(seed);
		int crossoverIndex = newRandom.nextInt(chromosomeLength);
		otherChromosome.getGeneString();
		if (newRandom.nextBoolean()) {
			for (int index = 0; index < crossoverIndex; index++) {
				otherChromosome.geneList.set(index, this.geneList.get(index));
				this.geneList.set(index, new Gene(otherChromosome.geneString.charAt(index) - '0'));
			}
		} else {
			for (int index = crossoverIndex; index < this.chromosomeLength; index++) {
				otherChromosome.geneList.set(index, this.geneList.get(index));
				this.geneList.set(index, new Gene(otherChromosome.geneString.charAt(index) - '0'));
			}
		}
	}

	public void kBitCrossover(Chromosome otherChromosome, long seed, int kBits) {
		if (kBits == 1) {
			this.crossover(otherChromosome, seed);
		} else {
			Random newRandom = new Random(seed);
			ArrayList<Integer> crossoverIndices = new ArrayList<Integer>();
			for (int i = 0; i < kBits; i++) {
				int crossoverIndex = newRandom.nextInt(chromosomeLength);
				if (crossoverIndices.contains(crossoverIndex)) {
					i--;
				} else {
					crossoverIndices.add(crossoverIndex);
				}
			}
			Collections.sort(crossoverIndices);
			otherChromosome.getGeneString();
			for (int i = 0; i < crossoverIndices.size(); i += 2) {
				for (int index = crossoverIndices.get(i); index < crossoverIndices.get(i + 1); index++) {
					otherChromosome.geneList.set(index, this.geneList.get(index));
					this.geneList.set(index, new Gene(otherChromosome.geneString.charAt(index) - '0'));
				}
			}
		}
	}
}
