package geneticAlgorithmPackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

import java.awt.BasicStroke;
import java.awt.Color;

public class LineGraph extends JComponent {

	private static final int PLOT_HEIGHT = 300;
	private static final int PLOT_WIDTH = 1500;

	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	private ArrayList<Double> averageFitnessLog = new ArrayList<Double>();
	private ArrayList<Double> averageHammingLog = new ArrayList<Double>();
	private ArrayList<Integer> uniqueLog = new ArrayList<Integer>();
	private ArrayList<Double> zerosLog = new ArrayList<Double>();
	private ArrayList<Double> onesLog = new ArrayList<Double>();
	private ArrayList<Double> twosLog = new ArrayList<Double>();
	private boolean baldwin = false;

	/**
	 * ensures: Constructs a line graph component and sets the preferred size
	 */
	public LineGraph() {
		this.setPreferredSize(new Dimension(200, 200));
	}

	/**
	 * ensures: the line graph can accept information from the population and store
	 * each generations best, worst, and average fitness scores. along with the
	 * hamming distance
	 * 
	 * @param chromosomeList the list of chromosomes in the population to be
	 *                       analayzed
	 * @param populationSize
	 */
	public void addEntry(ArrayList<Chromosome> chromosomeList, int populationSize) { // add hamming
		this.bestFitnessLog.add(chromosomeList.get(0).getFitness());
		this.worstFitnessLog.add(chromosomeList.get(chromosomeList.size() - 1).getFitness());
		int sum = 0;
		for (Chromosome chromosome : chromosomeList) {
			sum += chromosome.getFitness();
		}
		this.averageFitnessLog.add((double) (sum / chromosomeList.size()));
		this.averageHammingLog.add(calculateAverageHammingDistance(chromosomeList, populationSize));
		this.uniqueLog.add(this.totalUnique(chromosomeList, populationSize));
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

		for (int i = 0; i < 11; i++) {
			g2.drawLine(i * 150, -10, i * 150, 10);
			g2.drawString("" + i * 50, i * 150 - 5, 20);
		}

		// Graph key text

		g2.drawString("Best Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 - 40);
		g2.drawString("Average Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 + 10);
		g2.drawString("Worst Fitness", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 + 60);
		g2.drawString("Average Hamming", PLOT_WIDTH - 100 + 5, -PLOT_HEIGHT / 2 + 110);
		g2.drawString("Unique Chromosomes", PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 - 90);

		Font font = new Font(null, Font.BOLD, 15);
		g2.setFont(font);

		// Title of graph

		g2.drawString("Fitness Over Generations", PLOT_WIDTH / 2 - 80, -PLOT_HEIGHT - 15);
		g2.drawString("Generation Number", PLOT_WIDTH / 2 - 80, 40);
		g2.setColor(Color.GREEN);

		// Graph key colors

		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 - 50, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 - 50, 15, 15);
		g2.setColor(Color.ORANGE);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2, 15, 15);
		g2.setColor(Color.RED);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 50, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 50, 15, 15);
		g2.setColor(Color.YELLOW);
		g2.drawRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 100, 15, 15);
		g2.fillRect(PLOT_WIDTH - 120, -PLOT_HEIGHT / 2 + 100, 15, 15);
		g2.setColor(Color.BLACK);

		// rotates graph

		g2.translate(85, -PLOT_HEIGHT / 2 + 10);
		g2.rotate(-Math.PI / 2);
		g2.drawString("Fitness", -30, -PLOT_HEIGHT / 2 + 35);
		g2.rotate(Math.PI / 2);
		g2.translate(-85, -1 * (-PLOT_HEIGHT / 2 + 10));

		g2.setStroke(new BasicStroke(3));

		int previousYBest = 0; // consider setting to -150 or -180
		int previousYWorst = 0;
		int previousYAverage = 0;
		int previousYAverageHamming = 0;
		int previousZero = 0;
		int previousOne = 0;
		int previousTwo = 0;

		// graphs each line and live updates for each

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

			g2.setColor(Color.YELLOW);
			g2.drawLine(x * 3, previousYAverageHamming, x * 3 + 3, (int) (-this.averageHammingLog.get(x) * plotRatio));
			previousYAverageHamming = (int) (-this.averageHammingLog.get(x) * plotRatio);
			g2.setColor(Color.GREEN);
			g2.drawString("" + this.getFitnesses()[0], PLOT_WIDTH - 100 + 10, -PLOT_HEIGHT / 2 - 15);
			g2.setColor(Color.ORANGE);
			g2.drawString("" + this.getFitnesses()[1], PLOT_WIDTH - 100 + 10, -PLOT_HEIGHT / 2 + 35);
			g2.setColor(Color.RED);
			g2.drawString("" + this.getFitnesses()[2], PLOT_WIDTH - 100 + 10, -PLOT_HEIGHT / 2 + 85);
			g2.setColor(Color.YELLOW);
			g2.drawString("" + this.getFitnesses()[3], PLOT_WIDTH - 100 + 10, -PLOT_HEIGHT / 2 + 135);
			g2.setColor(Color.BLACK);
			g2.drawString("" + this.getFitnesses()[4], PLOT_WIDTH - 100 + 10, -PLOT_HEIGHT / 2 - 65);

			if (this.baldwin) {
				g2.setColor(Color.BLACK);
				g2.drawLine(x * 3, previousZero, x * 3 + 3, (int) (-this.zerosLog.get(x) * plotRatio));
				previousZero = (int) -this.zerosLog.get(x) * plotRatio;

				g2.setColor(Color.GRAY);
				g2.drawLine(x * 3, previousOne, x * 3 + 3, (int) (-this.onesLog.get(x) * plotRatio));
				previousOne = (int) -this.onesLog.get(x) * plotRatio;

				g2.setColor(Color.DARK_GRAY);
				g2.drawLine(x * 3, previousTwo, x * 3 + 3, (int) (-this.twosLog.get(x) * plotRatio));
				previousTwo = (int) -this.twosLog.get(x) * plotRatio;
			}
		}
	}

	public double[] getFitnesses() {
		double[] fitnesses = new double[5];
		fitnesses[0] = bestFitnessLog.get(bestFitnessLog.size() - 1);
		fitnesses[1] = averageFitnessLog.get(averageFitnessLog.size() - 1);
		fitnesses[2] = worstFitnessLog.get(worstFitnessLog.size() - 1);
		fitnesses[3] = averageHammingLog.get(averageHammingLog.size() - 1);
		fitnesses[4] = uniqueLog.get(uniqueLog.size() - 1);
		return fitnesses;
	}

	/**
	 * ensures: the logs of each data point is cleared when the user clicks the
	 * reset button
	 */
	public void reset() {
		this.bestFitnessLog.clear();
		this.averageFitnessLog.clear();
		this.worstFitnessLog.clear();
		this.averageHammingLog.clear();
		this.zerosLog.clear();
		this.onesLog.clear();
		this.twosLog.clear();

	}

	/**
	 * ensures: calculates hamming distance between population of chromosomes
	 * 
	 * @param populationSize
	 * 
	 * @param current        chromosome list
	 */

	public double calculateAverageHammingDistance(ArrayList<Chromosome> chromosomeList, int populationSize) {
		double columnHamming = 0;
		int chromosomeLength = chromosomeList.get(0).getGeneLength();
		for (int gene = 0; gene < chromosomeLength; gene++) {
			int columnSum = 0;
			for (int chromosome = 0; chromosome < populationSize; chromosome++) {
				columnSum += chromosomeList.get(chromosome).getBitAt(gene);
			}
			columnHamming += columnSum * (populationSize - columnSum) / populationSize;
		}
		return columnHamming / chromosomeLength;
	}

	/**
	 * ensures: counts the number of unique chromosomes in the population
	 * 
	 * @param chromosomeList
	 * @param populationSize
	 * 
	 */
	private int totalUnique(ArrayList<Chromosome> chromosomeList, int populationSize) {
		int unique = populationSize - 1;
		for (int index = 0; index < populationSize; index++) {
			String current = chromosomeList.get(index).getGeneString();
			for (int j = index + 1; j < chromosomeList.size(); j++) {
				String other = chromosomeList.get(j).getGeneString();
				if (current.equals(other)) {
					unique -= 1;
					break;
				}
			}
		}
		return unique;
	}

	public void addBaldwinEntry(ArrayList<BaldwinChromosome> baldwinChromosomeList, int populationSize, int zeros,
			int ones, int twos) {
		this.baldwin = true;
		this.bestFitnessLog.add(baldwinChromosomeList.get(0).getFitness());
		this.worstFitnessLog.add(baldwinChromosomeList.get(baldwinChromosomeList.size() - 1).getFitness());
		int sum = 0;
		for (Chromosome chromosome : baldwinChromosomeList) {
			sum += chromosome.getFitness();
		}
		this.averageFitnessLog.add((double) (sum / baldwinChromosomeList.size()));
		this.averageHammingLog.add((double) 0);
		this.uniqueLog.add(0);
		this.zerosLog.add((double) (zeros / 20));
		this.onesLog.add((double) (ones / 20));
		this.twosLog.add((double) (twos / 20));

	}
}