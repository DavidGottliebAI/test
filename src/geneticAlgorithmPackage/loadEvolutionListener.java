package geneticAlgorithmPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class loadEvolutionListener implements ActionListener {

	private final JFileChooser fileChooser = new JFileChooser();

	public loadEvolutionListener() {
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")
				+ "git" + System.getProperty("file.separator") + "GeneticAlgorithm"
				+ System.getProperty("file.separator") + "evolutionRepo"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
	}

	public void actionPerformed(ActionEvent arg0) {
		int returnValue = fileChooser.showOpenDialog(fileChooser);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				ImageIO.read(fileChooser.getSelectedFile());
				System.out.println("Worked");
			} catch (IOException e) {
				System.err.println("Didn't work");
			}
		}
	}
}
