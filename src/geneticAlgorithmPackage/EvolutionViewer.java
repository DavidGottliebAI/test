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
	public ScatterGraph scatterGraph;
	private Population population;

	private EditableViewer editableViewer;
	private BestChromosomeViewer bestChromosomeViewer;
	private PopulationViewer populationViewer;
	private FitnessViewer fitnessViewer;

	public JFrame frame;
	private JPanel southAdminPanel;
	private JPanel eastAdminPanel;
	private JTextField mutateField;
	private JTextField generationsField;
	private JTextField maxFitnessField;
	private JTextField elitismField;
	private JTextField extraSelectionField;
	private JTextField extraFitnessField;
	private JButton resetButton;
	private JButton startButton;
	private JTextField seedField;
	private JTextField chromosomeLengthField;
	private JTextField populationSizeField;
	private JComboBox<String> fitnessField;
	private JComboBox<String> selectionField;
	private JTextField truncationField;
	private JLabel numberGenerationsLabel;

	private static final int DELAY = 50;
	protected static final int GENERATION_LIMIT = 500;
	public final String title = "Evolution Viewer";

	public boolean evolutionRunning = false;
	public boolean crossover = false;
	public int maxFitness = 100;
	private int maxGenerations = 100;
	private int elitismPercent = 1;
	private int averageNumMutations = 1;
	private int extraSelection = 50;
	private int extraFitness = 50;
	private int numLoops = 0;
	private int seed = 0;
	private int chromosomeLength = 100;
	private int populationSize = 100;
	private int truncationPercent = 50;
	private String fitnessFunction = "One for All!";
	private String selectionMethod = "Truncation";
	private JCheckBox crossoverBox;

	/**
	 * ensures: Evolution Viewer is constructed and instantiates editable viewer for
	 * eventual use in target fitness function. Also creates the first population
	 * for the evolutionaryLoop to build off. Also creates the template GUI
	 * elements.
	 * 
	 * @param editableViewer the visualization of the editable target chromosome
	 */
	public EvolutionViewer() {
		this.editableViewer = new EditableViewer();
		this.bestChromosomeViewer = new BestChromosomeViewer();
		this.populationViewer = new PopulationViewer();
		this.fitnessViewer = new FitnessViewer();

		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.southAdminPanel = new JPanel();
		this.eastAdminPanel = new JPanel();
		this.lineGraph = new LineGraph();
		this.population = new ReproducePopulation(this, this.seed, this.chromosomeLength, this.populationSize,
				this.editableViewer, this.bestChromosomeViewer, this.populationViewer, this.fitnessViewer);

		this.frame.add(this.lineGraph, BorderLayout.CENTER);
		this.lineGraph.repaint();
		createAdminPanels();

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
					resetButton.setVisible(true);
					if (getNumLoops() >= GENERATION_LIMIT) {
						frame.setTitle(title + ": Restart! Number of generations exceeded " + GENERATION_LIMIT);
						resetButton.setVisible(false);
						startButton.setText("Reset");
						setEvolutionRunning(false);
						return;
					} else if (getNumLoops() >= maxGenerations) {
						startButton.setText("Continue");
						setEvolutionRunning(false);
						return;
					}
					if (population.evolutionLoop()) {
						frame.setTitle(title + ": A chromosome has reached maximum fitness!");
						resetButton.setVisible(false);
						startButton.setText("Reset");
						setEvolutionRunning(false);
					}
					numberGenerationsLabel.setText("Number of Generations: " + getNumLoops());
					frame.repaint();
					fitnessViewer.scatterGraph.repaint();
					numLoops += 1;
				}
			}
		});

		t.start();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1800, 500);
		this.frame.setLocation(0, 525); // might want to play with later
		this.frame.setVisible(true);
	}

	/**
	 * ensures: creates, adds functionality and adds buttons to viewer frame
	 * 
	 */
	private void createAdminPanels() {

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
		this.extraFitnessField = new JTextField("10");

		JLabel maxFitnessLabel = new JLabel("Max Fitness");
		this.maxFitnessField = new JTextField("100");
		this.maxFitnessField.setPreferredSize(new Dimension(40, 20));

		JLabel selectionLabel = new JLabel("Selection");
		this.selectionField = new JComboBox<String>();
		this.selectionField.addItem("Truncation");
		this.selectionField.addItem("Roulette Wheel");
		this.selectionField.addItem("Ranked");
		this.selectionField.addItem("Reproduce");
		this.selectionField.addItem("Tournament");
		this.selectionField.addItem("Steady-State");
		this.selectionField.addItem("SUS");
		this.selectionField.addItem("Boltzmann");
		this.extraSelectionField = new JTextField("50");

		JLabel truncationLabel = new JLabel("Truncate %");
		this.truncationField = new JTextField("50");
		this.truncationField.setPreferredSize(new Dimension(30, 20));

		JLabel crossoverLabel = new JLabel("Crossover?");
		this.crossoverBox = new JCheckBox();

		JLabel populationSizeLabel = new JLabel("Population Size");
		this.populationSizeField = new JTextField("100");
		this.populationSizeField.setPreferredSize(new Dimension(40, 20));

		JLabel generationsLabel = new JLabel("Generations");
		this.generationsField = new JTextField("100");
		this.generationsField.setPreferredSize(new Dimension(30, 20));

		JLabel chromosomeLength = new JLabel("Chromosome Length");
		this.chromosomeLengthField = new JTextField("100");
		this.chromosomeLengthField.setPreferredSize(new Dimension(30, 20));

		JLabel elitismLabel = new JLabel("Elitism %");
		this.elitismField = new JTextField("0");
		this.elitismField.setPreferredSize(new Dimension(30, 20));

		this.startButton = new JButton("Start");
		this.startButton.addActionListener(new startListener(this, this.startButton));

		this.resetButton = new JButton("Reset");
		this.resetButton.addActionListener(new resetListener(this));

		this.numberGenerationsLabel = new JLabel("Number of Generations: ");

		this.eastAdminPanel.add(this.numberGenerationsLabel);
		this.southAdminPanel.add(seedLabel);
		this.southAdminPanel.add(this.seedField);
		this.southAdminPanel.add(mutateLabel);
		this.southAdminPanel.add(this.mutateField);
		this.southAdminPanel.add(fitnessLabel);
		this.southAdminPanel.add(this.fitnessField);
		this.southAdminPanel.add(this.extraFitnessField);
		this.southAdminPanel.add(maxFitnessLabel);
		this.southAdminPanel.add(this.maxFitnessField);
		this.southAdminPanel.add(selectionLabel);
		this.southAdminPanel.add(this.selectionField);
		this.southAdminPanel.add(this.extraSelectionField);
		this.southAdminPanel.add(truncationLabel);
		this.southAdminPanel.add(this.truncationField);
		this.southAdminPanel.add(crossoverLabel);
		this.southAdminPanel.add(crossoverBox);
		this.southAdminPanel.add(populationSizeLabel);
		this.southAdminPanel.add(this.populationSizeField);
		this.southAdminPanel.add(generationsLabel);
		this.southAdminPanel.add(this.generationsField);
		this.southAdminPanel.add(chromosomeLength);
		this.southAdminPanel.add(this.chromosomeLengthField);
		this.southAdminPanel.add(elitismLabel);
		this.southAdminPanel.add(this.elitismField);
		this.southAdminPanel.add(this.startButton);
		this.southAdminPanel.add(resetButton);

		this.frame.add(this.southAdminPanel, BorderLayout.SOUTH);
		this.frame.add(this.eastAdminPanel, BorderLayout.EAST);

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
			this.killAndReset();
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

	public void setAverageNumMutations() {
		this.averageNumMutations = getTextFieldNumber(mutateField);
		this.population.setAverageNumMutations(this.averageNumMutations);
	}

	public void setMaxFitness() {
		this.maxFitness = getTextFieldNumber(maxFitnessField);
		this.population.setMaxFitness(this.maxFitness);
	}
	
	public void setExtraFitness() {
		this.extraFitness = getTextFieldNumber(extraFitnessField);
		this.population.setExtraFitness(this.extraFitness);
	}
	
	public void setExtraSelection() {
		this.extraSelection = getTextFieldNumber(extraSelectionField);
		this.population.setExtraSelection(this.extraSelection);
	}

	public void setElitismPercent() {
		this.elitismPercent = getTextFieldNumber(elitismField);
		this.population.setNumberElite(this.elitismPercent);
	}

	public void setCrossover() {
		this.crossover = this.crossoverBox.isSelected();
		this.population.setCrossover(this.crossover);
	}
	
	

	/**
	 * ensures: seed can be set based on user input from text field and resets GUI
	 * if a change is made when start is clicked
	 */
	public void setSeed() {
		this.seed = getTextFieldNumber(seedField);
		this.population.setSeed(this.seed);
//		if (this.seed != oldSeed) {
//			this.reset();
//		}
	}

	/**
	 * ensures: chromosome length can be set based on user input from text field and
	 * resets GUI if a change is made when start is clicked
	 */
	public void setChromosomeLength() {
		this.chromosomeLength = getTextFieldNumber(chromosomeLengthField);
	}

	/**
	 * ensures: population size can be set based on user input from text field and
	 * resets GUI if a change is made when start is clicked
	 */
	public void setPopulationSize() {
		this.populationSize = getTextFieldNumber(this.populationSizeField);
	}

	public void setFitnessFunction() {
		this.fitnessFunction = this.fitnessField.getSelectedItem().toString();
		this.population.setFitnessFunction(this.fitnessFunction);
		if (this.fitnessFunction.equals("Target")) {
			if (this.chromosomeLength != 100) {
				this.chromosomeLengthField.setText("100");
			}
		}
	}

	public void setSelectionMethod() {
		this.selectionMethod = this.selectionField.getSelectedItem().toString();
		this.population.setSelectionMethod(this.selectionMethod);
	}

	public void setTruncationPercent() {
		this.truncationPercent = getTextFieldNumber(this.truncationField);
		this.population.setTruncationPercent(this.truncationPercent);
	}

	public int getNumLoops() {
		return numLoops;
	}

	public void setEvolutionRunning(boolean b) {
		this.evolutionRunning = b;
	}

	/**
	 * ensures: GUI is reset when reset button is pressed and modifications require
	 * a change in graphics
	 */
	public void reset() {
		try {
			this.setTruncationPercent();
			this.setSeed();
			this.setPopulationSize();
			this.setMaxGenerations();
			this.setAverageNumMutations();
			this.setMaxFitness();
			this.setElitismPercent();
		} catch (NumberFormatException e) {
			this.killAndReset();
		} catch (IllegalArgumentException e) {
			this.killAndReset();
		}
		this.setEvolutionRunning(false);
		this.startButton.setText("Start");
		this.numLoops = 1;
		this.lineGraph.reset();
		this.population = new Population(this, this.seed, this.chromosomeLength, this.populationSize,
				this.editableViewer, this.bestChromosomeViewer, this.populationViewer, this.fitnessViewer);
		this.lineGraph.repaint();
		this.populationViewer.reset(this.populationSize, this.chromosomeLength);
		this.bestChromosomeViewer.reset(this.chromosomeLength);
		this.fitnessViewer.scatterGraph.reset(this.populationSize, this.chromosomeLength);
		this.startButton.setVisible(true);
		return;
	}

	public void killAndReset() {
		this.setEvolutionRunning(false);
		this.startButton.setVisible(false);
		return;
	}
}
