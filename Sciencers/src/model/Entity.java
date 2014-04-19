package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface Entity {
	public void update();
	public Point2D getPos();
	public Dimension getSize();
}
