package api.object;

import api.geo_location;

public interface AgentsInterface{


    public int getId();
    public double getValue();
    public int getSrc();
    public int getDest();
    public double getSpeed();
    public geo_location getPos();

    /*
    {"Agents":[{"Agent":{"id":0,"value":0.0,"src":1,"dest":-1,"speed":1.0,"pos":"35.20319591121872,32.10318254621849,0.0"}}]}

     */
}
