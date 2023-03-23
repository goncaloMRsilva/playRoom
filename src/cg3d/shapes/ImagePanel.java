package cg3d.shapes;

import java.awt.Component;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

import com.sun.j3d.utils.image.TextureLoader;

import cg3d.appearance.TextureAppearence;

public class ImagePanel extends Shape3D {

	public ImagePanel(Component c, String image, float size) {


		TextureAppearence app = new TextureAppearence(c, image, new Material(), false);

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.FASTEST);
		app.setTransparencyAttributes(ta);
		
		float r = app.getImageHeight() / (float)app.getImageWidth();
		
		QuadArray panel = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);
		panel.setCoordinate(0, new Point3f(-size/r, -size, 0));
		panel.setCoordinate(1, new Point3f(size/r, -size, 0f));
		panel.setCoordinate(2, new Point3f(size/r, size, 0f));
		panel.setCoordinate(3, new Point3f(-size/r, size, 0f));

		panel.setTextureCoordinate(0, 0, new TexCoord2f(0f, 0f));
		panel.setTextureCoordinate(0, 1, new TexCoord2f(1f, 0f));
		panel.setTextureCoordinate(0, 2, new TexCoord2f(1f, 1f));
		panel.setTextureCoordinate(0, 3, new TexCoord2f(0f, 1f));

		this.setGeometry(panel);
		this.setAppearance(app);
	}

	public void ImagePanel1(String image) {

		URL url = getClass().getClassLoader().getResource(image);
		TextureLoader loader = new TextureLoader(url, null);
		Texture texture = loader.getTexture();

		Appearance ap = new Appearance();

		TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.FASTEST);
		ap.setTransparencyAttributes(ta);
		ap.setTexture(texture);

		QuadArray panel = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);
		panel.setCoordinate(0, new Point3f(-1f, -1f, 0f));
		panel.setCoordinate(1, new Point3f(1f, -1f, 0f));
		panel.setCoordinate(2, new Point3f(1f, 1f, 0f));
		panel.setCoordinate(3, new Point3f(-1f, 1f, 0f));

		panel.setTextureCoordinate(0, 0, new TexCoord2f(0f, 0f));
		panel.setTextureCoordinate(0, 1, new TexCoord2f(1f, 0f));
		panel.setTextureCoordinate(0, 2, new TexCoord2f(1f, 1f));
		panel.setTextureCoordinate(0, 3, new TexCoord2f(0f, 1f));

		this.setGeometry(panel);
		this.setAppearance(ap);
	}
}
