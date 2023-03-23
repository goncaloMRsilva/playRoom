package cg3d.shapes;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;

public class SimpleObj extends BranchGroup {

	public SimpleObj(float size, Appearance app) {

		Box obj1 = new Box(size, size, size, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		Transform3D tr = new Transform3D();
		tr.setTranslation(new Vector3f(0f, size + 0.001f, 0f));
		TransformGroup tg = new TransformGroup(tr);
		tg.addChild(obj1);
		this.addChild(tg);

		Cone obj2 = new Cone(size / 2, size, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		tr = new Transform3D();
		tr.setTranslation(new Vector3d(0, size, -size - size / 2));
		tr.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(-90)));
		tg = new TransformGroup(tr);
		tg.addChild(obj2);

		this.addChild(tg);

	}
}
