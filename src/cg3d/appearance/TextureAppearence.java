package cg3d.appearance;


import java.awt.Component;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;


public class TextureAppearence extends Appearance{
	ImageComponent2D image = null;
	
	public TextureAppearence(Component observer, String file, Material material, boolean generate) {
	
		URL filename = getClass().getClassLoader().getResource(file);
		TextureLoader loader = new TextureLoader(filename, observer);
		image = loader.getImage();
		if (image == null) {
			System.out.println("can't find texture file.");
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		this.setTexture(texture);

		// combine texture and lighting
		TextureAttributes texatt = new TextureAttributes();
		texatt.setTextureMode(TextureAttributes.COMBINE);
		this.setTextureAttributes(texatt);
		this.setMaterial(material);

		if (generate) {
			TexCoordGeneration tcg = new TexCoordGeneration();
			tcg.setGenMode(TexCoordGeneration.OBJECT_LINEAR);
			this.setTexCoordGeneration(tcg);
		}
	}
	
	public int getImageWidth() {
		return image.getWidth();
	}
	
	public int getImageHeight() {
		return image.getHeight();
	}
}
