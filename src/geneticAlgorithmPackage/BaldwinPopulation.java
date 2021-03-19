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

	private int zeros;
	private int twos;
	private int ones;

	public BaldwinPopulation(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {
		super(evolutionViewer, seed, chromosomeLength, populationSize, editableViewer, bestChromosomeViewer,
				populationViewer, fitnessViewer);
		this.chromosomeList.clear();
		for (int i = 0; i < this.populationSize; i++) {
			Chromosome chromosome = new BaldwinChromosome(random.nextLong(), this.chromosomeLength,
					this.editableViewer);
			this.chromosomeList.add(chromosome);
		}
	}

	public boolean evolutionLoop() {
		this.zeros = 0;
		this.ones = 0;
		this.twos = 0;

		for (Chromosome chromosome : this.chromosomeList) {
			BaldwinChromosome baldwinChromosome = (BaldwinChromosome) chromosome;
			baldwinChromosome.runLearningLoop();
//			baldwinChromosome.calculateFitness();
			this.zeros += baldwinChromosome.getNumberOf(0);
			this.ones += baldwinChromosome.getNumberOf(1);
			this.twos += baldwinChromosome.getNumberOf(2);

		}
		this.zeros /= 10;
		this.ones /= 10;
		this.twos /= 10;
		
		Collections.sort(this.chromosomeList); // Sorts the list based on fitness
		
//		System.out.println();
//		System.out.println("After sort:");
//		for (Chromosome chromosome : this.baldwinChromosomeList) {
//			System.out.print(chromosome.getFitness() + ", ");
//		}

		//this.bestChromosomeViewer.updateGeneGrid(this.chromosomeList.get(0));
		// this.populationViewer.updateChromosomeGrid(this.baldwinChromosomeList);
		this.evolutionViewer.lineGraph.addBaldwinEntry(this.chromosomeList, this.populationSize, this.zeros, this.ones,
				this.twos);
		// this.fitnessViewer.scatterGraph.updateChromosomeList(this.baldwinChromosomeList);

		if (this.chromosomeList.get(0).getFitness() >= this.maxFitness) {
			return true;
		}

		select();
		repopulate();
		crossover();
		return false;
	}

	private void select() {
		ArrayList<BaldwinChromosome> rouletteList = new ArrayList<BaldwinChromosome>();
		for (Chromosome chromosome : this.chromosomeList) {
			BaldwinChromosome baldwinChromosome = (BaldwinChromosome) chromosome;
			for (int i = 0; i < baldwinChromosome.getLearningScore() * 100; i++) {
				rouletteList.add(baldwinChromosome);
			}
		}
		Random random = new Random(this.random.nextLong());
		this.chromosomeList.clear();
		while (chromosomeList.size() < this.populationSize) {
			this.chromosomeList.add(rouletteList.get(random.nextInt(rouletteList.size() - 1)));
		}
//		double numberSurvive = (this.chromosomeList.size()
//				- Math.ceil((double) 50 / 100 * this.chromosomeList.size()));
//		while (this.chromosomeList.size() >= numberSurvive & this.chromosomeList.size() > this.numberElite) {
//			this.chromosomeList.remove(this.chromosomeList.size() - 1);
//		}
	}

	private void crossover() {
		Random randomCrossover = new Random(this.random.nextLong());
		for (int index = this.numberElite; index < this.populationSize - 1; index += 2) {
			this.chromosomeList.get(index).crossover(this.chromosomeList.get(index + 1), randomCrossover.nextLong());
		}
	}
	
	protected void repopulate() {
		ArrayList<Chromosome> repopulatedChromosomeList = new ArrayList<Chromosome>();
		int index = 0;
		while (repopulatedChromosomeList.size() < this.numberElite) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			repopulatedChromosomeList.add(newChromosome);
			index++;
		}
		index = 0;
		while (repopulatedChromosomeList.size() < this.populationSize) {
			if (index > this.chromosomeList.size() - 1) {
				index = 0;
			}
			Chromosome newChromosome = this.chromosomeList.get(index).deepCopy();
			repopulatedChromosomeList.add(newChromosome);

			index++;
		}
		this.chromosomeList = repopulatedChromosomeList;
	}
	
}
