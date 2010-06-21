package tmnuelaerm;

import TUIO.TuioListener;
import java.util.ArrayList;
import java.util.Vector;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioTime;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import tmnuelaerm.ObstacleObject;
import processing.core.PApplet;


import particleSystem.Particle;
import particleSystem.ParticleSystem;
import particleSystem.Path;
import particleSystem.Repeller;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PFont;


public class TmnUELaerm extends PApplet implements TuioListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1217858472488377688L;

public ArrayList<ObstacleObject> obstclObjList;
	
	public int obstclCounter;
	public boolean isPressed = false;
	
	public TuioClient tuioClient = new TuioClient();

	
	public ArrayList<TuioCursor> tuioCursorList;

	public PFont font;
	
//	Setup the Particles
	// A path object (series of connected points)
	Path path;
//	our particle System
	ParticleSystem ps;
//	Some Arraylists to store the objects
	ArrayList <Particle> ptclsList =  new ArrayList<Particle>();
	ArrayList<Repeller> repellers;
//	for the particles
	int numPtcls = 500; // number of particles
	
//	every particle can have his own force / radius / speed
//	they can be chaged later
	float ptclRadius = 5; // standard radius for the particles
	public float myForce = 0.5f; // std force for the particles 
	public float mySpeed = 0.5f; // std speed for the particles

//	some repellers
	int numRepellers = 5;
	
//	to count the time
	public int counter;
//	just for unique filenames when saving a frame as .jpg in the folder data
	public float time;
//	this is for exporting image sequences
	public boolean writeImg = false;
	public int imgNum = 0;
	

	//PDXIII background Stuff
	public PImage fadingBG;
	public float tinter;
	
	//end PDXIII background Stuff

	
	public void setup() {
		colorMode(HSB,360,100,100);
		background(0);
		size(500,400);
		frameRate(25);

		//PDXIII background Stuff
		//fadingBG = loadImage("fadingBG.png");

		//PDXIII TUIO Stuff
		// enable on system installed fonts
		hint(ENABLE_NATIVE_FONTS);
		font = createFont("Gentium", 18);
		textFont(font,18);

		//init TUIO
		tuioClient.addTuioListener(this);
		tuioClient.connect();
		
		obstclObjList = new ArrayList<ObstacleObject>();
		
		// making ObstacleObjects
		for (obstclCounter = 0; obstclCounter < 1; obstclCounter++){
			int obstclNo = obstclCounter + 1;
			float firstX = obstclNo*150;
			float firstY = height/2;
			PVector obstclPos = new PVector (firstX, firstY);
			obstclObjList.add(new ObstacleObject(this, obstclNo, obstclPos));
			
		}
		//end PDXIII TUIO Stuff
		
//		particle stuff
		  // Call a function to generate new Path object with 12 segments
		initCirclePath(23);
		  // We are now making random Particles and storing them in an ArrayList ptclsList
		initParticles(numPtcls);
//		we need the particle system to interact with the repellers
		ps = new ParticleSystem(this,1,new PVector(width/2,height/2),ptclsList,path);
		//make some repellers
//		for(int i = 0; i <=360;i+=360/numRepellers){
//			
//			Repeller rep = new Repeller(this,width / 2 + sin(radians(i))*100,height / 2 + cos(radians(i))*100);
//			rep.setG(pow(10,3));
//		repellers.add(rep);
//		}
		
	time = millis();
	counter = 0;	
		
	}

	public void draw() {
		
//		background(125);
//		just a clearScreen method
		
		//PDXIII background Stuff
//		tint(tinter, 255, 255,100);
//		image(fadingBG,0,0);
		
		tinter += 0.5f;
		
		if(tinter > 360){ tinter = 0;}
		//end PDXIII background Stuff

		
		cls();
		smooth();
		
		
		for (int i = 0; i < ptclsList.size(); i++) {
				Particle ptkl =  ptclsList.get(i);
				// Path following and separation are worked on in this function
				ptkl.applyForces(ptclsList,path);
				// Call the generic run method (update, borders, display, etc.)
				ptkl.run();
			}
		
		// Get all Repellers into repellers
		
		repellers = new ArrayList<Repeller>();

		for(int i = 0; i < obstclObjList.size(); i++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(i);
			
			repellers.add(obstclObject.ObstclsRepellerList.get(0));
		
		}
		// Apply repeller objects to all Particles
		ps.applyRepellers(repellers);
		
		// Run the Particle System
		ps.run();
		// Display all repellers
//		for (int i = 0; i < repellers.size(); i++) {
//			Repeller r = (Repeller) repellers.get(i); 
//			r.display();
//			r.drag();
//		}

		counter++;
		
		
		//PDXIII TUIO Stuff
		//just for adjustment
		drawGrid();
		
		tuioCursorList = new ArrayList<TuioCursor> (tuioClient.getTuioCursors());
		
		drawObstacleObjects();
		
		drawCursors();
		
		noStroke();
		fill(255);
		text(tuioCursorList.size(), 50, 50);
		noFill();
		//end PDXIII TUIO Stuff
		  writeIMGs();

	}
	
