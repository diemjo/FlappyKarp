import processing.core.PApplet;
import processing.core.PImage;

public class Digda {
	static PImage imageDefault;
	static PImage imageFlipped;
	static int numberOfDigdas;
	static int space;
	static int width, height;
	static float picOffset;
	PApplet parent;
	int locX;
	int rand;
	
	public Digda(PApplet parent, int rand) {
		this.parent = parent;
		this.rand = rand;
		width = parent.width;
		height = parent.height;
		locX = width+imageDefault.width/2;
	}
	
	public void display() {
		parent.noStroke();
		parent.image(imageDefault, locX-imageDefault.width/2, rand+space/2);
		brown(locX-imageDefault.width/2, rand+space/2+imageDefault.height, imageDefault.width, height-rand+space/2+imageDefault.height);
		parent.image(imageFlipped, locX-imageDefault.width/2, rand-space/2-imageFlipped.height);
		brown(locX-imageDefault.width/2, 0, imageDefault.width, rand-space/2-imageDefault.height);
	}
	
	private void brown(int x, int y, int w, int h) {
		parent.fill(139, 86, 49);
		parent.rect(x, y, w*picOffset, h);
		parent.fill(130, 82, 40);
		parent.rect(x+w*picOffset, y, w*(1-picOffset), h);
	}
}
