import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectedComponentFinder {

	public ConnectedComponentFinder() {
    }
    
    /*
     * Find the connected component of a given node
     * @param srcNode - the node whose connected component is to be found. 
     */
    public Set<Friend> findCC(Friend srcNode) {
        HashSet<Friend> marked = new HashSet<Friend>();
    	HashSet<Friend> cc = new HashSet<Friend>();
    	ArrayList<Friend> q = new ArrayList<Friend>();
    	
    	marked.add(srcNode);
    	cc.add(srcNode);
    	q.add(srcNode);
    	while (!q.isEmpty()) {
    		Friend user = q.remove(0);
    		Iterator<Friend> iter = user.friends.keySet().iterator();
    	
    		while(iter.hasNext()) {
    			Friend v = iter.next();
    			if(!marked.contains(v)) {
    				q.add(v);
    				marked.add(v);
    				cc.add(v);
    			}
    		}
    	} 
    	return cc;
    }
    
}
