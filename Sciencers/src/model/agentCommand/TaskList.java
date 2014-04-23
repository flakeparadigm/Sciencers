package model.agentCommand;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Collection for class Task
 * Ranks Tasks in priority order for completion by Agents. Basically a mediator between Buildings and Agents
 */

public class TaskList {
	
	private static Queue<Task> tasks = new ArrayDeque<Task>();
	
	public static void addTask(Task t) {
		tasks.add(t);
	}
	
	public static Task nextTask() {
		return tasks.poll();
	}
	
	public static Queue<Task> getList() {
		return tasks;
	}
}
