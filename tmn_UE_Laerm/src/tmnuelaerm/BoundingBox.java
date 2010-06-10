package tmnuelaerm;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class BoundingBox extends PApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1047107748121172490L;
	
	ArrayList points;

	BoundingBox() {
		points = new ArrayList();
	}

	public void display() {
		stroke(0);
		strokeWeight(1);
		beginShape();
		for (int i = 0; i < points.size(); i++) {
			Point point = (Point) points.get(i);
			vertex(point.getX(), point.getY());
		}
		endShape();
		
		noStroke();
	}

	void addPoint(Point point) {
	points.add(point);
	}

	Point getPoint(int index) {
		return (Point) points.get(index);
	}
	
	public void translate(float x, float y) {
		PVector v = new PVector(x, y);
		
		for (int i = 0; i < points.size(); i++) {
			Point point = (Point) points.get(i);
			point.translate(x, y);
		}
	}
	
	public void rotate(float angle) {
		for (int i = 0; i < points.size(); i++) {
			Point point = (Point) points.get(i);
			point.rotate(angle);
		}
	}
	
	public int length() {
		return points.size();
	}

	/**
	* Checks whether a point is inside a polygon.
	*/
	boolean contains(float x, float y) {
		boolean inside = false;
		
		for (int i = 0, j = this.length()-1; i < this.length(); j = i++) {
			Point pi = this.getPoint(i);
			Point pj = this.getPoint(j);
			if ((((pi.getY() <= y) && (y < pj.getY())) ||
					((pj.getY() <= y) && (y < pi.getY()))) &&
					(x < (pj.getX() - pi.getX()) * (y - pi.getY()) / (pj.getY() - pi.getY()) + pi.getX())) {
				inside = !inside;
			}
		}
		return inside;
	}

	/**
	* Checks whether a point is inside a polygon.
	*/
	boolean contains(Point point) {
		return contains(point.x, point.y);
	}
}
