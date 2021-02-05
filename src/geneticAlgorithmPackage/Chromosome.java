package geneticAlgorithmPackage;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br> Creates chromosome full of genes for EditableViewer to interpret
 *         Restriction: <br> Only creates array of genes, must pass to EditableViewer to create the chromosome
 *         For example: <br> Chromosome chromosome = new Chromosome()
 *
 */
public class Chromosome {
	ArrayList<EditableGene> editableGeneList = new ArrayList<EditableGene>();
	ArrayList<Gene> geneList = new ArrayList<Gene>();
	private String geneString = "";
	private EditableViewer editableViewer;
	public int fitness;
	
	/**
	 * ensures: 
	 */
	
	public Chromosome() { // maybe create new chromosome class
		this.fitness = 0;
		for (int i = 0; i < 100; i++) {
			Gene gene = new Gene();
			this.geneList.add(gene);
		}
	}
	
	public void calculateLameFitness() {
		this.fitness = 0;
		for (Gene gene : this.geneList) {
				fitness += gene.getBit();
		}
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	/**
	 * ensures: adds genes to a geneList and adds their actionListeners, thereby creating a chromosome.
	 * Also creates a geneString so it can be reproduced later
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
	 * ensures: adds genes to geneList based on specified geneString as well as actionListeners, thereby creating a chromosome.
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
	 * @return geneList
	 */
	public ArrayList<EditableGene> getGeneList() {
		return editableGeneList;
	}
}
