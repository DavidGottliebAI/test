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

	/**
	 * ensures:
	 */

	public Chromosome() { // maybe create new chromosome class
		for (int i = 0; i < 100; i++) {
			Gene gene = new Gene(); // allow to be seeded or not
			this.geneList.add(gene);
		}
	}

	public Chromosome(long seed) { // maybe create new chromosome class
		this.fitness = 0;
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			random.setSeed(seed);
			Gene gene = new Gene(random.nextInt(2));
			this.geneList.add(gene);
		}
	}

	public void calculateLameFitness() {
		this.fitness = 0;
		for (Gene gene : this.geneList) {
			this.fitness += gene.getBit();
		}
	}

	public int getFitness() {
		return this.fitness;
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
		for (int i = 0; i < 100; i++) {
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

	public void mutate() {
		Random random = new Random();
		int averageNumMutations = 2;
		for (Gene gene : this.geneList) {
			if (random.nextInt(this.geneList.size()) <= averageNumMutations)
				gene.changeBit();
		}
	}

	public Chromosome deepCopy() {
		Chromosome copiedChromosome = new Chromosome();
		copiedChromosome.geneList.clear();
		for (Gene gene : this.geneList) {
			Gene newGene = new Gene();
			newGene.setBit(gene.getBit());
			copiedChromosome.geneList.add(newGene);
		}
		copiedChromosome.calculateLameFitness();
		return copiedChromosome;
	}
}
