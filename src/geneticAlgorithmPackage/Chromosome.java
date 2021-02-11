package geneticAlgorithmPackage;

import java.util.ArrayList;
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
	private String geneString = "";
	private EditableViewer editableViewer;
	private int fitness;
	private int chromosomeLength;
	private long seed;
	protected Random random;

	// private String fitnessFunction = "Fitness1";

	/**
	 * ensures:
	 */

	public Chromosome() { // maybe create new chromosome class
		this.chromosomeLength = 100;
		for (int i = 0; i < this.chromosomeLength; i++) {
			Gene gene = new Gene(); // allow to be seeded or not
			this.geneList.add(gene);
		}
	}

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

	public void calculateFitness(String fitnessFunction, int populationSize) throws NullPointerException {
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
				this.fitness = 0;
				for (int i = 0; i < this.geneList.size(); i++) {
					int currentEditableBit = this.editableViewer.getChromosome().getGeneList().get(i).getBit();
					int currentPopulationBit = this.geneList.get(i).getBit();
					this.fitness += Math.abs(currentEditableBit - currentPopulationBit);

				}
				this.fitness = 100 - this.fitness;
			} catch (NullPointerException e) {
				// re-title EvolutionViewer
				// System.err.println("No Chromosome");
			}
		}
		normalizeFitness();
	}

	public void normalizeFitness() {
		this.fitness = 100 * this.fitness / this.chromosomeLength;
	}

	public int getFitness() {
		return this.fitness;
	}

	/**
	 * ensures: creates a new geneString based on geneList
	 * 
	 * @return geneString
	 */
	public String getUpdatedGeneString() {
		this.geneString = "";
		for (EditableGene gene : editableGeneList) {
			this.geneString = this.geneString + gene.getBit();
		}
		return this.geneString;
	}

	public long getBits() {
		String bits = "";
		for (EditableGene gene : editableGeneList) {
			bits += gene.getBit();
		}
		long bitString = Long.parseLong(bits);
		return bitString;
	}

	/**
	 * ensures: gets geneList
	 * 
	 * @return geneList
	 */
	public ArrayList<EditableGene> getGeneList() {
		return editableGeneList;
	}

	@Override
	public int compareTo(Chromosome other) {
		return other.fitness - this.fitness;
	}

	public void mutate(int averageNumMutations, long seed) {
		Random newRandom = new Random(seed);
		for (Gene gene : this.geneList) {
			if (newRandom.nextInt(this.geneList.size()) + 1 <= averageNumMutations) {
				gene.changeBit();
			}
		}
	}

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
}
