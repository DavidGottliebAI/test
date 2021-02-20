package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Chromosome specifically designed to utilize genes from the paper
 *         Exact same as Chromosome, but with slightly different methods to call
 *         on slightly different genes
 *
 */

public class BaldwinChromosome extends Chromosome {
	ArrayList<BaldwinGene> geneList = new ArrayList<BaldwinGene>();
	private double learningScore;

	public BaldwinChromosome(long seed, int chromosomeLength, EditableViewer editableViewer) {

		super(seed, chromosomeLength, editableViewer);
		this.chromosomeLength = 20;
		this.geneList.clear();

		for (int i = 0; i < this.chromosomeLength; i++) {
			BaldwinGene gene = new BaldwinGene(this.random.nextInt(4)); // allow to be seeded or not
			this.geneList.add(gene);
		}
	}

	public void runLearningLoop() {
		if (this.containsZero()) {
			this.learningScore = 1;
			return;
		}
		int day = 1000;
		while (day > 0) {
			mutateTwoGenes();
			if (allOnes()) {
				this.learningScore = 1 + (19 * day) / 1000;
				resetTwoGenes();
				return;
			}
			this.resetTwoGenes();
			day--;
		}
		this.learningScore = 1;
		resetTwoGenes();
	}

	private void resetTwoGenes() {
		for (BaldwinGene gene : this.geneList) {
			if (gene.isTwoGene()) {
				gene.setBit(2);
			}
		}
		return;
	}

	private boolean containsZero() {
		for (BaldwinGene gene : this.geneList) {
			if (gene.getBit() == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean allOnes() {
		for (BaldwinGene gene : this.geneList) {
			if (!(gene.getBit() == 1)) {
				return false;
			}
		}
		return true;
	}

	private void mutateTwoGenes() {
		Random random = new Random();
		for (BaldwinGene gene : this.geneList) {
			if (gene.getBit() == 2) {
				gene.setBit(random.nextInt(2));
			}
		}
		return;
	}

	public void calculateFitness() {
		this.fitness = 0;
		for (BaldwinGene gene : this.geneList) {
			if (gene.getBit() == 1) {
				this.fitness += 1;
			}
		}
		normalizeFitness();
	}

	public double getLearningScore() {
		return this.learningScore;
	}

	public int getNumberOf(int number) {
		int count = 0;
		for (BaldwinGene gene : this.geneList) {
			if (gene.getBit() == number) {
				count++;
			}
		}
		return count;
	}
}