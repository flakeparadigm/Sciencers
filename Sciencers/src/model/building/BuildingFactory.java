package model.building;

import java.awt.Point;
import java.lang.reflect.Constructor;

import model.Entity;

public class BuildingFactory {
	
	public static Entity makeBuilding(String buildType, Point p) {
		
		Class<?> clazz;
		try {
			clazz = Class.forName("model.building." + buildType);
			Constructor<?> ctor = clazz.getConstructor(Point.class);
			Entity ent = (Entity) ctor.newInstance(new Object[] { p });
			return ent;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
