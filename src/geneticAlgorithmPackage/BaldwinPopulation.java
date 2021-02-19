package geneticAlgorithmPackage;

import java.util.ArrayList;

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

	private ArrayList<BaldwinChromosome> chromosomeList = new ArrayList<BaldwinChromosome>();

	public BaldwinPopulation(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {
		super(evolutionViewer, seed, chromosomeLength, populationSize, editableViewer, bestChromosomeViewer,
				populationViewer, fitnessViewer);
		this.chromosomeList.clear();
		for (int i = 0; i < this.populationSize; i++) {
			BaldwinChromosome chromosome = new BaldwinChromosome(random.nextLong(), this.chromosomeLength,
					this.editableViewer);
			this.chromosomeList.add(chromosome);
		}
	}

	public void updateFitnessScores() {
		super.updateFitnessScores();
		for (BaldwinChromosome chromosome : this.chromosomeList) {
			chromosome.calculateFitness(this.fitnessFunction, this.chromosomeLength, this.evolutionViewer);
		}
	}
}
