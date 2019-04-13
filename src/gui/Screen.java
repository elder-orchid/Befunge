package gui;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import javax.media.j3d.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

// This class is the main handler for screen content
@SuppressWarnings("serial")
public class Screen extends JFrame {
	ProgramGrid progGrid;
	FileFilter bfFilter = new FileFilter() {
		public String getDescription() {
			return "3D Befunge Files (*.bf3)";
		}
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			} else {
				String filename = f.getName().toLowerCase();
				return filename.endsWith(".bf3");
			}
		}
	};

	public Screen(int x, int y, int z) {
		// Config
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		// Canvas for 3d stuff
		Canvas3D canvas = new Canvas3D(config);

		// Create the universe!
		SimpleUniverse universe = new SimpleUniverse(canvas);

		// Add mouse control over the camera
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);

		// Set up the viewer looking into the scene.
		universe.getViewingPlatform().setNominalViewingTransform();

		// Create and set up the Program Grid
		progGrid = new ProgramGrid(x, y, z, 0.2f, "A");

		// Get the content branch from the Program Grid and add it to the universe
		// Input is to specify line width in pixels
		BranchGroup scene = progGrid.getBranchGroup(5);
		universe.addBranchGraph(scene);

		// Panel/gui
		JPanel controlPanel = new JPanel(new FlowLayout());
		JButton newP = new JButton("New Program"), saveP = new JButton("Save Program"), loadP = new JButton("Load Program");
		newP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New program");
				//Discard current board string
				progGrid.b.board = new char[progGrid.b.board.length][progGrid.b.board[0].length][progGrid.b.board[0][0].length];
			}
		});
		saveP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save program");
				save();
			}
		});
		loadP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Load program");
				open();
			}
		});
		controlPanel.add(newP);
		controlPanel.add(saveP);
		controlPanel.add(loadP);

		// Container to hold both
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(controlPanel, BorderLayout.NORTH);

		// Configure this JFrame
		setSize(500, 500);
		setTitle("BEFUNG3D");
		setVisible(true);

		// Add key listener
		universe.getCanvas().addKeyListener(new KeyHandler(progGrid));

		// Exit when window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Entry main method to invoke the constructor on the event dispatcher thread
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Screen(3, 4, 5);
			}
		});
	}

	// File related tom-foolery
	public void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(bfFilter);
		if (fileChooser.showOpenDialog(Screen.this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// Load from file
			try {
				@SuppressWarnings("unused")
				String contents = new String(Files.readAllBytes(file.toPath()));
				// TODO convert output into 3d char array
			} catch (IOException e) {}
		}
	}

	public void save() {
		System.out.println(progGrid.b.toString());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(bfFilter);
		if (fileChooser.showSaveDialog(Screen.this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// save to file
			try(PrintWriter out = new PrintWriter(file)){
				out.println(progGrid.b.toString());
			} catch(Exception e) {}
		}
	}

}