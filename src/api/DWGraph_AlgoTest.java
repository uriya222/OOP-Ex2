package api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    private directed_weighted_graph graph_creator(int v, int e) {
        directed_weighted_graph g = new DWGraph_DS();
        int[] V = new int[v];
        for (int i = 0; i < v; i++) {
            V[i] = i + 5000;
        }
        AddNodeToGraph(g, V);
        while (g.edgeSize() < e) {
            int rand1 = (int) (Math.random() * v) - 1;
            int rand2 = (int) (Math.random() * v) - 1;
            double rand3 = (Math.random() * 500) + 1;
            g.connect(rand1, rand2, rand3);
        }
        return g;
    }

    private void AddNodeToGraph(directed_weighted_graph g1, int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            node_data x = new NodeData(arr[i]);
            g1.addNode(x);
        }
    }

    private boolean CheckList(dw_graph_algorithms g2, ArrayList<Integer> match, int[] a) {
        int mone = 0, x = -1;
        if (g2.shortestPath(a[0], a[1]) != null) {
            for (node_data i : g2.shortestPath(a[0], a[1])) {
                if (!match.isEmpty()) {
                    x = match.get(mone++);
                }
                if (x != i.getKey()) return false;
            }
        }
        return true;
    }

    private void ConnectNodesInGraph(directed_weighted_graph g1, double[][] a) {
        for (int i = 0; i < a.length; i++) {
            g1.connect((int) (a[i][0]), (int) (a[i][1]), a[i][2]);
        }

    }

    /**
     * this test build graph g1 with 2 nodes and an edge between with weight 80.6
     * build graph_algorithm g2 and init g2 to g1.
     * cheking if edge_size in both graphs are equal.
     */
    @Test
    public void test0() {
        directed_weighted_graph g1 = new DWGraph_DS();
        int[] arr = {3, 4};
        AddNodeToGraph(g1, arr);
        double[][] a = {{3, 4, 80.6}};
        ConnectNodesInGraph(g1, a);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        assertEquals(g2.getGraph().edgeSize(), g1.edgeSize());
    }

    /**
     * this test build graph g1 with 5 nodes and 4 edges.
     * build graph_algorithm g2 and init g2 to g1.
     * build graph_algorithm g3 and copy g2 to g3.
     * cheking if the nodes in both graphs (g1,g3) are equal.
     * remove one node from g3 and checking that g2 still stay the same.
     */
    @Test
    public void test1() {
        directed_weighted_graph g1 = new DWGraph_DS();
        int[] arr = {0, 1, 2, 3, 4};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 1}, {1, 3, 1}, {4, 0, 5}, {0, 2, 3}};
        ConnectNodesInGraph(g1, a);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        directed_weighted_graph g3 = g2.copy();
        for (node_data x : g3.getV()) {
            assertNotNull(g1.getNode(x.getKey()));
        }
        g3.removeNode(0);
        assertNotNull(g2.getGraph().getNode(0));
    }

    @Test
    public void testCopy() {
        directed_weighted_graph g1 = new DWGraph_DS();
        int[] arr = {0, 1, 2, 3, 4};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 1}, {1, 3, 1}, {4, 0, 5}, {0, 2, 3}};
        ConnectNodesInGraph(g1, a);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        directed_weighted_graph g3 = g2.copy();
        g1.removeNode(0);
        assertNotNull(g3.getEdge(0, 1));
        assertNotNull(g3.getEdge(0, 2));
        assertNotNull(g3.getEdge(4, 0));
        assertNotNull(g3.getNode(0));
    }

    /**
     * this test checks if 3 basic kinds of directed_weighted_graph is connected:
     * 1) empty graph - return true
     * 2) singel node - return true
     * 3) two singel nodes - return false
     */
    @Test
    public void testConnected1() {
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        assertTrue(g2.isConnected());
        node_data n1 = new NodeData(0);
        g1.addNode(n1);
        assertTrue(g2.isConnected());
        node_data n2 = new NodeData(1);
        g1.addNode(n2);
        assertFalse(g2.isConnected());
    }

    /**
     * this test checks if more complex directed_weighted_graph is connected
     * using methods like "remove node", "remove edge"
     */
    @Test
    public void testConnected2() {
        directed_weighted_graph g1 = new DWGraph_DS();
        int[] arr = {0, 1, 2, 3, 4};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 1}, {1, 2, 1}, {2, 3, 5}, {3, 4, 3}, {4, 0, 6}};
        ConnectNodesInGraph(g1, a);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        assertTrue(g2.isConnected());
        g2.getGraph().removeNode(2);
        assertFalse(g2.isConnected());
        g2.getGraph().removeNode(1);
        assertFalse(g2.isConnected());
        directed_weighted_graph g3 = graph_creator(10000, 500);
        //directed_weighted_graph g4=graph_creator(1000000, 5000000);
        for (int i = 0; i < 10000; i++) {
            g3.removeEdge(1, i);
        }
        g2.init(g3);
        assertFalse(g2.isConnected());
    }


    @Test
    public void testShortDistSize1() {
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        assertEquals(g2.shortestPathDist(1, 3), -1);
        int[] arr1 = {10};
        AddNodeToGraph(g1, arr1);
        assertEquals(g2.shortestPathDist(10, 10), 0);
        int[] arr3 = {30};
        AddNodeToGraph(g1, arr3);
        assertEquals(g2.shortestPathDist(10, 30), -1);
        int[] arr2 = {0, 1, 2, 3, 4};
        AddNodeToGraph(g1, arr2);
        double[][] a = {{0, 1, 6}, {0, 3, 1}, {1, 2, 5}, {3, 1, 2}, {1, 3, 2}, {1, 4, 2}, {2, 4, 5}, {3, 4, 1}};
        ConnectNodesInGraph(g1, a);
        g2.init(g1);
        assertEquals(g2.shortestPathDist(0, 2), 8);
        g1.removeNode(3);
        assertEquals(g2.shortestPathDist(0, 2), 11);
        g1.removeEdge(1, 2);
        assertEquals(g2.shortestPathDist(0, 2), -1);
    }

    @Test
    public void testShortDistSize2() {
        directed_weighted_graph g1 = new DWGraph_DS();
        int[] arr = {0, 1, 2, 3, 4};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 6}, {1, 0, 6}, {1, 2, 5}, {2, 1, 5}, {2, 3, 2}, {3, 2, 2,}, {0, 3, 1.5}, {3, 0, 1.5}};
        ConnectNodesInGraph(g1, a);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        g2.init(g1);
        assertEquals(g2.shortestPathDist(1, 3), 7);
        g1.removeNode(4);
        assertEquals(g2.shortestPathDist(1, 3), 7);
        g1.removeEdge(2, 3);
        assertEquals(g2.shortestPathDist(1, 3), 7.5);
    }


    @Test
    public void testShortDistList() {
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms g2 = new DWGraph_Algo();
        ArrayList<Integer> match = new ArrayList<>();
        g2.init(g1);
        int[] a1 = {0, 2};
        assertNull(g2.shortestPath(0,2));
        int[] arr1 = {10};
        AddNodeToGraph(g1, arr1);
        match.add(10);
        int[] a2 = {10, 10};
        assertTrue(CheckList(g2, match, a2));
        int[] arr3 = {30};
        AddNodeToGraph(g1, arr3);
        match.clear();
        int[] a3 = {10, 30};
        assertTrue(CheckList(g2, match, a3));
        int[] arr = {0, 1, 2, 3, 4, 5};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 6}, {0, 3, 1}, {1, 2, 5}, {1, 3, 2}, {1, 4, 2,}, {2, 4, 5}, {3, 4, 1}};
        ConnectNodesInGraph(g1, a);
        g2.init(g1);
        int a4[] = {0, 4};
        match.add(0);
        match.add(3);
        match.add(4);
        assertTrue(CheckList(g2, match, a4));
        assertNull(g2.shortestPath(2,5));
        g1.removeNode(3);
        match.clear();
        match.add(0);
        match.add(1);
        match.add(4);
        assertTrue(CheckList(g2, match, a4));
        g1.removeEdge(1, 4);
        match.clear();
        match.add(0);
        match.add(1);
        match.add(2);
        match.add(4);
        assertTrue(CheckList(g2, match, a4));
    }

    @Test
    public void testSaveAndLoad1() {
        directed_weighted_graph g1 = new DWGraph_DS();
        directed_weighted_graph g4 = new DWGraph_DS();
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        AddNodeToGraph(g1, arr);
        double[][] a = {{0, 1, 1}, {1, 5, 6}, {3, 2, 3}, {9, 8, 3}, {5, 9, 2}, {2, 9, 1}, {1, 2, 16.3}, {1, 7, 4}, {7, 3, 8.2}, {0, 6, 1.5}, {6, 10, 18}, {10, 2, 1}, {8, 9, 3}, {8, 2, 4}, {0, 4, 20}};
        ConnectNodesInGraph(g1, a);
        geo_location gn=new GeoLocation(12.5,3,0);
        g1.getNode(0).setLocation(gn);
        dw_graph_algorithms g2 = new DWGraph_Algo();
        dw_graph_algorithms g3 = new DWGraph_Algo();
        g2.init(g1);
        assertEquals(g2.shortestPathDist(0, 2), 16);
        assertTrue(g2.save("US1.json"));
        assertTrue(g3.load("US1.json"));
        assertEquals(g3.shortestPathDist(0, 2), 16);
        assertEquals(g3,g2);
        g4= g3.getGraph();
        g4.removeEdge(0,1);
        assertNotEquals(g2,g3);
    }

    @Test
    public void LoadFromGivenFile() {
       dw_graph_algorithms g2 = new DWGraph_Algo();
       assertTrue(g2.load("data\\A0.json"));
        assertTrue(g2.load("data\\A1.json"));
        assertTrue(g2.load("data\\A2.json"));
        assertTrue(g2.load("data\\A3.json"));
        assertTrue(g2.load("data\\A4.json"));
        assertTrue(g2.load("data\\A5.json"));
    }
}

