package projeto3d;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3f;

public class KeyControl extends Behavior {
		
	private TransformGroup moveTg = null;
	private Node node = null;
	private WakeupCondition wakeupCondition = null;
	boolean collision = false;
	int lastKey;
	
	
	public KeyControl(TransformGroup tg, Node n) {
		// The constructor is used to pass to the behavior the objects that it needs
		moveTg = tg;
		node = n;
	}
	
	
	
	@Override
	public void initialize() {
		WakeupCriterion[] events = new WakeupCriterion[4];
		events[0] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		events[1] = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
		events[2] = new WakeupOnCollisionEntry(node, WakeupOnCollisionEntry.USE_GEOMETRY);
		events[3] = new WakeupOnCollisionExit(node, WakeupOnCollisionExit.USE_GEOMETRY);

		// The wakeup condition is a combination of 4 wakeup criterion
		wakeupCondition = new WakeupOr(events);
		wakeupOn(wakeupCondition);
	
	}
	

	@Override
	public void processStimulus(Enumeration criteria) {
		WakeupCriterion wakeupCriterion;
		AWTEvent[] events;

		while (criteria.hasMoreElements()) {
			// Get each wakeup criterion
			wakeupCriterion = (WakeupCriterion) criteria.nextElement();

			// Find its type and process it
			if (wakeupCriterion instanceof WakeupOnAWTEvent) {
				events = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();

				for (int i = 0; i < events.length; i++) {
					if (events[i].getID() == KeyEvent.KEY_PRESSED) {
						keyPressed((KeyEvent) events[i]);
					} else if (events[i].getID() == KeyEvent.KEY_RELEASED) {
						// Not implemented in this example
					}
				}
			} else if (wakeupCriterion instanceof WakeupOnCollisionEntry) {
				collision = true;
			} else if (wakeupCriterion instanceof WakeupOnCollisionExit) {
				collision = false;
			}
		}

		wakeupOn(wakeupCondition);
	
	}

	private void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		
		
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			if (!collision || (collision && lastKey != KeyEvent.VK_LEFT))
				doRotation(Math.toRadians(1.0));
			break;
		case KeyEvent.VK_RIGHT:
			if (!collision || (collision && lastKey != KeyEvent.VK_RIGHT))
				doRotation(Math.toRadians(-1.0));
			break;
		case KeyEvent.VK_UP:
			if (!collision || (collision && lastKey != KeyEvent.VK_UP))
				doTranslation(new Vector3f(0f, 0f, -0.01f));
			break;
		case KeyEvent.VK_DOWN:
			if (!collision || (collision && lastKey != KeyEvent.VK_DOWN))
				doTranslation(new Vector3f(0f, 0f, 0.01f));
			break;
		}

		lastKey = keyCode;
	
	}

	private void doRotation(double t) {
		// Standard code to add a transformation to the actual transformation of a
		// TransformGroup

		// Get old transformation of the TransformGroup
		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);

		// Create the new transformation to add
		Transform3D newTr = new Transform3D();
		newTr.rotY(t);

		// Add the new transformation by multiplying the transformations
		oldTr.mul(newTr);

		// Set the new transformation
		moveTg.setTransform(oldTr);
	}

	private void doTranslation(Vector3f v) {
		Transform3D oldTr = new Transform3D();
		moveTg.getTransform(oldTr);

		Transform3D newTr = new Transform3D();
		newTr.setTranslation(v);

		oldTr.mul(newTr);

		moveTg.setTransform(oldTr);

		// Example to get the object coordinates in the world coordinate system
		if (node != null) {
			Transform3D tr = new Transform3D();
			Vector3f position = new Vector3f();

			node.getLocalToVworld(tr);
			tr.get(position);

		}
	}
}
