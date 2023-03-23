package projeto3d;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import cg3d.appearance.TextureAppearence;
import java.net.URL;
import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import cg3d.shapes.Floor;
import cg3d.shapes.ImagePanel;
import com.sun.j3d.utils.geometry.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.j3d.*;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.picking.*;


public class Projeto3D extends Frame implements MouseListener {
	
	public static void main(String[] args) {
		Frame frame = new Projeto3D();
		frame.setPreferredSize(new Dimension(800, 700));
		frame.setTitle("Game Room 3D");
		frame.pack();
		frame.setVisible(true);
	}
	
	
	//Variaveis globais
	BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), 15);
	BoundingSphere bounds1 = new BoundingSphere(new Point3d(8f, 3.5f, -0.9f), 0.3f);
	BoundingSphere bounds2 = new BoundingSphere(new Point3d(1.0f, 0.5f, -3.7f), 1f);
	BoundingSphere bounds3 = new BoundingSphere(new Point3d(-1.0f, 3.5f, -8.0f), 1f);
	
	
	PickCanvas pc = null;
	
	objPerson pessoa = null;
	
	BufferedImage[] images = new BufferedImage[3];
	
	GeometryArray geom = null;
	GeometryArray geom1 = null;
	GeometryArray geom2 = null;
	
	
	private Canvas3D cv;
	private Canvas3D offScreenCanvas;
	private View view;
	
	private TransformGroup rotacao = null;
	private TransformGroup scale = null;
	 	 
	JMenuBar menuBar;
	JMenu menu;
	 
	public Projeto3D() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
	    cv = new Canvas3D(gc);
	    
	    //Adiciona o modelo canvas ao frame
	    setLayout(new BorderLayout());
	    add(cv, BorderLayout.CENTER);
	    
	    SimpleUniverse su = new SimpleUniverse(cv);
	    
	    //Criacao do ponto de vista personalizado
	    Transform3D viewTr = new Transform3D();
		viewTr.lookAt(new Point3d(-30.0, 6.5, 0.5), new Point3d(0.0, 0.0, 0.0), new Vector3d(0.0, 1.0, 0.0));
		viewTr.invert();
		su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTr);
	    
		// Criacao audioDevice
		AudioDevice audioDev = new JavaSoundMixer(su.getViewer().getPhysicalEnvironment());
		audioDev.initialize();
		
	    BranchGroup bg = createSceneGraph();
	    bg.compile();
	    pc = new PickCanvas(cv, bg);
	    pc.setMode(PickTool.GEOMETRY);
	    su.addBranchGraph(bg); //adiciona o BranchGroup ao SimpleUniverse
	    
	    //Criacao do off screen canvas
	    view = su.getViewer().getView();
	    offScreenCanvas = new Canvas3D(gc, true);
	    Screen3D sOn = cv.getScreen3D();
	    Screen3D sOff = offScreenCanvas.getScreen3D();
	    Dimension dim = sOn.getSize();
	    sOff.setSize(dim);
	    sOff.setPhysicalScreenWidth(sOn.getPhysicalScreenWidth());
	    sOff.setPhysicalScreenHeight(sOn.getPhysicalScreenHeight());
	    Point loc = cv.getLocationOnScreen();
	    offScreenCanvas.setOffScreenLocation(loc);
	    
	    //botao
	    Button button = new Button("Save image");
	    add(button, BorderLayout.SOUTH);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ev) {
	        BufferedImage bi = capture();
	        save(bi);
	      }
	    });
	    
	    
	    //Criacao do OrbitBehavior de modo a controlar a vista com o rato
	    OrbitBehavior orbit = new OrbitBehavior(cv);
		orbit.setSchedulingBounds(bounds);
		su.getViewingPlatform().setViewPlatformBehavior(orbit);
		
		WindowListener wListener = new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		};
		addWindowListener(wListener);
		
		
		
		cv.addMouseListener(this);
	}
	
	
	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		
		//Menu
		menuBar = new JMenuBar();
        
		JMenuItem jMenuItem;
		JMenuItem jMenuExit;
		menu = new JMenu("File");
        menuBar.add(menu);       
        
        jMenuItem = new JMenuItem("Save Image");
        jMenuItem.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ev) {
                BufferedImage bi = capture();
                save(bi);
              }
         });
           
        menu.add(jMenuItem);
               
        jMenuExit = new JMenuItem("Exit");
        jMenuExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
        });
        
        menu.add(jMenuExit);
            
        add("North", menuBar);
        //
		 
		
		BoundingSphere soundBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		
		
		//chamar a funcao Ecra()
		geom2 = Ecra();
		Appearance apEcra = new Appearance();
		PolygonAttributes paEcra = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		apEcra.setPolygonAttributes(paEcra);
		Color3f corEcra = new Color3f(0f, 0f, 0f);
		ColoringAttributes caEcra = new ColoringAttributes(corEcra, ColoringAttributes.FASTEST);
		apEcra.setColoringAttributes(caEcra);
		
		int tMode = TransparencyAttributes.BLENDED;
		float tValue = 0.5f;
		TransparencyAttributes ta = new TransparencyAttributes(tMode, tValue);
		apEcra.setTransparencyAttributes(ta);
		
		Shape3D ecra = new Shape3D(geom2, apEcra);
		
		Transform3D trEcra = new Transform3D();
		trEcra.setScale(1.1);
		trEcra.setTranslation(new Vector3f(-5.5f, 2.3f, -5.0f));
		trEcra.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(-45)));
		TransformGroup tgEcra = new TransformGroup(trEcra);
		tgEcra.addChild(ecra);
		root.addChild(tgEcra);
		//
		
		
		//chamar a funcao Painel()
		geom = Painel();
		Appearance apPainel = new Appearance();
		apPainel.setColoringAttributes(new ColoringAttributes(new Color3f(Color.RED), ColoringAttributes.SHADE_FLAT));
		apPainel.setLineAttributes(new LineAttributes(6f, LineAttributes.PATTERN_DASH_DOT, true));
		
		Shape3D painel = new Shape3D(geom, apPainel);
		
		Transform3D trPainel = new Transform3D();
		trPainel.setScale(0.8);
		trPainel.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(90)));
		trPainel.setTranslation(new Vector3f(-6.01f, 1f, -5.8f));
		TransformGroup tgPainel = new TransformGroup(trPainel);
		tgPainel.addChild(painel);
		root.addChild(tgPainel);
		//
		
		//chamar a funcao Pontos()
		geom1 = Pontos();
		Appearance apPontos = new Appearance();
		apPontos.setColoringAttributes(new ColoringAttributes(new Color3f(Color.GREEN), ColoringAttributes.NICEST));
		apPontos.setPointAttributes(new PointAttributes(10f, true));
		
		Shape3D pontos = new Shape3D(geom1, apPontos);
		
		Transform3D trPontos = new Transform3D();
		trPontos.setScale(0.5);
		trPontos.setTranslation(new Vector3f(-2.9f, 1.7f, -6f));
		TransformGroup tgPontos = new TransformGroup(trPontos);
		tgPontos.addChild(pontos);
		root.addChild(tgPontos);
		//
		
		
		//perna da mesa (cone)
		Transform3D trCone = new Transform3D();
		trCone.setTranslation(new Vector3f(5.5f, 1f, 3.0f));
		trCone.setScale(0.9);
		
		TransformGroup tgCone = new TransformGroup(trCone);
	    Cone perna1 = new Cone();
	    root.addChild(tgCone);
		
	    TransformGroup coloracao = new TransformGroup();
	    coloracao.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    coloracao.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    
	    tgCone.addChild(coloracao);
	    coloracao.addChild(perna1);
	    
	    Alpha alphaCor = new Alpha(-1, 5000);
	    Material material = new Material();
	    Color3f colorA = new Color3f(1f, 0f, 0f);
	    Color3f colorB = new Color3f(0f, 1f, 0f);
	    ColorInterpolator colorCone = new ColorInterpolator(alphaCor, material, colorA, colorB);
	    colorCone.setSchedulingBounds(new BoundingSphere());
	    tgCone.addChild(colorCone);
		//
		
		
		//perna da mesa (cilindro)
		Appearance ap1 = new Appearance();
		ap1.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_POINT, PolygonAttributes.CULL_NONE, 0f)); 
		ap1.setPointAttributes(new PointAttributes(10f, true));
		Cylinder perna2 = new Cylinder(1.0f, 2.0f,Primitive.ENABLE_GEOMETRY_PICKING | Primitive.ENABLE_APPEARANCE_MODIFY | Primitive.GENERATE_NORMALS, ap1);

		Transform3D tr1 = new Transform3D();
		tr1.setTranslation(new Vector3f(5f, 1f, -2.7f));
		tr1.setScale(0.9);
		TransformGroup tg1 = new TransformGroup(tr1);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(tg1);
		tg1.addChild(perna2);
		//
		
		//perna da mesa (box)
		Appearance ap2 = new Appearance();
		ap2.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0f)); 
		Box perna3 = new Box(1f, 2f, 1f, Primitive.ENABLE_GEOMETRY_PICKING | Primitive.ENABLE_APPEARANCE_MODIFY | Primitive.GENERATE_NORMALS, ap2);

		Transform3D tr2 = new Transform3D();
		tr2.setTranslation(new Vector3f(1.5f, 0.9f, 2.9f));
		tr2.setScale(0.5);
		TransformGroup tg2 = new TransformGroup(tr2);
		tg2.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(tg2);
		tg2.addChild(perna3);		
		//
		
		
		//Jogador e sua movimentacao
		Appearance personObj = new Appearance();
		personObj.setMaterial(new Materials(Materials.GOLD));
		pessoa = new objPerson(personObj);	
		Transform3D trPessoa = new Transform3D();
		trPessoa.setTranslation(new Vector3f(0f, 0.1f, 0f));
		trPessoa.setScale(3.5);
		
		TransformGroup tgPessoa = new TransformGroup(trPessoa);
		tgPessoa.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgPessoa.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		tgPessoa.addChild(pessoa);
		root.addChild(tgPessoa);
		
		KeyControl kc = new KeyControl(tgPessoa, pessoa);
		kc.setSchedulingBounds(new BoundingSphere());
		root.addChild(kc);
		//
		
		
		//chamar o objeto Pyramid			
		Transform3D trPP = new Transform3D();
		trPP.setScale(0.32);
		trPP.setTranslation(new Vector3f(0.2f, 0f, -3.7f));
		
		TransformGroup tgPP = new TransformGroup(trPP);
		tgPP.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgPP.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Shape3D shapeP = new Pyramid();
		Appearance apPP = new Appearance();
		apPP.setMaterial(new Materials(Materials.POLISHED_COPPER));
		shapeP.setAppearance(apPP);
		root.addChild(tgPP);
		
		scale = new TransformGroup();
		scale.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		scale.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		tgPP.addChild(scale);
		scale.addChild(shapeP);
		
		Alpha alphaPrd = new Alpha(-1, 8000);
	    ScaleInterpolator siPrd = new ScaleInterpolator(alphaPrd, scale);
	    BoundingSphere boundsSi = new BoundingSphere();
	    siPrd.setSchedulingBounds(boundsSi);
		alphaPrd.setMode(Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE);
	    alphaPrd.setDecreasingAlphaDuration(4000);
		scale.addChild(siPrd);
		//
		
		//chamar o objeto Moldura
		Appearance apM = new Appearance();
		apM.setMaterial(new Materials(Materials.PEWTER));
		Shape3D shapeM = new Shape3D(new Moldura(), apM);
		PolygonAttributes paMoldura = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		apM.setPolygonAttributes(paMoldura);
		shapeM.setAppearance(apM);

		Transform3D trM = new Transform3D();
		trM.setScale(0.25);
		trM.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(20)));
		trM.setRotation(new AxisAngle4d(0, 0, 1, Math.toRadians(90)));
		trM.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(-50)));
		trM.setTranslation(new Vector3f(-6.3f, 1.2f, 6.0f));
		TransformGroup tgM = new TransformGroup(trM);
		tgM.addChild(shapeM);
		root.addChild(tgM);
		//
		
		
		//chamar o objeto Diamante
		Shape3D shapeD = new Shape3D();
		shapeD = new Diamante(new Color3f(Color.RED));
		Appearance apDiamante = new Appearance();
		PolygonAttributes paDiamante = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		apDiamante.setPolygonAttributes(paDiamante);
		shapeD.setAppearance(apDiamante);
		
		Transform3D trD = new Transform3D();
		trD.setScale(0.4);
		trD.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(90)));
		trD.setTranslation(new Vector3f(8f, 1f, 6f));
		TransformGroup tgD = new TransformGroup(trD);
		tgD.addChild(shapeD);
		root.addChild(tgD);	
		//
		
		
		//criar o objeto MachineGames
		Appearance apMachine = new Appearance();
		PolygonAttributes paMG = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		Color3f colMac = new Color3f(0f, 0f, 255f);
		ColoringAttributes caMac = new ColoringAttributes(colMac, ColoringAttributes.ALLOW_COLOR_READ);
		
		apMachine.setColoringAttributes(caMac);
		apMachine.setPolygonAttributes(paMG);
		Shape3D shapeMachine = new MachineGames();
		shapeMachine.setAppearance(apMachine);
		
		Transform3D trMac = new Transform3D();
		trMac.setScale(0.6);
		trMac.setTranslation(new Vector3f(-3f, 0f, -8f));
		trMac.setRotation(new AxisAngle4d(0, 0, 1, Math.toRadians(90)));
		TransformGroup tgMac = new TransformGroup(trMac);
		tgMac.addChild(shapeMachine);
		root.addChild(tgMac);
		//
		
		
		//criar o objeto faisca
		Appearance apFaisca = new Appearance();
		PolygonAttributes paFaisca = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		apFaisca.setPolygonAttributes(paFaisca);
		Shape3D shapeFaisca = new Faisca();
		shapeFaisca.setAppearance(apFaisca);
		
		
		Transform3D trFaisca = new Transform3D();
		trFaisca.setScale(0.5);
		trFaisca.setTranslation(new Vector3f(-2f, 0f, 8f));
		trFaisca.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(90)));
		TransformGroup tgFaisca = new TransformGroup(trFaisca);
		tgFaisca.addChild(shapeFaisca);
		root.addChild(tgFaisca);
		//
		
		
		//chamar a mesa
		TextureAppearence appM = new TextureAppearence(this, "images/textura.jpg", new Material(), true);
			
		PolygonAttributes pGM = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_NONE, 0);
		appM.setPolygonAttributes(pGM);
		
		Mesa mesa = new Mesa(appM);
		Transform3D trMesa = new Transform3D();
		trMesa.setScale(0.5);
		trMesa.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(90)));
		trMesa.setTranslation(new Vector3f(-6.0f, 0f, 8f));
		TransformGroup tgMesa = new TransformGroup(trMesa);
		tgMesa.addChild(mesa);
		root.addChild(tgMesa);		
		//
		
		
		//Criacao do texto(8ball)
		Appearance apText = new Appearance();
		apText.setMaterial(new Material());
		Font3D font = new Font3D(new Font("Calibri", Font.BOLD, 2), new FontExtrusion());
		Text3D text = new Text3D(font, "8 Ball");
		Shape3D shapeText = new Shape3D(text, apText);		
		
		Transform3D trText = new Transform3D();
		trText.setTranslation(new Vector3f(8.0f, 3.5f, -2.0f));
		trText.setRotation(new AxisAngle4d(0, -1, 0, Math.toRadians(90)));
		TransformGroup tgText = new TransformGroup(trText);
		root.addChild(tgText);
		tgText.addChild(shapeText);
		//
		
		
		//Criacao do texto(arcade machine)
		Appearance apText1 = new Appearance();
		apText1.setMaterial(new Material());
		Font3D font1 = new Font3D(new Font("CASTELLAR", Font.PLAIN, 1), new FontExtrusion());
		Text3D text1 = new Text3D(font1, "Arcade Machine");
		Shape3D shapeText1 = new Shape3D(text1, apText1);
		
		Transform3D trText1 = new Transform3D();
		trText1.setScale(0.9);
		trText1.setTranslation(new Vector3f(-1.5f, 3.5f, -8.0f));
		TransformGroup tgText1 = new TransformGroup(trText1);
		root.addChild(tgText1);
		tgText1.addChild(shapeText1);
		//
		
		//Aplicacao do Billboard
		Transform3D trB = new Transform3D();
		trB.setTranslation(new Vector3f(7f, 1.3f, -7f));
		TransformGroup tgB = new TransformGroup(trB);
		ImagePanel planta = new ImagePanel(this, "images/planta.png", 1.5f);
		tgB.addChild(planta);
		
		TransformGroup bbTg = new TransformGroup();
		bbTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(bbTg);
		bbTg.addChild(tgB);
		
		Billboard bb = new Billboard(bbTg, Billboard.ROTATE_ABOUT_POINT, new Point3f(7f, 1.3f, -7f));
		bb.setSchedulingBounds(bounds);
		bbTg.addChild(bb);
		//
		
		//Tampo da mesa de jogo
		TextureAppearence tampo = new TextureAppearence(this, "images/mesaJogo.jpg", new Material(), false);
		Box tampoMesa = new Box(2.5f, 0.1f, 3.5f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, tampo);
		Transform3D trTampo = new Transform3D();
		trTampo.setTranslation(new Vector3f(3.5f, 2.0f, 0f));
		TransformGroup tgTampo = new TransformGroup(trTampo);
		tgTampo.addChild(tampoMesa);
		root.addChild(tgTampo);
		//
		
		//Parede de betao
		TextureAppearence wallApp = new TextureAppearence(this, "images/concrete_wall.jpg", new Material(), false);
		Box wall = new Box(3.0f, 0f, 8.5f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, wallApp);
		Transform3D trWall = new Transform3D();
		trWall.setTranslation(new Vector3f(8.5f, 3.0f, 0f));
		trWall.setRotation(new AxisAngle4d(0, 0, 1, Math.toRadians(90)));
		TransformGroup tgWall = new TransformGroup(trWall);
		tgWall.addChild(wall);
		root.addChild(tgWall);
		//
		
		//Parede de colorida
		TextureAppearence wallWoodApp = new TextureAppearence(this, "images/color_wall.jpg", new Material(), false);
		Box woodWall = new Box(0f, 3.0f, -8.5f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, wallWoodApp);
		Transform3D trWood = new Transform3D();
		trWood.setTranslation(new Vector3f(0f, 3.0f, -8.5f));
		trWood.setRotation(new AxisAngle4d(0, 1, 0, Math.toRadians(-90)));
		TransformGroup tgWood = new TransformGroup(trWood);
		tgWood.addChild(woodWall);
		root.addChild(tgWood);
		//
		
					
		//Cubo colorido
		Transform3D trCubo = new Transform3D();
		trCubo.setScale(0.3);
		trCubo.setTranslation(new Vector3f(-4.5f, 1.8f, 7.0f));
		
		TransformGroup tgCubo = new TransformGroup(trCubo);
		ColorCube cube = new ColorCube();
		root.addChild(tgCubo);
		
		rotacao = new TransformGroup();
		rotacao.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rotacao.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		tgCubo.addChild(rotacao);
		rotacao.addChild(cube);
		
		
		Alpha alphaCube = new Alpha(-1, 8000);
		RotationInterpolator rotator = new RotationInterpolator(alphaCube, rotacao);
		BoundingSphere boundsInterpolator =  new BoundingSphere();
		rotator.setSchedulingBounds(boundsInterpolator);
		alphaCube.setMode(Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE);
	    alphaCube.setDecreasingAlphaDuration(4000);
	    rotacao.addChild(rotator);
	    //
	    
		
		//globo (LOD)
		Transform3D trGlobo = new Transform3D();
		trGlobo.setTranslation(new Vector3f(-4.5f, 2.5f, 7.0f));
		TransformGroup tgGlobo = new TransformGroup(trGlobo);
		tgGlobo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgGlobo.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		root.addChild(tgGlobo); 
		
		Switch sw = new Switch(0);
	    sw.setCapability(Switch.ALLOW_SWITCH_READ);
	    sw.setCapability(Switch.ALLOW_SWITCH_WRITE);
	    tgGlobo.addChild(sw);
	    
	    loadImages();
	    Appearance apGlobo = createAppearance(0);
	    sw.addChild(new Sphere(0.4f, Primitive.GENERATE_TEXTURE_COORDS, 40, apGlobo));
	    apGlobo = createAppearance(1);
	    sw.addChild(new Sphere(0.4f, Primitive.GENERATE_TEXTURE_COORDS, 20, apGlobo));
	    apGlobo = createAppearance(2);
	    sw.addChild(new Sphere(0.4f, Primitive.GENERATE_TEXTURE_COORDS, 10, apGlobo));
	    apGlobo = new Appearance();
	    apGlobo.setColoringAttributes(new ColoringAttributes(0.5f, 0f, 0f,ColoringAttributes.FASTEST));
	    sw.addChild(new Sphere(0.4f, Sphere.GENERATE_NORMALS, 5, apGlobo));
		
	    
	    float[] distances = new float[3];
	    distances[0] = 5.0f;
	    distances[1] = 10.0f;
	    distances[2] = 30.0f;
	    DistanceLOD lod = new DistanceLOD(distances);
	    lod.addSwitch(sw);
	    BoundingSphere boundsLod =
	    new BoundingSphere(new Point3d(0f,0f,0f), 10.0);
	    lod.setSchedulingBounds(boundsLod);
	    tgGlobo.addChild(lod);
	    //
		
	    
	    //PositionPathInterpolator (bola amarela)
	    Appearance ballApp = new Appearance();
	    Color3f colB = new Color3f(1.0f, 1.0f, 0.0f);
	    ColoringAttributes caB = new ColoringAttributes(colB, ColoringAttributes.NICEST);
	    ballApp.setColoringAttributes(caB);
	    Sphere ball = new Sphere();
	    ball.setAppearance(ballApp);
	    
	    Transform3D trBall = new Transform3D();
	    trBall.setTranslation(new Vector3f(1.8f, 2.3f, 2.3f));
	    trBall.setScale(0.2);
	    
	    TransformGroup tgBall = new TransformGroup(trBall);
	    
	    root.addChild(tgBall);
	    
	    TransformGroup target = new TransformGroup();
	    target.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    target.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    
	    tgBall.addChild(target);
	    target.addChild(ball);
	    
	    Point3f[] positions = {new Point3f(1.8f, 2.3f, 2.3f), new Point3f(40.4f, 2.3f, -120.3f),
	    		new Point3f(90.6f, 2.3f, 2.3f), new Point3f(1.8f, 2.3f, 2.3f)};
	    float[] knots = {0.0f, 0.4f, 0.8f, 1.0f};
	    
	    Alpha alphaBall = new Alpha(-1, 4000);
	    
	    PositionPathInterpolator interpolatorBall = new PositionPathInterpolator(
	    		alphaBall, target, trBall, knots, positions);
	    
	    interpolatorBall.setSchedulingBounds(new BoundingSphere());
	    tgBall.addChild(interpolatorBall);
	    //
	    
	    
	    //PositionPathInterpolator (bola cor de rosa)
	    Appearance apBall2 = new Appearance();
	    Color3f colBl2 = new Color3f(1.0f, 0.2f, 0.7f);
	    ColoringAttributes caBall2 = new ColoringAttributes(colBl2, ColoringAttributes.NICEST);
	    apBall2.setColoringAttributes(caBall2);
	    Sphere ball2 = new Sphere();
	    ball2.setAppearance(apBall2);
	    
	    Transform3D trBall2 = new Transform3D();
	    trBall2.setTranslation(new Vector3f(1.8f, 2.3f, -2.3f));
	    trBall2.setScale(0.2);
	    
	    TransformGroup tgBall2 = new TransformGroup(trBall2);
	    
	    root.addChild(tgBall2);
	    
	    TransformGroup target2 = new TransformGroup();
	    target2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    target2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    
	    tgBall2.addChild(target2);
	    target2.addChild(ball2);
	    
	    Point3f[] positions2 = {new Point3f(1.8f, 2.3f, 2.3f), new Point3f(4.4f, 2.3f, 5.3f),
	    		 new Point3f(3.6f, 2.3f, 3.3f), new Point3f(4.6f, 2.3f, 2.3f), new Point3f(1.8f, 2.3f, 2.3f)};
	    
	    float[] knots2 = {0.0f, 0.2f, 0.4f, 0.6f, 1.0f};
	    
	    Alpha alphaBall2 = new Alpha(-1, 2000);
	    
	    PositionPathInterpolator interpolatorBall2 = new PositionPathInterpolator(
	    		alphaBall2, target2, trBall2, knots2, positions2);
	    
	    interpolatorBall2.setBounds(new BoundingSphere());
	    tgBall2.addChild(interpolatorBall2);
	    //
	    
	    
		//Criacao do Background
		Background background = new Background(new Color3f(Color.LIGHT_GRAY));
		background.setApplicationBounds(bounds);
		root.addChild(background);
		
		//chamar o Floor
		root.addChild(new Floor(50, -8.5f, 8.5f, new Color3f(Color.WHITE), new Color3f(Color.DARK_GRAY), true));
		
		
		//Iluminacao		
		AmbientLight abLight = new AmbientLight(true, new Color3f(Color.WHITE));
		abLight.setInfluencingBounds(bounds);
		root.addChild(abLight);
		
		PointLight pLight = new PointLight(new Color3f(Color.WHITE), new Point3f(0f, 1f, 1f), new Point3f(-1f, 0f, 0f));
        pLight.setInfluencingBounds(bounds);
        root.addChild(pLight);
		
		PointLight lhtPyramid = new PointLight(new Color3f(Color.WHITE),
				new Point3f(0.6f, 1f, -2.5f), new Point3f(0.1f,0.1f,0.1f));
				lhtPyramid.setInfluencingBounds(bounds2);
		root.addChild(lhtPyramid);
					
		DirectionalLight dlTx = new DirectionalLight(new Color3f(Color.WHITE), new Vector3f(0f, 3.5f, 8.0f));
		dlTx.setInfluencingBounds(bounds3);
		root.addChild(dlTx);
		
		DirectionalLight tl8B1 = new DirectionalLight(new Color3f(Color.BLUE), new Vector3f(7f, 3.5f, 8f));
		tl8B1.setInfluencingBounds(bounds1);
		root.addChild(tl8B1);
		
		DirectionalLight tl8B2 = new DirectionalLight(new Color3f(Color.RED), new Vector3f(7f, 3.5f, -8f));
		tl8B2.setInfluencingBounds(bounds1);
		root.addChild(tl8B2);
		
		
		//SpotLight spotTM = new SpotLight(true, new Color3f(Color.WHITE), new Point3f(-0.5f, 4.0f, 1f),
			//		new Point3f(1f, 0f, 0f), new Vector3f(1.0f, 2.0f, 1f), 45f, 1f);
		//spotTM.setInfluencingBounds(bounds3);
		//root.addChild(spotTM);
		
		
		//Som
		URL url = this.getClass().getClassLoader().getResource("images/level.wav");
		MediaContainer mc = new MediaContainer(url);
		BackgroundSound bSound = new BackgroundSound();
		bSound.setSoundData(mc);
		bSound.setSchedulingBounds(soundBounds);
		bSound.setLoop(Sound.INFINITE_LOOPS);
		bSound.setInitialGain(0.1f);
		bSound.setEnable(true);
		root.addChild(bSound);
		//
		
		return root;
	}
	
	private GeometryArray Painel() {
		IndexedLineArray la = new IndexedLineArray(5, IndexedLineArray.COORDINATES, 8);
		
		la.setCapability(LineArray.ALLOW_REF_DATA_READ);
		la.setCapability(LineArray.ALLOW_REF_DATA_WRITE);
		
		Point3f[] coords = new Point3f[5];	
		coords[0] = new Point3f(0f, 0f, 0f);
		coords[1] = new Point3f(0f, 1f, 0f);
		coords[2] = new Point3f(2f, 1f, 0f);
		coords[3] = new Point3f(2f, 0f, 0f);
		coords[4] = new Point3f(0f, 0f, 0f);
		
		la.setCoordinates(0, coords);
		int[] indices = {0,1, 1,2, 2,3, 3,4};
		la.setCoordinateIndices(0, indices);
		
		return la;
	}
	
	private GeometryArray Ecra() {
		
		IndexedQuadArray iqa = new IndexedQuadArray(8, IndexedQuadArray.COORDINATES, 20);
		
		Point3f[] coords = new Point3f[8];	
		coords[0] = new Point3f(0f, 1.2f, 0f);
		coords[1] = new Point3f(0f, 1.2f, 0f);
		coords[2] = new Point3f(2f, 1.2f, 0f);
		coords[3] = new Point3f(2f, 0f, 0f);
		coords[4] = new Point3f(0f, 0f, 0.2f);
		coords[5] = new Point3f(0f, 1.2f, 0.2f);
		coords[6] = new Point3f(2f, 1.2f, 0.2f);
		coords[7] = new Point3f(2f, 0f, 0.2f);
		
		iqa.setCoordinates(0, coords);
		int[] indices = {0,1,2,3,  0,4,5,1,  5,6,2,1,  2,3,7,6,  7,4,5,6};
		iqa.setCoordinateIndices(0, indices);
		
		
		return iqa;
	}
	
	
	private GeometryArray Pontos() {
		
		IndexedPointArray ipa = new IndexedPointArray(5, IndexedPointArray.COORDINATES, 5);
		
		ipa.setCapability(PointArray.ALLOW_REF_DATA_READ);
		ipa.setCapability(PointArray.ALLOW_REF_DATA_WRITE);
		
		Point3f[] coords = new Point3f[5];
		coords[0] = new Point3f(0f, 0f, -1f);
		coords[1] = new Point3f(0f, 1f, -2f);
		coords[2] = new Point3f(0f, 2f, -1f);
		coords[3] = new Point3f(0f, 1f, 0f);
		coords[4] = new Point3f(0f, 1f, -1f);
		
		ipa.setCoordinates(0, coords);
		int[] indices = {0, 1, 2, 3, 4};
		ipa.setCoordinateIndices(0, indices);
		
		return ipa;
	}
	
	  void loadImages() {
		    URL filename =
		    getClass().getClassLoader().getResource("images/padrao.jpg");
		    try {
		      images[0] = ImageIO.read(filename);
		      AffineTransform xform = AffineTransform.getScaleInstance(0.5, 0.5);
		      AffineTransformOp scaleOp = new AffineTransformOp(xform, null);
		      for (int i = 1; i < 3; i++) {
		          images[i] = scaleOp.filter(images[i-1], null);
		      }
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		  }
	
	  Appearance createAppearance(int i){
		    Appearance appear = new Appearance();
		      ImageComponent2D image = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, images[i]);
		      Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
		      image.getWidth(), image.getHeight());
		      texture.setImage(0, image);
		      texture.setEnable(true);
		      texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		      texture.setMinFilter(Texture.BASE_LEVEL_POINT);
		      appear.setTexture(texture);
		    return appear;
		  }
	
	  
	  public BufferedImage capture() {
		    // render off screen image
		    Dimension dim = cv.getSize();
		    view.stopView();
		    view.addCanvas3D(offScreenCanvas);
		    BufferedImage bImage =
		    new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		    ImageComponent2D buffer =
		    new ImageComponent2D(ImageComponent.FORMAT_RGB, bImage);
		    offScreenCanvas.setOffScreenBuffer(buffer);
		    view.startView();
		    offScreenCanvas.renderOffScreenBuffer();
		    offScreenCanvas.waitForOffScreenRendering();
		    bImage = offScreenCanvas.getOffScreenBuffer().getImage();
		    view.removeCanvas3D(offScreenCanvas);
		    return bImage;
		  }
	  
	  
	  public void save(BufferedImage bImage) {
		    // save image to file
		    JFileChooser chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new File("."));
		    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		      File oFile = chooser.getSelectedFile();
		      try {
		        ImageIO.write(bImage, "jpeg", oFile);
		      } catch (IOException ex) {
		        ex.printStackTrace();
		      }
		    }
		  }
	  
	  
	@Override
	public void mouseClicked(MouseEvent e) {
		pc.setShapeLocation(e);
		PickResult result = pc.pickClosest();
		
		if(result != null) {
			TransformGroup tg = (TransformGroup) result.getNode(PickResult.TRANSFORM_GROUP);
			if(tg != null) {
				
				Transform3D tr = new Transform3D();
				tg.getTransform(tr);
				
				Transform3D rot = new Transform3D();
				rot.rotY(Math.PI/8);
				
				tr.mul(rot);
		        
				tg.setTransform(tr);
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
