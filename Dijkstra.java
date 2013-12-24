import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Shaanan Cohney
 */
public class Dijkstra {
	// initial capacity for PriorityQueue required by constructor
	private static final int INITIAL_CAPACITY = 300;
	
	HashMap<Friend, Integer> distMap = new HashMap<Friend, Integer>();
	HashMap<Friend, Friend> predecessorMap = new HashMap<Friend, Friend>();
	
    /**
     * an Comparator for PriorityQueue<Friend> to compare the distance of 2
     * nodes(Friends).  The distance is retrieved from the distance Map
     **/
    private class PQCompare implements Comparator<Friend> {
        public int compare(Friend o1, Friend o2) {
        	int d1 = getDistance(o1);
        	int d2 = getDistance(o2);
            if(d1 > d2) 
            	return 1;
            else if (d1 < d2) 
            	return -1;
            else 
            	return 0;
        }
    }

    /*
     * Runs Dijkstra's algorithm to find the shortest path between the given 
     * source and all nodes in people. 
     * To return both the distances and the actual paths that Dijkstra's gives,
     * you will store those in a DijkstraPair. All Friends in people should be
     * included in both Maps of the pair. 
     * dijkstraPair.parentMap.get(source) should be null.
     */
    public DijkstraPair dijkstraSP(Set<Friend> people, Friend source) {
    	PQCompare qcompare = new PQCompare();
    	PriorityQueue<Friend> pq = 
    			new PriorityQueue<Friend>(INITIAL_CAPACITY, qcompare);

    	distMap.put(source, 0);
    	pq.add(source);
    	
    	while(!pq.isEmpty()) {
    		Friend u = pq.remove();
	    	Iterator<Entry<Friend, Integer>> iter = u.friends.entrySet().iterator();
	 		
	    	while(iter.hasNext()) {
	    		Map.Entry<Friend, Integer> pairs = 
	    				(Map.Entry<Friend, Integer>) iter.next();
	    		Friend v = pairs.getKey();
	    		int distanceUV = pairs.getValue();
	    		int distanceU = getDistance(u);
	    		int distanceV = getDistance(v);
	    		int newDistance = distanceU + distanceUV;
	    		
	    		if(distanceV > newDistance) {
	    			distMap.put(v, newDistance);
	    			predecessorMap.put(v, u);
	    			pq.add(v);
	    		}
	    	}
    	}
    	DijkstraPair dpair = new DijkstraPair(distMap, predecessorMap);
    	return dpair;
    }
    /**
     * get the current distance value from distance Map of a Friend
     * if the node not in the distance Map, then it's set to infinity  
     * Integer.MAX_VALUE.  Otherwise, the distance value of the node 
     * is returned
     */
    private int getDistance(Friend node) {
    	int d = Integer.MAX_VALUE;
    	if(distMap.containsKey(node))
    		d = distMap.get(node);
    	
    	return d;
    }
}
