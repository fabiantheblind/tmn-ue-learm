package tmnuelaerm;

import java.util.ArrayList;
/*
import TUIO.TuioClient;
import TUIO.TuioCursor;
//import TUIO.TuioProcessing;
*/
import particleSystem.Repeller;
import processing.core.*;


public class ObstacleObject {
	
	public String id;
	
	
	public int coursor01ID = 99;
	public PVector coursor01Pos;
	public PVector newCoursor01Pos;

	public int coursor02ID = 99;
	public PVector coursor02Pos;
	public PVector newCoursor02Pos;
	
	public PVector obstclSize;
	
	public float obstclXpos = 0;
	public float obstclYpos = 0;
	
	public PVector obstclTrans;
	
	public float obstclRotate = 0;
	
	public BoundingBox boundingBox;
	
	public Point bounds1;
	public Point bounds2;
	public Point bounds3;
	public Point bounds4;
	
	public float boundsX1;
	public float boundsX2;
	public float boundsY1;
	public float boundsY2;
	
//	public Repeller repeller01;
	public ArrayList <Repeller> ObstclsRepellerList;
	
	
	public float scale = 1;
	public PShape svg;
	public PApplet pa;
	public String obstclName;
	
	public boolean active = false;

	public PVector offSet;
	public float offsetX = 0;
	public float offsetY = 0;
	
	public ObstacleObject(PApplet _pa, int _id, PVector _trans) {
		
		obstclTrans = _trans;
		
		pa = _pa;
		id = PApplet.nf(_id,2);
		obstclName = "Object" + id + ".svg";
		
		ObstclsRepellerList = new ArrayList<Repeller>();
		ObstclsRepellerList.add(new Repeller(pa, obstclTrans));
//		repeller01 = new Repeller(pa, obstclTrans);
		ObstclsRepellerList.get(0).setG(pa.pow(10,3));
//		repeller01.setG(pa.pow(10,3));

		
		svg = pa.loadShape(obstclName);
		svg.disableStyle();

		pa.shapeMode(pa.CENTER);
		/*
		obstclWidth = svg.width;
		obstclHeight = svg.height;
		*/
		obstclSize = new PVector(svg.width, svg.height);
				
		boundingBox();
	}
	
	public void draw(){
		
		pa.pushMatrix();
		
		setTranslation();
		
		if(coursor01ID < 99){
			
			pa.fill(255);
			
			if(coursor02ID <99){
				
				setScale();
			}
		}else{
			pa.fill(255,200);
		}
		
		pa.noStroke();
		//setRotation();

		pa.shape(svg, obstclXpos, obstclYpos, obstclSize.x, obstclSize.y);
		boundingBox.rotate(obstclRotate);
		//boundingBox.display();

		pa.popMatrix();
		
		setSize();
		boundingBox();
		pa.stroke(255);
		pa.noFill();
		
		ObstclsRepellerList.get(0).display();

		scale = 1;
		coursor01Pos = newCoursor01Pos;
		coursor02Pos = newCoursor02Pos;
	}
	
	public void setTranslation(){
		
		pa.translate( obstclTrans.x, obstclTrans.y);
		
	}
	
	public void setRotation(){
		
		if (coursor01Pos != null && coursor02Pos != null && newCoursor01Pos != null && newCoursor02Pos != null){

			PVector v01 = PVector.sub(coursor01Pos, coursor02Pos);
						
			float theta01 = PVector.angleBetween(v01,obstclTrans);
			
			PVector v02 = PVector.sub(newCoursor01Pos, newCoursor02Pos);
			
			float theta02 = PVector.angleBetween(v02,obstclTrans);
			
			obstclRotate += (theta02-theta01);
			
			pa.rotate(obstclRotate);
		}
	}
	
	public void setOffset(PVector nowPos){
		
		offSet = PVector.sub(nowPos,obstclTrans);
	}
	
	public void setScale(){
		
		if (coursor01Pos != null && coursor02Pos != null && newCoursor01Pos != null && newCoursor02Pos != null){
		float s1 = PVector.dist(coursor01Pos, coursor02Pos);
		float s2 = PVector.dist(newCoursor01Pos, newCoursor02Pos);
		scale = s2 / s1;
		
		}
	}
	
	public void setSize(){
		
		obstclSize.mult(scale);
		ObstclsRepellerList.get(0).radius *= scale;
		ObstclsRepellerList.get(0).G *= scale;
		
	}
	
	public void move(PVector nowPos){
		
		obstclTrans = PVector.sub(nowPos, offSet);
		ObstclsRepellerList.get(0).update(obstclTrans);
	}

	public void boundingBox(){
		
		boundsX1 = obstclTrans.x - obstclSize.x/2;
		boundsX2 = obstclTrans.x + obstclSize.x/2;
		boundsY1 = obstclTrans.y - obstclSize.y/2;
		boundsY2 = obstclTrans.y + obstclSize.y/2;
		
		bounds1 = new Point(boundsX1, boundsY1);
		bounds2 = new Point(boundsX1, boundsY2);
		bounds3 = new Point(boundsX2, boundsY2);
		bounds4 = new Point(boundsX2, boundsY1);
		
		boundingBox = new BoundingBox();

		
		boundingBox.addPoint(bounds1);
		boundingBox.addPoint(bounds2);
		boundingBox.addPoint(bounds3);
		boundingBox.addPoint(bounds4);

		//pa.println(boundingBox.points);
		
	}
}