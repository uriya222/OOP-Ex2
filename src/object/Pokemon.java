package object;

import api.GeoLocation;
import api.edge_data;
import api.geo_location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * this class represent single pokemon in client graph,by implementing PokemonInterface interface, with this following method:
 * InitFromJson(String json)
 */
public class Pokemon implements PokemonInterface{
    private edge_data edge;
    private double value;
    private int type;
    private geo_location pos;
    private double min_dist;
    private int min_ro;

    public Pokemon(edge_data edge, double value, int type, geo_location pos){
        this.edge = edge;
        this.value = value;
        this.type = type;
        this.pos = pos;
    }

    public Pokemon(String json) {
        InitFromJson(json);
        this.edge=null;
        min_dist = -1;
        min_ro = -1;
    }
    public void InitFromJson(String json) {
            GsonBuilder b=new GsonBuilder();
            Gson gson=b.create();
            JsonElement r=gson.fromJson(json,JsonElement.class);
            JsonObject r2=r.getAsJsonObject().get("Pokemon").getAsJsonObject();
            String[] pos2=r2.get("pos").getAsString().split(",");
            double x=Double.parseDouble(pos2[0]);
            double y=Double.parseDouble(pos2[1]);
            double z=Double.parseDouble(pos2[2]);
            geo_location a=new GeoLocation(x,y,z);
            setPosition(a);
            double v=r2.get("value").getAsDouble();
            int t=r2.get("type").getAsInt();
            this.type=t;
            this.value=v;
    }
    public void setPosition(geo_location gl) {this.pos = new GeoLocation(gl);}
    public int getType(){return this.type;}
    public geo_location getPos() {return this.pos;}
    public double getValue() {return this.value;}
    public edge_data getEdge() {return this.edge;}
    public void setEdge(edge_data ed) {this.edge=ed;}
}
