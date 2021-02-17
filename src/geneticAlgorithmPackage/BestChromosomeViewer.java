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
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.geneGrid = new JPanel();
		reset(100);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(500, 25);
	}

	/**
	 * ensures: accepts a loaded chromosome and adds it to a button grid
	 * 
	 * @param chromosome <br>
	 *                   requires: chromosome
	 */
	public void reset(int chromosomeLength) {
		this.geneGrid.removeAll();

		int chromosomeDimension = (int) Math.ceil(Math.sqrt(chromosomeLength));
		this.geneGrid.setLayout(new GridLayout(chromosomeDimension, chromosomeDimension));
		for (int j = 0; j < chromosomeLength; j++) {
			this.geneGrid.add(new EditableGene(j, -1));
		}

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
		for (int i = 0; i < this.geneGrid.getComponentCount(); i++) {
			((EditableGene) this.geneGrid.getComponent(i)).setBit(this.bestChromosome.getGeneList().get(i).getBit());
		}
	}
}
