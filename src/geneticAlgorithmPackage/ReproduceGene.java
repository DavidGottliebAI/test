package geneticAlgorithmPackage;

import java.util.Random;

public class ReproduceGene extends Gene{

	/**
	 * ensures: constructs Gene that reproduces bit strings used in paper
	 */
	
	public ReproduceGene() {
		super();
		Random random = new Random();
		if(random.nextInt(4) == 0) {
			this.bit = 0;
		} else if (random.nextInt(4) == 1) {
			this.bit = 1;
		} else {
			this.bit = 2;
		}
	}
}
