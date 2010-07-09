package tmnuelaerm;

import TUIO.TuioListener;
import interaction.TNObstacleObject;

import java.util.ArrayList;
import java.util.List;

import old.ObstacleObject;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioTime;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import util.Debug;
import util.Style;
import util.XMLImporter;

import particleSystem.PSUtil;
import particleSystem.Particle;
import particleSystem.ParticleSystem;
import particleSystem.Path;
import particleSystem.Property;
import particleSystem.Repeller;

/**
 * This is the main Class of the Multitouch project by the-moron.net<br>
 * represented by fabiantheblind and PDXIII <br>
 * students at the University of Applied Sciences Potsdam (FHP) <br>
 * during the class of Till Nagel "Urbane Ebenen" (urban layers).<br>
 * the {@code code} is available here: <a
 * href="http://code.google.com/p/tmn-ue-learm/" target="blanc">Google Code</a><br>
 * or here: <a href="http://github.com/fabiantheblind/TMN_UE_Laerm.git"
 * target="blanc"> GitHub</a><br>
 * 
 * @author PDXIII
 * @author fabianthelbind
 * @version 0.108
 * 
 * 
 */
public class TmnUELaerm extends PApplet implements TuioListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1217858472488377688L;
	/**
	 * An List of TNObstacleObject
	 * 
	 * @see Class TNObstacleObject Class
	 */
	List<TNObstacleObject> transObjects = new ArrayList<TNObstacleObject>();

	/**
	 * controls the amount of TNObstleOjects
	 */
	public int howManyObstacles = 9;
	/**
	 * the number of Obstacles
	 * 
	 * @see Class ObstacleObject Class
	 */
	public int obstclCounter;
	/**
	 * if something is pressed... Ask PDXIII
	 */
	public boolean isPressed = false;
	/**
	 * The Tuio Client
	 */
	public TuioClient tuioClient = new TuioClient();
	/**
	 * a ArrayList of tuio cursors
	 */
	public ArrayList<TuioCursor> tuioCursorList;
	/**
	 * An ArrayList of Particles
	 * 
	 * @see Class Particle Class
	 */
	public ArrayList<Particle> ptclsList = new ArrayList<Particle>();
	/**
	 * An ArrayList of <code>Repeller</code>'s
	 * 
	 * @see Class Repeller Class
	 */
	public ArrayList<Repeller> repellers;
	/**
	 * our particle System
	 * 
	 * @see Class ParticleSystem Class
	 */
	public ParticleSystem ps;
	/**
	 * An ArrayList of Paths
	 * 
	 * @see Class Path Class
	 */
	public ArrayList<Path> pathsList = new ArrayList<Path>();
	/**
	 * This is for debugging and make some lose repellers
	 * 
	 * @see Class Repeller CLass
	 * @see PSUtil#makeSomeRepellers(ArrayList)
	 * @see PSUtil#displaySomeRepellers(ArrayList)
	 */
	public ArrayList<Repeller> someRepellers = new ArrayList<Repeller>();
	/**
	 * number of particles
	 */
	int numPtcls = 1005;
	/**
	 * An ArrayList of {@link PSUtil#initPropertysList()} for all the Objects
	 * 
	 * @see Class Property Class
	 */
	public ArrayList<Property> propertysList;
	/**
	 * standard radius for the particles<br>
	 * every particle can have his own force / radius / speed<br>
	 * they can be changed later<br>
	 * this is for the collision of particles<br>
	 * 
	 * 
	 */
	float ptclRadius = 2;
	/**
	 * a boolean for switching the path
	 */
	private boolean switchPath = false;
	/**
	 * the time switches with the color
	 * 
	 */
	public boolean DAY = true;
	/**
	 * to count the time
	 */
	public static int runtimeCounter;
	/**
	 * display the debug Stuff if {@code true}
	 */
	private boolean showDebug = false;
	/**
	 * display the paths if {@code true}
	 */
	private boolean showDebugPath = false;

	/**
	 * PDXIII background Stuff
	 */
	public PImage fadingBG;
	/**
	 * PDXIII background Stuff
	 */
	public float tinter;
	/**
	 * PDXIII background Stuff
	 */
	public boolean tintBack = false;
	/**
	 * PDXIII background Stuff
	 */
	public float tintMax = 60;
	/**
	 * PDXIII background Stuff
	 */
	public float tintMin = 20;

	public float tintSize = tintMax - tintMin;

	public float tintingStep = 0.2f;

	public int inactiveCol;

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#setup()
	 */
	public void setup() {

		colorMode(HSB, 360, 100, 100);
		// passing the PApplet thru to all static methods
		// make the Styling
		Style.setPApplet(this);
		Style.create();
		// this is for overlays and Stuff
		Debug.setPAppletDebug(this);
		// these are some methods that help with the ParticleSystem
		PSUtil.setPApplet(this);
		// this is for importing the Property of the objects from an .xml file
		XMLImporter.setPAppelt(this);
		propertysList = PSUtil.initPropertysList();

		background(0);
		size(1024, 768, OPENGL);

		// PDXIII background Stuff
		fadingBG = loadImage("fadingBG.png");

		// PDXIII TUIO Stuff
		// enable on system installed fonts
		hint(ENABLE_NATIVE_FONTS);

		// init TUIO
		tuioClient.addTuioListener(this);
		tuioClient.connect();

		// making ObstacleObjects
		for (int i = 0; i < howManyObstacles; i++) {
			Property property = propertysList.get(i);
			transObjects.add(new TNObstacleObject(this, 50 * i, 50 * i, 0, 0,property));
		}

		// end PDXIII TUIO Stuff

		// particle stuff
		
		PSUtil.makeSpaces(pathsList);

		// We are now making random Particles and storing them in an ArrayList
		// ptclsList
		ptclsList = PSUtil.initParticles(numPtcls, ptclRadius, ptclsList);

		// add the Path ptclPoints ArrayList of Particles to the ptclsList
		for (int pl = 0; pl < pathsList.size(); pl++) {
			for (int pp = 0; pp < pathsList.get(pl).ptclPoints.size(); pp++) {
				ptclsList.add(pathsList.get(pl).ptclPoints.get(pp));
			}
		}
		// we need the particle system to interact with the repellers
		ps = new ParticleSystem(this, 0, new PVector(width / 2, height / 2),
				ptclsList);

		runtimeCounter = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw()
	 */
	public void draw() {

		// PDXBGStuff
		// drawBG();
		theBackground();
		// just a clearScreen method
		// clearScreen();
		smooth();

		// this is for the particles that make the paths
		// to get them back into their original position we have to reset them
		// in the function Path.resetPointPtcls() you can set
		// how fast and strong the want to get back
		PSUtil.resetPath(runtimeCounter, pathsList);
		// this is for switching every 300 frames the path to follow
//		if (runtimeCounter % 432 == 0) {
//			// println("Range: "+myRange +"   Pathnum: "+myPathNum
//			// +"   Dir: "+myDirection);
//		}

		if (tinter > tintSize / 2 + tintMin) {
			DAY = true;
			switchPath = true;

		} else {
			DAY = false;
			switchPath = true;

		}

//		 println(DAY + ", "+ tinter);

		for (int i = 0; i < ptclsList.size(); i++) {
			Particle ptcl = ptclsList.get(i);
			// if the particle is not part of a path
			if (ptcl.hidden != true) {
				// use the switchPath variable for random path selection
				if (switchPath) {
					ptcl.pathNum = floor(random(0, PSUtil.numOfPaths));// myPathNum;
				}
				ptcl.applyForces(ptclsList, pathsList.get(ptcl.pathNum));
				switchPath = false;

			}
			// Call the generic run method (update, borders, display, etc.)
			ptcl.run();

		}

		// ArrayList<Repeller> repeller
		// must be rebuild at runtime so it doesn't store all the time new
		// repellers in the list
		// else it has to be deleted at runtime
		// does that matter to the performace?

//		repellers = new ArrayList<Repeller>();
//		// get all repellers in all objects into one list
//		// to use them in the ParticleSystem Class
//		for (int j = 0; j < transObjects.size(); j++) {
//
//			TNObstacleObject obstclObject = (TNObstacleObject) transObjects.get(j);
//			if (obstclObject.ObstclsRepellerList != null) {
//				for (int k = 0; k < obstclObject.ObstclsRepellerList.size(); k++) {
//					repellers.add(obstclObject.ObstclsRepellerList.get(k));
//				}
//			}
//		}

		for (TNObstacleObject transformableObject : transObjects) {
			transformableObject.draw();

		}

		// pass all Objects over to the ParticleSystem
		ps.applyObstcles(transObjects, DAY);
		// Run the Particle System
		ps.run();

		runtimeCounter++;

		// PDXIII TUIO Stuff
		tuioCursorList = new ArrayList<TuioCursor>(tuioClient.getTuioCursors());
		// //end PDXIII TUIO Stuff

		// DEBUGGING START press "d"
		if (showDebug) {
			Debug.watchAParticle(ptclsList, ps);
//			Debug.watchARepellers(someRepellers);
//			PSUtil.displaySomeRepellers(someRepellers);

			if (showDebugPath) {
				Debug.displayAllPaths(pathsList);
			}
			Debug.drawFrameRate();
			Debug.drawFrameCount();
		}
		// //just for adjustment
		// debug.drawGrid();
		Debug.drawCursors(tuioCursorList);
		// Debug.drawCursorCount(tuioCursorList);
		Debug.writeIMGs();
		// DEBUGGING END
		
		
//		 println(" ptcl ("+ptclsList.size()+") *obj ("+transObjects.size()+") *repel("+repellers.size()+") = "
//				 + ptclsList.size()* transObjects.size()*repellers.size());

	}

	/**
	 * A Clear Screen Method writes a
	 * {@code processing.core.PApplet.rect(float, float, float, float)} every
	 * Frame
	 * 
	 */
	void clearScreen() {
		noStroke();
		fill(Style.clsColor);
		rect(0, 0, width, height);

	}

	/**
	 * PDXIII background Stuff
	 */
	void drawBG() {

		// PDXIII background Stuff
		tint(tinter, 255, 255, 100);
		image(fadingBG, 0, 0);
		tinter += 0.5f;
		if (tinter > 360) {
			tinter = 0;
		}
		// end PDXIII background Stuff

	}

	/**
	 * PDXIII background Stuff
	 */
	public void theBackground() {
		colorMode(HSB, 360, 100, 100);

		tint(220, 40 + tinter, 40 + tinter,100);
		image(fadingBG, 0, 0);
		if (tinter >= tintMax) {
			tintBack = true;
		}

		if (tinter <= tintMin) {
			tintBack = false;
		}

		if (!tintBack) {
			tinter += 0.2f;
		} else {
			tinter -= 0.2f;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#addTuioCursor(TUIO.TuioCursor)
	 */

	@Override
	public void addTuioCursor(TuioCursor tcur) {
		// Hit test for all objects: first gets the hit, ordered by creation.
		// TODO Order by z-index, updated by last activation/usage
		for (TNObstacleObject ttObj : transObjects) {
			if (ttObj.isHit(tcur.getScreenX(width), tcur.getScreenY(height))) {
				ttObj.addTuioCursor(tcur);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#updateTuioCursor(TUIO.TuioCursor)
	 */

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		for (TNObstacleObject ttObj : transObjects) {
			if (ttObj.isHit(tcur.getScreenX(width), tcur.getScreenY(height))) {
				ttObj.updateTuioCursor(tcur);

				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#removeTuioCursor(TUIO.TuioCursor)
	 */

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		for (TNObstacleObject ttObj : transObjects) {
			// Pass trough remove-event to all objects, to allow fingerUp also
			// out of boundaries,
			// as objects decide themselves (via cursor-id) whether cursor
			// belongs to it.

			ttObj.removeTuioCursor(tcur);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#addTuioObject(TUIO.TuioObject)
	 */
	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#refresh(TUIO.TuioTime)
	 */
	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#removeTuioObject(TUIO.TuioObject)
	 */
	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see TUIO.TuioListener#updateTuioObject(TUIO.TuioObject)
	 */
	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub

	}

	// TUIO methods end

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#keyPressed()
	 */
	public void keyPressed() {
		if (key == 'd') {
			// do something fancy
			if (showDebug == true) {
				showDebug = false;

			} else {
				showDebug = true;

			}
		}
		if (key == 'p') {
			// do something fancy
			if (showDebugPath == true) {
				showDebugPath = false;

			} else {
				showDebugPath = true;

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#keyReleased()
	 * 
	 * @see Debug#saveFrame(float)
	 */
	public void keyReleased() {

		// not important for the main programm
		if (key == 's' || key == 'S') {

			// just for unique filenames when saving a frame as .jpg in the
			// folder data
			float time;
			time = millis();
			Debug.saveFrame(time);
			println("wrote \"MyImg" + time + ".jpg\" to the folder bilder");

		}
		if (key == 'e' || key == 'E') {
			exit();
		}
		if (key == 'i' || key == 'I') {
			Debug.writeImg = true;
		}
		if (key == 'o' || key == 'O') {
			Debug.writeImg = false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#mousePressed()
	 */
	public void mousePressed() {
		// PSUtil.newPtkl(this, mouseX, mouseY, ptclsList, ptclRadius);

		for (int i = 0; i < someRepellers.size(); i++) {
			Repeller r = someRepellers.get(i);
			r.clicked(mouseX, mouseY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#mouseReleased()
	 */
	public void mouseReleased() {

		for (int i = 0; i < someRepellers.size(); i++) {
			Repeller r = someRepellers.get(i);
			r.stopDragging();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { tmnuelaerm.TmnUELaerm.class.getName() });
	}
}
