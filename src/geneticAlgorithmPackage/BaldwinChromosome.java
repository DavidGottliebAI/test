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
	ArrayList<BaldwinGene> baldwinGeneList = new ArrayList<BaldwinGene>();
	private double learningScore;
	private String baldwinGeneString;

	public BaldwinChromosome(long seed, int chromosomeLength, EditableViewer editableViewer) {

		super(seed, chromosomeLength, editableViewer);
		this.chromosomeLength = 20;
//		this.baldwinGeneList.clear();

		for (int i = 0; i < this.chromosomeLength; i++) {
			BaldwinGene gene = new BaldwinGene(this.random.nextInt(4)); // allow to be seeded or not
			this.baldwinGeneList.add(gene);
		}
	}

	@Override
	public String getGeneString() {
		this.baldwinGeneString = "";
		for (Gene gene : this.baldwinGeneList) {
			this.baldwinGeneString += gene.getBit();
		}
		return this.baldwinGeneString;
	}
	@Override
	public Chromosome deepCopy() {
		BaldwinChromosome copiedChromosome = new BaldwinChromosome(this.seed, this.chromosomeLength, this.editableViewer);
		copiedChromosome.baldwinGeneList.clear();
		for (BaldwinGene gene : this.baldwinGeneList) {
			BaldwinGene newGene = new BaldwinGene(gene.getBit());
			copiedChromosome.baldwinGeneList.add(newGene);
		}
		return copiedChromosome;
	}

	public void runLearningLoop() {
		if (this.containsZero()) {
			this.learningScore = 1;
			this.fitness = (int) learningScore;
			return;
		}
		int day = 1000;
		while (day > 0) {
			mutateTwoGenes();

			if (allOnes()) {
				this.learningScore = 1 + (19 * day) / 1000.0;
				resetTwoGenes();
				this.fitness = (int) learningScore;
				return;
			}
			this.resetTwoGenes();

			day--;
		}
		this.learningScore = 1;
		this.fitness = (int) learningScore;
		resetTwoGenes();
	}

	private void resetTwoGenes() {
		for (BaldwinGene gene : this.baldwinGeneList) {
			if (gene.isTwoGene()) {
				gene.setBit(2);
			}
		}
		return;
	}

	private boolean containsZero() {
		for (BaldwinGene gene : this.baldwinGeneList) {
			if (gene.getBit() == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean allOnes() {
		for (BaldwinGene gene : this.baldwinGeneList) {
			if (!(gene.getBit() == 1)) {
				return false;
			}
		}
		return true;
	}

	private void mutateTwoGenes() {
		Random random = new Random();
		for (BaldwinGene gene : this.baldwinGeneList) {
			if (gene.getBit() == 2) {
				gene.setBit(random.nextInt(2));
			}
		}
		return;
	}

//	public void calculateFitness() {
//		this.fitness = 0;
//		for (BaldwinGene gene : this.baldwinGeneList) {
//			if (gene.getBit() == 1) {
//				this.fitness += 1;
//			}
//		}
//		normalizeFitness();
//	}

	public double getLearningScore() {
		return this.learningScore;
	}

	public int getNumberOf(int number) {
		int count = 0;
		for (BaldwinGene gene : this.baldwinGeneList) {
			if (gene.getBit() == number) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public void crossover(Chromosome otherChromosome2, long seed) {
		BaldwinChromosome otherChromosome = (BaldwinChromosome) otherChromosome2;
		Random newRandom = new Random(seed);
		int crossoverIndex = newRandom.nextInt(chromosomeLength);
		otherChromosome.getGeneString();
		for (int index = 0; index < crossoverIndex; index++) {
			otherChromosome.baldwinGeneList.set(index, this.baldwinGeneList.get(index));
			this.baldwinGeneList.set(index, new BaldwinGene(otherChromosome.baldwinGeneString.charAt(index) - '0'));
		}

	}
}