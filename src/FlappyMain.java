import java.awt.event.HierarchyBoundsAdapter;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class FlappyMain extends PApplet{
	PImage imageDefault;
	PImage imageFlipped;
	PImage karpador;
	int space = 500;
	float picOffset = 0.8f;
	int numberOfDigdas = 5;
	long time;
	final static float gravity = 0.8f;
	float ySpeed;
	float yMaxSpeed = 20;
	float locY;
	int karpadorSize = 150;
	ArrayList<Digda> digdas; 
	ArrayList<Laser> lasers;
	boolean gameover;
	float gameOverY;
	boolean started=false;

	public static void main(String[] args) {
		String[] a = {"MAIN"};
		PApplet.runSketch(a, new FlappyMain());
	}
	
	public void setup() {
		noStroke();
		imageDefault = loadImage("ressources/diglet_up.png");
		imageFlipped = loadImage("ressources/diglet_down.png");
		karpador = loadImage("ressources/magikarp_right.png");

		Digda.imageDefault = imageDefault;
		Digda.imageFlipped = imageFlipped;
		Digda.height = height;
		Digda.width = width;
		Digda.numberOfDigdas = numberOfDigdas;
		Digda.picOffset = picOffset;
		Digda.space = space;
		locY = height/2;
		reset();
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void draw() {
		background(255);
		if (gameover) {
			pushMatrix();
		}

		if (System.currentTimeMillis()-time>1500 && started && !gameover) {
			addNewDigda();
			time = System.currentTimeMillis();
		}
		
		displayDigdas();
		for (Laser laser : lasers) {
			laser.display();
			laser.update();
		}
		//ellipse(width/2, locY, karpadorSize, karpadorSize);
		image(karpador,width/2-karpadorSize/2, locY-karpadorSize/2, karpadorSize, karpadorSize);
		if (gameover) {
			explosion();
			locY += ySpeed;
			ySpeed += gravity;
			popMatrix();
		}
		if (started && !gameover) {
			locY += ySpeed;
			ySpeed += gravity;
			checkCrash();
			updateDigdas();
		}
		
	}
	
	private void explosion() {
		for (int i=0; i<10; i++) {
			fill(random(200, 255), random(100), random(50));
			ellipse (width/2+random(50)-25, gameOverY+random(50)-25, random(30,100), random(30,100));
		}
		
	}

	private void displayDigdas() {
		if (digdas.size()!=0) {
			int i=0;
			while (digdas.size()>i) {
				Digda dig = digdas.get(i);
				dig.display();
				i++;
			}
		}
	}

	public void updateDigdas() {
		if (digdas.size()!=0) {
			int i=0;
			while (digdas.size()>i) {
				Digda dig = digdas.get(i);
				dig.locX-=10;
				if (dig.locX<-imageDefault.width) {
					digdas.remove(i);
					i--;
				}
				i++;
			}
		}
	}
	
	public void reset() {
		digdas = new ArrayList<Digda>();
		lasers = new ArrayList<Laser>();
		gameover = false;
		started = false;
		ySpeed = 0;
		locY = height/2;
	}
	
	public void checkCrash() {
		if (locY<karpadorSize/2){
			gameover = true;
			gameOverY = locY;
			ySpeed = -10;
			return;
		}
		else if (locY>height-karpadorSize/2) {
			gameover = true;
			gameOverY = locY;
			ySpeed = -10;
			return;
		}
		Digda d = getClosestDigda();
		if (d==null) {
			return;
		}
		if (abs(locY-d.rand)>space/2+imageDefault.height) {
			gameover = true;
			gameOverY = locY;
			ySpeed = -10;
		}
		else if (dist(width/2, locY, d.locX, d.rand+space/2+imageDefault.width/2)<karpadorSize/2+imageDefault.width/2) {
			gameover = true;
			gameOverY = locY;
			ySpeed = -10;
		}
		else if (dist(width/2, locY, d.locX, d.rand-space/2-imageDefault.width/2)<karpadorSize/2+imageDefault.width/2) {
			gameover = true;
			gameOverY = locY;
			ySpeed = -10;
		}
//		rect(0,0,width, imageDefault.height);ellipse(width/2,locY, karpadorSize, karpadorSize);
//		ellipse(width/2,locY, karpadorSize, karpadorSize);
//		ellipse(d.locX, d.rand-space/2-imageDefault.width/2, imageDefault.width, imageDefault.height);
	}
	
	public Digda getClosestDigda() {
		Digda d;
		int index = 0;
		while (index<digdas.size() && digdas.get(index) != null && digdas.get(index).locX+karpadorSize/2<width/2-imageDefault.width/2) {
			index++;
		}
		if (index>=digdas.size()) {
			return null;
		}
		d = digdas.get(index);
		if (abs(d.locX -width/2)>imageDefault.width/2+karpadorSize/2	) {
			return null;
		}
		return d;
		//rect(d.locX-imageDefault.width/2, 0, imageDefault.width, height);
	}
	public void addNewDigda() {
		int randP;
		if (digdas.size()!=0) {
			Digda dOld = digdas.get(digdas.size()-1);
			randP = dOld.rand;
		}
		else {
			randP = width/2;
		}
		int lowerBound = max(imageDefault.height+space/2, randP-height/3);
		int upperBound = min(height-imageDefault.height-space/2, randP+height/3);
		int rand = (int) random(lowerBound, upperBound);
		Digda d = new Digda(this, rand);
		digdas.add(d);
	}
	
	public void keyReleased() {
		if (key == ' ') {
			if (!started) {
				started = true;
			}
			else if (!gameover){
				ySpeed = -yMaxSpeed;
			}
			else {
				reset();
			}
		}
		else if (key == 'l') {
			lasers.add(new Laser(this, width/2, (int)locY));
		}
	}

}
