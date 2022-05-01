package model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Target {

	private PropertyChangeSupport support;
	private Point position;
	
	public Point getPosition() {
		return position;
	}
	
	public Target(Point initialPosition) {
		support = new PropertyChangeSupport(this);
		position = initialPosition;
	}
	
	public void addListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void changeLocation(Point newPosition) {
		if (!position.equals(newPosition))
		{
			var oldPosition = position;
			position = newPosition;
			support.firePropertyChange("target position", oldPosition, newPosition);
		}
	}

}
