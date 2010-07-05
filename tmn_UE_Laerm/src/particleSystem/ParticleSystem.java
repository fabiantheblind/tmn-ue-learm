package particleSystem;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import tmnuelaerm.ObstacleObject;

/**
 * the whole <code>ParticleSystem</code>
 * here the collision and stuff get worked out
 * based on: <a href="http://www.shiffman.net/teaching/nature/" target="blanc">Daniel Shiffman's Nature of Code</a>
 * @author fabianthelbind
 * @see Particle Class Particle
 * @see Path Class Path
 * @see Repeller Class Repeller
 *
 */
public class ParticleSystem {
	
		/**
		 * the PApplet
		 */
		PApplet p;

		/**
		 * An <code>ArrayList</code> for all the <code>Particle</code>'s
		 */
		ArrayList <Particle> particles;
		
	/**
	 * An origin point for where particles are birthed when using the emitter
	 * @see #addParticleEmitter(boolean)
	 * @see #setEmitterOrigin(PVector)
	 */
	public PVector origin;

	  /**
	   * this is the masterconstructor
	 * @param p  the PApplet
	 * @param num int create at startup a number of particles
	 * @param v the origin of the emitter
	 * @param particles an Arraylist of Particles
	 */
	public ParticleSystem(PApplet p, int num, PVector v, ArrayList<Particle> particles) {
	    this.particles = particles;              // Initialize the arraylist
	    this.p = p;
	    this.origin = v.get();                        // Store the origin point
	    for (int i = 0; i < num; i++) {
	      particles.add(new Particle(p,origin,true,false));    // Add "num" amount of particles to the arraylist
	    }
	  }


