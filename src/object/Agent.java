package object;

import api.geo_location;

/**
 * this class represent an agent in client graph,by implementing AgentsInterface interface, with this following method:
 * update(String json)
 * boolean isMoving()
 * String toJSON()
 */

public class Agent implements AgentsInterface {
    private int id;
    private double value; //amount of money this agent scored
    private int src;
    private int dest;
    private double speed;
    private geo_location pos;

    public Agent(int id, double value, int src, int dest, geo_location geo, double speed) {
        this.id = id;
        this.value = value;
        setSpeed(speed);
        this.src = src;
        this.dest = dest;
        this.pos = geo;
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
}
