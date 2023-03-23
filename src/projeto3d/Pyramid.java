package projeto3d;

import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;

public class Pyramid extends Shape3D {
	public Pyramid() {
		GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
		Point3d[] vertices = { new Point3d(2, 0, 2), new Point3d(2, 0, 6),
		new Point3d(6, 0, 6), new Point3d(6, 0, 2), new Point3d(4, 6, 4)};
		int[] indices = {0,1,2,3, 0,1,4, 1,2,4, 2,3,4, 3,0,4};
		gi.setCoordinates(vertices);
		gi.setCoordinateIndices(indices);
		int[] stripCounts = {4,3,3,3,3};
		gi.setStripCounts(stripCounts);
		
		NormalGenerator ng = new NormalGenerator();
	    ng.generateNormals(gi);
	    
	    this.setGeometry(gi.getGeometryArray());
	}
}
