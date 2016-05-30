package gui;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

public class ProgramGrid {
	
	final float linesize = 0.005f;
	
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
	
	private TransformGroup drawOrthogonalLine(int plane, float pos1, float pos2, Appearance a){
		//plane 1=XY, plane 2=YZ, plane 3=XZ
		// Create a new Transform Group and apply a transformationss
		TransformGroup nodeTrans = new TransformGroup();
		Vector3f vector = null;
		switch(plane){
			case 0:
				vector = new Vector3f(pos1 * sidelength, pos2 * sidelength, 0);				
				break;
			case 1:
				vector = new Vector3f(0, pos1 * sidelength, pos2 * sidelength);
				break;
			case 2:
				vector = new Vector3f(pos1 * sidelength, 0, pos2 * sidelength);
				break;
		}
		Transform3D transform = new Transform3D();
		transform.setTranslation(vector);
		
		// Add the transform to the new group
		nodeTrans.setTransform(transform);
		
		// Add the new cube to the group, and the new group to the root
		switch(plane){
			case 0:
				nodeTrans.addChild(new Box(linesize, linesize, sidelength, a));
				break;
			case 1:
				nodeTrans.addChild(new Box(sidelength, linesize, linesize, a));
				break;
			case 2:
				nodeTrans.addChild(new Box(linesize, sidelength, linesize, a));
				break;
		}
		return nodeTrans;
	}
	
	public BranchGroup getBranchGroup() {
		// Create the root node of the content branch
		BranchGroup nodeRoot = new BranchGroup();

		// Create the TransformGroup node, which is writable to support
		// animation, and add it under the root

		// Render as a wireframe
		Appearance ap = new Appearance();
	    PolygonAttributes polyAttrbutes = new PolygonAttributes();
	    polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE);
	    ap.setPolygonAttributes(polyAttrbutes);
		
	    // Given the dimensions, draw lines which go perpendicular to the planes
	    
	    //XY plane
	    for(int x = -1; x < xdim; x++) {
		    for(int y = -1; y < ydim; y++) {
				nodeRoot.addChild(drawOrthogonalLine(0, x, y, ap));
		    }	    	
	    }
	    
	    //YZ plane
	    for(int y = -1; y < ydim; y++) {
			for(int z = -1; z < zdim; z++) {
				nodeRoot.addChild(drawOrthogonalLine(1, y, z, ap));
			}
	    }

	    //XZ plane
	    for(int x = -1; x < xdim; x++) {
			for(int z = -1; z < zdim; z++) {
				nodeRoot.addChild(drawOrthogonalLine(2, x, z, ap));
			}
	    }

		// Compile to perform optimizations on this content branch.
		nodeRoot.compile();

		return nodeRoot;
	}
	
}
