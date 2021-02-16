package geneticAlgorithmPackage;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class crossoverListener implements ItemListener {

	private EvolutionViewer evolutionViewer;

	public crossoverListener(EvolutionViewer evolutionViewer) {
		this.evolutionViewer = evolutionViewer;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == 1) {
			this.evolutionViewer.crossover = true;
		} else {
			this.evolutionViewer.crossover = false;
		}
		
	}

}
