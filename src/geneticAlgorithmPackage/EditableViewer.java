package geneticAlgorithmPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: Visualizes a chromosome that can be manipulated by the user
 *         <br>
 *         Restriction: one chromosome loaded at a time <br>
 *         For example: EditableViewer editableViewer = new EditableViewer()<br>
 *
 */
public class EditableViewer {

	public JFrame frame;
	private JPanel buttonGrid;
	public final String title = "Editable Chomosome Viewer";
	private Chromosome chromosome;
	private JTextField mutationRate;
	private JPanel adminGrid;

	/**
	 * ensures: creates a specific JPanel and instantiates chromosome
	 */
	public EditableViewer() {
		this.chromosome = null;
		this.frame = new JFrame();
		this.frame.setTitle(title);
		this.buttonGrid = new JPanel();
		this.adminGrid = new JPanel();
		createAdminPanel();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500, 500);
		this.frame.setLocation(0, 20);
		this.frame.setVisible(true);
	}

	/**
	 * ensures: creates an admin panel and associated buttons with listeners
	 */
	private void createAdminPanel() {
		this.adminGrid.setLayout(new GridLayout(2, 3));
		JButton newChromosomeButton = new JButton("New Chromosome");
		JButton loadButton = new JButton("Load");
		JButton saveButton = new JButton("Save");
		JButton mutateButton = new JButton("Mutate: ___/N");
		this.mutationRate = new JTextField("Enter Positive Integer");

		this.adminGrid.add(newChromosomeButton);
		this.adminGrid.add(loadButton);
		this.adminGrid.add(saveButton);
		this.adminGrid.add(mutateButton);
		this.adminGrid.add(mutationRate);

		loadButton.addActionListener(new loadListener(this));
		saveButton.addActionListener(new saveListener(this));
		newChromosomeButton.addActionListener(new newChromosomeListener(this));
		mutateButton.addActionListener(new mutateListener(this));

		this.frame.add(this.adminGrid, BorderLayout.SOUTH);
	}

	/**
	 * ensures: creates a randomized chromosome and and adds it to a button grid
	 * panel
	 */
	public void createButtonGrid() {
		this.buttonGrid.removeAll();
		this.buttonGrid.setLayout(new GridLayout(10, 10));
		this.chromosome = new Chromosome(this);
		for (EditableGene gene : getChromosome().editableGeneList) {
			gene.setSize(30, 30);
			this.buttonGrid.add(gene);
		}
		this.frame.add(this.buttonGrid);
		this.frame.setVisible(true);
		this.frame.repaint();

	}

	/**
	 * ensures: accepts a loaded chromosome and adds it to a button grid
	 * 
	 * @param chromosome <br>
	 *                   requires: chromosome
	 */
	public void createButtonGrid(Chromosome chromosome) {
		this.chromosome = chromosome;
		this.buttonGrid.removeAll();
		this.buttonGrid.setLayout(new GridLayout(10, 10));
		for (EditableGene gene : this.getChromosome().editableGeneList) {
//			gene.setPreferredSize(new Dimension(50, 50));
			this.buttonGrid.add(gene);
		}
		this.frame.add(this.buttonGrid);
		this.frame.setVisible(true);
		this.frame.repaint();
	}

	/**
	 * ensures: returns the chromosome visualized in editable viewer
	 * 
	 * @return chromosome
	 */
	public Chromosome getChromosome() {
		return chromosome;
	}

	public JTextField getMutationRate() {
		return this.mutationRate;
	}

	/**
	 * ensures: gets the user input from mutation rate text field
	 * 
	 * @return user input if between 0 and chromosome size <br>
	 *         else returns 0
	 */
	public int getMutationNumber(JTextField textField) {
		String text = textField.getText();
		try {
			int mutationNumber = Integer.parseInt(text);
			if (mutationNumber < 0 || mutationNumber > this.getChromosome().getGeneList().size()) {
				throw new NumberFormatException();
			}
			return mutationNumber;
		} catch (NumberFormatException e) {
			if (text.matches("[0-9]+") && Integer.parseInt(text) > 9000) { // EASTER EGG!
				this.frame.setTitle("Your Power Levels Are Too High!");
				return 0;
			}
			this.frame.setTitle("Enter Mutation Rate between 0 and " + this.getChromosome().getGeneList().size());
			return 0;
		}
	}

	/**
	 * ensures: called to rename the title when a mutation occurs
	 */
	void updateMutantTitle() {
		this.frame.setTitle(this.title + ": Mutant");
	}

}
