package model;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Timer;



public class Game{

	private LinkedList<Everything> obstacles; // add walls on border
	private LinkedList<Everything> movableObjects;
	
	
	private final PropertyChangeSupport support;
	private final Timer timer;
	/*public Map(File source) {
		
	}
	*/
	public Game() {
		support = new PropertyChangeSupport(this);
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
	}
	
	public void gameStart() {
		timer.start();
	}
	
	
	public void gameStop() {
		timer.stop();
	}

	public void addListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void addMovableObject(Everything newObject) {
		this.movableObjects.add(newObject);
	}
	
	public void removeObject(Everything object) {
		this.movableObjects.remove(object);
		this.obstacles.remove(object);
	}
	
	public void addObstacle(Everything obstacle) {
		this.obstacles.add(obstacle);
	}
	
	public void step() {
		for (Everything object : movableObjects) {
			object.step();
		}
		
		for (Everything object : movableObjects) {
			for (Everything obstacle : obstacles) {
				if (checkCollision(object, obstacle))
					object.onHit();
			}
			for (Everything movable : movableObjects) {
				if (movable != object) {
					if (checkCollision(object, movable))
						object.onHit();
						movable.onHit();
				}
			}
		}
		support.firePropertyChange("step", null, null);
	}

	private boolean checkCollision(Everything object, Everything obstacle) {
		// TODO some strange math
		return false;
	}
}
