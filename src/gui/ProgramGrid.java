package gui;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

public class ProgramGrid {

	static int xdim, ydim, zdim;
	static float sidelength;
	String program;
	static Point3d boxLoc = new Point3d(0, 0, 0);
	static TransformGroup transformGroup = new TransformGroup();
	static Transform3D transform = new Transform3D();
	// Although only one string is used for input, it can be broken down based on the other dimensions.
	
	@SuppressWarnings("static-access")
	public ProgramGrid(int xdim, int ydim, int zdim, float sidelength, String program) {
		this.xdim = xdim;
		this.ydim = ydim;
		this.zdim = zdim;
		this.sidelength = sidelength;
		this.program = program;
	}

	private TransformGroup drawOrthogonalLine(int plane, float pos1, float pos2, int linewidth, Appearance a) {
		// plane 1=XY, plane 2=YZ, plane 3=XZ
		// Create a new Transform Group and apply a transformation
		TransformGroup nodeTrans = new TransformGroup();
		
		LineArray lineArray = new LineArray(2,LineArray.COORDINATES);
		LineAttributes lineAttributes = new LineAttributes(linewidth, LineAttributes.PATTERN_SOLID, true);
		a.setLineAttributes(lineAttributes);
		Point3f[] p = new Point3f[2];
		switch(plane){
		case 0:
			p[0] = new Point3f(pos1 * sidelength - sidelength * xdim / 2, pos2 * sidelength - sidelength * ydim / 2, -sidelength * zdim / 2);
			break;
		case 1:
			p[0] = new Point3f(-sidelength * xdim / 2, pos1 * sidelength - sidelength * ydim / 2, pos2 * sidelength - sidelength * zdim / 2);
			break;
		case 2:
			p[0] = new Point3f(pos1 * sidelength - sidelength * xdim / 2, -sidelength * ydim / 2, pos2 * sidelength - sidelength * zdim / 2);
			break;
		}

		// Add the new cube to the group, and the new group to the root
		switch(plane){
		case 0:
			p[1] =  new Point3f(pos1 * sidelength - sidelength * xdim / 2, pos2 * sidelength- sidelength * ydim / 2, sidelength * (float)zdim - sidelength * zdim / 2);
			break;
		case 1:
			p[1] = new Point3f(sidelength * (float)xdim - sidelength * xdim / 2, pos1 * sidelength - sidelength * ydim / 2, pos2 * sidelength - sidelength * zdim / 2);
			break;
		case 2:
			p[1] = new Point3f(pos1 * sidelength - sidelength * xdim / 2, sidelength * (float)ydim - sidelength * ydim / 2, pos2 * sidelength - sidelength * zdim / 2);
			break;
		}
		lineArray.setCoordinates(0, p);
		nodeTrans.addChild(new Shape3D(lineArray, a));
		return nodeTrans;
	}
	
	public static TransformGroup highlightBox(int x, int y, int z, float transparency, Color3f color) {

		// Check to make sure that the coordinates are in the prism
		if(x > xdim-1 || y > ydim-1 || z > zdim-1 || x < 0 || y < 0 || z < 0) {
			System.out.println("Cannot highlight box (invalid coordinates)");
			return null;
		}

		// Initialize the transform group
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		//Set up color
		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
		
		// Set up appearance
		Appearance ap = new Appearance();
		ap.setColoringAttributes(ca);
		TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.NICEST, transparency);
		ap.setTransparencyAttributes(ta);

		// Set up box
		Box b = new Box(.1f, .1f, .1f, ap);

		// Set offset based on input
		Vector3f vector = new Vector3f(
				sidelength / 2 + sidelength * x - sidelength * xdim / 2,
				sidelength / 2 + sidelength * y - sidelength * ydim / 2,
				sidelength / 2 + sidelength * z - sidelength * zdim / 2);

		// Add offset
		transform.setTranslation(vector);

		// Add the transform to the new group
		transformGroup.setTransform(transform);

		// Add box
		transformGroup.addChild(b);
		return transformGroup;
	}
	
	public static void moveBox(int key) {
		switch(key)
		{
		// Left
		case 37:
			if(boxLoc.x > 0)
				boxLoc.x--;
			break;
		// Up
		case 38:
			if(boxLoc.y < ydim-1)
				boxLoc.y++;
			break;
		// Right
		case 39:
			if(boxLoc.x < xdim-1)
				boxLoc.x++;
			break;
		// Down
		case 40:
			if(boxLoc.y > 0)
				boxLoc.y--;
			break;
		// Close
		case -1:
			if(boxLoc.z > 0)
				boxLoc.z--;
			break;
		// Far
		case -2:
			if(boxLoc.z < zdim-1)
				boxLoc.z++;
			break;
		}
		// Set offset based on input
		Vector3f vector = new Vector3f(
				sidelength / 2 + sidelength * (int)boxLoc.x - sidelength * xdim / 2,
				sidelength / 2 + sidelength * (int)boxLoc.y - sidelength * ydim / 2,
				sidelength / 2 + sidelength * (int)boxLoc.z - sidelength * zdim / 2);
		
		transform.setTranslation(vector);
		
		// Add offset
		transformGroup.setTransform(transform);
	}
	

	public BranchGroup getBranchGroup(int linewidth) {
		// Create the root node of the content branch
		BranchGroup rootGroup = new BranchGroup(), lineGroup = new BranchGroup(), boxGroup = new BranchGroup();

		// Render as a wireframe
		Appearance ap = new Appearance();
		PolygonAttributes polyAttrbutes = new PolygonAttributes();
		polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE);
		ap.setPolygonAttributes(polyAttrbutes);

		// Given the dimensions, draw lines which go perpendicular to the planes

		// XY plane
		for(int x = 0; x <= xdim; x++) {
			for(int y = 0; y <= ydim; y++) {
				lineGroup.addChild(drawOrthogonalLine(0, x, y, linewidth, ap));
			}
		}

		// YZ plane
		for(int y = 0; y <= ydim; y++) {
			for(int z = 0; z <= zdim; z++) {
				lineGroup.addChild(drawOrthogonalLine(1, y, z, linewidth, ap));
			}
		}

		// XZ plane
		for(int x = 0; x <= xdim; x++) {
			for(int z = 0; z <= zdim; z++) {
				lineGroup.addChild(drawOrthogonalLine(2, x, z, linewidth, ap));
			}
		}
		
		// 0,0,0 is the farthest bottom left corner
		boxGroup.addChild(highlightBox((int)boxLoc.x, (int)boxLoc.y, (int)boxLoc.z, 0.5f, new Color3f(0.0f, 0.0f, 1.0f)));
		
		// Add the subgroups
		rootGroup.addChild(lineGroup);
		rootGroup.addChild(boxGroup);
		
		// Compile to perform optimizations on this content branch.
		rootGroup.compile();

		return rootGroup;
	}

}
