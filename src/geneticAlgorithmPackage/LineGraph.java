package geneticAlgorithmPackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.awt.BasicStroke;
import java.awt.Color;

public class LineGraph extends JComponent {

	private static final int LINE_WIDTH = 7;
	private static final int LINE_HEIGHT = 7;
	private static final int PLOT_HEIGHT = 300;

	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	private ArrayList<Double> averageFitnessLog = new ArrayList<Double>();
	private ArrayList<Double> averageHammingLog = new ArrayList<Double>();

	public LineGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	public void addEntry(int bestFitness, int worstFitness, double averageFitness, double averageHamming) {
		this.bestFitnessLog.add(bestFitness);
		this.worstFitnessLog.add(worstFitness);
		this.averageFitnessLog.add(averageFitness);
		this.averageHammingLog.add(averageHamming);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.translate(50, this.getHeight() - 50);
		g2.drawRect(0, -PLOT_HEIGHT, 1200, PLOT_HEIGHT);
		int plotRatio = PLOT_HEIGHT / 100;
		
		for(int i = 0; i < 11; i++) {
			g2.drawLine(-10, -i * 30, 10, -i * 30);
			g2.drawString("" + i * 10, -15, -i * 30);
		}
		
		g2.setStroke(new BasicStroke(3));
		
		int previousYBest = 0;
		int previousYWorst = 0;
		int previousYAverage = 0;
		int previousYAverageHamming = 0;

		for (int x = 0; x < this.bestFitnessLog.size(); x++) {
			g2.setColor(Color.GREEN);
			g2.drawLine(x*3, previousYBest, x*3 + 3, -this.bestFitnessLog.get(x) * plotRatio);
			previousYBest = -this.bestFitnessLog.get(x) * plotRatio;
			
			g2.setColor(Color.RED);
			g2.drawLine(x*3, previousYWorst, x*3 + 3, -this.worstFitnessLog.get(x) * plotRatio);
			previousYWorst = -this.worstFitnessLog.get(x) * plotRatio;
			
			g2.setColor(Color.ORANGE);
			g2.drawLine(x*3, previousYAverage, x*3 + 3, (int) (-this.averageFitnessLog.get(x) * plotRatio));
			previousYAverage = (int) (-this.averageFitnessLog.get(x) * plotRatio);
			
			g2.setColor(Color.YELLOW);
			g2.drawLine(x*3, previousYAverageHamming, x*3 + 3, (int) (-this.averageFitnessLog.get(x) * plotRatio));
			previousYAverageHamming = (int) (-this.averageFitnessLog.get(x) * plotRatio);
		}
	}

	public void reset() {
		this.bestFitnessLog.clear();
	}
}