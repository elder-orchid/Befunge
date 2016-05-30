package gui;
import javax.media.j3d.Appearance;
import com.sun.j3d.utils.geometry.Box;

public class Cube extends Box{
	// Simple class for custom cubes
	public Cube(float sidelength, Appearance appearance) {
		super(sidelength, sidelength, sidelength, appearance);
	}
	
	// Custom overrides will likely be necessary in the near future 
}
