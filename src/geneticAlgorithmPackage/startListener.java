package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class startListener implements ActionListener {

	private EvolutionViewer evolutionViewer;
	private JButton startButton;

	public startListener(EvolutionViewer evolutionViewer, JButton startButton) {
		this.evolutionViewer = evolutionViewer;
		this.startButton = startButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.evolutionViewer.flipEvolutionRunning();
		this.evolutionViewer.setMaxGenerations();
		
		if(this.startButton.getText().equals("Start Evolution")) {
			this.startButton.setText("Pause");
		} else if(this.startButton.getText().equals("Pause")) {
			this.startButton.setText("Continue");
		} else {
			this.startButton.setText("Pause");
		}
	}
}
