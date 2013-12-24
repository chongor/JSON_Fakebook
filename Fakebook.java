import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Fakebook {
	// FakebookReader Test 1: Test json data file
	public static void testFakebook1(String filename) {
		FakebookReader fr = new FakebookReader(filename);
		Iterator<Friend> iter = fr.iterator();
		System.out.println(">>>>>>>> Start Graph Iterator >>>>>>>>>");
		while(iter.hasNext()) {
			Friend f = iter.next();
			String s = f.toString();
			System.out.println(s);
			System.out.println("*************");
		}
		
		Friend r = fr.getRoot();
		String t = r.toString();
		System.out.println("------- Root --------");
		System.out.println(t);
	}
	// FakebookReader Test 2: Test ConnectedComponentFinder
	public static void testFakebook2(String filename) {
		FakebookReader fr = new FakebookReader(filename);
		ConnectedComponentFinder ccfind = new ConnectedComponentFinder();
		Friend root = fr.getRoot();
		Set<Friend> fset = ccfind.findCC(root);
		
		System.out.println(">>>>> Connected Component: " + fset.size() + " nodes >>>>>>>>>");
		for(Friend f : fset) {
			String s = f.toString();
			System.out.println(s);
			System.out.println("*************");
		}
		
		Friend r = fr.getRoot();
		String t = r.toString();
		System.out.println("------- Root --------");
		System.out.println(t);
	}
	// FakebookReader Test 3: Test getConnectedComponents
	// and getConnectedComnponentsWithoutRoot
	public static void testFakebook3(String filename) {
		FakebookReader fakebook = new FakebookReader(filename);
		Friend root = fakebook.getRoot();
		Set<Set<Friend>> fsets = fakebook.getConnectedComponents();
		System.out.println(">>>>> getConnectedComponents: " + fsets.size() + " Sets >>>>>");
		int i = 0;
		for(Set<Friend> fset : fsets) {
			i++;
			System.out.println("------ Set " + i + "--------");
			for(Friend f : fset) {
				String s = f.toString();
				System.out.println(s);
				System.out.println("++++++++++");
			}
		}
		
		System.out.println(">>>>>>>> get Connected Component Without Root >>>>>>>>>");
		Set<Set<Friend>> fsets1 = fakebook.getConnectedComponentsWithoutRoot();
		int n = 0;
		for(Set<Friend> fset1 : fsets1) {
			n++;
			System.out.println("------ Set " + n + "--------");
			for(Friend f1 : fset1) {
				String s1 = f1.toString();
				System.out.println(s1);
				System.out.println("++++++++++");
			}
		}
		
	}
	// FakebookReader Test 4: Test ConnectedComponentFinder
	// Not use FakebookReader class, no json file input
	
	public static void testFakebook4() {
		Friend f0 = getFriend("000", "000000");
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		Friend f6 = getFriend("666", "666666");
		Friend f7 = getFriend("777", "777777");
		
		f0.friends.put(f2, 26);
		f0.friends.put(f4, 38);
		f0.friends.put(f6, 58);
		f0.friends.put(f7, 16);

		f1.friends.put(f2, 36);
		f1.friends.put(f3, 29);
		f1.friends.put(f5, 32);
		f1.friends.put(f7, 19);

		f2.friends.put(f0, 26);
		f2.friends.put(f1, 36);
		f2.friends.put(f3, 17);
		f2.friends.put(f6, 40);
		f2.friends.put(f7, 34);

		f3.friends.put(f1, 29);
		f3.friends.put(f2, 17);
		f3.friends.put(f6, 52);

		f4.friends.put(f0, 38);
		f4.friends.put(f5, 35);
		f4.friends.put(f6, 93);
		f4.friends.put(f7, 37);

		f5.friends.put(f1, 32);
		f5.friends.put(f4, 35);
		f5.friends.put(f7, 28);

		f6.friends.put(f0, 58);
		f6.friends.put(f2, 40);
		f6.friends.put(f3, 52);
		f6.friends.put(f4, 93);
		
		f7.friends.put(f0, 16);
		f7.friends.put(f1, 19);
		f7.friends.put(f2, 34);
		f7.friends.put(f4, 37);
		f7.friends.put(f5, 28);

		ConnectedComponentFinder ccfind = new ConnectedComponentFinder();
		Set<Friend> fset = ccfind.findCC(f1);
		
		System.out.println(">>>>>>>> Start Connected Component >>>>>>>>>");
		for(Friend f : fset) {
			String s = f.toString();
			System.out.println(s);
			System.out.println("*************");
		}
	}
	
	// Test Kruskal Algorithm: Example in handout
	public static Friend getFriend(String id, String name) {
		Friend fr = new Friend();
		fr.id = id;
		fr.name = name;
		return fr;
	}
	
	public static void writeEdgeSet(HashSet<Edge> edges) {
		try{
		   // Create file 
		   FileWriter fstream = new FileWriter("MST.csv");
		   BufferedWriter out = new BufferedWriter(fstream);
		   int i = 0;
		   for(Edge e : edges) {
			   out.write(e.A.id + ";" + e.B.id);
			   out.newLine();
			   i++;
			   System.out.println(i + " -- " + e.A.id + ";" + e.B.id);
		   }
		   //Close the output stream
		   out.close();
	   }catch (Exception e) {    //Catch exception if any
		   System.err.println("Error: " + e.getMessage());
	   }
	}
	// Test Kruskal Algorithm: Example in handout
	public static void testKruskal1() {
		Friend f1 = getFriend("123", "Node 123");
		Friend f2 = getFriend("325", "Node 324");
		Friend f3 = getFriend("345", "Node 345");
		Friend f4 = getFriend("789", "Node 789");
		
		f1.friends.put(f2, 500);
		f1.friends.put(f3, 500);

		f2.friends.put(f1, 500);
		f2.friends.put(f3, 500);

		f3.friends.put(f1, 500);
		f3.friends.put(f2, 500);
		f3.friends.put(f4, 1000);

		f4.friends.put(f3, 1000);
		
		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f4);
		people.add(f3);
		people.add(f2);
		people.add(f1);
		
		HashSet<Edge> edges = MSTKruskal.MST(people);
		
		writeEdgeSet(edges);
	}
	// Test Kruskal Algorithm: Json files -- 5.json, 50.json, 300.json
	public static void testKruskal2(String filename) {
		FakebookReader fakebook = new FakebookReader(filename);
		HashSet<Friend> people = new HashSet<Friend>();
		Iterator<Friend> iter = fakebook.iterator();
		while(iter.hasNext()) {
			Friend f = iter.next();
			people.add(f);
		}
		
		HashSet<Edge> edges = MSTKruskal.MST(people);
		
		writeEdgeSet(edges);
	}
	// Test Kruskal Algorithm: Example in Textbook
	public static void testKruskal3(String filename) {
		Friend f0 = getFriend("000", "000000");
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		Friend f6 = getFriend("666", "666666");
		Friend f7 = getFriend("777", "777777");
		
		f0.friends.put(f2, 26);
		f0.friends.put(f4, 38);
		f0.friends.put(f6, 58);
		f0.friends.put(f7, 16);

		f1.friends.put(f2, 36);
		f1.friends.put(f3, 29);
		f1.friends.put(f5, 32);
		f1.friends.put(f7, 19);

		f2.friends.put(f0, 26);
		f2.friends.put(f1, 36);
		f2.friends.put(f3, 17);
		f2.friends.put(f6, 40);
		f2.friends.put(f7, 34);

		f3.friends.put(f1, 29);
		f3.friends.put(f2, 17);
		f3.friends.put(f6, 52);

		f4.friends.put(f0, 38);
		f4.friends.put(f5, 35);
		f4.friends.put(f6, 93);
		f4.friends.put(f7, 37);

		f5.friends.put(f1, 32);
		f5.friends.put(f4, 35);
		f5.friends.put(f7, 28);

		f6.friends.put(f0, 58);
		f6.friends.put(f2, 40);
		f6.friends.put(f3, 52);
		f6.friends.put(f4, 93);
		
		f7.friends.put(f0, 16);
		f7.friends.put(f1, 19);
		f7.friends.put(f2, 34);
		f7.friends.put(f4, 37);
		f7.friends.put(f5, 28);

		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f0);
		people.add(f1);
		people.add(f2);
		people.add(f3);
		people.add(f4);
		people.add(f5);
		people.add(f6);
		people.add(f7);
		
		HashSet<Edge> edges = MSTKruskal.MST(people);
		
		writeEdgeSet(edges);
	}
	// Test Dijkstra Algorithm
	public static void testDijkstra1() {
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		
		f1.friends.put(f2, 8);
		f1.friends.put(f3, 16);

		f2.friends.put(f1, 8);
		f2.friends.put(f3, 5);
		f2.friends.put(f4, 2);

		f3.friends.put(f1, 16);
		f3.friends.put(f2, 5);
		f3.friends.put(f4, 11);

		f4.friends.put(f2, 2);
		f4.friends.put(f3, 11);
		f4.friends.put(f5, 28);

		f5.friends.put(f4, 28);
		
		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f5);
		people.add(f4);
		people.add(f3);
		people.add(f2);
		people.add(f1);
		
		Dijkstra d = new Dijkstra();
		DijkstraPair dpair = d.dijkstraSP(people, f1);
		
		Map<Friend, Integer> dmap = dpair.distanceMap;
		Map<Friend, Friend> pmap = dpair.parentMap;
		
    	Iterator<Entry<Friend, Integer>> diter = dmap.entrySet().iterator();
 		
    	System.out.println(">>>>> Dijkstra -- " + "distance: " + dmap.size() + ">>>>>>");
    	int i = 0;
    	while(diter.hasNext()) {
    		Map.Entry<Friend, Integer> dnode = 
    				(Map.Entry<Friend, Integer>) diter.next();
    		Friend v = dnode.getKey();
    		int dval = dnode.getValue();
    		i++;
    		System.out.println("dmap " + i + ": " + v.id + ", " + dval);
    	}
    	System.out.println("--------- End Dijkstra distance -------");
		
    	Iterator<Entry<Friend, Friend>> piter = pmap.entrySet().iterator();
 		
    	System.out.println(">>>>> Dijkstra -- parent: " + pmap.size());
    	int n = 0;
    	while(piter.hasNext()) {
    		Map.Entry<Friend, Friend> pnode = 
    				(Map.Entry<Friend, Friend>) piter.next();
    		Friend u1 = pnode.getKey();
    		Friend v1 = pnode.getValue();
    		i++;
    		System.out.println("pmap " + i + "-- " + u1.id + " ; " + v1.id);
    	}
    	System.out.println("------------ End Dijkstra parent --------");
		/*
		 * dpair.distanceMap -- Map<Friend, Integer>
		 * dpair.parentMap -- Map<Friend, Friend>
		 */
	}
	// Test Dijkstra Algorithm
	public static void testDijkstra2() {
		Friend f0 = getFriend("000", "000000");
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		Friend f6 = getFriend("666", "666666");
		Friend f7 = getFriend("777", "777777");
		
		f0.friends.put(f2, 26);
		f0.friends.put(f4, 38);
		f0.friends.put(f6, 58);
		f0.friends.put(f7, 16);

		f1.friends.put(f2, 36);
		f1.friends.put(f3, 29);
		f1.friends.put(f5, 32);
		f1.friends.put(f7, 19);

		f2.friends.put(f0, 26);
		f2.friends.put(f1, 36);
		f2.friends.put(f3, 17);
		f2.friends.put(f6, 40);
		f2.friends.put(f7, 34);

		f3.friends.put(f1, 29);
		f3.friends.put(f2, 17);
		f3.friends.put(f6, 52);

		f4.friends.put(f0, 38);
		f4.friends.put(f5, 35);
		f4.friends.put(f6, 93);
		f4.friends.put(f7, 37);

		f5.friends.put(f1, 32);
		f5.friends.put(f4, 35);
		f5.friends.put(f7, 28);

		f6.friends.put(f0, 58);
		f6.friends.put(f2, 40);
		f6.friends.put(f3, 52);
		f6.friends.put(f4, 93);
		
		f7.friends.put(f0, 16);
		f7.friends.put(f1, 19);
		f7.friends.put(f2, 34);
		f7.friends.put(f4, 37);
		f7.friends.put(f5, 28);

		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f0);
		people.add(f1);
		people.add(f2);
		people.add(f3);
		people.add(f4);
		people.add(f5);
		people.add(f6);
		people.add(f7);
		
		Dijkstra d = new Dijkstra();
		DijkstraPair dpair = d.dijkstraSP(people, f1);
		
		Map<Friend, Integer> dmap = dpair.distanceMap;
		Map<Friend, Friend> pmap = dpair.parentMap;
		
    	Iterator<Entry<Friend, Integer>> diter = dmap.entrySet().iterator();
 		
    	System.out.println(">>>>> Dijkstra -- " + "distance: " + dmap.size() + ">>>>>>");
    	int i = 0;
    	while(diter.hasNext()) {
    		Map.Entry<Friend, Integer> dnode = 
    				(Map.Entry<Friend, Integer>) diter.next();
    		Friend v = dnode.getKey();
    		int dval = dnode.getValue();
    		i++;
    		System.out.println("dmap " + i + ": " + v.id + ", " + dval);
    	}
    	System.out.println("--------- End Dijkstra distance -------");
		
    	Iterator<Entry<Friend, Friend>> piter = pmap.entrySet().iterator();
 		
    	System.out.println(">>>>> Dijkstra -- parent: " + pmap.size());
    	int n = 0;
    	while(piter.hasNext()) {
    		Map.Entry<Friend, Friend> pnode = 
    				(Map.Entry<Friend, Friend>) piter.next();
    		Friend u1 = pnode.getKey();
    		Friend v1 = pnode.getValue();
    		i++;
    		System.out.println("pmap " + i + "-- " + u1.id + " ; " + v1.id);
    	}
    	System.out.println("------------ End Dijkstra parent --------");
		/*
		 * dpair.distanceMap -- Map<Friend, Integer>
		 * dpair.parentMap -- Map<Friend, Friend>
		 */
	}
	
	// Test Degree Centrality
	public static void testDegreeCentrality1(String filename) {
		FakebookReader fakebook = new FakebookReader(filename);
		
		HashSet<Friend> people = new HashSet<Friend>();
		Iterator<Friend> iter = fakebook.iterator();
		while(iter.hasNext()) {
			Friend f = iter.next();
			people.add(f);
		}
		
		int degree = DegreeCentrality.getDegree(people, "123456");
		
	}
	// Test Degree Centrality
	public static void testDegreeCentrality2() {
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		
		f1.friends.put(f2, 8);
		f1.friends.put(f3, 16);
	
		f2.friends.put(f1, 8);
		f2.friends.put(f3, 5);
		f2.friends.put(f4, 2);
	
		f3.friends.put(f1, 16);
		f3.friends.put(f2, 5);
		f3.friends.put(f4, 11);
	
		f4.friends.put(f2, 2);
		f4.friends.put(f3, 11);
		f4.friends.put(f5, 28);
	
		f5.friends.put(f4, 28);
		
		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f5);
		people.add(f4);
		people.add(f3);
		people.add(f2);
		people.add(f1);
		
		int degree = DegreeCentrality.getDegree(people, "111");
		System.out.println(">>> Degree Centrality -- 111: " + degree +" >>>");

		degree = DegreeCentrality.getDegree(people, "222");
		System.out.println(">>> Degree Centrality -- 222: " + degree +" >>>");
		
		degree = DegreeCentrality.getDegree(people, "555");
		System.out.println(">>> Degree Centrality -- 555: " + degree +" >>>");
	
		try {
			degree = DegreeCentrality.getDegree(people, "123456");
		} catch(IllegalArgumentException e) {
			System.out.println(">>> Test IllegalArgumentException: Correct >>>");
		}
	}	
	
	// Test Closeness Centrality
	public static void testClosenessCentrality() {
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		
		f1.friends.put(f2, 8);
		f1.friends.put(f3, 16);
	
		f2.friends.put(f1, 8);
		f2.friends.put(f3, 5);
		f2.friends.put(f4, 2);
	
		f3.friends.put(f1, 16);
		f3.friends.put(f2, 5);
		f3.friends.put(f4, 11);
	
		f4.friends.put(f2, 2);
		f4.friends.put(f3, 11);
		f4.friends.put(f5, 28);
	
		f5.friends.put(f4, 28);
		
		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f5);
		people.add(f4);
		people.add(f3);
		people.add(f2);
		people.add(f1);

		double c = ClosenessCentrality.Centrality(people, f1);
		System.out.println(">>>>>> Closeness Centrality >>>>>>");
		System.out.println("Centrality f1: " + c);
		
		c = ClosenessCentrality.Centrality(people, f2);
		System.out.println("Centrality f2: " + c);
		
		c = ClosenessCentrality.Centrality(people, f4);
		System.out.println("Centrality f4: " + c);
		
		c = ClosenessCentrality.Centrality(people, f5);
		System.out.println("Centrality f5: " + c);
	// ClosenessCentrality.Centrality(Set<Friend> people, Friend source);
		
	}
	// Test Bron Kerbosch Algorithm
	public static void testBronKerbosch() {
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		
		f1.friends.put(f2, 8);
		f1.friends.put(f3, 16);
	
		f2.friends.put(f1, 8);
		f2.friends.put(f3, 5);
		f2.friends.put(f4, 2);
	
		f3.friends.put(f1, 16);
		f3.friends.put(f2, 5);
		f3.friends.put(f4, 11);
	
		f4.friends.put(f2, 2);
		f4.friends.put(f3, 11);
		f4.friends.put(f5, 28);
	
		f5.friends.put(f4, 28);
		
		HashSet<Friend> people = new HashSet<Friend>();
		people.add(f5);
		people.add(f4);
		people.add(f3);
		people.add(f2);
		people.add(f1);
		
		BronKerbosch bronKerbosch1 = new BronKerbosch();
	    Set<Set<Friend>> fsets = bronKerbosch1.maxCliques(people);
	    System.out.println(">>>>>> Bron Kerbosh >>>>>>");
		int i = 0;
		for(Set<Friend> fset : fsets) {
			i++;
			System.out.println("------ Set " + i + "--------");
			for(Friend f : fset) {
				String s = f.toString();
				System.out.println(s);
				System.out.println("++++++++++");
			}
		}
	}
	// Test Betweenness Centrality: Ulik Brandes Algorithm
	public static void testBetweeness() {
		Friend f1 = getFriend("111", "111111");
		Friend f2 = getFriend("222", "222222");
		Friend f3 = getFriend("333", "333333");
		Friend f4 = getFriend("444", "444444");
		Friend f5 = getFriend("555", "555555");
		
		f1.friends.put(f2, 8);
		f1.friends.put(f3, 16);
	
		f2.friends.put(f1, 8);
		f2.friends.put(f3, 5);
		f2.friends.put(f4, 2);
	
		f3.friends.put(f1, 16);
		f3.friends.put(f2, 5);
		f3.friends.put(f4, 11);
	
		f4.friends.put(f2, 2);
		f4.friends.put(f3, 11);
		f4.friends.put(f5, 28);
	
		f5.friends.put(f4, 28);
		
		HashMap<String, Friend> people = new HashMap<String, Friend>();
		people.put(f5.id, f5);
		people.put(f4.id, f4);
		people.put(f3.id, f3);
		people.put(f2.id, f2);
		people.put(f1.id, f1);
		
		System.out.println(">>>>>>> Betweeness Centrality >>>>>>");
		Betweeness between = new Betweeness(people);
		double centrality = between.calculate(f1);
		
		System.out.println("f1 Betweeness: " + centrality);
		
		centrality = between.calculate(f2);
		System.out.println("f2 Betweeness: " + centrality);

		centrality = between.calculate(f3);
		System.out.println("f3 Betweeness: " + centrality);

		centrality = between.calculate(f5);
		
		System.out.println("f5 Betweeness: " + centrality);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testFakebook1("5.json");
		//testFakebook1("50.json");
		// testFakebook1("300.json");

		// testFakebook2("5.json");
		
		//testFakebook2("50.json");
		//testFakebook2("300.json");
		
		// testFakebook3("5.json");
		//testFakebook3("50.json");
		//testFakebook3("300.json");
		
		// testFakebook4();
		
		//testKruskal1();
		
		// testKruskal2("300.json");
		// testDijkstra1();
		// testDijkstra2();
		// testDegreeCentrality2();
		//testClosenessCentrality();
		//testBronKerbosch();
		//testBetweeness();
	}
}
