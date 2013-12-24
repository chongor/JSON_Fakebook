import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import com.google.gson.stream.JsonReader;
import java.util.HashMap;

public class FakebookReader implements Iterable<Friend> {

	Friend r;	// root of the graph G
	HashSet<Friend> Graph;
	
    /**
     * Construct a friend graph from the JSON document at
     * filename. If the file does not exist or is otherwise
     * improperly formatted, create an empty friend graph.
     *
     * @param filename The JSON file to be read
     **/
	public FakebookReader(String filename) {
    	if(filename == null) 
    		return;
		// G is an interim Graph data structure used for building from JSON 
		HashMap<String, Friend> G = new HashMap<String, Friend>();
    	// Final Graph structure after processing JSON data file
    	Graph = new HashSet<Friend>();
    	
    	File f = new File(filename);
    	InputStream jinput = null;
    	
    	try {
    		jinput = new FileInputStream(f);
    	} catch(FileNotFoundException e) {
    		return;
    	}
    	
    	try {
    		readJsonStream(G, jinput);
    	} catch(IOException e) {
    		return;
    	}
    	// loop thru the graph to build weights of all nodes
    	buildWeight(G);
    	
     	Collection<Friend> c = G.values();
     	for(Friend node : c) {
     		Graph.add(node);
     	}
     	// printGraph(Graph);
    }

    public void setRoot(Friend root) {
    	if(root != null) r = root;
    }

    /**
     * Get the root of the friend graph built from the file we were given
     * in the constructor.
     *
     * @returns the root of the friend graph
     **/
    public Friend getRoot() {
        return r;
    }

    
    /** 
     * Returns an iterator that traverses the graph in BFS order from the root
     * When adding nodes to the queue, the ordering should be based on edge 
     * weight in ascending order, and then ID in ascending order if tied.
     * You can assume all nodes will be connected through the root node.
     *
     */
    public Iterator<Friend> iterator() {
        return (Iterator<Friend>) new bfsIterator();
    }

    /**
     * an inner helper class containing Friend object and weight.  Used to 
     * assist sorting in Iterator.  The sorting is by weight first, then by 
     * id if weight equal
     **/
    private class FriendNode {
        Friend node;
        int weight;
        
        FriendNode(Friend n, int w) {
        	node = n;
        	weight = w;
        }
    }
    /**
     * an inner helper class for Comparator compare method 
     * It compares weight first, then compares id for 2 FriendNode
     **/
    private class NodeCompare implements Comparator {
        public int compare(Object o1, Object o2) {
            FriendNode f1 = (FriendNode) o1;
            FriendNode f2 = (FriendNode) o2; 
            if(f1.weight == f2.weight)
            	return f1.node.id.compareTo(f2.node.id);
            else 
            	return f1.weight - f2.weight;
        }
    }
    
    /**
     * Bread-First Search Graph Traversal Iterator
     */
    private class bfsIterator implements Iterator<Friend> {
        ArrayList<Friend> iteratorq = new ArrayList<Friend>();
		
        public bfsIterator() {
        	HashSet<Friend> marked = new HashSet<Friend>();
        	ArrayList<Friend> q = new ArrayList<Friend>();
            q.add(r);
            marked.add(r);
            iteratorq.add(r);
            
            while (!q.isEmpty()) {
            	ArrayList<FriendNode> nodeList = new ArrayList<FriendNode>();
            	Friend user = q.remove(0);
        	    Iterator<Friend> it = user.friends.keySet().iterator();
        	    
        	    while (it.hasNext()) {
        	        Friend fr = (Friend) it.next();
        	        int w = (int) user.friends.get(fr);
        	        FriendNode nd = new FriendNode(fr, w);
        	        if(!marked.contains(nd.node))
        	        	nodeList.add(nd);
        	    }
        	    NodeCompare ncompare = new NodeCompare();
        	    Collections.sort(nodeList, ncompare);
        	    Iterator<FriendNode> iter = nodeList.iterator();
        	    
        	    while(iter.hasNext()) {
        	    	FriendNode n = iter.next();
             		if(!marked.contains(n.node)) {
             			q.add(n.node);
             			marked.add(n.node);
             			iteratorq.add(n.node);
             		}
             	}
        	} 
        }
		
