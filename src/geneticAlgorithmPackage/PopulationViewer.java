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

		reset(100, 100);

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(1000, 25);
		this.frame.setVisible(true);
	}

	/**
	 * ensures: accepts a loaded chromosome and adds it to a button grid
	 * 
	 * @param chromosome <br>
	 *                   requires: chromosome
	 */
	public void reset(int populationSize, int chromosomeLength) {
		this.chromosomeGrid.removeAll();
		int populationDimension = (int) Math.ceil(Math.sqrt(populationSize));
		int chromosomeDimension = (int) Math.ceil(Math.sqrt(chromosomeLength));
		this.chromosomeGrid.setLayout(new GridLayout(populationDimension, populationDimension));
		for (int i = 0; i < populationSize; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(chromosomeDimension, chromosomeDimension));
			chromosomeGrid.add(panel);
			for (int j = 0; j < chromosomeLength; j++) {
				panel.add(new EditableGene());
			}
		}
		this.frame.add(this.chromosomeGrid);
		this.frame.setVisible(true);
	}

	/**
	 * ensures: returns the bestChromosome visualized in editable viewer
	 * 
	 * @return bestChromosome
	 */

	public void updateChromsomeGrid(ArrayList<Chromosome> chromosomeList) {
		this.chromosomeList = chromosomeList;
		for (int i = 0; i < this.chromosomeList.size(); i++) {
			JPanel chromosomePanel = (JPanel) this.chromosomeGrid.getComponent(i);
			Chromosome chromosome = chromosomeList.get(i);
			for (int j = 0; j < chromosomePanel.getComponentCount(); j++) {
				((EditableGene) chromosomePanel.getComponent(j)).setBit(chromosome.getGeneList().get(j).getBit());
			}
		}
		this.frame.repaint();
	}
}