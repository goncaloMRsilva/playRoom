package projeto3d;

import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;

public class MachineGames extends Shape3D {
	public MachineGames() {
		
		GeometryInfo linhas = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
		
		
		Point3f[] coords = { new Point3f(0f, 0f, 0f), new Point3f(7.5f, 0f, 0f),
		 new Point3f(8f, 0f, 1f), new Point3f(8f, 0f, 4.5f),
		 new Point3f(7.5f, 0f, 4.5f), new Point3f(6.5f, 0f, 2.5f),
		 new Point3f(2.7f, 0f, 6f), new Point3f(0f, 0f, 4.5f),
		 
		 new Point3f(0f, 5f, 0f), new Point3f(7.5f, 5f, 0f),
		 new Point3f(8f, 5f, 1f), new Point3f(8f, 5f, 4.5f),
		 new Point3f(7.5f, 5f, 4.5f), new Point3f(6.5f, 5f, 2.5f),
		 new Point3f(2.7f, 5f, 6f), new Point3f(0f, 5f, 4.5f),
		};
		
		
		int[] indicesMachine = {0,1,2,3,4,5,6,7,  0,1,9,8,  1,2,10,11,  
				2,3,11,10,  3,4,12,11,  4,5,13,12,  5,6,14,13,  6,7,15,14,
				7,0,8,15,  8,9,10,11,12,13,14,15};
		linhas.setCoordinates(coords);
		linhas.setCoordinateIndices(indicesMachine);
		int[] stripCountsMachine = {8,4,4,4,4,4,4,4,4,8};
		linhas.setStripCounts(stripCountsMachine);
		
		NormalGenerator ngMachine = new NormalGenerator();
	    ngMachine.generateNormals(linhas);
	    
	    this.setGeometry(linhas.getGeometryArray());
	}
}