        public boolean hasNext() { return (!iteratorq.isEmpty()); }
        public void remove() { throw new UnsupportedOperationException(); }
		
        public Friend next() {
        	if(!iteratorq.isEmpty())
        		return iteratorq.remove(0);
        	else
        		return null;
        }
    }

// ****************************************
// NOTE: next two methods to be implemented in section 2.3, not 2.2
// ****************************************

    /**
     * Uses ConnectedComponentFinder to generate the entire set of connected
     * components in the graph. Each CC is a Set of Friends, so the set of CCs
     * is a Set of Set of friends.
     * Your implementation here should not be inefficient - that is, it should
     * not call BFS from every single node.
     */
    public Set<Set<Friend>> getConnectedComponents(){
    	HashSet<Friend> marked = new HashSet<Friend>();
    	Set<Set<Friend>> ccsets = new HashSet<Set<Friend>>();
    	
    	ConnectedComponentFinder ccfinder = new ConnectedComponentFinder();   	

    	for(Friend node : Graph) {
    		if(!marked.contains(node)) {
	    		Set<Friend> fset = ccfinder.findCC(node);
	    		for (Friend f : fset) {
	    			if(!marked.contains(f))
	    				marked.add(f);
	    		}
	    		ccsets.add(fset);
    		}
    	}
    	return ccsets;
    }

