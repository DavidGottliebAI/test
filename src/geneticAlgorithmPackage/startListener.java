package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startListener implements ActionListener {

	private EvolutionViewer evolutionViewer;

	public startListener(EvolutionViewer evolutionViewer) {
		this.evolutionViewer = evolutionViewer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.evolutionViewer.flipEvolutionRunning();
	}
}
