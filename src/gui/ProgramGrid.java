package gui;
import java.awt.Font;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Geometry;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3b;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

import interp.Board;

public class ProgramGrid {

	Board b;
	float sidelength;
	String program;
	Point3d boxLoc = new Point3d(0, 0, 0);
	TransformGroup transformGroup = new TransformGroup();
	
	Transform3D transform = new Transform3D();
	Text3D[][][] letters;
	// Although only one string is used for input, it can be broken down based on the other dimensions.
	
	public ProgramGrid(int xdim, int ydim, int zdim, float sidelength, String program) {
		this.b = new Board(new int[] {xdim, ydim, zdim});
		this.letters = new Text3D[xdim][ydim][zdim];
		this.sidelength = sidelength;
		this.program = program;
	}

	private TransformGroup drawOrthogonalLine(int plane, float pos1, float pos2, int linewidth, Color3b color, Appearance a) {
		// plane 1=XY, plane 2=YZ, plane 3=XZ
		// Create a new Transform Group and apply a transformation
		TransformGroup nodeTrans = new TransformGroup();
		
		LineArray lineArray = new LineArray(2,LineArray.COORDINATES | LineArray.COLOR_3);
		LineAttributes lineAttributes = new LineAttributes(linewidth, LineAttributes.PATTERN_SOLID, true);
		a.setLineAttributes(lineAttributes);
		Point3f[] p = new Point3f[2];
		switch(plane){
		case 0:
			p[0] = new Point3f(pos1 * sidelength - sidelength * b.board.length / 2, pos2 * sidelength - sidelength * b.board[0].length / 2, -sidelength * b.board[0][0].length / 2);
			break;
		case 1:
			p[0] = new Point3f(-sidelength * b.board.length / 2, pos1 * sidelength - sidelength * b.board[0].length / 2, pos2 * sidelength - sidelength * b.board[0][0].length / 2);
			break;
		case 2:
			p[0] = new Point3f(pos1 * sidelength - sidelength * b.board.length / 2, -sidelength * b.board[0].length / 2, pos2 * sidelength - sidelength * b.board[0][0].length / 2);
			break;
		}

		// Add the new cube to the group, and the new group to the root
		switch(plane){
		case 0:
			p[1] =  new Point3f(pos1 * sidelength - sidelength * b.board.length / 2, pos2 * sidelength- sidelength * b.board[0].length / 2, sidelength * (float)b.board[0][0].length - sidelength * b.board[0][0].length / 2);
			break;
		case 1:
			p[1] = new Point3f(sidelength * (float)b.board.length - sidelength * b.board.length / 2, pos1 * sidelength - sidelength * b.board[0].length / 2, pos2 * sidelength - sidelength * b.board[0][0].length / 2);
			break;
		case 2:
			p[1] = new Point3f(pos1 * sidelength - sidelength * b.board.length / 2, sidelength * (float)b.board[0].length - sidelength * b.board[0].length / 2, pos2 * sidelength - sidelength * b.board[0][0].length / 2);
			break;
		}
		
		lineArray.setCoordinates(0, p);
		
		//colors
		lineArray.setColor(0, color);
		lineArray.setColor(1, color);

		nodeTrans.addChild(new Shape3D(lineArray, a));
		return nodeTrans;
	}
	
