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
	ArrayList<EditableGene> geneList = new ArrayList<EditableGene>();
	private String geneString = "";
	private EditableViewer editableViewer;
	
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
			this.geneList.add(gene);
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
			this.geneList.add(gene);
		}
	}
	
//	public JPanel populationChromosome(PopulationViewer populationViewer) {
//		this.populationViewer = populationViewer;
//		JPanel chromosomeGrid = new JPanel();
//		chromosomeGrid.setLayout(new GridLayout(100, 100));
//		chromosomeGrid.setSize(50, 50);
//		for (int i = 0; i < 100; i++) {
//			Gene gene = new Gene();
//			chromosomeGrid.add(gene);
//		}
//		return chromosomeGrid;
//	}
	
	/**
	 * ensures: creates a new geneString based on geneList
	 * @return geneString
	 */
	public String getUpdatedGeneString() {
		this.geneString = "";
		for (EditableGene gene : geneList) {
			this.geneString = this.geneString + gene.getBit();
		}
		return this.geneString;
	}
	
	/**
	 * ensures: gets geneList
	 * @return geneList
	 */
	public ArrayList<EditableGene> getGeneList() {
		return geneList;
	}
}
