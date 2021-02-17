package geneticAlgorithmPackage;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class FitnessViewer {

	public ScatterGraph scatterGraph;
	private JFrame frame;
	private String title = "Fitness Viewer";

	public FitnessViewer() {

		this.frame = new JFrame();
		this.frame.setTitle(title);

		this.scatterGraph = new ScatterGraph();

		this.frame.add(this.scatterGraph, BorderLayout.CENTER);
		this.scatterGraph.repaint();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(400, 500);
		this.frame.setLocation(1500, 25); // might want to play with later
		this.frame.setVisible(true);

	}
}
