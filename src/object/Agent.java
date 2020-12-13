package object;

import api.GeoLocation;
import api.geo_location;

/**
 * this class represent an agent in client graph,by implementing AgentsInterface interface.
 */

public class Agent implements AgentsInterface {
    private int id;
    private double value; //amount of money this agent scored
    private int src;
    private int dest;
    private double speed;
    private geo_location pos;
    private long timing =-1;
    public Agent(int id, double value, int src, int dest, geo_location geo, double speed) {
        this.id = id;
        this.value = value;
        setSpeed(speed);
        this.src = src;
        this.dest = dest;
        this.pos = geo;
    }

    /**
     * copy constructor
     * @param a
     */
    public Agent(AgentsInterface a){
        id = a.getId();
        value = a.getValue();
        src = a.getSrc();
        dest = a.getDest();
        pos = new GeoLocation(a.getPos());
        speed = a.getSpeed();
        timing = a.getTiming();
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public double getSpeed() {
        return speed;
    }

    public geo_location getPos() {
        return pos;
    }

    public void setTiming(long timing){
        this.timing = timing;
    }

    public long getTiming(){
        return timing;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setPos(geo_location pos) {
        this.pos = pos;
    }

    public void setSpeed(double i) {
        this.speed = i;
    }
    @Override
    public String toString(){
        String ans = "{\"Agent\":{"
                + "\"id\":"+this.id+","
                + "\"value\":"+this.value+","
                + "\"src\":"+this.src+","
                + "\"dest\":"+dest+","
                + "\"speed\":"+getSpeed()+","
                + "\"pos\":\""+this.pos.toString()+"\""
                + "}"
                + "}";
        return ans;
    }
}
