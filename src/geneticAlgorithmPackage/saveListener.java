package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br> Saves a chromosome to a txt file
 *         Restriction: <br> No ability to tell user whether or not identical file has been created
 *         For example: <br> button.addActionListener(saveListener(editableViewer))
 *
 */
public class saveListener implements ActionListener {

	private EditableViewer editableViewer;
	private File selectedFile;
	
	/**
	 * ensures: initializes editableViewer
	 * @param editableViewer
	 */
	public saveListener(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
	}
	
	/**
	 * ensures: saves a file which can overwrite an existing file or be saved as a new file. Throws exception if no file can be found to save
	 * @param editableViewer
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
				+ "git" + System.getProperty("file.separator") + "GeneticAlgorithm"
				+ System.getProperty("file.separator") + "chromosomeRepo")); // professor does not have access
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.selectedFile = fileChooser.getSelectedFile();
		}

		try {
			PrintWriter printWriter = new PrintWriter(this.selectedFile);
			try {
				printWriter.println(this.editableViewer.getChromosome().getEditableGeneString());
			} catch (NullPointerException e) {
				this.editableViewer.frame.setTitle("Load chromosome before saving!");
			}
			printWriter.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
			return;
		} catch (NullPointerException e2) {
			System.out.println("No chromosome is present to save!");
			return;
		}
	}
}