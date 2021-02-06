package geneticAlgorithmPackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Visualizes the fitness of the best, worst and average chromosomes as
 *         well as their Hamming Distance Restriction: <br>
 *         Only visualizes fitness over time For example: <br>
 *         EvolutionViewer evolutionViewer = new EvolutionViewer
 *
 */
public class EvolutionViewer {

	public JFrame frame;
	private JPanel buttonGrid;
	public final String title = "Evolution Viewer";
	private Population population;
	private int DELAY = 50;

//	
	public EvolutionViewer() {
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.buttonGrid = new JPanel();

		LineGraph lineGraph = new LineGraph();
		this.population = new Population(lineGraph);

		frame.add(lineGraph, BorderLayout.CENTER);

		lineGraph.repaint();

		createAdminPanel();

		Timer t = new Timer(DELAY , new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				population.evolutionLoop();
				frame.repaint();
			}
		});

		t.start();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1300, 500);
		this.frame.setLocation(500, 20); // might want to play with later
		this.frame.setVisible(true);
	}

//
	private void createAdminPanel() {
		JLabel mutateLabel = new JLabel("Mutation Rate (N/Pop)");
		JTextField mutateField = new JTextField("0");

		JLabel fitnessLabel = new JLabel("Fitness");
		JComboBox fitnessField = new JComboBox();
		fitnessField.addItem("All 1's");
		JLabel selectionLabel = new JLabel("Selection");
		JComboBox selectionField = new JComboBox();
		selectionField.addItem("Truncation");
		JLabel crossoverLabel = new JLabel("Crossover?");
		JCheckBox crossoverBox = new JCheckBox();
		crossoverBox.addActionListener(new crossoverListener());
		JLabel populationSizeLabel = new JLabel("Population Size");
		JTextField populationSizeField = new JTextField("100");
		populationSizeField.addActionListener(new populationSizeListener());
		JLabel generationsLabel = new JLabel("Generations");
		JTextField generationsField = new JTextField("100");
		generationsField.addActionListener(new generationsListener());
		JLabel genomeLengthLabel = new JLabel("Genome Length");
		JTextField genomeLengthField = new JTextField("100");
		genomeLengthField.addActionListener(new genomeLengthListener());
		JLabel elitismLabel = new JLabel("Elitism %");
		JTextField elitismField = new JTextField("0.1");
		elitismField.addActionListener(new elitismListener());
		JButton evolutionButton = new JButton("Start Evolution");
		evolutionButton.addActionListener(new evolutionListener());
		this.buttonGrid.add(mutateLabel);
		this.buttonGrid.add(mutateField);
		this.buttonGrid.add(fitnessLabel);
		this.buttonGrid.add(fitnessField);
		this.buttonGrid.add(selectionLabel);
		this.buttonGrid.add(selectionField);
		this.buttonGrid.add(crossoverLabel);
		this.buttonGrid.add(crossoverBox);
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

	// Starts the simulator

//	public int getTextFieldNumber(JTextField textField) { // may need refactoring
//		String text = textField.getText();
//		try {
//			int textFieldNumber = Integer.parseInt(text);
//			if (textFieldNumber < 0 || textFieldNumber > this.getChromosome().getGeneList().size()) {
//				throw new NumberFormatException();
//			}
//			return textFieldNumber;
//		} catch (NumberFormatException e) {
//			if(!(text instanceof String) && Integer.parseInt(text) > 9000) {
//				this.frame.setTitle("Your Power Levels Are Too High!");
//				return 0;
//			}
//			this.frame.setTitle("Enter Mutation Rate between 0 and " + this.getChromosome().getGeneList().size());
//			return 0;
//		}
//	}
}
