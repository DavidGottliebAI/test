package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Population specifically designed to utilize chromosomes from the
 *         paper Exact same as Population, but with slightly different methods
 *         to call on slightly different chromosomes
 *
 */

public class BaldwinPopulation extends Population {

	ArrayList<BaldwinChromosome> baldwinChromosomeList = new ArrayList<BaldwinChromosome>();
	private int zeros;
	private int twos;
	private int ones;

	public BaldwinPopulation(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {
		super(evolutionViewer, seed, chromosomeLength, populationSize, editableViewer, bestChromosomeViewer,
				populationViewer, fitnessViewer);
		for (int i = 0; i < this.populationSize; i++) {
			BaldwinChromosome chromosome = new BaldwinChromosome(random.nextLong(), this.chromosomeLength,
					this.editableViewer);
			this.baldwinChromosomeList.add(chromosome);
		}
	}

	public boolean evolutionLoop() {
		this.zeros = 0;
		this.ones = 0;
		this.twos = 0;

		for (BaldwinChromosome chromosome : this.baldwinChromosomeList) {
			chromosome.runLearningLoop();
			chromosome.calculateFitness();
			this.zeros += chromosome.getNumberOf(0);
			this.ones += chromosome.getNumberOf(1);
			this.twos += chromosome.getNumberOf(2);

		}

		Collections.sort(this.baldwinChromosomeList); // Sorts the list based on fitness

//		System.out.println();
//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.baldwinChromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		this.bestChromosomeViewer.updateGeneGrid(this.baldwinChromosomeList.get(0));
		// this.populationViewer.updateChromosomeGrid(this.baldwinChromosomeList);
		this.evolutionViewer.lineGraph.addBaldwinEntry(this.baldwinChromosomeList, this.populationSize, this.zeros,
				this.ones, this.twos);
		// this.fitnessViewer.scatterGraph.updateChromosomeList(this.baldwinChromosomeList);

		if (this.chromosomeList.get(0).getFitness() >= this.maxFitness) {
			return true;
		}

		select();
		crossover();
		return false;
	}

	private void select() {
		ArrayList<BaldwinChromosome> rouletteList = new ArrayList<BaldwinChromosome>();
		for (BaldwinChromosome chromosome : this.baldwinChromosomeList) {
			for (int i = 0; i < chromosome.getLearningScore() * 100; i++) {
				rouletteList.add(chromosome);
			}
		}
		Random random = new Random(this.random.nextLong());
		this.baldwinChromosomeList.clear();
		while (baldwinChromosomeList.size() < this.populationSize) {
			this.baldwinChromosomeList.add(rouletteList.get(random.nextInt(rouletteList.size() - 1)));
		}
	}

	private void crossover() {
		Random randomCrossover = new Random(this.random.nextLong());
		for (int index = this.numberElite; index < this.populationSize - 1; index += 2) {
			this.chromosomeList.get(index).crossover(this.chromosomeList.get(index + 1), randomCrossover.nextLong());
		}
	}
}
