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
import processing.core.PVector;
import tmnuelaerm.ObstacleObject;
import processing.core.PApplet;


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

	public void setup() {
		
		size(500,400);
		
		frameRate(25);

		//PDXIII TUIO Stuff
		// enable on system installed fonts
//		hint(ENABLE_NATIVE_FONTS);
//		font = createFont("Gentium", 18);
//		textFont(font,18);

		//init TUIO
//		tuioClient.addTuioListener(this);
//		tuioClient.connect();

		
//		obstclObjList = new ArrayList<ObstacleObject>();
		
		// making ObstacleObjects
//		for (obstclCounter = 0; obstclCounter < 2; obstclCounter++){
//			int obstclNo = obstclCounter + 1;
//			float firstX = obstclNo*150;
//			float firstY = height/2;
//			PVector obstclPos = new PVector (firstX, firstY);
//			obstclObjList.add(new ObstacleObject(this, obstclNo, obstclPos));
//			
//		}
		
	}

	public void draw() {
		
		background(125);
		
		smooth();
		
		
		//PDXIII TUIO Stuff
		//just for adjustment
//		drawGrid();
//		
//		tuioCursorList = new ArrayList<TuioCursor> (tuioClient.getTuioCursors());
//		
//		drawObstacleObjects();
//		
//		drawCursors();
//		
//		noStroke();
//		fill(0);
//		text(tuioCursorList.size(), 50, 50);
//		noFill();
	}
	
	//PDXIII TUIO Stuff

//	public void drawObstacleObjects(){
//		
//		for(int i = 0; i < obstclObjList.size(); i++){
//			
//			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(i);
//			obstclObject.draw();
//		}
//	}
//	
//	public void drawCursors(){
//		
//		for (int i=0; i<tuioCursorList.size(); i++) {
//			TuioCursor tcur = (TuioCursor)tuioCursorList.get(i);
//			
//			stroke(100,255,255);
//			noFill();
//			ellipse( tcur.getScreenX(width), tcur.getScreenY(height),10,10);
//		}
//	}
//	

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
				
			}else if(obstclObject.coursor01ID == nowID && obstclObject.coursor02ID < 99){
				
				obstclObject.newCoursor01Pos = nowPos;
				
			}else if(obstclObject.coursor02ID == nowID){
				
				obstclObject.newCoursor02Pos = nowPos;
				
			
			}else if(obstclObject.boundingBox.contains(nowX, nowY)){
				
				if(obstclObject.coursor01ID < 99){
					
					obstclObject.coursor02ID = nowID;
					obstclObject.coursor02Pos = nowPos;
					
				}else{
					
					obstclObject.coursor01ID = nowID;
					obstclObject.coursor01Pos = nowPos;

					obstclObject.setOffset(nowPos);
					
				}
			}
		}
	}
	
	@Override
	public void removeTuioCursor(TuioCursor arg0) {
		
		int nowID = arg0.getCursorID();

		for(int j = 0; j < obstclObjList.size(); j++){
			
			ObstacleObject obstclObject = (ObstacleObject) obstclObjList.get(j);
			
			if(obstclObject.coursor01ID == nowID){

				obstclObject.coursor01ID = obstclObject.coursor02ID; 
				
				//obstclObject.coursor01Pos = obstclObject.coursor02Pos;
				obstclObject.coursor02ID = 99;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { tmnuelaerm.TmnUELaerm.class.getName() });
	}
}
