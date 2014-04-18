package model;

import java.awt.Dimension;
import java.awt.Point;

public interface Entity {
	public void update();
	public Point getPos();
	public Dimension getSize();
}
