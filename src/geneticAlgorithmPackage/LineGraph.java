package geneticAlgorithmPackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import java.awt.Color;

public class LineGraph extends JComponent {

	private static final int LINE_WIDTH = 5;
	private static final int LINE_HEIGHT = 5;
	private static final int PLOT_HEIGHT = 300;

	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();

	public LineGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	public void addEntry(int fitness) {
		this.bestFitnessLog.add(fitness);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(50, this.getHeight() - 50);
		g2.drawRect(0, -PLOT_HEIGHT, 1200, PLOT_HEIGHT);
		int plotRatio = PLOT_HEIGHT / 100;

		for (int x = 1; x < this.bestFitnessLog.size(); x++) {
			g2.setColor(Color.BLUE);
			g2.fillRect(x * 5, -this.bestFitnessLog.get(x) * plotRatio, LINE_WIDTH, LINE_HEIGHT);

		}
	}
}