	/**
	 * A function to apply a force to all Particles
	 * @param f PVector
	 * @see Particle#applyRepellForce(PVector)
	 */
	void applyForce(PVector f) {
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      ptcl.applyRepellForce(f);
	    }
	  }

	/**
	 * A function for particles to interact with all Repellers
	 * this function is not used in the main programm but still is usefull to have
	 * @param repellers ArrayList
	 * 
	 */
	public void applyRepellers(ArrayList<Repeller> repellers) {
	    // For every Particle
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      // For every Repeller
	      for (int j = 0; j < repellers.size(); j++) {
	        Repeller r = (Repeller) repellers.get(j);
	        // Calculate and apply a force from Repeller to Particle
	        
	        PVector repel = r.pushParticle(ptcl);        
	        ptcl.applyRepellForce(repel);
	      }
	    }
	  }

	  
	  // 
	/**
	 * A function for particles to interact with all <code>Repeller</code>'s that are near to the repeller
	 * @param repellers ArrayList
	 * @see Particle Class Particle
	 */
	public void myApplyRepellers(ArrayList<Repeller> repellers,boolean day) {
	    // For every Particle
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      
	        float distToCenterPS = ptcl.loc.dist(origin);
	        float n = PApplet.norm(distToCenterPS,0,p.width/2f);
	        ptcl.setMass(n);
	      // For every Repeller
	     

	      for (int j = 0; j < repellers.size(); j++) {
	        Repeller r = repellers.get(j);
	        // Calculate the distance from a Repeller to the particle
	        float d = ptcl.loc.dist(r.loc);
	        	
	        	PVector repel = new PVector(0,0);
	        	
	        	 if(d <= r.getRadius()){
	        		 
	        		 
//	        		 this is in private space
	        		 if((ptcl.pathNum == 0)|| (ptcl.pathNum == 1) ||(ptcl.pathNum == 2)){
	        			 
	        			 
	        			 if(day){
//			        		at daytime
	        				 	if((r.property.valueByIndex(0,0) > 1) || (r.property.valueByIndex(0,0) < -1)){
	        				 		
	        				 	repel = r.pushParticle(ptcl);
				      		    ptcl.applyRepellForce(repel);
				    	        ptcl.setColorCol2(40, 100, 100, 100); 			 				    	      
//				    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//				    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
				    	        
	        				 	}else if(((r.property.valueByIndex(0,0) < 2)&&(r.property.valueByIndex(0,0) > -2))&&((r.property.valueByIndex(0,0)!=0))){
	        				 		
	        				 	repel = r.pushParticle(ptcl);
				      		    ptcl.applyRepellForce(repel);
				    	        ptcl.setColorCol2(80, 100, 100, 100); 			 				    	      
//				    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//				    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

	        				 	}else if((r.property.valueByIndex(0,0) == 0)){
//	        				 		do nothing
					    	        ptcl.setColorCol2(120, 100, 100, 100); 			 				    	      

	        				 	}
	        				 	
	        				 	
	        			 }else{
//	        				 at nite
	        				 	if((r.property.valueByIndex(1,0) > 1) || (r.property.valueByIndex(1,0) < -1)){
	        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(160, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
					    	        
		        				 	}else if(((r.property.valueByIndex(1,0) < 2)&&(r.property.valueByIndex(1,0) > -2))&&((r.property.valueByIndex(1,0)!=0))){
		        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(200, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

		        				 	}else if((r.property.valueByIndex(1,0) == 0)){
//		        				 		do nothing
						    	        ptcl.setColorCol2(240, 100, 100, 100); 			 				    	      

		        				 	} 
	        				 
	        				 
	        				 
	        			 }
//	        			 this is in public space
	        		 }else if ((ptcl.pathNum == 3)|| (ptcl.pathNum == 4) ||(ptcl.pathNum == 5)){
	        			 
//	        			 at Daytime
	        			 if(day){
	        				 	if((r.property.valueByIndex(0,1) > 1) || (r.property.valueByIndex(0,1) < -1)){
	        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(280, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
					    	        
		        				 	}else if(((r.property.valueByIndex(0,1) < 2)&&(r.property.valueByIndex(0,1) > -2))&&((r.property.valueByIndex(0,1)!=0))){
		        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(320, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

		        				 	}else if((r.property.valueByIndex(0,1) == 0)){
//		        				 		do nothing
						    	        ptcl.setColorCol2(360, 100, 100, 100); 			 				    	      

		        				 	}
	        			 }else{
//	        				 at nite
	        				 	if((r.property.valueByIndex(1,1) > 1) || (r.property.valueByIndex(1,1) < -1)){
	        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(40, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
					    	        
		        				 	}else if(((r.property.valueByIndex(1,1) < 2)&&(r.property.valueByIndex(1,1) > -2))&&((r.property.valueByIndex(1,1)!=0))){
		        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(80, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

		        				 	}else if((r.property.valueByIndex(1,1) == 0)){
//		        				 		do nothing
						    	        ptcl.setColorCol2(120, 100, 100, 100); 			 				    	      

		        				 	}
	        				 
	        			 }
//	        			 this is in work space
	        		 }else if((ptcl.pathNum == 6)|| (ptcl.pathNum == 7) ||(ptcl.pathNum == 8)){
//	        			 at Daytime
	        			 if(day){
	        				 	if((r.property.valueByIndex(0,2) > 1) || (r.property.valueByIndex(0,2) < -1)){
	        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(40, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
					    	        
		        				 	}else if(((r.property.valueByIndex(0,2) < 2)&&(r.property.valueByIndex(0,2) > -2))&&((r.property.valueByIndex(0,2)!=0))){
		        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(80, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

		        				 	}else if((r.property.valueByIndex(0,2) == 0)){
//		        				 		do nothing
						    	        ptcl.setColorCol2(120, 100, 100, 100); 			 				    	      

		        				 	}
	        				 
	        			 }else{
//	        				 at nite
			 	if((r.property.valueByIndex(1,2) > 1) || (r.property.valueByIndex(1,2) < -1)){
	        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(40, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));
					    	        
		        				 	}else if(((r.property.valueByIndex(1,2) < 2)&&(r.property.valueByIndex(1,2) > -2))&&((r.property.valueByIndex(1,2)!=0))){
		        				 		
		        				 	repel = r.pushParticle(ptcl);
					      		    ptcl.applyRepellForce(repel);
					    	        ptcl.setColorCol2(80, 100, 100, 100); 			 				    	      
//					    	        ptcl.setMaxforce(ptcl.maxforce + r.property.valueByIndex(0, 0));
//					    	        ptcl.setMaxspeed(ptcl.maxspeed + r.property.valueByIndex(0, 0));

		        				 	}else if((r.property.valueByIndex(1,2) == 0)){
//		        				 		do nothing
						    	        ptcl.setColorCol2(120, 100, 100, 100); 			 				    	      

		        			}
	        			 }
	        		 }
	        		 
	        	 
	        	 
	        	 }else{
//	        		 if the distance his higher than the Repeller's radius
//	        		do nothing 
	        		 
	        }
	      }
	    }
	  }
	  
	   
	/**
	 * A function for particles to interact with all Repellers that are near to the repeller
	 * @param ObstclsList ArrayList
	 * @see #myApplyRepellers(ArrayList)
	 * @see tmnuelaerm.ObstacleObject#ObstclsRepellerList
	 */
	public void myApplyObstcles(ArrayList<ObstacleObject> ObstclsList) {
	    // For every Particle
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      
	        float distToCenterPS = ptcl.loc.dist(origin);
	        float n = PApplet.norm(distToCenterPS,0,p.width/2f);
	        ptcl.setMass(n);

	
//	        for every Obstacle
	     for(int j = 0; j < ObstclsList.size();j++ ){
	        
	    	 if(ObstclsList.get(j).ObstclsRepellerList != null){
	    	 ArrayList<Repeller> repellersList = ObstclsList.get(j).ObstclsRepellerList;
	    	 
		      // For every Repeller

	      for (int k = 0; k < repellersList.size(); k++) {
	        Repeller r = (Repeller) repellersList.get(k);
	        // Calculate and apply a force from Repeller to Particle
	        
	        float d = ptcl.loc.dist(r.loc);


	        
	        if(d < r.getRadius() /*&& ptcl.affection == true*/){
	        	
	       
	        PVector repel = r.pushParticle(ptcl);
	        	
		    ptcl.applyRepellForce(repel);
	        ptcl.setMaxforce((d/100));
	        ptcl.setGravity((d/100)*0.0001f);
	        ptcl.setMaxspeed((d/10));
	        ptcl.setMass(d/100);
//	        ptcl.setMaxforce(r.getG()*n);
//	        }else if(d < r.getRadius()+5){	
//		        PVector repel = r.pushParticle(ptcl);        
//		        ptcl.applyRepellForce(repel);
	        }else{
		        ptcl.setMass((n));
		        ptcl.resetMass();
		        ptcl.resetGravity();
		        
		        
		        if(ptcl.maxforce>0.2f)ptcl.maxforce = ptcl.maxforce*0.5f;
		        if(ptcl.maxspeed>1.5f)ptcl.maxspeed = ptcl.maxspeed*0.5f;
	        	
	        }
	      }
	     }
	     }
	    }
	  }
	  
	  
	  /**
	   * this runs the ParticleSystem
	 * 
	 */
	public void run() {
	    // Cycle through the ArrayList backwards b/c we are deleting
	    for (int i = particles.size()-1; i >= 0; i--) {
	      Particle ptcl = (Particle) particles.get(i);
	      ptcl.run();
	      if (ptcl.dead()) {
	        particles.remove(i);
	      }
	    }
	  }

	  /**
	   * this is an basic Particle Emitter it is not worked out
	   * if u set the pointOrigin to true it will emit Particles from  the center the window
	   * 
	 * @param pointOrigin boolean
	 * @see Particle#Particle(PApplet, PVector, boolean, boolean)
	 */
	public void addParticleEmitter(boolean pointOrigin) {
		  
		  Particle ptcl;
		  
	  if(pointOrigin){
		   ptcl = new Particle(p,origin,true,false);

		  }else {
		
			  PVector myOrigin = new PVector(p.random(p.width),p.random(p.height));

		  ptcl = new Particle(p,myOrigin,true,false);
		  ptcl.setMaxspeed(0.03f);
		  ptcl.setRadius(p.random(2));
		  
		  }
		  ptcl.setLifeTime(p.random(23,42));
		  ptcl.setMaxspeed(0.7f);
		  ptcl.setRadius(p.random(2));
		  particles.add(ptcl);
	  }
	  
	  
  /**
   * sets a new origin for the Emitter
 * @param newOrigin
 * @return PVector
 */
public PVector setEmitterOrigin(PVector newOrigin){
		  
	  origin = newOrigin;
	  return origin;
	  }


	  /**
	   * adds a particle to the ArrayList particles
	 * @param ptcl
	 * @see #particles
	 */
	void addParticle(Particle ptcl) {
	    particles.add(ptcl);
	  }

	  /**
	   * A method to test if the particle system still has particles
	 * @return boolean
	 */
	boolean dead() {
	    if (particles.isEmpty()) {
	      return true;
	    } 
	    else {
	      return false;
	    }
	  }

}