//	write an rect at everyframe
	void cls(){
		noStroke();
		fill(360,0,0,23);
		rect(0,0,width,height);
	}
	
	// just  writing TIff Sequenzes for videos
	public void writeIMGs(){
		if(writeImg){
			String sa = nf(imgNum,6);
			  saveFrame("./data/ParticleSystem-"+sa+".tif");
			  imgNum++;
		}
	}
	
	//PDXIII TUIO Stuff
	public void drawObstacleObjects(){
		
		for(int i = 0; i < obstclObjList.size(); i++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(i);
			obstclObject.draw();
		}
	}
	
	public void drawCursors(){
		
		for (int i=0; i<tuioCursorList.size(); i++) {
			TuioCursor tcur = (TuioCursor)tuioCursorList.get(i);
			stroke(100,255,255);
			noFill();
			ellipse( tcur.getScreenX(width), tcur.getScreenY(height),10,10);
		}
	}
	
	//end PDXIII TUIO Stuff


	// TUIO methods
	@Override
	public void addTuioCursor(TuioCursor arg0) {
		
		float nowX = arg0.getScreenX(width);
		float nowY = arg0.getScreenY(height);
		int nowID = arg0.getCursorID();
		PVector nowPos = new PVector(nowX, nowY);
		
		for(int j = 0; j < obstclObjList.size(); j++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(j);
				
			if(obstclObject.boundingBox.contains(nowX, nowY)){
				
				if(obstclObject.coursor01ID < 99){
					
					obstclObject.coursor02ID = nowID;
					obstclObject.coursor02Pos = nowPos;
					obstclObject.newCoursor02Pos = nowPos;

					
				}else{
				
					obstclObject.coursor01ID = nowID;
					obstclObject.coursor01Pos = nowPos;
					obstclObject.newCoursor01Pos = nowPos;

					obstclObject.setOffset(nowPos);
				}
			}
		}
	}

	@Override
	public void updateTuioCursor(TuioCursor arg0) {
		
		float nowX = arg0.getScreenX(width);
		float nowY = arg0.getScreenY(height);
		int nowID = arg0.getCursorID();

		
		PVector nowPos = new PVector(nowX, nowY);

		
		for(int j = 0; j < obstclObjList.size(); j++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(j);
				
			
			if(obstclObject.coursor01ID == nowID && obstclObject.coursor02ID == 99){
				
				obstclObject.move(nowPos);
				obstclObject.newCoursor01Pos = nowPos;
				
			
			}else if(obstclObject.boundingBox.contains(nowX, nowY)){
				
				if(obstclObject.coursor01ID < 99 && obstclObject.coursor01ID != nowID){
					
					obstclObject.coursor02ID = nowID;
					obstclObject.coursor02Pos = nowPos;
					
				}else{
					
					obstclObject.coursor01ID = nowID;
					obstclObject.coursor01Pos = nowPos;

					obstclObject.setOffset(nowPos);
					
				}
			}
		}
		
//		PARTICLE STUFF
//		this method sets the Force and Speed if the Particles depending on the Radius
//		of the Repellers in the Obstacle Object
//		bigger repeller means more force and speed
//		ptclsReactOnObject();
		
//		this method sets the radius of the path depending on the radius 
//		of the Repellers in the Obstacle Object
//		bigger repellers means wider path

//		pathReactOnObject();
	}
	
	@Override
	public void removeTuioCursor(TuioCursor arg0) {
		
		int nowID = arg0.getCursorID();

		for(int j = 0; j < obstclObjList.size(); j++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(j);
			
			if(obstclObject.coursor01ID == nowID){
				
				//obstclObject.coursor01Pos = obstclObject.coursor02Pos;
				obstclObject.coursor01ID = 99;
			}
			
			if(obstclObject.coursor02ID == nowID){
				
				obstclObject.coursor02ID = 99;
			}
		}
	}
	
	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}
	// TUIO methods end
	
	//a grid just for adjustment
	public void drawGrid(){
		
		float gridSize = 100;
		
		for(int i = 0; i < 100; i++){
			strokeWeight(1);
			stroke(0);
			
			line(i*gridSize, 0, i*gridSize, height);
			line(0, i*gridSize, width, i*gridSize);
			
			noStroke();
		}
	}
	//grid end
	
