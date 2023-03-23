package projeto3d;

import javax.vecmath.*;
import javax.media.j3d.*;

public class Diamante extends Shape3D {
		public Diamante(Color3f cor) {
			
			int[] stripIndexCounts = {4, 4}; 
			
			IndexedTriangleStripArray itsa = new IndexedTriangleStripArray(6,
			  GeometryArray.COORDINATES, 8, stripIndexCounts);
			
			Point3f[] coords = new Point3f[6];
			coords[0] = new Point3f(0f, 2.5f, 0f);
			coords[1] = new Point3f(5f, 5f, 0f);
			coords[2] = new Point3f(10f, 2.5f, 0f);
			coords[3] = new Point3f(5f, 0f, 0f);
			coords[4] = new Point3f(2.5f, 2.5f, 0f);
			coords[5] = new Point3f(7.5f, 2.5f, 0f);
			
			Appearance apDmt = new Appearance();
			apDmt.setColoringAttributes(new ColoringAttributes(cor, ColoringAttributes.FASTEST));
			
			itsa.setCoordinates(0, coords);
			int[] indices = {0, 3, 1, 2, 4, 3, 1, 5};
			itsa.setCoordinateIndices(0, indices);
			this.setGeometry(itsa);			
			this.setAppearance(apDmt);
		}
}
