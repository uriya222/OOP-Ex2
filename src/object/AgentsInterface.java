package object;

import api.geo_location;

public interface AgentsInterface{


    public int getId();
    public double getValue();
    public int getSrc();
    public int getDest();
    public double getSpeed();
    public geo_location getPos();

    public void setValue(double value);
    public void setSrc(int src);
    public void setDest(int dest);
    public void setPos(geo_location pos);
    public void setSpeed(double i);

}
