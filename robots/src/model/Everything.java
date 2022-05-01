package model;

import java.awt.Point;

public interface Everything { // this name is awful

	public void step(); // action on step

	public void onCollision();
	
	public void getFullBorders();
	
	public Point getMaxBorder();
	
	public Point getMinBorder();
}
