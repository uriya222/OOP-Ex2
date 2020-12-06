package object;

import api.edge_data;
import api.geo_location;
import api.node_data;

public interface AgentsInterface{


    public int getId();
    public double getValue();
    public int getSrc();
    public int getDest();
    public double getSpeed();
    public geo_location getPos();
    public node_data getCurr_node();
    public edge_data getCurr_edge();

    public void setValue(double value);
    public void setSrc(int src);
    public void setDest(int dest);
    public void setCurr_edge(edge_data curr_edge);
    public void setCurr_node(node_data curr_node);
    public void setPos(geo_location pos);
    public void setSpeed(double i);

    /**
     * return true if and only if the agent location is not on a node
     * (by using edge_data as class variable)
     * @return true if and only if the agent location is not on a node
     */
    public boolean isMoving();
    /**
    this function allow you to update status of an agent by sending a json string represent an agent
    @param json - example:  {"Agent":{"id":0,"value":0.0,"src":1,"dest":-1,"speed":1.0,"pos":"35.20319591121872,32.10318254621849,0.0"}}
     */
    public void update(String json);

    /**
     * convert Agent to json String
     * @return json String
     */
    public String toJSON();

    /**
     * when agent is on his way, on particular edge,
     * this function return the id of the node which our agent is getting to.
     * @return id of the node which our agent is getting to
     */
    public int getNextNode();

    /**
     * set new destination to this agent
     * @param dest id of the destination node
     * @return true only if the implementation done
     */
    public boolean setNextNode(int dest);
}
