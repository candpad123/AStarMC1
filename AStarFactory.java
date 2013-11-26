package astar;

/**
 * A simple Factory for example nodes.
 *
 *
 */


public class AStarFactory implements NodeFactory {

        @Override
        public AbstractNode createNode(int x, int y) {
            return new Node(x, y);
        }

}
