package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Pokemon {
    private edge_data edge;
    private double value;
    private int type;
    private geo_location pos;
    private double min_dist;
    private int min_ro;

    public Pokemon(String json) {
        FromJson(json);
        EdgeFromPosition e2=new EdgeFromPosition(this.pos,this.type);
        setEdge(e2.getEdge());
        min_dist = -1;
        min_ro = -1;
    }
    public void FromJson(String json) {
        try {
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
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void setPosition(geo_location gl) {this.pos = new GeoLocation(gl);}
    public void setEdge(edge_data e) {this.edge=new EdgeData(e);}
}
