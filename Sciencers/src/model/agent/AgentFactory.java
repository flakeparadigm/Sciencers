package model.agent;

import java.awt.Point;
import java.lang.reflect.Constructor;

import model.Entity;
import model.World;

public class AgentFactory {
	
	public static Entity makeAgent(String agentType, Point p) {
		
		Point fixedP = new Point(p.x, World.terrain.getAltitude(p.x) - 1);
		
		Class<?> clazz;
		try {
			clazz = Class.forName("model.agent." + agentType + "Agent");
			Constructor<?> ctor = clazz.getConstructor(Point.class);
			Entity ent = (Entity) ctor.newInstance(new Object[] { fixedP });
			return ent;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
