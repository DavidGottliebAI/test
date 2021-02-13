package geneticAlgorithmPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

	public LineGraph lineGraph;
	private Population population;

	public JFrame frame;
	private JPanel buttonGrid;
	private JTextField mutateField;
	private JTextField generationsField;
	private JButton startButton;
	private JTextField seedField;
	private JTextField chromosomeLengthField;
	private JTextField populationSizeField;
	private JComboBox<String> fitnessField;
	private JComboBox<String> selectionField;

	private static final int DELAY = 50;
	protected static final int FITNESS_LIMIT = 100;
	protected static final int GENERATION_LIMIT = 399;
	public static final String title = "Evolution Viewer";

	public boolean evolutionRunning = false;
	private int maxGenerations = 100;
	private int averageNumMutations = 1;
	private int numLoops = 0;
	private int seed = 0;
	private int chromosomeLength = 100;
	private int populationSize = 100;
	private String fitnessFunction = "One for All!";
	private String selectionMethod = "Truncation";
	private EditableViewer editableViewer;

	/**
	 * ensures: Evolution Viewer is constructed and instantiates editable viewer for
	 * eventual use in target fitness function. Also creates the first population
	 * for the evolutionaryLoop to build off. Also creates the template GUI
	 * elements.
	 * 
	 * @param editableViewer the visualization of the editable target chromosome
	 */
	public EvolutionViewer(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.buttonGrid = new JPanel();
		this.lineGraph = new LineGraph();
		this.population = new Population(this, this.seed, this.chromosomeLength, this.populationSize,
				this.editableViewer);

		frame.add(this.lineGraph, BorderLayout.CENTER);
		this.lineGraph.repaint();
		createAdminPanel();

		/**
		 * purpose: Creates a timer to loop evolutionary process based on
		 * evolutionRunnning boolean state and runs continuously while Evolution Viewer
		 * is open and executing
		 * 
		 */
		Timer t = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (evolutionRunning) {
					if (getNumLoops() > maxGenerations) {
						startButton.setText("Continue");
						flipEvolutionRunning();
						return;
					} else if (getNumLoops() >= GENERATION_LIMIT) {
						startButton.setText("Reset");
						flipEvolutionRunning();
						return;
					} else if(getNumLoops() > 0 && lineGraph.getFitnesses()[0] >= FITNESS_LIMIT) {
						
					}
					if (population.evolutionLoop()) {
						flipEvolutionRunning();
					}
					frame.repaint();
					numLoops = getNumLoops() + 1;
				}
			}
		});

		t.start();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1450, 500);
		this.frame.setLocation(450, 20); // might want to play with later
		this.frame.setVisible(true);
	}

	/**
	 * ensures: creates, adds functionality and adds buttons to viewer frame
	 * 
	 */
	private void createAdminPanel() {
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new loadEvolutionListener(this));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new saveEvolutionListener(this, this.buttonGrid));

		JLabel mutateLabel = new JLabel("Mutation Rate (N/Pop)");
		this.mutateField = new JTextField("1");

		JLabel seedLabel = new JLabel("Seed");
		this.seedField = new JTextField("0");
		this.seedField.setPreferredSize(new Dimension(40, 20));

		JLabel fitnessLabel = new JLabel("Fitness");
		this.fitnessField = new JComboBox<String>();
		this.fitnessField.addItem("One for All!");
		this.fitnessField.addItem("Absolutely!");
		this.fitnessField.addItem("Target");

		JLabel selectionLabel = new JLabel("Selection");
		this.selectionField = new JComboBox<String>();
		this.selectionField.addItem("Truncation");
		this.selectionField.addItem("Roulette Wheel");

		JLabel crossoverLabel = new JLabel("Crossover?");
		JCheckBox crossoverBox = new JCheckBox();
		crossoverBox.addActionListener(new crossoverListener());

		JLabel populationSizeLabel = new JLabel("Population Size");
		this.populationSizeField = new JTextField("100");
		this.populationSizeField.addActionListener(new populationSizeListener());
		this.populationSizeField.setPreferredSize(new Dimension(40, 20));

		JLabel generationsLabel = new JLabel("Generations");
		this.generationsField = new JTextField("100");
		this.generationsField.addActionListener(new generationsListener());
		this.generationsField.setPreferredSize(new Dimension(30, 20));

		JLabel chromosomeLength = new JLabel("Chromosome Length");
		this.chromosomeLengthField = new JTextField("100");
		this.chromosomeLengthField.addActionListener(new genomeLengthListener());
		this.chromosomeLengthField.setPreferredSize(new Dimension(30, 20));

		JLabel elitismLabel = new JLabel("Elitism %");
		JTextField elitismField = new JTextField("10");
		elitismField.addActionListener(new elitismListener());
		elitismField.setPreferredSize(new Dimension(30, 20));

		this.startButton = new JButton("Start");
		this.startButton.addActionListener(new startListener(this, this.startButton));

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new resetListener(this));

		this.buttonGrid.add(saveButton);
		this.buttonGrid.add(loadButton);
		this.buttonGrid.add(seedLabel);
		this.buttonGrid.add(this.seedField);
		this.buttonGrid.add(mutateLabel);
		this.buttonGrid.add(this.mutateField);
		this.buttonGrid.add(fitnessLabel);
		this.buttonGrid.add(this.fitnessField);
		this.buttonGrid.add(selectionLabel);
		this.buttonGrid.add(this.selectionField);
		this.buttonGrid.add(crossoverLabel);
		this.buttonGrid.add(crossoverBox);
		this.buttonGrid.add(populationSizeLabel);
		this.buttonGrid.add(this.populationSizeField);
		this.buttonGrid.add(generationsLabel);
		this.buttonGrid.add(this.generationsField);
		this.buttonGrid.add(chromosomeLength);
		this.buttonGrid.add(this.chromosomeLengthField);
		this.buttonGrid.add(elitismLabel);
		this.buttonGrid.add(elitismField);
		this.buttonGrid.add(this.startButton);
		this.buttonGrid.add(resetButton);

		this.frame.add(this.buttonGrid, BorderLayout.SOUTH);
	}

	/**
	 * ensures: the state of evolution is flipped to opposite of it's current state
	 */
	public void flipEvolutionRunning() {
		if (evolutionRunning) {
			evolutionRunning = false;
		} else {
			evolutionRunning = true;
		}
	}

	/**
	 * ensures: a general solution for extracting information from JTextFields in
	 * GUI and handling exceptions
	 * 
	 * @param textField the text field a user entered number into
	 * @return integer of the user input
	 */
	public int getTextFieldNumber(JTextField textField) { // may need refactoring
		String text = textField.getText();
		try {
			int textFieldNumber = Integer.parseInt(text);
			if (!(textFieldNumber >= 0)) {
				throw new NumberFormatException();
			}
			return textFieldNumber;
		} catch (NumberFormatException e) {
			this.frame.setTitle("Enter a number larger than 0 into the text field!");
			return 0;
		}
	}

	public int getMaxGenerations() {
		return this.maxGenerations;
	}

	public void setMaxGenerations() {
		this.maxGenerations = getTextFieldNumber(generationsField);
	}

	public int getAverageNumMutations() {
		return this.averageNumMutations;
	}

	public void setAverageNumMutations() {
		this.averageNumMutations = getTextFieldNumber(mutateField);
	}

	/**
	 * ensures: seed can be set based on user input from text field and resets GUI
	 * if a change is made when start is clicked
	 */
	public void setSeed() {
		int oldSeed = this.seed;
		this.seed = getTextFieldNumber(seedField);
		if (this.seed != oldSeed) {
			this.reset();
		}
	}

	/**
	 * ensures: chromosome length can be set based on user input from text field and
	 * resets GUI if a change is made when start is clicked
	 */
	public void setChromosomeLength() {
		int oldChromosomeLength = this.chromosomeLength;
		this.chromosomeLength = getTextFieldNumber(chromosomeLengthField);
		if (this.chromosomeLength != oldChromosomeLength) {
			this.reset();
		}
	}

	/**
	 * ensures: population size can be set based on user input from text field and
	 * resets GUI if a change is made when start is clicked
	 */
	public void setPopulationSize() {
		int oldPopulationSize = this.populationSize;
		this.populationSize = getTextFieldNumber(this.populationSizeField);
		if (this.populationSize != oldPopulationSize) {
			this.reset();
		}
	}

	public void setFitnessFunction() {
		this.fitnessFunction = this.fitnessField.getSelectedItem().toString();
		this.population.setFitnessFunction(this.fitnessFunction);
	}

	public void setSelectionMethod() {
		this.selectionMethod = this.selectionField.getSelectedItem().toString();
		this.population.setSelectionMethod(this.selectionMethod);
	}

	/**
	 * ensures: GUI is reset when reset button is pressed and modifications require
	 * a change in graphics
	 */
	public void reset() {
		this.startButton.setText("Start");
		this.evolutionRunning = false;
		this.numLoops = 1;
		this.lineGraph.reset();
		this.population = new Population(this, this.seed, this.chromosomeLength, this.populationSize,
				this.editableViewer);
		this.lineGraph.repaint();

	}

	public int getNumLoops() {
		return numLoops;
	}

	public void setEvolutionRunning(boolean b) {
		this.evolutionRunning = b;
	}
}
