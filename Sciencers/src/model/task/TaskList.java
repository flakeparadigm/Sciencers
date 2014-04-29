package model.task;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

import model.agent.EAgent;

/**
 * Collection for class Task
 * Ranks Tasks in priority order for completion by Agents. Basically a mediator between Buildings and Agents
 */

public class TaskList {
	
	private static Queue<Task> genericTasks = new ArrayDeque<Task>();
	private static Queue<Task> farmerTasks = new ArrayDeque<Task>();
	private static Queue<Task> minerTasks = new ArrayDeque<Task>();

	
	public static void addTask(Task t, EAgent agentType) {
		if (agentType.equals(EAgent.FARMER)){
			farmerTasks.add(t);
		} else if (agentType.equals(EAgent.MINER)){
			minerTasks.add(t);
		} else {
			genericTasks.add(t);
		}
	}
	
//	public static Task nextTask(EAgent agentType) {
//		if (agentType.equals(EAgent.FARMER)){
//			return farmerTasks.poll();
//		} else if (agentType.equals(EAgent.MINER)){
//			return minerTasks.poll();
//		} else {
//			return genericTasks.poll();
//		}
//	}
	
	public static Queue<Task> getList(EAgent agentType) {
		if (agentType.equals(EAgent.FARMER)){
			return farmerTasks;
		} else if (agentType.equals(EAgent.MINER)){
			return minerTasks;
		} else {
			return genericTasks;
		}
	}
	
	public static void emptyList() {
		genericTasks = new ArrayDeque<Task>();
	}
}
