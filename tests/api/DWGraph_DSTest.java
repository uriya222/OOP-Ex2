package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest{

    @Test
    void nodes() {
        DWGraph_DS g = new DWGraph_DS();
        System.out.println("MC1: "+g.getMC());
        //if graph empty
        assertNull(g.getNode(12));
        assertNull(g.getE(12));
        assertNull(g.getV());
        assertNull(g.removeEdge(1,2));
        g.connect(1,2,4);
        System.out.println("MC2: "+g.getMC());
        assertEquals(0,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //if 1 node
        g.addNode(new NodeData(12));

        System.out.println("MC3: "+g.getMC());
        assertNotNull(g.getNode(12));
        assertNull(g.getE(12));
        assertNotNull(g.getV());
        assertNull(g.removeEdge(12,32));
        g.connect(12,2,4);
        System.out.println("MC4: "+g.getMC());
        assertEquals(1,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //if removed and reinsert
        g.removeNode(12);
        System.out.println("MC5: "+g.getMC());
        assertNull(g.getNode(12));
        g.addNode(new NodeData(12));
        System.out.println("MC6: "+g.getMC());
        assertEquals(12,g.getNode(12).getKey());
    }

    @Test
    void Edges() {
        DWGraph_DS g = new DWGraph_DS();
        System.out.println("MC1: "+g.getMC());
        //if null
        g.connect(1,2,12);
        System.out.println("MC2: "+g.getMC());
        //only 1
        g.addNode(new NodeData(12));
        System.out.println("MC3: "+g.getMC());
        g.connect(1,12,12);
        System.out.println("MC4: "+g.getMC());
        g.connect(12,1,12);
        System.out.println("MC5: "+g.getMC());
        g.addNode(new NodeData(10));
        System.out.println("MC6: "+g.getMC());
        g.connect(12,10,12);
        assertEquals(1,g.edgeSize());
        g.connect(10,12,12);
        assertEquals(2,g.edgeSize());

        // couple
    }

    @Test
    void removeNode() {
        DWGraph_DS g = new DWGraph_DS();
        //check normal connection
        g.addNode(new NodeData(12));
        g.addNode(new NodeData(10));
        g.connect(12,10,1);
        System.out.println("MC1: "+g.getMC());
        assertEquals(1,g.edgeSize());
        //check if remove src
        g.removeNode(12);
        System.out.println("MC2: "+g.getMC());
        assertEquals(0,g.edgeSize());
        //again
        g.addNode(new NodeData(12));
        g.connect(12,10,1);
        assertEquals(1,g.edgeSize());
        g.removeNode(12);
        assertEquals(0,g.edgeSize());
        //check opposite - remove dest
        g.addNode(new NodeData(12));
        g.connect(12,10,1);
        g.removeNode(10);
        assertEquals(0,g.edgeSize());
        //some more edges deletion
        g.addNode(new NodeData(15));
        g.addNode(new NodeData(16));
        g.addNode(new NodeData(17));
        System.out.println(g.edgeSize());
        g.connect(12,15,1);
        System.out.println(g.edgeSize());
        g.connect(12,16,1);
        System.out.println(g.edgeSize());
        g.connect(12,17,1);
        System.out.println(g.edgeSize());
        g.connect(17,12,1);
        System.out.println(g.edgeSize());
        assertEquals(4,g.edgeSize());
        g.removeNode(12);
        assertEquals(0,g.edgeSize());

    }

    @Test
    void NodeCopyConstructors() {
        //empty
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_DS c = new DWGraph_DS(g);
        //1 node
        g.addNode(new NodeData(12));
        c = new DWGraph_DS(g);
        assertEquals(12,c.getNode(12).getKey());
        //2 nodes
        g.addNode(new NodeData(13));
        c = new DWGraph_DS(g);
        //2 nodes 1 edge
        g.connect(10,12,2);
        c = new DWGraph_DS(g);
        //3 nodes 5 edges
        g.addNode(new NodeData(14));
        g.connect(12,10,2);
        g.connect(13,12,2);
        g.connect(14,12,2);
        g.connect(14,10,2);
        edge_data e;
        c = new DWGraph_DS(g);
        //after removing edge
        e = g.removeEdge(14,10);
        assertNotNull(e);
        c = new DWGraph_DS(g);
        //after removing node
        g.removeNode(12);
        c = new DWGraph_DS(g);
    }
    @Test
    void GraphCopyConstructors() {

    }
    @Test
    void EdgeCopyConstructors() {

    }
}