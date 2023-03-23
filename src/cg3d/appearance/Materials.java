package cg3d.appearance;

import javax.media.j3d.Material;

public class Materials extends Material {
	public final static int BRASS = 0;
	public final static int BRONZE = 1;
	public final static int GOLD = 2;
	public final static int PEWTER = 3;
	
	public Materials(int type) {
		switch (type) {
		case BRASS:
			this.setAmbientColor(0.329412f, 0.223529f, 0.027451f);
			this.setDiffuseColor(0.790392f, 0.568627f, 0.113725f);
			this.setSpecularColor(0.992157f, 0.941176f, 0.807843f);
			this.setShininess(27.8974f);
			break;
		case BRONZE:
			this.setAmbientColor(0.2125f, 0.1275f, 0.054f);
			this.setDiffuseColor(0.714f, 0.4284f, 0.18144f);
			this.setSpecularColor(0.393548f, 0.271906f, 0.166721f);
			this.setShininess(25.6f);
			break;
		case GOLD:
			this.setAmbientColor(0.24725f, 0.1995f, 0.0745f);
			this.setDiffuseColor(0.75164f, 0.60648f, 0.22648f);
			this.setSpecularColor(0.628281f, 0.555802f, 0.366065f);
			this.setShininess(51.2f);
			break;
		case PEWTER:
			this.setAmbientColor(0.105882f, 0.058824f, 0.113725f);
			this.setDiffuseColor(0.427451f, 0.470588f, 0.541176f);
			this.setSpecularColor(0.333333f, 0.333333f, 0.521569f);
			this.setShininess(9.84615f);
			break;
		}
	}

}
