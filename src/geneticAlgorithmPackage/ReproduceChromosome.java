package geneticAlgorithmPackage;

import java.util.ArrayList;
import java.util.Random;

public class ReproduceChromosome extends Chromosome {

	ArrayList<ReproduceGene> geneList = new ArrayList<ReproduceGene>();
	private int ones = 0;
	private int zeros = 0;
	private int twos = 0;

	public ReproduceChromosome(long seed, int chromosomeLength, EditableViewer editableViewer) {
		super(seed, chromosomeLength, editableViewer);
		this.chromosomeLength = 20;
		for (int i = 0; i < this.chromosomeLength; i++) {
			ReproduceGene gene = new ReproduceGene(); // allow to be seeded or not
			this.geneList.add(gene);
		}
	}

	public void calculateFitness(String fitnessFunction, int populationSize, EvolutionViewer evolutionViewer) {
		if (fitnessFunction.equals("Reproduce")) {
			System.out.println("yo");
			ArrayList<ReproduceGene> original = this.geneList;
			// if there exists a 0 originally, highest fitness will be 1
			for (int i = 0; i < original.size(); i++) {
				if (original.get(i).getBit() == 0) {
					this.fitness = 1;
					return;
				}
			}
			for (int day = 1000; day >= 1; day--) {
				ArrayList<ReproduceGene> newList = this.mutate2s(original);
				for (int i = 0; i < newList.size(); i++) {
					if (newList.get(i).getBit() == 0) {
						break;
					}
					this.fitness = 1 + 19 * day / 1000;
					System.out.println(this.fitness);
					for (int j = 0; j < newList.size(); j++) {
						if (newList.get(j).getBit() == 1) {
							ones += 1;
						} else {
							zeros += 1;
						}
					}
					System.out.println(this.fitness);
					return;
				}
			}
		}
		super.calculateFitness(fitnessFunction, populationSize, evolutionViewer);
	}

	private ArrayList<ReproduceGene> mutate2s(ArrayList<ReproduceGene> geneList) {
		Random random = new Random();
		for (int i = 0; i < geneList.size(); i++) {
			if (geneList.get(i).getBit() == 2) {
				if (random.nextInt(2) == 0) {
					geneList.get(i).setBit(1);
				} else {
					geneList.get(i).setBit(0);
				}
			}
		}
		return geneList;
	}
}