    /**
     * Uses ConnectedComponentFinder to generate the entire set of connected
     * components in the graph when the root has been removed (ie, the root 
     * should not be in any of the connected components).
     * Each CC is a Set of Friends, so the set of CCs is a Set of Set of 
     * friends.
     *
     * Your implementation here should not be inefficient - that is, it should
     * not call BFS from every single node.
     */
    public Set<Set<Friend>> getConnectedComponentsWithoutRoot(){
    	HashSet<Friend> marked = new HashSet<Friend>();
    	marked.add(r);
    	Set<Set<Friend>> ccsets = new HashSet<Set<Friend>>();
    	
    	for(Friend node : Graph) {
    		if(!marked.contains(node)) {
    			Set<Friend> fset = findCCWithoutNode(node, r);
	    		for (Friend f : fset) {
	    			if(!marked.contains(f))
	    				marked.add(f);
	    		}
    			ccsets.add(fset);
    		}
    	}
    	return ccsets;
    }
    /*
     * Find the connected component of a given node exclude one specific node
     * @param srcNode - the node whose connected component is to be found. 
     * @param r - the node to be excluded in connected component. 
     */
    private Set<Friend> findCCWithoutNode(Friend srcNode, Friend t) {
    	HashSet<Friend> marked = new HashSet<Friend>();
    	HashSet<Friend> cc = new HashSet<Friend>();
    	ArrayList<Friend> q = new ArrayList<Friend>();
    	
    	marked.add(r);
    	marked.add(srcNode);
    	cc.add(srcNode);
    	q.add(srcNode);
        while (!q.isEmpty()) {
        	Friend node = q.remove(0);
    	    Iterator<Friend> iter = node.friends.keySet().iterator();
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
/*  
  * input Json data format:
  * {
  *  "222222": 
	{	"friends": [
			{ "name": "Caroline White", "id": "8234567" },
			{ "name": "Johnson Evens", "id": "90357762" }
		]
		"name": "Jim Bekin"
	}
	
	"3572691": 
	{	"friends": [
			{ "name": "Sam Hughes", "id": "5566234567" },
			{ "name": "Steve Goldbaun", "id": "11117762" },
			{ "name": "Peter Cox", "id": "20103045" }
		]
		"name": "Fred Stevenson"
	}

  }
  */
    /**
     * read a Json data file. The Json data file has the friends list
     */
    private void readJsonStream(HashMap<String, Friend> G, InputStream jin) 
    		throws IOException {
    	JsonReader reader = new JsonReader(new InputStreamReader(jin, "UTF-8"));
		reader.beginObject();
    	while (reader.hasNext()) {
    		Friend user = new Friend();
    		try {
    	        String id = reader.nextName();  // "nnnnnnn" -- id string
    	        user.id = id;
    	        if(G.containsKey(id)) {
    	        	user = G.get(id);
    	        }
    			readUser(G, reader, user);
    		} catch (IOException e) {
    			break;
    		}
    		if(user.name.equals("Shaanan Cohney"))
    			r = user;
    		G.put(user.id, user);
    		//String s = user.toString();
    		//System.out.println(s);
    		//System.out.println("-------------");
    		//String x = s;
    		// printG();
    		//String u = x;
    		//x = u + "5";
        }
    	reader.endObject();
    	//printG();
    }
    /**
     * read an user from the json data file.  each user is a first degree friend
     * Each user has the id string, an array of friends, and the name string
     */
    public void readUser(HashMap<String, Friend> G, JsonReader reader, 
    		Friend user) throws IOException {
        reader.beginObject();
        String s = reader.nextName();  // "friends" -- starts array of friends
        try {
        	readFriendArray(G, reader, user);
        } catch(IOException e) {
        	return;
        }
        
        s = reader.nextName();  // "name": "xxxxxxx"
        user.name = reader.nextString();
        reader.endObject();
    }
    /**
     * read the "friends" array.  friends contains an array of friend objects.
     * The friend object is a second degree friend.
     */
    private void readFriendArray(HashMap<String, Friend> G, 
    		JsonReader reader, Friend user) throws IOException {
    	reader.beginArray();
        while (reader.hasNext()) {
        	try {
        		Friend fr = readFriend(reader);
                // assign id to root, update root weight
                Friend f1 = G.get(fr.id);
                if(f1 == null) {
                	f1 = fr;
                }
                f1.friends.put(user, 0);
               	G.put(f1.id, f1);
        		user.friends.put(f1, 0);
        	} catch(IOException e) {
        		break;
        	}
        }
        reader.endArray();
    }
    /**
     * read one friend from the friends array.  
     * Each friend object contains "name" and "id" strings
     */
    private Friend readFriend(JsonReader reader) throws IOException {
    	reader.beginObject();
        Friend fr = new Friend();
        
        try {
        	String s = reader.nextName();
            fr.name = reader.nextString();
           	s = reader.nextName();
            fr.id = reader.nextString();
        } catch (IOException e) {
        	throw new IOException();
        }
        reader.endObject();
        return fr;
    }
    /**
     * traverse all nodes of a graph to build the weight of mutual friends
     */
     private void buildWeight(HashMap<String, Friend> G) {
     	Collection<Friend> c = G.values();
     	Iterator<Friend> iter = c.iterator();
     	while(iter.hasNext()) {
     		Friend user = (Friend) iter.next();
     		buildW(user);
     		// printG();
     	}
     }
     /**
      * traverse all friends of a node to build the weight of mutual friends
      */
     private void buildW(Friend user) {
     	Set<Friend> fset1 = user.friends.keySet();
     	Set<Friend> fset2 = user.friends.keySet();
     	for(Friend node : fset1) {
     		int w = 1;
     		for(Friend fr : fset2) {
     			if(fr.id.equals(node.id))
     				continue;
     			if(node.friends.containsKey(fr))
   					w++;
     		}
         	int w1 = (int) Math.floor(1000 / (w + 1));
        	user.friends.put(node, w1);
        	node.friends.put(user, w1);
     	}
    }
    
   
    /**
     * temporary debug tool -- print the whole Graph
     * 
     */
    private void printGraph(HashSet<Friend> Graph) {
    	System.out.println("Graph Total Nodes: " + Graph.size());
    	int i = 0;
    	for(Friend f : Graph) {
    		i++;
    		System.out.println("Node " + i);
    		System.out.println(f.toString());
    		System.out.println("===================");
    	}
    }
}
