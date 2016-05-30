package gui;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import javax.media.j3d.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

// This class is the main handler for screen content
@SuppressWarnings("serial")
public class Screen extends JFrame {
	public Screen() {
		// Create the universe!
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		SimpleUniverse universe = new SimpleUniverse(canvas);

		// Add mouse control over the camera
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);

		// Set up the viewer looking into the scene.
		universe.getViewingPlatform().setNominalViewingTransform();

		// Create and set up the Program Grid
		ProgramGrid progGrid = new ProgramGrid(2, 3, 3, 0.2f, "A");

		// Get the content branch from the Program Grid and add it to the universe
		BranchGroup scene = progGrid.getBranchGroup();
		universe.addBranchGraph(scene);

		// Configure this JFrame
		setSize(500, 500);

		// Ha its like Befunge but like with 3D
		setTitle("B3DUNGE");
		// Nevermind...

		setVisible(true);
		
		// Add listener to exit when window is closed
		addWindowStateListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.out.println("EXIT");
					System.exit(0);
				}
			}
	    );
	}

	// Entry main method to invoke the constructor on the event dispatcher thread
	public static void main(String args[]) {
		// Invoke later my ass
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Screen();
			}
		});
	}
}