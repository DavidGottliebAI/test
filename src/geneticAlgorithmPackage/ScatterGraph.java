package geneticAlgorithmPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class ScatterGraph extends JComponent {
	private static final int PLOT_HEIGHT = 300;
	private static final int PLOT_WIDTH = 300;
	private ArrayList<Chromosome> chromosomeList = new ArrayList<Chromosome>();

	/**
	 * ensures: Constructs a line graph component and sets the preferred size
	 */
	public ScatterGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	public void updateChromosomeList(ArrayList<Chromosome> chromosomeList) {
		this.chromosomeList = chromosomeList;
	}

	/**
	 * ensures: the line graph including live graph with several logs and
	 * borders/labels are drawn on the graph component
	 */
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

		// outline of graph and every 10 fitness ticks

		for (int i = 0; i < 11; i++) {
			g2.drawLine(-10, -i * 30, 10, -i * 30);
			g2.drawString("" + i * 10, -20, -i * 30);
		}

		// outline of graph and every 50 generation ticks

		for (int i = 0; i < 3; i++) {
			g2.drawLine(i * 150, -10, i * 150, 10);
			g2.drawString("" + i * 50, i * 150 - 5, 20);
		}

		Font font = new Font(null, Font.BOLD, 15);
		g2.setFont(font);

		// Title of graph

		g2.drawString("Fitness of Each Chromosome", PLOT_WIDTH / 2 - 70, -PLOT_HEIGHT - 15);
		g2.drawString("Chromosome Number", PLOT_WIDTH / 2 - 80, 40);
		g2.setColor(Color.GREEN);

		for (int x = 0; x < this.chromosomeList.size(); x++) {
			g2.setColor(Color.RED);
			g2.fillRect(x * 3, -this.chromosomeList.get(x).getFitness() * plotRatio, x * 3 + 3,
					this.chromosomeList.get(x).getFitness() * plotRatio);
		}
	}

	/**
	 * ensures: the logs of each data point is cleared when the user clicks the
	 * reset button
	 */
	public void reset(int populationSize, int chromosomeLength) {
		this.chromosomeList.clear();
		this.repaint();

	}

}