package geneticAlgorithmPackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.BasicStroke;
import java.awt.Color;

public class LineGraph extends JComponent {

	private static final int PLOT_HEIGHT = 300;
	private static final int PLOT_WIDTH = 1200;

	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	private ArrayList<Double> averageFitnessLog = new ArrayList<Double>();
	private ArrayList<Double> averageHammingLog = new ArrayList<Double>();

	public LineGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	public void addEntry(int bestFitness, int worstFitness, double averageFitness) { // add hamming
		this.bestFitnessLog.add(bestFitness);
		this.worstFitnessLog.add(worstFitness);
		this.averageFitnessLog.add(averageFitness);
		// this.averageHammingLog.add(averageHamming);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Font smallFont = new Font(null, Font.BOLD, 10);
		g2.setFont(smallFont);
		
		g2.setStroke(new BasicStroke(2));

		g2.translate(50, this.getHeight() - 50);
		g2.drawRect(0, -PLOT_HEIGHT, PLOT_WIDTH, PLOT_HEIGHT);
		int plotRatio = PLOT_HEIGHT / 100;

		for (int i = 0; i < 11; i++) {
			g2.drawLine(-10, -i * 30, 10, -i * 30);
			g2.drawString("" + i * 10, -20, -i * 30);
		}
		
		
		g2.drawString("Best Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 - 40);
		g2.drawString("Average Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 + 10);
		g2.drawString("Worst Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 + 60);
		
		Font font = new Font(null, Font.BOLD, 15);
		g2.setFont(font);

		g2.drawString("Fitness Over Generations", PLOT_WIDTH / 2 - 80, -PLOT_HEIGHT - 15);
		g2.setColor(Color.GREEN);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 - 50, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 - 50, 15, 15);
		g2.setColor(Color.ORANGE);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2, 15, 15);
		g2.setColor(Color.RED);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 50, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 50, 15, 15);
		g2.setColor(Color.BLACK);

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(-90), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		g2.setFont(rotatedFont);
		g2.drawString("Population", -30, -PLOT_HEIGHT / 2 + 35);

		g2.setStroke(new BasicStroke(3));

		int previousYBest = 0;
		int previousYWorst = 0;
		int previousYAverage = 0;
		int previousYAverageHamming = 0;

		for (int x = 0; x < this.bestFitnessLog.size(); x++) {
			g2.setColor(Color.GREEN);
			g2.drawLine(x * 3, previousYBest, x * 3 + 3, -this.bestFitnessLog.get(x) * plotRatio);
			previousYBest = -this.bestFitnessLog.get(x) * plotRatio;

			g2.setColor(Color.RED);
			g2.drawLine(x * 3, previousYWorst, x * 3 + 3, -this.worstFitnessLog.get(x) * plotRatio);
			previousYWorst = -this.worstFitnessLog.get(x) * plotRatio;

			g2.setColor(Color.ORANGE);
			g2.drawLine(x * 3, previousYAverage, x * 3 + 3, (int) (-this.averageFitnessLog.get(x) * plotRatio));
			previousYAverage = (int) (-this.averageFitnessLog.get(x) * plotRatio);

//			g2.setColor(Color.YELLOW);
//			g2.drawLine(x*3, previousYAverageHamming, x*3 + 3, (int) (-this.averageFitnessLog.get(x) * plotRatio));
//			previousYAverageHamming = (int) (-this.averageHammingLog.get(x) * plotRatio);
		}
	}

	public void reset() {
		this.bestFitnessLog.clear();
		this.averageFitnessLog.clear();
		this.worstFitnessLog.clear();
	}
}