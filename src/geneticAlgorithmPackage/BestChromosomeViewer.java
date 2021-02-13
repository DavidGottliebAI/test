package geneticAlgorithmPackage;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Restriction: <br>
 *         For example: <br>
 *
 */
public class BestChromosomeViewer {

	private Chromosome bestChromosome;
	private JFrame frame;
	private JPanel geneGrid;
	private static final String title = "Best Chromosome Viewer";

	public BestChromosomeViewer() {
		this.bestChromosome = new Chromosome();
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.geneGrid = new JPanel();
		createButtonGrid();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(0, 20);
		this.frame.setVisible(true);
	}

	/**
	 * ensures: accepts a loaded chromosome and adds it to a button grid
	 * 
	 * @param chromosome <br>
	 *                   requires: chromosome
	 */
	public void createButtonGrid() {
		this.geneGrid.setLayout(new GridLayout(10, 10));
		this.frame.add(this.geneGrid);
		this.frame.setVisible(true);
		this.frame.repaint();
	}

	/**
	 * ensures: returns the bestChromosome visualized in editable viewer
	 * 
	 * @return bestChromosome
	 */
	public Chromosome getChromosome() {
		return this.bestChromosome;
	}

	public void updateGeneGrid(Chromosome bestChromosome) {
		this.bestChromosome = bestChromosome;
		this.geneGrid.removeAll();
		int index = 0;
		for (Gene gene : this.bestChromosome.getGeneList()) {
			this.geneGrid.add(new EditableGene(gene, index++));
		}
		this.frame.setVisible(true);
		this.frame.repaint();
	}
}
