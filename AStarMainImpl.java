

package astar;

import java.util.List;

/**
 * Class that shows how A * implementation works.
 * 
 
 */
public class AStarMainImpl {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		double initMoveCost = 2;

		String text [][]  = {
				{"@","*","^","^","^"},
				{"~","~","*","~","."},
				{"*","*",".",".","."},
				{"^",".",".","*","~"},
				{"~","~","*","~","X"}

		};
		Map<Node> myMap = new Map<Node>(5,5, text, new AStarFactory());
		List<Node> path = myMap.findPath(0, 0, 4, 4);


		for (int i=0;i<path.size();i++)      
			path.get(i).setContext("#");

		System.out.println("Total cost of movement: "  + (path.get(path.size()-1).getgCosts() + initMoveCost));


		// System.out.println(myMap.gcost);
		myMap.printMapToFile();
	}


}
