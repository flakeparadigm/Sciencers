package model.agentCommand;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Collection for class Task
 * Ranks Tasks in priority order for completion by Agents. Basically a mediator between Buildings and Agents
 */

public class TaskList {
	
	private static Queue<Task> tasks = new PriorityQueue<Task>();
	
	public static void addTask(Task t) {
		tasks.add(t);
	}
	
	public static Task assignTask() {
		return tasks.poll();
	}
}
