package model;


public interface Everything { // this name is awful

	public void step(); // action on step

	public void onCollision(Everything other);
	public Iterable<Point> getFullBorders();
	
	public Point getMaxBorder();
	
	public Point getMinBorder();
}
