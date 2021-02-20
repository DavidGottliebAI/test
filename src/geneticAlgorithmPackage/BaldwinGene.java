package geneticAlgorithmPackage;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Gene specifically designed to hold specific bit types from paper
 *         Exact same as Population, but with new type of bit
 *
 */

public class BaldwinGene extends Gene {

	private boolean twoGene = false;

	/**
	 * ensures: constructs Gene that reproduces bit strings used in paper
	 * 
	 * @param bit
	 */

	public BaldwinGene(int bit) {
		if (bit == 0) {
			this.bit = 0;
		} else if (bit == 1) {
			this.bit = 1;
		} else {
			this.bit = 2;
			setTwoGene(true);
		}
	}

	public boolean isTwoGene() {
		return twoGene;
	}

	public void setTwoGene(boolean twoGene) {
		this.twoGene = twoGene;
	}

}
