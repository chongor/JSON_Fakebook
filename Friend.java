
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Friend implements Comparable, Serializable {

    // Basic Elements 
    public String name;
    public String id;
    //The value in each k-v pair here is the weight of the edge between
    //this user and that friend. 
    //Weight is defined as Math.floor(1000 / (number of mutual friends + 1))
    //where mutual friends = number of friends two users have in common in 
    //the graph.
    public HashMap<Friend, Integer> friends;

    public Friend() {
        friends = new HashMap<Friend, Integer>();
    }

    @Override
    public String toString() {
    	// name and id, # of friends
    	String s = "name: " + name + "  id: " + id + "\n";
    	s += "# friends: " + friends.size();
    	// traverse thru friends HashMap to list all friends
    	Set<Friend> keys = friends.keySet();
    	int i = 0;
    	for(final Friend f : keys) {
    		i++;
    		s += "\nFriend " + i + " -- name: " + f.name + " id: " + f.id + 
    				" weight: " + friends.get(f);
    	}
        return s;
    }

    // Suitable as facebook IDs are unique
    @Override
    public int hashCode() {
        return (Long.decode(id).hashCode());
    }

    @Override
    public int compareTo(Object o) {
    	// compare id
    	Friend f = (Friend) o;
    	
        return f.id.compareTo(id);
    }

}
