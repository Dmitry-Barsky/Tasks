package model;


import log.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class Robot implements Everything, PropertyChangeListener {

	private Point position;
	private Point speed;
	private Point target;
	private double direction;
	private static final double maxVelocity = 0.1;
	private static final double maxAngularVelocity = 0.01;
	private static final int length = 10;
	private static final int Width = 5;
	
	
	
	public Robot(Point initialPosition, Target target) {
		this.position = initialPosition;
		this.target = target.getPosition();
		target.addListener(this);
	}
	
	@Override
	public void step() {
		double distance = distance(target.x, target.y, 
				position.x, position.y);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(position.x, position.y, target.x, target.y);
        double angularVelocity = 0;
        double angle = asNormalizedRadians(angleToTarget - direction);
        if (angle > Math.PI)
        	angle -= Math.PI*2;
        if (angle > 0)
        	angularVelocity = maxAngularVelocity;
        else if (angle < 0)
        	angularVelocity = -maxAngularVelocity;
        moveRobot(velocity, angularVelocity, 10);
	}

    @Override
    public void onCollision(Everything other) {
        Logger.debug("Collision");
    }


    @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (("target position".equals(evt.getPropertyName())) && (evt.getNewValue() instanceof Point))
		    target = (Point) evt.getNewValue();
	}
	
	private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    
    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
	
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
    
    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = position.x + velocity / angularVelocity * 
            (Math.sin(direction  + angularVelocity * duration) -
                Math.sin(direction));
        if (!Double.isFinite(newX))
        {
            newX = position.x + velocity * duration * Math.cos(direction);
        }
        double newY = position.y - velocity / angularVelocity * 
            (Math.cos(direction  + angularVelocity * duration) -
                Math.cos(direction));
        if (!Double.isFinite(newY))
        {
            newY = position.y + velocity * duration * Math.sin(direction);
        }
        position = new Point(round(newX), round(newY));
        direction = asNormalizedRadians(direction + angularVelocity * duration);
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }
    
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

	@Override
	public Iterable<Point> getFullBorders() {
		Point frontLeft = new Point(
				round(position.x + length/2.0 * Math.cos(direction) + Width/2.0 *Math.cos(direction + Math.PI /2)), 
				round(position.y + length/2.0 * Math.sin(direction) + Width/2.0 *Math.sin(direction + Math.PI /2)));
		
		Point frontRight = new Point(
				round(position.x + length/2.0 * Math.cos(direction) - Width/2.0 *Math.cos(direction + Math.PI /2)), 
				round(position.y + length/2.0 * Math.sin(direction) - Width/2.0 *Math.sin(direction + Math.PI /2)));
		
		Point backLeft = new Point(
				round(position.x - length/2.0 * Math.cos(direction) + Width/2.0 *Math.cos(direction + Math.PI /2)), 
				round(position.y - length/2.0 * Math.sin(direction) + Width/2.0 *Math.sin(direction + Math.PI /2)));
		
		Point backRight = new Point(
				round(position.x - length/2.0 * Math.cos(direction) - Width/2.0 *Math.cos(direction + Math.PI /2)), 
				round(position.y - length/2.0 * Math.sin(direction) - Width/2.0 *Math.sin(direction + Math.PI /2)));
		
		return Arrays.asList(
                frontLeft,
                frontRight,
                backRight,
                backLeft
        );
	}

	@Override
	public Point getMaxBorder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getMinBorder() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
