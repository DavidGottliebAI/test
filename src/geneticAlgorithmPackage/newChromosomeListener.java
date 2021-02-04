package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br> Creates a new randomized chromosome with 100 genomes
 *         Restriction: <br> Only random and only length 100
 *         For example: <br> button.addActionListener(new newChromosomeListener(editableViewer))
 *
 */
public class newChromosomeListener implements ActionListener {

	private EditableViewer editableViewer;
	
	/**
	 * ensures: initializes editableViewer
	 * @param editableViewer
	 */
	public newChromosomeListener(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
	}
	
	/**
	 * ensures: creates random chromosome and renames title
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.editableViewer.createButtonGrid();
		this.editableViewer.frame.setTitle(this.editableViewer.title + ": " + "New Chromosome");

	}

}
