
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class MSTKruskal {

	// initial capacity for PriorityQueue required by constructor
	private static final int INITIAL_CAPACITY = 300;
    /**
     * an inner helper class for Comparator compare method 
     * for PriorityQueue<Edge>
     **/
    private static class EdgeCompare implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            int w1 = o1.A.friends.get(o1.B);
            int w2 = o2.A.friends.get(o2.B);
            return w1 - w2;
        }
    }
  /**
   * Generate the Minimum Spanning Tree of the graph. The graph is passed in
   * as a set of friends, as each friend knows his/her own friends.  
   *
   * Note that you also need to output a file for MST, though that does not
   * necessarily need to happen in this method.
   */
    public static HashSet<Edge> MST(HashSet<Friend> people) {
    	HashSet<Edge> mst = new HashSet<Edge>();
    	EdgeCompare ecompare = new EdgeCompare();
    	PriorityQueue<Edge> pq = new PriorityQueue<Edge>(INITIAL_CAPACITY, ecompare);
    	
    	// Union Implementation: Set<Set<Friend>> is a group of all groups
    	Set<Set<Friend>> vgroups = new HashSet<Set<Friend>>();
    	
    	// add all edges to the PriorityQueue<Edge>
    	buildPQ(people, pq);
    			
    	while(!pq.isEmpty()) {
    		Edge e = pq.remove();
    		boolean found = unionFind(vgroups, e);
    		if(!found)
    			mst.add(e);
    	}
        return mst;
    }
    /**
     * build the PriorityQueue<Edge> for all edges in the Graph
     * The parameter people is a graph, it contains all vertices(Friend) of  
     * the graph.  Add all edges to PriorityQueue<Edge>
     * Note that the edge has 2 vertices in either A or B, need to check the
     * existence of an Edge in the queue first before adding to the queue.
     */
    private static void buildPQ(HashSet<Friend> people, PriorityQueue<Edge> pq) {
    	for(Friend p : people) {
    		Set<Friend> pfriends = p.friends.keySet();
    		for(Friend fr : pfriends) {
    			Edge e1 = new Edge(p, fr);	// Edge of <p, fr> pair
    			Edge e2 = new Edge(fr, p);	// Edge of <fr, p> pair
    			if(!pq.contains(e1)) {
    				if(!pq.contains(e2))
    					pq.add(e1);
    			}
    		}
    	}
    }
 
    /**
     * Implementation of Union and UnionFind
     * Find the Union of two nodes in an Edge.  There are 3 cases for nodes
     * 1. both nodes are in a group(Set) already
     * 2. One node is not in any group
     * 3. both nodes are not in any group
     * returns true if both nodes are in one 
     */
    private static boolean unionFind(Set<Set<Friend>> vgroups, Edge e) {
    	Set<Friend> groupA = findGroup(vgroups, e.A);
    	Set<Friend> groupB = findGroup(vgroups, e.B);
    	boolean found = true;
    	
    	if(groupA == null) {
    		found = false;
    		// if both are not in any group, create a new group
    		if(groupB == null) {
    			HashSet<Friend> newgroup = new HashSet<Friend>();
    			newgroup.add(e.A);	// add vertex A
    			newgroup.add(e.B);	// add vertex B
    			vgroups.add(newgroup);
    		}
    		else {
    			groupB.add(e.A);	// else add A to Group with B
    		}
    	}
    	else {
    		if (groupB == null) {
    			groupA.add(e.B);
    			found = false;
    		}
    		else if (groupA != groupB){
    			// A, B belong to 2 Groups, Union these 2 Groups
    			groupA.addAll(groupB);	// add Group B to Group A
    			vgroups.remove(groupB);	// remove Group B
    			found = false;
    		}
    	}
    	return found;
    }
 
    /**
     * find the group a Friend belongs to.  Returns null if the Friend is not
     * included in all groups.  
     *
     */
    private static Set<Friend> findGroup(Set<Set<Friend>> vgroups, Friend fr) {
    	for(Set<Friend> vgrp : vgroups) {
    		HashSet<Friend> group = (HashSet<Friend>) vgrp;
    		if(group.contains(fr))
    			return group;
    	}
    	return null;
    }
}
