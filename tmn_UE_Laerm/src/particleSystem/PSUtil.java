package particleSystem;

import java.util.ArrayList;

import old.ObstacleObject;

import processing.core.PApplet;
import processing.core.PVector;
import processing.xml.XMLElement;
import util.XMLImporter;

/**
 * This Class is for helping with the <code>ParticleSystem</code><br>
 * they don't affect the Core Application but are very usefull<br>
 * 
 * @author fabianthelbind
 * @see Particle Class Particle
 * @see Path Class Path
 * @see Repeller Class Repeller
 * @author fabianthelbind
 * 
 */
@SuppressWarnings("deprecation")
public class PSUtil {

	/**
	 * the PApplet
	 * 
	 */
	private static PApplet p;

	/**
	 * 
	 * this sets the PApplet for all the functions it has to be called first
	 * 
	 * @param p_
	 */
	public static void setPApplet(PApplet p_) {

		p = p_;

	}

	// Particle stuff

	/**
	 * This a number of points circling around the center. for a smother path
	 * give him more segments
	 * 
	 * @param segments
	 *            the number of segments in a path
	 * @param radius
	 *            the radius around the path
	 * @param size
	 *            the diameter of the circle
	 * @return Path
	 * @see Path Class Path
	 * @see Path#addPointPtcl(float, float)
	 */
	public static Path initCirclePath(int segments, int radius, int size) {

		Path path = new Path(p, radius);
		for (int i = 0; i <= 360; i += 360 / segments) {
			// path.addPoint(width / 2 + sin(radians(i))*100,height / 2 +
			// cos(radians(i))*100);
			path.addPointPtcl((p.width / 2) + (PApplet.sin(PApplet.radians(i)))
					* size, (p.height / 2) + (PApplet.cos(PApplet.radians(i)))
					* size);
		}
		return path;
	}

	// public static ArrayList <Path> initPaths(PApplet p, ArrayList <Path>
	// pathsList){
	//
	// int [] segments = {13,13,13,13,13,13,13,13,13,13};
	// int [] r = {50,50,50,50,50,50,50,50,50,50};
	// int radiusFormula = (((p.height/2)/9)+50);
	// for(int i = 0; i< 1; i++){
	// Path path = new Path(p,r[i]);
	// for(int j = 0; i <=360;j+=360/segments[i]){
	// // path.addPoint(width / 2 + sin(radians(j))*100,height / 2 +
	// cos(radians(j))*100);
	// path.addPointPtcl((p.width / 2 +
	// p.sin(p.radians(j)))*(radiusFormula*i),(p.height / 2 +
	// p.cos(p.radians(j)))*(radiusFormula*i));
	// }
	// pathsList.add(path);
	// }
	// return pathsList;
	// }

	// for easier initalizing of particles
	/**
	 * @param numPtkls
	 *            the number of particles
	 * @param ptclRadius
	 *            the radius of the particle for collision with others
	 * @param ptclsList
	 *            the List of all Particles
	 * @return ptclsList
	 * @see #newPtkl(float, float, ArrayList, float)
	 * @see Particle Class Particle
	 */
	public static ArrayList<Particle> initParticles(int numPtkls,
			float ptclRadius, ArrayList<Particle> ptclsList) {

		ptclsList = new ArrayList<Particle>();
		for (int i = 0; i < numPtkls; i++) {
			newPtkl(p.random(p.width), p.random(p.height), ptclsList,
					ptclRadius);

			// Set some random force and speed
			// ptclsList.get(i).setMaxforce(random(-5,5));
			// ptclsList.get(i).setMaxspeed(random(-2,2));

		}
		return ptclsList;
	}

	// Create new Particles
	/**
	 * builds a new PArticle and adds him to the list of Perticles
	 * 
	 * @param x
	 * @param y
	 * @param ptclsList
	 * @param ptclRadius
	 * @see Particle Class Particle
	 * 
	 */
	public static void newPtkl(float x, float y, ArrayList<Particle> ptclsList,
			float ptclRadius) {

		// float maxforce = 0.3f; // Maximum steering force
		// float maxspeed = 0.3f; // Maximum speed
		// float myMaxspeed = Particle.maxspeed;
		// float myMaxforce = Particle.maxforce;//+random(-1f,1f);
		int pathNum = PApplet.floor(p.random(0, 8));

		Particle ptcl = new Particle(p, new PVector(x, y), new PVector(x, y),
				ptclRadius, pathNum, true, false);
		// ptcl.setMaxforce(10f);
		// ptcl.setMaxforce(5f);
		// ptcl.setMaxspeed(2f);

		ptclsList.add(ptcl);
		// or use:
		// ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y),
		// Particle.radius));

	}

