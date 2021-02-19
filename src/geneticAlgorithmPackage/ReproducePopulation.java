package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Collections;

public class ReproducePopulation extends Population {
	
	private ArrayList<ReproduceChromosome> newChromosomeList = new ArrayList<ReproduceChromosome>();
	
	public ReproducePopulation(EvolutionViewer evolutionViewer, long seed, int chromosomeLength, int populationSize,
			EditableViewer editableViewer, BestChromosomeViewer bestChromosomeViewer, PopulationViewer populationViewer,
			FitnessViewer fitnessViewer) {
		super(evolutionViewer, seed, chromosomeLength, populationSize, editableViewer, bestChromosomeViewer, populationViewer,
				fitnessViewer);
		for (int i = 0; i < this.populationSize; i++) {
			ReproduceChromosome chromosome = new ReproduceChromosome(random.nextLong(), this.chromosomeLength, this.editableViewer);
			this.newChromosomeList.add(chromosome);
		}
	}
	
	private void updateFitnessScores() {
		
		for (ReproduceChromosome chromosome : this.newChromosomeList) {
			chromosome.calculateFitness(this.fitnessFunction, this.chromosomeLength, this.evolutionViewer);
		}
	}
}