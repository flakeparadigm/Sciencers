package model.task;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

import controller.InfoObserver;
import model.agent.AgentReplacement;
import model.agent.EAgent;
import model.agent.FarmerAgent;
import model.agent.MinerAgent;
import model.agent.SciencerAgent;

/**
 * Collection for class Task
 * Ranks Tasks in priority order for completion by Agents. Basically a mediator between Buildings and Agents
 */

public class TaskList {
	
	private static Queue<Task> genericTasks = new ArrayDeque<Task>();
	private static Queue<Task> farmerTasks = new ArrayDeque<Task>();
	private static Queue<Task> minerTasks = new ArrayDeque<Task>();
	private static Queue<Task> sciencerTasks = new ArrayDeque<Task>();
	
	public static void addTask(Task t, EAgent agentType) {
		if (agentType == EAgent.FARMER){
			farmerTasks.add(t);
		} else if (agentType == EAgent.MINER){
			minerTasks.add(t);
		} else if (agentType == EAgent.SCIENCER) {
			sciencerTasks.add(t);
		} else {
			genericTasks.add(t);
		}
		InfoObserver.updateObserver();
	}
	
	public static void addTask(Task t) {
		addTask(t, null);	
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
	
	public static Queue<Task> getList(EAgent agentType) { //be nice to phase this out
		if (agentType.equals(EAgent.FARMER)){
			return farmerTasks;
		} else if (agentType.equals(EAgent.MINER)){
			return minerTasks;
		} else if (agentType.equals(EAgent.SCIENCER)) {
			return sciencerTasks;
		} else {
			return genericTasks;
		}
	}
	
	public static Queue<Task> getList(Class<?> clazz) {
		if(clazz == FarmerAgent.class) {
			return farmerTasks;
		} else if(clazz == MinerAgent.class) {
			return minerTasks;
		} else if(clazz == SciencerAgent.class) {
			return sciencerTasks;
		} else {
			return genericTasks;
		}
	}
	
	public static void emptyList() {
		genericTasks = new ArrayDeque<Task>();
	}
	
}
