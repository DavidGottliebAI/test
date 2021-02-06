package geneticAlgorithmPackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import java.awt.Color;

public class LineGraph extends JComponent {

	private static final int LINE_WIDTH = 5;
	private static final int LINE_HEIGHT = 5;
	private static final int PLOT_HEIGHT = 300;
	private int maxGenerations = 500;

	public LineGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(50, this.getHeight() - 50);
		g2.drawRect(0, -PLOT_HEIGHT, 1200, PLOT_HEIGHT);
		int plotRatio = PLOT_HEIGHT / 100;

		int fitness = 40;

		for (int x = 0; x < maxGenerations; x++) {
			g2.setColor(Color.BLUE);
			g2.fillRect(x, -fitness * plotRatio, LINE_WIDTH, LINE_HEIGHT);

		}
	}
}