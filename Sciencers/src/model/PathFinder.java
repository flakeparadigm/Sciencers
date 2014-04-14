package model;

import java.util.ArrayList;
import java.util.List;

// based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
// and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE
public class PathFinder {

	private List<PathFinderNode> openList = new ArrayList<PathFinderNode>();
	private List<PathFinderNode> closedList = new ArrayList<PathFinderNode>();
	private PathFinderNode startNode, targetNode, checkingNode;

	// Magic Numbers
	private final int MOVEMENT_COST = 10;

}
