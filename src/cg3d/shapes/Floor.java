package cg3d.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class Floor extends Shape3D {

	public Floor(int divisions, float min, float max, Color3f color1, Color3f color2, boolean fill) {

		// Range of X
		int m = divisions;
		float a = min;
		float b = max;
		float divX = (b - a) / m;

		// Range of Z
		int n = divisions;
		float c = min;
		float d = max;
		float divZ = (d - c) / n;

		int totalPts = m * n * 4;

		Point3f[] pts = new Point3f[totalPts];
		Color3f[] col = new Color3f[totalPts];

		int index = 0;
		boolean invert = true;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {

				// vertex 1
				float x = a + i * divX;
				float y = 0;
				float z = c + j * divZ;
				pts[index] = new Point3f(x, y, z);
				col[index] = (invert ? color1 : color2);
				index++;

				// vertex 2
				x = a + i * divX;
				z = c + (j + 1) * divZ;
				pts[index] = new Point3f(x, y, z);
				col[index] = (invert ? color1 : color2);
				index++;

				// vertex 3
				x = a + (i + 1) * divX;
				z = c + (j + 1) * divZ;
				pts[index] = new Point3f(x, y, z);
				col[index] = (invert ? color1 : color2);
				index++;

				// vertex 4
				x = a + (i + 1) * divX;
				z = c + j * divZ;
				pts[index] = new Point3f(x, y, z);
				col[index] = (invert ? color1 : color2);
				index++;

				invert = !invert;
			}
			if (divisions % 2 == 0)
				invert = !invert;
		}

		QuadArray geom = null;

		if (fill) {
			geom = new QuadArray(totalPts, QuadArray.COORDINATES | QuadArray.COLOR_3);
			geom.setCoordinates(0, pts);
			geom.setColors(0, col);
		} else {
			geom = new QuadArray(totalPts, QuadArray.COORDINATES);
			geom.setCoordinates(0, pts);
		}

		Appearance app = new Appearance();

		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);

		if (fill) {
			pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
		} else {
			pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		}

		ColoringAttributes ca = new ColoringAttributes(color1, ColoringAttributes.SHADE_FLAT);

		LineAttributes la = new LineAttributes();
		la.setLineAntialiasingEnable(true);
		
		app.setPolygonAttributes(pa);
		app.setColoringAttributes(ca);
		app.setLineAttributes(la);

		this.setGeometry(geom);
		this.setAppearance(app);

	}
}
