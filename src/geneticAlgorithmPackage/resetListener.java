package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class resetListener implements ActionListener {

	private EvolutionViewer evolutionViewer;

	public resetListener(EvolutionViewer evolutionViewer) {
		this.evolutionViewer = evolutionViewer;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.evolutionViewer.reset();
	}
}
