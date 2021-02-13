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
	private JPanel buttonGrid;
	private static final String title = "Best Chromomsome Viewer";

	public BestChromosomeViewer() {
		this.bestChromosome = new Chromosome();
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.buttonGrid = new JPanel();
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
	public void createButtonGrid(Chromosome bestChromosome) {
		this.bestChromosome = bestChromosome;
		this.buttonGrid.removeAll();
		this.buttonGrid.setLayout(new GridLayout(10, 10));
		for (EditableGene gene : this.bestChromosome.getGeneList()) {
//			gene.setPreferredSize(new Dimension(50, 50));
			this.buttonGrid.add(gene);
		}

		this.frame.add(this.buttonGrid);
		this.frame.setVisible(true);
		this.frame.repaint();
	}

	public void createButtonGrid() {
		this.buttonGrid.removeAll();
		this.buttonGrid.setLayout(new GridLayout(10, 10));
		Chromosome chromosome = new Chromosome(this);
		for (EditableGene gene : chromosome.editableGeneList) {
			gene.setSize(30, 30);
			this.buttonGrid.add(gene);
		}
		this.frame.add(this.buttonGrid);
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
}
