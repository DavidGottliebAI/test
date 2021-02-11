package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Chance to mutate any or all genes in a chromosome given a specific
 *         percentage Restriction: <br>
 *         Needs a number as input For example: <br>
 *         button.addActionListener(new mutateListener(editableViewer))
 *
 */
public class mutateListener implements ActionListener {

	private EditableViewer editableViewer;

	/**
	 * ensures: initializes editableViewer
	 * 
	 * @param editableViewer
	 */
	public mutateListener(EditableViewer editableViewer) {
		this.editableViewer = editableViewer;
	}

	/**
	 * ensures: Depending on the number - num - placed in the JTextField and number
	 * - N - of genes in the chromosome, there will be a num/N percent chance for
	 * each button to mutate
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.editableViewer.updateMutantTitle();
		Random random = new Random();

		try {
			for (EditableGene gene : this.editableViewer.getChromosome().getGeneList()) {
				if (random.nextInt(this.editableViewer.getChromosome().getGeneList().size()) < this.editableViewer
						.getMutationNumber(this.editableViewer.getMutationRate())) {
					gene.changeBit();
				}
			}
		} catch (NullPointerException e) {
			this.editableViewer.frame.setTitle("Load chromosome before mutating!");
		}
	}

}
