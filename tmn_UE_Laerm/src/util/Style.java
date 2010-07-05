package util;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * Here we make all the color and text stuff centralized. Its like a CSS
 * @author fabiantheblind
 * 
 */
public class Style {
	
	/**
	 * the PApplet
	 */
	private static PApplet p;

	/**
	 * Color 1
	 */
	public static int col1;
	/**
	 * Color 2
	 */
	public static int col2; 
	/**
	 * Color 3
	 */
	public static int col3;
	
	public static int tmn_green;
	
	
	/**
	 * If you wan't the Text white
	 */
	public static int textColorWhite;

	/**
	 * if u want it black
	 */
	public static int textColorBlk;

	/**
	 * the color of the clearscreen
	 * @see <a href="../tmnuelaerm/TmnUELaerm.html#clearScreen()"><code>TMN_UE_Laerm.clearScreen()</code></a>
	 */
	public static int clsColor;
	
	/**
	 * to set the PApplet for the whole Class
	 * @param _p
	 */
	public static void setPApplet(PApplet _p){
		p = _p;
	 }
	
	public static PFont MisoBold18;
	public static PFont MisoBold36;
	public static PFont MisoBold72;
	public static PFont MisoBold144;

	
/**
 * Create all colors and stuff
 */
public static void create(){
		
//		colors
	col1 = p.color(0,100,0,100);
	col2 = p.color(0,100,0,23); 
	col3 = p.color(360,0,100,100);
	
	tmn_green = p.color(107,100,100);

	textColorWhite = p.color(255,0,100,100);
	textColorBlk = p.color(255,0,0,100);
	
	clsColor = p.color(255,0,100,100);
	
	MisoBold18 = p.createFont("Miso-Bold", 18);
	MisoBold36 = p.createFont("Miso-Bold", 36);
	MisoBold72 = p.createFont("Miso-Bold", 72);
//	MisoBold144 = p.createFont("Miso-Bold", 144);
	
	}

}
