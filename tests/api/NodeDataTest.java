package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest{
    private NodeData n;
    @BeforeEach
    void setUp(){
        n = new NodeData(1);
    }

    @Test
    void Key(){
      //  NodeData n = new NodeData(1);
        //check int
        assertEquals(1,n.getKey());
    }

    @Test
    void Location(){
        n = new NodeData(1);
        //check null
        assertNull(n.getLocation());
        //set location
        geo_location l = new GeoLocation(1,2,0);
        n.setLocation(l);
        assertEquals(l,n.getLocation());
    }

    @Test
    void Weight(){
        //if null
        assertEquals(0,n.getWeight());
        //if set
        n.setWeight(1);
        assertEquals(1,n.getWeight());
    }

    @Test
    void Info(){
        //if null
        assertEquals("",n.getInfo());
        //if set
        n.setInfo("test");
        assertEquals("test",n.getInfo());
    }


    @Test
    void Tag(){
        //if not set
        assertEquals(0,n.getTag());
        //if set
        n.setTag(12);
        assertEquals(12,n.getTag());
    }

}