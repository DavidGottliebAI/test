package geneticAlgorithmPackage;

/**
 * 
 * @author oblaznjc and gottlijd
 * 
 *         Purpose: Visualize editable viewer, population, evolutionary graph,
 *         and best chromosome Restriction: <br>
 *         Can only visualize, has no effect on what is drawn For example: <br>
 *         ViewerMain viewerMain = new ViewerMain()
 *
 */
public class ViewerMain {

	/**
	 * ensures: runs the evolutionary simulator
	 */
	public static void main(String[] args) {
		handleOpenAll();
	}

	/**
	 * ensures: opens all viewers
	 */
	public static void handleOpenAll() {
		EditableViewer editableViewer = new EditableViewer();
		PopulationViewer populationViewer = new PopulationViewer();
		EvolutionViewer evolutionViewer = new EvolutionViewer(editableViewer);
	}

}
