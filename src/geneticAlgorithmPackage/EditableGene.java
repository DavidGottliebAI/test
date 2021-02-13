package geneticAlgorithmPackage;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: <br>
 *         Creates gene button for editable viewer Restriction: <br>
 *         Can only be used for editable viewer For example: <br>
 *         EditableGene gene = new EditableGene()
 *
 */
public class EditableGene extends JButton {

	private static final Color oneBitBackground = Color.GREEN;
	private static final Color oneBitForeground = Color.BLACK;
	private static final Color zeroBitBackground = Color.BLACK;
	private static final Color zeroBitForeground = Color.GREEN;
	private int bit;

	/**
	 * ensures: creates gene with specific index and random bit number
	 * 
	 * @param index of gene
	 */
	public EditableGene(int index) {
		this.setFont(new Font("Helvetica", Font.BOLD, 10));
		Random random = new Random();
		this.bit = random.nextInt(2);
		updateColor();
		this.setText("" + index);
	}

	/**
	 * ensures: creates gene with specific index and specified bit number
	 * 
	 * @param index of gene, and bit number
	 */
	public EditableGene(int index, int bit) {
		this.setFont(new Font("Helevetica", Font.BOLD, 10));
		this.bit = bit;
		updateColor();
		this.setText("" + index);
	}

	public EditableGene(Gene gene, int index) {
		this.setFont(new Font("Helvetica", Font.BOLD, 10));
		this.bit = gene.getBit();
		updateColor();
		this.setText("" + index);
	}

	/**
	 * ensures: sets the color of the gene depending on its bit
	 */
	public void updateColor() {
		if (this.getBit() == 0) {
			this.setBackground(zeroBitBackground);
			this.setForeground(zeroBitForeground);
		} else {
			this.setBackground(oneBitBackground);
			this.setForeground(oneBitForeground);
		}
	}

	/**
	 * ensures: changes the bit of the gene
	 */
	public void changeBit() {
		if (this.getBit() == 0) {
			this.bit = 1;
		} else {
			this.bit = 0;
		}
		updateColor();
	}

	/**
	 * ensures: gets the gene's bit
	 * 
	 * @return gene's bit
	 */
	public int getBit() {
		return this.bit;
	}
}
