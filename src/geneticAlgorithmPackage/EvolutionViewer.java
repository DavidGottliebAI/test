package geneticAlgorithmPackage;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br> Visualizes the fitness of the best, worst and average chromosomes as well as their Hamming Distance
 *         Restriction: <br> Only visualizes fitness over time
 *         For example: <br> EvolutionViewer evolutionViewer = new EvolutionViewer
 *
 */
public class EvolutionViewer {
	
	public JFrame frame;
	private JPanel buttonGrid;
	public final String title = "Evolution Viewer";
//	
	public EvolutionViewer() {
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.buttonGrid = new JPanel();
		createAdminPanel();
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1000, 500);
		this.frame.setLocation(500, 0); // might want to play with later
		this.frame.setVisible(true);
	}
//
	private void createAdminPanel() {
		JLabel mutateLabel = new JLabel("Mutation Rate (N/Pop)");
		JTextField mutateField = new JTextField("0");
		JLabel selectionLabel = new JLabel("Selection");
		JComboBox selectionField = new JComboBox();
		selectionField.addItem("Truncation");
		JLabel crossoverlabel = new JLabel("Crossover?");
		JLabel populationSizeLabel = new JLabel("Population Size");
		JTextField populationSizeField = new JTextField("100");
		JLabel generationsLabel = new JLabel("Generations");
		JTextField generationsField = new JTextField("100");
		JLabel genomeLengthLabel = new JLabel("Genome Length");
		JTextField genomeLengthField = new JTextField("100");
		JLabel elitismLabel = new JLabel("Elitism %");
		JTextField elitismField = new JTextField("0.1");
		JButton evolutionButton = new JButton("Start Evolution");
		
		this.buttonGrid.add(mutateLabel);
		this.buttonGrid.add(mutateField);
		this.buttonGrid.add(selectionLabel);
		this.buttonGrid.add(selectionField);
		this.buttonGrid.add(crossoverlabel);
		this.buttonGrid.add(populationSizeLabel);
		this.buttonGrid.add(populationSizeField);
		this.buttonGrid.add(generationsLabel);
		this.buttonGrid.add(generationsField);
		this.buttonGrid.add(genomeLengthLabel);
		this.buttonGrid.add(genomeLengthField);
		this.buttonGrid.add(elitismLabel);
		this.buttonGrid.add(elitismField);
		this.buttonGrid.add(evolutionButton);
		this.frame.add(this.buttonGrid, BorderLayout.SOUTH);
	}
}
