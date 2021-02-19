package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Population specifically designed to utilize chromosomes from the paper
 *         Exact same as Population, but with slightly different methods to call 
 *         on slightly different chromosomes
 *
 */

public class ReproducePopulation extends Population {
	
	private ArrayList<ReproduceChromosome> newChromosomeList = new ArrayList<ReproduceChromosome>();
	
	public ReproducePopulation(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {
		super(evolutionViewer, seed, chromosomeLength, populationSize, editableViewer, bestChromosomeViewer, populationViewer,
				fitnessViewer);
		for (int i = 0; i < this.populationSize; i++) {
			ReproduceChromosome chromosome = new ReproduceChromosome(random.nextLong(), this.chromosomeLength, this.editableViewer,
					this.evolutionViewer);
			this.newChromosomeList.add(chromosome);
		}
	}
	
	public void updateFitnessScores() {
		super.updateFitnessScores();
		for (ReproduceChromosome chromosome : this.newChromosomeList) {
			chromosome.calculateFitness(this.fitnessFunction, this.chromosomeLength, this.evolutionViewer);
		}
	}
}
