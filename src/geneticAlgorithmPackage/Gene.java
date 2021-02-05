package geneticAlgorithmPackage;

import java.util.Random;

public class Gene {

	private int bit;

	public Gene() {
		Random random = new Random();
		this.bit = random.nextInt(2);
		updateColor();
	}

	public void updateColor() {
		if (this.getBit() == 0) {
		} else {
		}
	}

	public void changeBit() {
		if (this.getBit() == 0) {
			this.bit = 1;
		} else {
			this.bit = 0;
		}
		updateColor();
	}

	public int getBit() {
		return bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}

//	private void draw(Graphics2D g2) {
//		g2.setColor(this.color);
//		g2.fillRect(0, 0, 5, 5);
//	}
}
