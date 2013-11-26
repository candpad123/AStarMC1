package astar;

import astar.AbstractNode;


public interface NodeFactory {

    /**
     * Create a node with coordinates
     *
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @return
     */
    public AbstractNode createNode(int x, int y);

}
