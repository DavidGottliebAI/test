package geneticAlgorithmPackage;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

public class Gene { // may need refactoring

	private static final Color oneBit = Color.GREEN;
	private static final Color zeroBit = Color.BLACK;
	private Color color;
	private int bit;

	/**
	 * ensures: constructs Gene and instantiates with a random bit
	 */
	public Gene() {
		Random random = new Random();
		this.bit = random.nextInt(2);
		updateColor();
	}

	/**
	 * ensures: constructs Gene and instantiates with a passed in bit
	 */
	public Gene(int bit) {
		this.bit = bit;
		updateColor();
	}

	/**
	 * ensures: flips bit color based on bit color
	 */
	public void updateColor() {
		if (this.getBit() == 0) {
			this.color = zeroBit;
		} else {
			this.color = oneBit;
		}
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
		updateColor();
	}

	public int getBit() {
		return this.bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}

//	private void draw(Graphics2D g2) {
//		g2.setColor(this.color);
//		g2.fillRect(0, 0, 5, 5);
//	}
}
