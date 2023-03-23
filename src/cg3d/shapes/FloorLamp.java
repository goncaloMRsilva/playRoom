package cg3d.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;


public class FloorLamp extends Group {
   public FloorLamp(Appearance app) {
	   
	   // Base
	   Cylinder base = new Cylinder(0.3f, 0.05f, Cylinder.GENERATE_NORMALS, 30, 1, app);
	   Transform3D tr = new Transform3D();
	   tr.set(new Vector3d(0, 0.025, 0));
	   TransformGroup tg = new TransformGroup(tr);
	   tg.addChild(base);
	   this.addChild(tg);
	   
	   // Body
	   Cylinder body = new Cylinder(0.025f, 1.5f, Cylinder.GENERATE_NORMALS, app);
	   tr = new Transform3D();
	   tr.set(new Vector3d(0, 0.75, 0));
	   tg = new TransformGroup(tr);
	   tg.addChild(body);
	   this.addChild(tg);
	   
	   // Lampshade
        	   
	      
   }
}
