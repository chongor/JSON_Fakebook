import java.util.Set;

public class DegreeCentrality {

    /**
     * Finds the degree centrality of a given node
     * If people is null or source cannot be found in the graph, throw and
     * IllegalArgumentException
     * @param source is the ID of the source node
     *
     */
	public static int getDegree(Set<Friend> people, String source) {
		if(people == null)
			throw new IllegalArgumentException();
		Friend fr = null;
    	for(Friend p : people) {
    		if (p.id.equals(source)) {
    			fr = p;
    			break;
    		}
    	}
    	// throw Exception if source(ID) not in the Graph people
    	if(fr == null)
    		throw new IllegalArgumentException();
    	
    	// find degree of the Friend
    	int degree = fr.friends.size();
		
		return degree;
	}
}
