package object;

import api.GeoLocation;
import api.edge_data;
import api.geo_location;

/**
 * this class represent single pokemon in client graph,by implementing PokemonInterface interface, with this following method:
 * InitFromJson(String json)
 */
public class Pokemon implements PokemonInterface{
    private edge_data edge;
    private double value;
    private int type;
    private int tag;
    private geo_location pos;

    public Pokemon(double value, int type, geo_location pos){
        this.value = value;
        this.type = type;
        this.pos = pos;
        this.edge=null;
        this.tag=0;
    }

    public void setPosition(geo_location gl) {this.pos = new GeoLocation(gl);}
    public int getType(){return this.type;}
    public geo_location getPos() {return this.pos;}
    public double getValue() {return this.value;}
    public edge_data getEdge() {return this.edge;}
    public void setEdge(edge_data ed) {this.edge=ed;}
    public int getTag(){return this.tag;}
    public void setTag(int t){this.tag=t;}
    @Override
    public String toString(){
        String ans = "{\"Pokemon\":{"
                + "\"value\":"+this.value+","
                + "\"src\":"+getEdge().getSrc()+","
                + "\"dest\":"+getEdge().getDest()+","
                + "\"pos\":\""+this.pos.toString()+"\""
                + "}"
                + "}";
        return ans;
    }
}
