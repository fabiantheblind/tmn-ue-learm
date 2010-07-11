/**
 * 
 */
package util;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author fabiantheblind
 * 
 */
public class Overlay {

	PApplet p;
	private PVector center;

	public Overlay(PApplet p) {
		super();
		this.p = p;
		this.center = new PVector(p.width / 2, p.height / 2);
	}

	/**
	 * @return the p
	 */
	public synchronized PApplet getP() {
		return p;
	}

	/**
	 * @param p
	 *            the p to set
	 */
	public synchronized void setP(PApplet p) {
		this.p = p;
	}

	public void initOverlay() {

	}

	public void display() {
		p.smooth();
		for (int i = 2; i < Style.pathsRadius9.length; i+=3) {
			int diam = Style.pathsSize9[i] * 2;
			int outerDiam = diam + Style.pathsRadius9[i] * 2;
			p.noFill();
			p.stroke(Style.superSoftWhite);
			p.ellipse(center.x, center.y, outerDiam, outerDiam);

		}

		p.textFont(Style.Unibody8);
		p.noStroke();
		p.fill(Style.textColorWhite);

		p.text("private", this.center.x ,
				this.center.y + Style.pathsSize9[0]);
		p.text("public", this.center.x,
				this.center.y + Style.pathsSize9[4]);
		p.text("work", this.center.x,
				this.center.y + Style.pathsSize9[7]);
		
	}

	/**
	 * @return the center
	 */
	public synchronized PVector getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public synchronized void setCenter(PVector center) {
		this.center = center;
	}

}
