package geneticAlgorithmPackage;

import java.util.Random;

public class Gene {

	protected int bit;

	/**
	 * ensures: constructs Gene and instantiates with a random bit
	 */
	public Gene() {
		Random random = new Random();
		this.bit = random.nextInt(2);
	}

	/**
	 * ensures: constructs Gene and instantiates with a passed in bit
	 */
	public Gene(int bit) {
		this.bit = bit;
	}

	/**
	 * ensures: flips bit to opposite of it's current state
	 */
	public void changeBit() {
		if (this.getBit() == 0) {
			this.bit = 1;
		} else {
			this.bit = 0;
		}
	}

	public int getBit() {
		return this.bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}
}
