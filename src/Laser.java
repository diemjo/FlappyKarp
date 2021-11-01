import processing.core.PApplet;

public class Laser {
	PApplet parent;
	int speed = 15;
	int x, y;
	public Laser(PApplet parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	public void display() {
		parent.strokeWeight(5);
		parent.stroke(255,0,0);
		parent.line (x, y, x+20, y);
		parent.strokeWeight(1);
	}
	
	public void update() {
		x += speed;
	}
}