//		Particle stuff
	
//	This a number of points circling  around the center. for a smother path 
//	give him more segments
	void initCirclePath(int segments){
		
		path = new Path(this,10f);
		for(int i = 0; i <=360;i+=360/segments){
			  path.addPoint(width / 2 + sin(radians(i))*100,height / 2 + cos(radians(i))*100);
		}
	}
	
//	for easier initalizing of particles
	void initParticles(int numPtkls){
		
		  ptclsList =  new ArrayList<Particle>();
		  for (int i = 0; i < numPtkls; i++) {
		    newPtkl(random(width),random(height),ptclsList);
		    
//		    Set some random force and speed
//		    ptclsList.get(i).setMaxforce(random(-5,5));
//		    ptclsList.get(i).setMaxspeed(random(-2,2));

		  }
	}
		  
//	Create new Particles	
	void newPtkl(float x, float y,ArrayList<Particle> ptclsList) {
				
//				  float maxforce = 0.3f;    // Maximum steering force
//				  float maxspeed =  0.3f;    // Maximum speed
//				  float myMaxspeed = Particle.maxspeed;
//				  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
				Particle ptcl = new Particle(this,new PVector(x,y),new PVector(x,y), ptclRadius);
				ptcl.setMaxforce(10f);
				ptcl.setMaxforce(5f);
				ptcl.setMaxspeed(2f);

				  ptclsList.add(ptcl);
//				or use:
//				  ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y), Particle.radius));

			}
			
	
	public void ptclsReactOnObject(){
		//println(obstclObjList.get(0).ObstclsRepellerList.get(0).radius);
		float mySize = obstclObjList.get(0).ObstclsRepellerList.get(0).radius;
		float myNewForce = map(mySize,10,100,0.5f,13f);
		
		float myNewSpeed = map(mySize,10,100,0.5f,13f);
		for(int i= 0;i < ptclsList.size();i++){
			
			ptclsList.get(i).setMaxforce(myNewForce);
			ptclsList.get(i).setMaxspeed(myNewSpeed);
			
			}
		}

	public void pathReactOnObject(){
		
		float mySize = obstclObjList.get(0).ObstclsRepellerList.get(0).radius;
		float myNewPathRadius = map(mySize,10,100,10f,200f);
		path.setRadius(myNewPathRadius);

		
	}
//		this is to apply coustum forces via keystroke
		public void keyPressed() {
			  if (key == 'd') {
//				do something fancy
			  }
			  
			    if( key==CODED ){
			        if( keyCode == UP ){ 
			        	myForce += 0.1f;
			        }
			        if( keyCode == DOWN ){ 
			        	myForce -= 0.1f;
			        }
			        if( keyCode == LEFT ){ 
			        	mySpeed += 0.1f;
			        }
			        if( keyCode == RIGHT ){ 
			        	mySpeed -= 0.1f;
			        }
			    }
			}

			
		
			public void keyReleased(){
				
//				 not important for the main programm
				if (key == 's' || key == 'S') {
					saveFrame("./data/MyImg"+time+".jpg");
					
				}	
				if (key == 'e' || key == 'E') {
				exit();			
				}
				if (key == 'i' || key == 'I') {
					writeImg = true;
				}
				if (key == 'o' || key == 'O') {
					writeImg = false;
				}
				
				
			}
			public void mousePressed() {
//			  newPtkl(mouseX,mouseY,ptclsList);
				
				for (int i = 0; i < repellers.size(); i++) {
				    Repeller r = (Repeller) repellers.get(i); 
				    r.clicked(mouseX,mouseY);
				  }
			}
			
			public void mouseReleased() {
				  for (int i = 0; i < repellers.size(); i++) {
				    Repeller r = (Repeller) repellers.get(i); 
				    r.stopDragging();
				  }
				}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { tmnuelaerm.TmnUELaerm.class.getName() });
	}
}
