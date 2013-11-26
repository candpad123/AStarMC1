package astar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class Map<Axis extends AbstractNode> {


	protected static boolean CANMOVEDIAGONALY = true;

	/** Holds all the nodes/tiles. First dimension represents x-, second y-axis. */
	private Axis[][] nodes;

	/** width of map */
	protected int width;

	/** height of map */
	protected int height;

	/** Factory class to create an instance of specified node. */
	private NodeFactory nodeFactory;

	/** list containing nodes not visited but adjacent to visited nodes. */
	private List<Axis> openList;

	/** list containing nodes already visited/taken care of. */
	private List<Axis> closedList;

	/** Boolean that is set to true when path is found */
	private boolean done = false;

	/**     
	 * Construct a Map with width, height, context (text on tiles) and nodefactory
	 * @param width
	 * @param height
	 * @param context
	 * @param nodeFactory 
	 * 
	 */
	public Map(int width, int height, String[][] context, NodeFactory nodeFactory) {

		this.nodeFactory = nodeFactory;        
		nodes = (Axis[][]) new AbstractNode[width][height];
		this.width = width -1;
		this.height = height -1;
		initEmptyNodes(context);
	}



	/**
	 * 
	 */
	private void initEmptyNodes(String[][] context) {
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				nodes[i][j] = (Axis) nodeFactory.createNode(i, j);
				nodes[i][j].setContext(context[i][j]);
				if (nodes[i][j].getContext().equals("~"))
					nodes[i][j].setWalkable(false);
				else 
					nodes[i][j].setWalkable(true);
			}
		}
	}

	/**
	 * Set walkable tiles/nodes
	 * 
	 * @param x
	 * @param y
	 * @param bool
	 */
	public void setWalkable(int x, int y, boolean bool) {
		nodes[x][y].setWalkable(bool);
	}

	/**
	 * Returns the node at given coordinates.
	 *
	 * @param x
	 * @param y
	 * @return node
	 */
	public final Axis getNode(int x, int y) {
		return nodes[x][y];
	}

	/**
	 * Print out the path to a file
	 */
	public void printMapToFile() {    

		StringBuffer mapText = new StringBuffer();

		int k =0;
		while (k != height + 1) {
			for (int j = 0; j <= height; j++) {

				if (openList.get(j).getContext().equals("#")) 	{
					mapText.append(openList.get(j).getContext());
				} else {
					print((nodes[k][j]).getContext());
					mapText.append((nodes[k][j]).getContext());
				}           	
			}
			mapText.append("\n");
			k = k +1;
		}

		BufferedWriter out;
		try {
			File map = new File("large_map.txt");
			out = new BufferedWriter(new FileWriter("large_map.txt"));
			out.write(mapText.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * prints something to sto.
	 */
	private void print(String s) {
		System.out.print(s);
	}


	/**
	 * Finds the path to the goal. If no path is found, an empty list is returned.
	 *
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @return
	 */
	public final List<Axis> findPath(int oldX, int oldY, int newX, int newY) {

		openList = new LinkedList<Axis>();
		closedList = new LinkedList<Axis>();
		openList.add(nodes[oldX][oldY]); // add starting node to open list


		Axis current;
		while (!openList.isEmpty()) {
			current = lowestFInOpen(); // get node with lowest fCosts from openList
			closedList.add(current); // add current node to closed list
			openList.remove(current); // delete current node from open list
			current.gscore = 0;

			if ((current.getxPosition() == newX) && (current.getyPosition() == newY)) { // found goal
				return reconstruct_path(nodes[oldX][oldY], current);
			}

			List<Axis> adjacentNodes = getAdjacent(current);
			for (int i = 0; i < adjacentNodes.size(); i++) {
				Axis currentAdj = adjacentNodes.get(i);
				if (!openList.contains(currentAdj)) { // node is not in openList

					currentAdj.setPrevious(current); // set current node as previous for this node
					currentAdj.sethCosts(nodes[newX][newY]); // set h costs of this node (estimated costs to goal)
					currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
					openList.add(currentAdj); // add node to openList


				} else { // node is in openList
					if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { 
						currentAdj.setPrevious(current); // set current node as previous for this node
						currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
					}
				}

			}

			if (openList.isEmpty()) { // no path found
				return new LinkedList<Axis>(); // return empty list
			}
		}
		return null; // unreachable
	}


	private List<Axis> reconstruct_path(Axis start, Axis goal) {

		LinkedList<Axis> path = new LinkedList<Axis>();
		Axis curr = goal;
		boolean done = false;
		while (!done) {
			path.addFirst(curr);
			curr = (Axis) curr.getPrevious();
			if (curr.equals(start)) {
				done = true;
			}
		}
		return path;
	}

	/**
	 * Returns the node with the lowest fCosts.
	 *
	 * @return
	 */
	private Axis lowestFInOpen() {	
		Axis cheapest = openList.get(0);
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getfCosts() < cheapest.getfCosts()) {
				cheapest = openList.get(i);
			}
		}
		return cheapest;
	}

	/**
	 * Returns a list with adjacent nodes
	 */
	private List<Axis> getAdjacent(Axis node) {

		int x = node.getxPosition();
		int y = node.getyPosition();
		List<Axis> adj = new LinkedList<Axis>();

		Axis temp;
		if (x > 0) {
			temp = this.getNode((x - 1), y);
			if (temp.isWalkable() && !closedList.contains(temp)) {
				temp.setIsDiagonally(false);
				adj.add(temp);
			}
		}

		if (x < width) {
			temp = this.getNode((x + 1), y);
			if (temp.isWalkable() && !closedList.contains(temp)) {
				temp.setIsDiagonally(false);
				adj.add(temp);
			}
		}

		if (y > 0) {
			temp = this.getNode(x, (y - 1));
			if (temp.isWalkable() && !closedList.contains(temp)) {
				temp.setIsDiagonally(false);
				adj.add(temp);
			}
		}

		if (y < height) {
			temp = this.getNode(x, (y + 1));
			if (temp.isWalkable() && !closedList.contains(temp)) {
				temp.setIsDiagonally(false);
				adj.add(temp);
			}
		}

		if (CANMOVEDIAGONALY) {
			if (x < width && y < height) {
				temp = this.getNode((x + 1), (y + 1));
				if (temp.isWalkable() && !closedList.contains(temp)) {
					temp.setIsDiagonally(true);
					adj.add(temp);
				}
			}

			if (x > 0 && y > 0) {
				temp = this.getNode((x - 1), (y - 1));
				if (temp.isWalkable() && !closedList.contains(temp)) {
					temp.setIsDiagonally(true);
					adj.add(temp);
				}
			}

			if (x > 0 && y < height) {
				temp = this.getNode((x - 1), (y + 1));
				if (temp.isWalkable() && !closedList.contains(temp)) {
					temp.setIsDiagonally(true);
					adj.add(temp);
				}
			}

			if (x < width && y > 0) {
				temp = this.getNode((x + 1), (y - 1));
				if (temp.isWalkable() && !closedList.contains(temp)) {
					temp.setIsDiagonally(true);
					adj.add(temp);
				}
			}
		}
		return adj;
	}


}
