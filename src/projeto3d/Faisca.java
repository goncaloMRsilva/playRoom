package projeto3d;

import javax.vecmath.*;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import javax.media.j3d.*;

public class Faisca extends Shape3D {
		public Faisca() {
		
			GeometryInfo linhas = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
			
			Point3f[] coords = { new Point3f(0f, 0f, 0f), new Point3f(0f, 3f, 2f),
					 new Point3f(0f, 3f, 0.3f), new Point3f(0f, 5f, 1.7f),
					 new Point3f(0f, 5f, 4.1f), new Point3f(0f, 3.5f, 3.5f),
					 new Point3f(0f, 3.5f, 4.5f),
					 
					 new Point3f(3f, 0f, 0f), new Point3f(3f, 3f, 2f),
					 new Point3f(3f, 3f, 0.3f), new Point3f(3f, 5f, 1.7f),
					 new Point3f(3f, 5f, 4.1f), new Point3f(3f, 3.5f, 3.5f),
					 new Point3f(3f, 3.5f, 4.5f)
					};
					
					
					int[] indicesFaisca = {0,1,2,3,4,5,6,  3,10,11,4,  11,12,5,4,
							5,12,13,6,  6,13,7,0,  0,7,8,1,  1,2,9,8,  2,9,10,3,
							7,8,9,10,11,12,13};
					
					linhas.setCoordinates(coords);
					linhas.setCoordinateIndices(indicesFaisca);
					int[] stripCountsFaisca = {7,4,4,4,4,4,4,4,7};
					linhas.setStripCounts(stripCountsFaisca);
					
					NormalGenerator ngFaisca = new NormalGenerator();
				    ngFaisca.generateNormals(linhas);
				    
				    this.setGeometry(linhas.getGeometryArray());
		}
}
