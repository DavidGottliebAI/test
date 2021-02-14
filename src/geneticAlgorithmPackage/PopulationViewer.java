package geneticAlgorithmPackage;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
public class PopulationViewer {

	private ArrayList<Chromosome> chromosomeList;
	private JFrame frame;
	private JPanel chromosomeGrid;
	private static final String title = "Population Viewer";

	public PopulationViewer() {
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.chromosomeGrid = new JPanel();
		createChromosomeGrid();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(500, 525);
		this.frame.setVisible(true);
	}

	/**
	 * ensures: accepts a loaded chromosome and adds it to a button grid
	 * 
	 * @param chromosome <br>
	 *                   requires: chromosome
	 */
	public void createChromosomeGrid() {
		this.chromosomeGrid.setLayout(new GridLayout(10, 10));
		for (int i = 0; i < 100; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(10, 10));
			chromosomeGrid.add(panel);
		}

//		for (int i = 0; i < 4; i++) {
//			JPanel chromosomePanel = new JPanel();
//			chromosomeGrid.add(chromosomePanel);
//		}
		this.frame.add(this.chromosomeGrid);
	}

	/**
	 * ensures: returns the bestChromosome visualized in editable viewer
	 * 
	 * @return bestChromosome
	 */

	public void updateChromsomeGrid(ArrayList<Chromosome> chromosomeList) {
		this.chromosomeList = chromosomeList;
		for (int i = 0; i < 100; i++) {
			JPanel panel = (JPanel) this.chromosomeGrid.getComponent(i);
			panel.removeAll();
			for (Gene gene : this.chromosomeList.get(i).getGeneList()) {
				panel.add(new EditableGene(gene));
			}
		}
		this.frame.setVisible(true);
		this.frame.repaint();
	}
}