package geneticAlgorithmPackage;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

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
		BufferedImage imagebuf = null;
		try {
	        imagebuf = new Robot().createScreenCapture(this.buttonGrid.getBounds());
	    } catch (AWTException e1) {
	        e1.printStackTrace();
	    }
		Graphics2D graphics2D = imagebuf.createGraphics();
		this.buttonGrid.paint(graphics2D);
	    try {
	        ImageIO.write(imagebuf,"jpeg", new File("\\save1.jpeg"));
	    } catch (Exception e2) {
	        System.out.println("error");
	    }
	}

}
