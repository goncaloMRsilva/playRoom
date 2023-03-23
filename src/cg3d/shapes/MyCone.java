package cg3d.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class MyCone extends Shape3D {
	public MyCone(int n, float h, float w, Appearance app) {
		int[] stripVertexCounts = { n + 2 }; // 1 strip width n+2 vertices
		TriangleFanArray tfa = new TriangleFanArray(n + 2, GeometryArray.COORDINATES, stripVertexCounts);
		Point3f apex = new Point3f(0, h, 0);
		tfa.setCoordinate(0, apex);
		for (int ii = 0; ii <= n; ii++) {
			float x = (float) (w*Math.cos(-ii * 2 * Math.PI / n));
			float z = (float) (w*Math.sin(-ii * 2 * Math.PI / n));
			Point3f p = new Point3f(x, 0, z);
			tfa.setCoordinate(ii + 1, p);
		}
		
		GeometryInfo gi = new GeometryInfo(tfa);
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);
		
		this.setGeometry(gi.getGeometryArray());
		this.setAppearance(app);
	}
}