	public TransformGroup highlightBox(int x, int y, int z, float transparency, Color3f color) {

		// Check to make sure that the coordinates are in the prism
		if(x > b.board.length-1 || y > b.board[0].length-1 || z > b.board[0][0].length-1 || x < 0 || y < 0 || z < 0) {
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
		Box box = new Box(.1f, .1f, .1f, ap);

		// Set offset based on input
		Vector3f vector = new Vector3f(
				sidelength / 2 + sidelength * x - sidelength * b.board.length / 2,
				sidelength / 2 + sidelength * y - sidelength * b.board[0].length / 2,
				sidelength / 2 + sidelength * z - sidelength * b.board[0][0].length / 2);

		// Add offset
		transform.setTranslation(vector);

		// Add the transform to the new group
		transformGroup.setTransform(transform);

		// Add box
		transformGroup.addChild(box);
		return transformGroup;
	}
	
	public void moveBox(int key) {
		switch(key)
		{
		// Left
		case 37:
			if(boxLoc.x > 0)
				boxLoc.x--;
			break;
		// Up
		case 38:
			if(boxLoc.y < b.board[0].length-1)
				boxLoc.y++;
			break;
		// Right
		case 39:
			if(boxLoc.x < b.board.length-1)
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
			if(boxLoc.z < b.board[0][0].length-1)
				boxLoc.z++;
			break;
		}
		// Set offset based on input
		Vector3f vector = new Vector3f(
				sidelength / 2 + sidelength * (int)boxLoc.x - sidelength * b.board.length / 2,
				sidelength / 2 + sidelength * (int)boxLoc.y - sidelength * b.board[0].length / 2,
				sidelength / 2 + sidelength * (int)boxLoc.z - sidelength * b.board[0][0].length / 2);
		
		transform.setTranslation(vector);
		
		// Add offset
		transformGroup.setTransform(transform);
	}
	

	public BranchGroup getBranchGroup(int linewidth) {
		// Create the root node of the content branch
		BranchGroup rootGroup = new BranchGroup(), lineGroup = new BranchGroup(), boxGroup = new BranchGroup(), letterGroup = new BranchGroup();

		// Render as a wireframe
		Appearance ap = new Appearance();
		PolygonAttributes polyAttrbutes = new PolygonAttributes();
		polyAttrbutes.setCullFace(PolygonAttributes.CULL_NONE);
		ap.setPolygonAttributes(polyAttrbutes);

		// Given the dimensions, draw lines which go perpendicular to the planes

		//x==0, y==0
		{
			lineGroup.addChild(drawOrthogonalLine(0, 0, 0, linewidth, new Color3b((byte)255,(byte)0,(byte)0),ap));
		}
		// XY plane
		for(int x = 0; x <= b.board.length; x++) {
			for(int y = 0; y <= b.board[0].length; y++) {
				if(x != 0 || y != 0){
					lineGroup.addChild(drawOrthogonalLine(0, x, y, linewidth, new Color3b((byte)255,(byte)255,(byte)255), ap));
				}
			}
		}

		//y==0, z==0
		{
			lineGroup.addChild(drawOrthogonalLine(1, 0, 0, linewidth, new Color3b((byte)0,(byte)255,(byte)0),ap));
		}
		// YZ plane
		for(int y = 0; y <= b.board[0].length; y++) {
			for(int z = 0; z <= b.board[0][0].length; z++) {
				if(y != 0 || z != 0){
					lineGroup.addChild(drawOrthogonalLine(1, y, z, linewidth,new Color3b((byte)255,(byte)255,(byte)255), ap));
				}
			}
		}

		//y==0, z==0
		{
			lineGroup.addChild(drawOrthogonalLine(2, 0, 0, linewidth, new Color3b((byte)0,(byte)0,(byte)255),ap));
		}
		// XZ plane
		for(int x = 0; x <= b.board.length; x++) {
			for(int z = 0; z <= b.board[0][0].length; z++) {
				if(x != 0 || z != 0){
					lineGroup.addChild(drawOrthogonalLine(2, x, z, linewidth, new Color3b((byte)255,(byte)255,(byte)255), ap));
				}
			}
		}
		
		// 0,0,0 is the farthest bottom left corner
		boxGroup.addChild(highlightBox((int)boxLoc.x, (int)boxLoc.y, (int)boxLoc.z, 0.5f, new Color3f(0.8f, 0.0f, 0.8f)));
		
		for(int x = 0; x < b.board.length; x++) {
	    	for(int y = 0; y < b.board[0].length; y++) {
	    		for(int z = 0; z < b.board[0][0].length; z++) {
	    			letterGroup.addChild(drawText(x, y, z));
	    		}
	    	}
	    }
		
		// Add the subgroups
		rootGroup.addChild(lineGroup);
		rootGroup.addChild(boxGroup);
		rootGroup.addChild(letterGroup);
		
		// Compile to perform optimizations on this content branch.
		rootGroup.compile();

		return rootGroup;
	}
	
	public Shape3D drawText(int x, int y, int z) {
		// Initialize the appearance related variables
	    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Material m = new Material(white, white, white, white, 100.0f);
	    Appearance a = new Appearance();
	    m.setLightingEnable(true);
	    a.setMaterial(m);
	    Font3D f3d = new Font3D(new Font("TimesRoman", Font.PLAIN, 1), new FontExtrusion());

	    // Initialize each shape and text
	    letters[x][y][z] = new Text3D(f3d, b.board[x][y][z]+"", new Point3f(0,0,0));
	    letters[x][y][z].setCapability(Geometry.ALLOW_INTERSECT | Text3D.ALLOW_STRING_READ | Text3D.ALLOW_STRING_WRITE);
		Shape3D shape = new Shape3D();
		shape.setGeometry(letters[x][y][z]);
		shape.setAppearance(a);
		return shape;
	}
}
