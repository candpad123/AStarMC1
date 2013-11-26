package astar;


/**
 * Abstract class created to represent a node/tile on the map.
 * 
 *
 */

public abstract class AbstractNode {

	//movement costs
	protected static final int FLATLANDSCOST = 1; 
	protected static final int FORESTMOVEMENTCOST = 2;
	protected static final int MOUNTAINCOST = 3;

	//coordinates on map
	private int xPosition;
	private int yPosition;


	private boolean walkable;
	//Text of node/tile
	private String context;

	protected double gscore;
	protected double fscore;
	protected double hscore;

	//previous node/tile
	private AbstractNode previous;


	private boolean diagonally;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	// calculated costs from start node to end node
	private int gCosts;

	// estimated costs to get from start node to end node
	private int hCosts;

	/**
	 * constructs a walkable AbstractNode with given coordinates.
	 *
	 * @param xPosition
	 * @param yPosition
	 */
	public AbstractNode(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.walkable = true;

	}

	/**
	 * Check to see if diagonal move is allowed
	 * @return boolean
	 */
	public boolean isDiagonally() {
		return diagonally;
	}

	/**
	 * 
	 * @param isDiagonaly
	 */
	public void setIsDiagonally(boolean isDiagonally) {
		this.diagonally = isDiagonally;
	}

	/**
	 * sets x and y coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public void setCoordinates(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}

	/**
	 * @return the xPosition
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public int getyPosition() {
		return yPosition;
	}

	/**
	 * @return boolean if node is walkable
	 */
	public boolean isWalkable() {
		return walkable;
	}

	/**
	 * @param Set the node to walkable
	 */
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	/**
	 * returns the node set as previous node on the current path.
	 *
	 * @return the previous
	 */
	public AbstractNode getPrevious() {
		return previous;
	}

	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(AbstractNode previous) {
		this.previous = previous;
	}


	/**
	 * @return the fCosts
	 */
	public int getfCosts() {
		return gCosts + hCosts;
	}

	/**
	 * Returns the calculated costs from start node to this node.
	 *
	 * @return the gCosts
	 */
	public int getgCosts() {
		return gCosts;
	}

	/**
	 
	 * @param gCosts the gCosts to set
	 */
	private void setgCosts(int gCosts) {
		this.gCosts = gCosts ;
	}

	/**
	 * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
	 * for this AbstractNode given the previous AbstractNode as well as the basic cost
	 * from it to this AbstractNode.
	 *
	 * @param previousAbstractNode
	 * @param basicCost
	 */
	public void setgCosts(AbstractNode previousAbstractNode, int basicCost) {
		setgCosts(previousAbstractNode.getgCosts() + basicCost);
	}

	/**
	 
	 * @param previousAbstractNode
	 */
	public void setgCosts(AbstractNode previousAbstractNode) {   

		if ( !(previousAbstractNode.getxPosition() == 0 && previousAbstractNode.getyPosition() == 0)) {
			if (previousAbstractNode.getContext().equals("*"))
				setgCosts(previousAbstractNode, FORESTMOVEMENTCOST);
			else if (previousAbstractNode.getContext().equals(".") ||(previousAbstractNode.getContext().equals("@") ) || (previousAbstractNode.getContext().equals("X") ))
				setgCosts(previousAbstractNode, FLATLANDSCOST);
			else if (previousAbstractNode.getContext().equals("^"))
				setgCosts(previousAbstractNode, MOUNTAINCOST);
		}

	}

	/**
	 *
	 * @param previousAbstractNode
	 * @return gCosts
	 */
	public int calculategCosts(AbstractNode previousAbstractNode) {	
			return (previousAbstractNode.getgCosts());
	}

	/**
	 * Calculates g costs, adding a movement cost.
	 *
	 * @param previousAbstractNode
	 * @param movementCost costs from previous AbstractNode to this AbstractNode.
	 * @return gCosts
	 */
	public int calculategCosts(AbstractNode previousAbstractNode, int movementCost) {
		return (previousAbstractNode.getgCosts() + movementCost );
	}

	/**
	 * returns estimated costs to get from this AbstractNode to end AbstractNode.
	 *
	 * @return the hCosts
	 */
	public int gethCosts() {
		return hCosts;
	}

	/**
	 * sets hCosts.
	 *
	 * @param hCosts the hCosts to set
	 */
	protected void sethCosts(int hCosts) {
		this.hCosts = hCosts;
	}

	/**
	 * calculates hCosts for this AbstractNode to a given end AbstractNode.
	 * Uses Manhattan method.
	 *
	 * @param endAbstractNode
	 */
	public abstract void sethCosts(AbstractNode endAbstractNode);


	/**
	 * Check whether the coordinates are equal
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractNode other = (AbstractNode) obj;
		if (this.xPosition != other.xPosition) {
			return false;
		}
		if (this.yPosition != other.yPosition) {
			return false;
		}
		return true;
	}

}
