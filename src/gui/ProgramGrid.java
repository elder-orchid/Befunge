package gui;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class ProgramGrid {
	int xdim, ydim, zdim;
	float sidelength;
	String program;
	
	// Although only one string is used for input, it can be broken down based on the other dimensions.
	public ProgramGrid(int xdim, int ydim, int zdim, float sidelength, String program) {
		this.xdim = xdim;
		this.ydim = ydim;
		this.zdim = zdim;
		this.sidelength = sidelength;
		this.program = program;
	}
	
	public BranchGroup getBranchGroup() {
		// Create the root node of the content branch
		BranchGroup nodeRoot = new BranchGroup();

		// Create the TransformGroup node, which is writable to support
		// animation, and add it under the root

		// Render as a wireframe
		Appearance ap = new Appearance();
	    PolygonAttributes polyAttrbutes = new PolygonAttributes();
	    polyAttrbutes.setPolygonMode(PolygonAttributes.POLYGON_LINE);
	    polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE);
	    ap.setPolygonAttributes(polyAttrbutes);
		
	    // Given the dimensions, add cubes in all dimensions
	    for(int x = 0; x < xdim; x++) {
	    	for(int y = 0; y < ydim; y++) {
	    		for(int z = 0; z < zdim; z++) {
	    			// Create a new Transform Group and apply a transformation
	    			TransformGroup nodeTrans = new TransformGroup();
	    			Vector3f vector = new Vector3f(x * sidelength * 2, y * sidelength * 2, z * sidelength * 2);
	    			Transform3D transform = new Transform3D();
	    			transform.setTranslation(vector);
	    			
	    			// Add the transform to the new group
	    			nodeTrans.setTransform(transform);
	    			
	    			// Add the new cube to the group, and the new group to the root
	    			nodeTrans.addChild(new Cube(sidelength, ap));
	    			nodeRoot.addChild(nodeTrans);
	    			System.out.println("CUBE ADDED");
	    	    }
		    }
	    }

		// Compile to perform optimizations on this content branch.
		nodeRoot.compile();

		return nodeRoot;
	}
	
}
