package particleSystem;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import tmnuelaerm.TmnUELaerm;

public class ParticleSystem {
		PApplet p;
	  ArrayList <Particle> particles;    // An arraylist for all the particles
	  public PVector origin;        // An origin point for where particles are birthed
	  Path path;

	  public ParticleSystem(PApplet p_, int num, PVector v, ArrayList<Particle> particles_,Path path_) {
	    particles = particles_;              // Initialize the arraylist
	    p = p_;
	    path = path_;
	    origin = v.get();                        // Store the origin point
	    for (int i = 0; i < num; i++) {
	      particles.add(new Particle(p,origin));    // Add "num" amount of particles to the arraylist
	    }
	  }

	  

	  // A function to apply a force to all Particles
	  void applyForce(PVector f) {
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      ptcl.applyRepellForce(f);
	    }
	  }

	  // A function for particles to interact with all Repellers
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

	  // A function for particles to interact with all Repellers that are near to the repeller
	  public void myApplyRepellers(ArrayList<Repeller> repellers) {
	    // For every Particle
	    for (int i = 0; i < particles.size(); i++) {
	      Particle ptcl = (Particle) particles.get(i);
	      // For every Repeller
	      for (int j = 0; j < repellers.size(); j++) {
	        Repeller r = (Repeller) repellers.get(j);
	        // Calculate and apply a force from Repeller to Particle
	        
	        float d = ptcl.loc.dist(r.loc);
	        float distToCenterPS = ptcl.loc.dist(origin);
	        float n = p.norm(distToCenterPS,0,p.width/2f);

	        if(d < r.getRadius()+50){
		        PVector repel = r.pushParticle(ptcl);        
		        ptcl.applyRepellForce(repel);
	        ptcl.setMaxforce(d/10);
	        ptcl.setGravity(r.getRadius()*(n*1));
	        ptcl.setMaxspeed(n*10f);
//	        ptcl.setMaxforce(r.getG()*n);
//	        }else if(d < r.getRadius()+5){	
//		        PVector repel = r.pushParticle(ptcl);        
//		        ptcl.applyRepellForce(repel);
	        }else{
//		        ptcl.setMass((n*2));

//		        ptcl.resetMass();
//		        ptcl.resetGravity();
	        	
	        }
	      }
	    }
	  }
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

	  public void addParticleEmitter(boolean pointOrigin) {
		  
		  Particle ptcl;
		  
	  if(pointOrigin){
		   ptcl = new Particle(p,origin);

		  }else {
		
			  PVector myOrigin = new PVector(p.random(p.width),p.random(p.height));

		  ptcl = new Particle(p,myOrigin);
		  ptcl.setMaxspeed(0.03f);
		  ptcl.setRadius(p.random(2));
		  
		  }
		  ptcl.setLifeTime(p.random(23,42));
		  ptcl.setMaxspeed(0.7f);
		  ptcl.setRadius(p.random(2));
		  particles.add(ptcl);
	  }
	  
	  
  public PVector setEmitterOrigin(PVector newOrigin){
		  
	  origin = newOrigin;
	  return origin;
	  }

	  void addParticle(Particle ptcl) {
	    particles.add(ptcl);
	  }

	  // A method to test if the particle system still has particles
	  boolean dead() {
	    if (particles.isEmpty()) {
	      return true;
	    } 
	    else {
	      return false;
	    }
	  }

}
