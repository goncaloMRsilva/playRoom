package projeto3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Mesa extends Shape3D {
	public Mesa(Appearance appM) {
		
		IndexedQuadArray qaM = new IndexedQuadArray(36, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2, 60);
		
		Point3f[] verticesCoords = { new Point3f(0, 0, 0), new Point3f(0, 0, 1), new Point3f(1, 0, 1), new Point3f(1, 0, 3),
				new Point3f(0, 0, 3), new Point3f(0, 0, 4), new Point3f(4, 0, 4), new Point3f(4, 0, 3),
				new Point3f(3, 0, 3), new Point3f(3, 0, 1), new Point3f(4, 0, 1), new Point3f(4, 0, 0),
				new Point3f(0, 3, 0), new Point3f(0, 3, 1), new Point3f(1, 3, 1), new Point3f(1, 3, 3),
				new Point3f(0, 3, 3), new Point3f(0, 3, 4), new Point3f(4, 3, 4),
				new Point3f(4, 3, 3), new Point3f(3, 3, 3), new Point3f(3, 3, 1),
				new Point3f(4, 3, 1), new Point3f(4, 3, 0)};
		
		int[] coordIndices = {0, 12, 13, 1, 13, 1, 2, 14, 2, 3, 15, 14, 3, 4, 16, 15, 4, 16, 17, 5, 5, 6, 18, 17,
							18, 6, 7, 19, 19, 20, 8, 7, 8, 20, 21, 9, 9, 10, 22, 21, 22, 23, 11, 10, 11, 23, 12, 0,
							23, 12, 13, 22, 21, 14, 15, 20, 19, 16, 17, 18};
		
		qaM.setCoordinates(0, verticesCoords);
		qaM.setCoordinateIndices(0, coordIndices);

		TexCoord2f[] tex = { new TexCoord2f(0, 0), new TexCoord2f(1, 0), new TexCoord2f(1, 1),
				new TexCoord2f(3, 1), new TexCoord2f(3, 0), new TexCoord2f(4, 0),
				new TexCoord2f(4, 4), new TexCoord2f(3, 4), new TexCoord2f(3, 3),
				new TexCoord2f(1, 3), new TexCoord2f(1, 4), new TexCoord2f(0, 4) };
		
		int[] texIndices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		qaM.setTextureCoordinates(0, 0, tex);
		qaM.setTextureCoordinateIndices(0, 0, texIndices);

		GeometryInfo giMesa = new GeometryInfo(qaM);
		NormalGenerator ngM = new NormalGenerator();
		ngM.generateNormals(giMesa);

		this.setGeometry(giMesa.getGeometryArray());
		this.setAppearance(appM);
	}
}
