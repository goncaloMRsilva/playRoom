package projeto3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.Primitive.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;


public class objPerson extends BranchGroup {
	
	public objPerson(Appearance apPerson) {
		
		Box leg1 = new Box(0.05f, 0.18f, 0.05f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		Transform3D trP = new Transform3D();
		trP.setTranslation(new Vector3f(-1.4f, 0.2f, 0f));
		TransformGroup tgP = new TransformGroup(trP);
		tgP.addChild(leg1);
		this.addChild(tgP);
		
		Box leg2 = new Box(0.05f, 0.18f, 0.05f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.2f, 0.2f));
		tgP = new TransformGroup(trP);
		tgP.addChild(leg2);
		this.addChild(tgP);
				
		Box chest = new Box(0.13f, 0.2f, 0.25f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.58f, 0.1f));
		tgP = new TransformGroup(trP);
		tgP.addChild(chest);
		this.addChild(tgP);
		
		Box head = new Box(0.11f, 0.11f, 0.11f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.95f, 0.1f));
		tgP = new TransformGroup(trP);
		tgP.addChild(head);
		this.addChild(tgP);
		
		Cylinder neck = new Cylinder(0.06f, 0.1f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.8f, 0.1f));
		tgP = new TransformGroup(trP);
		tgP.addChild(neck);
		this.addChild(tgP);
		
		Cone arm1 = new Cone(0.06f, 0.4f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.5f, -0.3f));
		trP.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(-130)));
		tgP = new TransformGroup(trP);
		tgP.addChild(arm1);
		this.addChild(tgP);
		
		Cone arm2 = new Cone(0.06f, 0.4f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, apPerson);
		trP = new Transform3D();
		trP.setTranslation(new Vector3d(-1.4f, 0.5f, 0.5f));
		trP.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(130)));
		tgP = new TransformGroup(trP);
		tgP.addChild(arm2);
		this.addChild(tgP);
	}
}
