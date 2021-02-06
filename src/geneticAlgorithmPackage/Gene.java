package geneticAlgorithmPackage;

import java.util.Random;


import javax.swing.JPanel;

public class Gene { // may need refactoring
	
	private static final Color oneBit = Color.GREEN;
	private static final Color zeroBit = Color.BLACK;
  private Color color;
	private int bit;

	public Gene() {
		Random random = new Random();
		this.bit = random.nextInt(2);
		updateColor();
  }
  
	public Gene(int bit) {
		this.bit = bit;
		updateColor();
	}

	public void updateColor() {
		if (this.getBit() == 0) {
      this.color = zeroBit;
		} else {
      this.color = oneBit;
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
