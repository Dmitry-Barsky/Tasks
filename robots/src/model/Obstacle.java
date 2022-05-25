package model;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Obstacle implements Everything
{
    private List<Point> border;

    public Obstacle(Collection<Point> borders) {
        this.border = borders.stream().toList();
    }

    @Override
    public void step() {
    }

    @Override
    public void onCollision(Everything other) {
    }

    @Override
    public Iterable<Point> getFullBorders() {
        return border;
    }

    @Override
    public Point getMaxBorder() {
        return null;
    }

    @Override
    public Point getMinBorder() {
        return null;
    }
}
