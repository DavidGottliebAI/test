package geneticAlgorithmPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class Gene { // may need refactoring
	
	private static final Color oneBit = Color.GREEN;
	private static final Color zeroBit = Color.BLACK;
	
	private int bit;
	private Color color;
	
	public Gene() {
		Random random = new Random();
		this.bit = random.nextInt(2);
		updateColor();
	}
	
	public void updateColor() {
		if (this.getBit() == 0) {
			this.color = oneBit;
		} else {
			this.color = zeroBit;
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
	
//	private void draw(Graphics2D g2) {
//		g2.setColor(this.color);
//		g2.fillRect(0, 0, 5, 5);
//	}
}
