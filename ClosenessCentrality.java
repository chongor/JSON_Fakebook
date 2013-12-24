import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * This class will find the closeness centrality of a given node
 */
public class ClosenessCentrality {

	/*
	 * Find the closeness centrality for the source, measured by the
	 * reciprocal of the sum of the distances between the source and all of the 
	 * other nodes in the graph.
	 * 
	 * @return the closeness centrality of the source
	 */
	public static double Centrality(Set<Friend> people, Friend source) {
		Dijkstra shortestPath = new Dijkstra();
		DijkstraPair dpair = shortestPath.dijkstraSP(people, source);
		
     	Collection<Integer> c = dpair.distanceMap.values();
     	Iterator<Integer> iter = c.iterator();
     	int distance = 0;
     	while(iter.hasNext()) {
     		int d = (Integer) iter.next();
     		distance += d;
     	}
		double closeCentrality = 1 / ((double) distance);
		return closeCentrality;
	}
}