	/**
	 * not used right now
	 * 
	 * @param obstclObjList
	 * @param ptclsList
	 * @deprecated also old stuff
	 */
	public static void ptclsReactOnObject(
			ArrayList<ObstacleObject> obstclObjList,
			ArrayList<Particle> ptclsList) {
		// println(obstclObjList.get(0).ObstclsRepellerList.get(0).radius);
		float mySize = obstclObjList.get(0).ObstclsRepellerList.get(0).radius;
		float myNewForce = PApplet.map(mySize, 10, 100, 0.5f, 13f);

		float myNewSpeed = PApplet.map(mySize, 10, 100, 0.5f, 13f);
		for (int i = 0; i < ptclsList.size(); i++) {

			ptclsList.get(i).setMaxforce(myNewForce);
			ptclsList.get(i).setMaxspeed(myNewSpeed);

		}
	}

	/**
	 * this is just for debugging
	 * 
	 * @param someRepellers
	 *            a list of some repellers
	 * @see Repeller Class Repeller
	 */
	public static void makeSomeRepellers(ArrayList<Repeller> someRepellers) {

		int numRepellers = 12;
		// make some repellers
		for (int i = 0; i <= 360; i += 360 / numRepellers) {

			Repeller rep = new Repeller(p, p.width / 2
					+ PApplet.sin(PApplet.radians(i)) * 180, p.height / 2
					+ PApplet.cos(PApplet.radians(i)) * 180);
			rep.setG(PApplet.pow(10, 3));
			someRepellers.add(rep);
		}

	}

	public static void makeSomeRepellersWithPropertys(
			ArrayList<Repeller> someRepellers, ArrayList<Property> propertysList) {

		int numRepellers = 7;
		int index = 0;
		// make some repellers
		for (int i = 0; i <= 360; i += 360 / numRepellers) {
			PVector loc = new PVector(p.width / 2
					+ PApplet.sin(PApplet.radians(i)) * 180, p.height / 2
					+ PApplet.cos(PApplet.radians(i)) * 180);
			Repeller rep = new Repeller(p, loc, ((10 * 10) * 10), 10,
					propertysList.get(index));// Repeller(p,p.width / 2 +
												// PApplet.sin(PApplet.radians(i))*180,p.height
												// / 2 +
												// PApplet.cos(PApplet.radians(i))*180);
			rep.setG(PApplet.pow(10, 3));
			someRepellers.add(rep);
			index++;
		}

	}

	/**
	 * this is for displaying the repellers for debugging
	 * 
	 * @param someRepellers
	 * @see Repeller Class Repeller
	 * @see #makeSomeRepellers(ArrayList)
	 * 
	 */
	public static void displaySomeRepellers(ArrayList<Repeller> someRepellers) {

		// Display all repellers
		for (int i = 0; i < someRepellers.size(); i++) {
			Repeller r = someRepellers.get(i);
			r.display();
			r.drag();
		}
	}

	public static ArrayList<Property> initPropertysList() {
		ArrayList<Property> propertysList = new ArrayList<Property>();
		XMLElement[] myObstaclObjcts = XMLImporter.getObsctlObjects();
		for (int i = 0; i < myObstaclObjcts.length; i++) {
			String theName = myObstaclObjcts[i].getStringAttribute("name");
			int[][] theValues = XMLImporter.ObjectPropertys(i,
					myObstaclObjcts[i].getParent());
			propertysList.add(new Property(i, theName, theValues));
		}
		return propertysList;

	}

	public static void resetPath(int runtimeCounter, ArrayList<Path> pathsList) {
		// TODO Auto-generated method stub
		// if(runtimeCounter%23==1){
		for (int i = 0; i < pathsList.size(); i++) {
			pathsList.get(i).resetPointPtcls();
		}
		// }
	}
}