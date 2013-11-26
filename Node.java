package astar;


/**
 * A simple Example implementation of a Node only overriding the sethCosts
 * method; uses Manhattan method.
 */
public class Node extends AbstractNode {

        public Node(int xPosition, int yPosition) {
            super(xPosition, yPosition);
            // do other init stuff
        }

        public void sethCosts(AbstractNode endNode) {
            this.sethCosts((absolute(this.getxPosition() - endNode.getxPosition())      + absolute(this.getyPosition() - endNode.getyPosition()))
                    );
        }

        private int absolute(int a) {
            return a > 0 ? a : -a;
        }

}
