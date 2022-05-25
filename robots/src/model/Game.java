package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.swing.Timer;


public class Game{
	private final LinkedList<Everything> obstacles; // add walls on border
	private final LinkedList<Everything> movableObjects;
	private final PropertyChangeSupport support;
	private final Timer timer;
	public Game(int width, int height) {
		obstacles = new LinkedList<Everything>();
		movableObjects = new LinkedList<Everything>();
		obstacles.add(new Obstacle(List.of(new Point(0, 0),
										   new Point(width, 0),
										   new Point(width, height),
										   new Point(0, height))));

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
					object.onCollision(obstacle);
			}
			for (Everything movable : movableObjects) {
				if (movable != object) {
					if (checkCollision(object, movable)) {
						object.onCollision(movable);
						movable.onCollision(object);
					}
				}
			}
		}
		support.firePropertyChange("step", null, null);
	}


	private Iterable<SegmentEquation> pointsToSegments(Iterable<Point> vertices) {
		final Point[] prev = {null, null};
		return Stream.concat(StreamSupport.stream(vertices.spliterator(), false).map(v -> {
			if (prev[0] == null) {
				prev[1] = v;
				return null;
			}
			var result = new SegmentEquation(prev[0], v);
			prev[0] = v;
			return result;
		}), Stream.of(new SegmentEquation(prev[0], prev[1]))).toList();
	}
	private boolean checkCollision(Everything object, Everything obstacle) {

		var objBorder = pointsToSegments(object.getFullBorders());
		var obstacleBorder = pointsToSegments(obstacle.getFullBorders());
		for (var objectSegment : objBorder) {
			for (var obstacleSegment : obstacleBorder) {
				if (objectSegment.hasIntersection(obstacleSegment))
					return true;
			}
		}
		return false;
	}

}
