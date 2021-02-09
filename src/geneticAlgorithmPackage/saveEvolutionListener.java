package geneticAlgorithmPackage;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class saveEvolutionListener implements ActionListener {
	
	private EvolutionViewer evolutionViewer;
	private JPanel buttonGrid;
	private File selectedFile;

	public saveEvolutionListener(EvolutionViewer evolutionViewer, JPanel buttonGrid) {
		this.evolutionViewer = evolutionViewer;
		this.buttonGrid = buttonGrid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
				+ "git" + System.getProperty("file.separator") + "GeneticAlgorithm"
				+ System.getProperty("file.separator") + "evolutionRepo")); // professor does not have access
		int result = fileChooser.showOpenDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.selectedFile = fileChooser.getSelectedFile();
		}
		
		
		BufferedImage imagebuf = new BufferedImage(this.buttonGrid.getWidth(), this.buttonGrid.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = imagebuf.createGraphics();
		this.buttonGrid.paintAll(g2);
		try {
			if (ImageIO.write(imagebuf, "png", new File(this.selectedFile + "output_image.png")))
            {
                System.out.println("-- saved");
            }
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
	}

}
