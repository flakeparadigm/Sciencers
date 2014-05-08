package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.Serializable;

public interface Entity extends Serializable {
	public void update();
	public Point2D getPos();
	public Dimension getSize();
	public boolean isDead();
}
