package projeto3d;

import javax.vecmath.*;
import javax.media.j3d.*;

public class Moldura extends IndexedTriangleArray{
	public Moldura() {
		super(6, TriangleArray.COORDINATES | TriangleArray.NORMALS |
		          GeometryArray.COLOR_3, 6);
		
		setCoordinate(0, new Point3f(4f, 6f, 0f));
	    setCoordinate(1, new Point3f(2f, 1, 0f));
	    setCoordinate(2, new Point3f(6f, 1f, 0f));
	    setCoordinate(3, new Point3f(3.5f, 4f, 0f));
	    setCoordinate(4, new Point3f(4.5f, 4f, 0f));
	    setCoordinate(5, new Point3f(8f, 1, 0f));
	    
	    int[] coords = {0,1,2, 3,4,5};
	    setCoordinateIndices(0, coords);
	}
}
