package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br> Load in txt file of specific saved chromosome
 *         Restriction: <br> Only loads in chromosomes that have already been saved
 *         For example: <br> button.addActionListener(loadListener(editableViewer))
 *
 */
public class loadListener implements ActionListener {

	private EditableViewer editableViewer;
	private File selectedFile;
	
	/**
	 * ensures: initializes editableViewer
	 * @param editableViewer
	 */
	public loadListener(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
	}
	
	/**
	 * ensures: shows all previously saved files and lets you choose and load that chromosome in. Throws exceptions if file cannot be found
	 * or if no file is selected
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
				+ "git" + System.getProperty("file.separator") + "GeneticAlgorithm"
				+ System.getProperty("file.separator") + "chromosomeRepo"));
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.selectedFile = fileChooser.getSelectedFile();
		}

		Scanner scanner = null;

		try {
			scanner = new Scanner(this.selectedFile);
			String geneString = "";
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				geneString += line;
			}
			Chromosome chromosome = new Chromosome(this.editableViewer, geneString);
			this.editableViewer.frame.setTitle(this.editableViewer.title + ": " + this.selectedFile.getName());
			this.editableViewer.createButtonGrid(chromosome);

		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
			return;
		} catch (NullPointerException e2) { // Edit for canceling loaded file
			System.out.println("No File Loaded");
			return;
		}
		scanner.close();
	}
}
