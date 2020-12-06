package object;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


public class Agent implements AgentsInterface{
    private int id;
    private double value; //amount of money this agent scored
    private int src;
    private int dest;
    private double speed;
    private geo_location pos;
    private edge_data curr_edge;
    private node_data curr_node;
    private directed_weighted_graph ga; //the major graph

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

    public edge_data getCurr_edge() {
        return curr_edge;
    }

    public node_data getCurr_node() {
        return curr_node;
    }

    public Agent(directed_weighted_graph g, int startNode){
        this.id=-1;
        ga=new DWGraph_DS(g);
        this.value=0;
        this.curr_node=ga.getNode(startNode);
        setSpeed(0);
        this.src=0;
        this.dest=-1;
        curr_edge=null;
        pos=new GeoLocation(this.curr_node.getLocation());
    }
    public void update(String json){
        GsonBuilder b=new GsonBuilder();
        Gson gson=b.create();
        JsonElement r=gson.fromJson(json,JsonElement.class);
        JsonObject r2=r.getAsJsonObject().get("Agent").getAsJsonObject();
        if (r2.get("id").getAsInt()==getId()||this.getId()==-1) {
            String[] pos1 = r2.get("pos").getAsString().split(",");
            double x = Double.parseDouble(pos1[0]);
            double y = Double.parseDouble(pos1[1]);
            double z = Double.parseDouble(pos1[2]);
            geo_location a2 = new GeoLocation(x, y, z);
            setPos(a2);
            setValue(r2.get("value").getAsDouble());
            setSrc(r2.get("src").getAsInt());
            setDest(r2.get("dest").getAsInt());
            setSpeed(r2.get("speed").getAsDouble());
        }
    }
    public void setValue(double value) {this.value = value;}
    public void setSrc(int src) {this.src = src;}
    public void setDest(int dest) {this.dest = dest; }
    public void setCurr_edge(edge_data curr_edge) {this.curr_edge = curr_edge; }
    public void setCurr_node(node_data curr_node) {this.curr_node = curr_node;}
    public void setPos(geo_location pos) {this.pos = pos;}
    public void setSpeed(double i) {this.speed=i;}
    public boolean isMoving() {return this.curr_edge!=null;}
    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":"+this.id+","
                + "\"value\":"+this.value+","
                + "\"src\":"+this.curr_node.getKey()+","
                + "\"dest\":"+d+","
                + "\"speed\":"+this.getSpeed()+","
                + "\"pos\":\""+pos.toString()+"\""
                + "}"
                + "}";
        return ans;
    }
    public int getNextNode() {
        if(this.curr_edge==null)
            return -1;
        else {
            return this.curr_edge.getDest();
        }
    }
    public boolean setNextNode(int dest) {
        int src = this.curr_node.getKey();
        this.curr_edge = this.ga.getEdge(src, dest);
        if(curr_edge!=null) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) throws InterruptedException {
        int scenario_num = 0;
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        System.out.println(game.toString());
        game.addAgent(0);
        String s2=game.getAgents();
        String s=game.getGraph();
        try {
            PrintWriter pw=new PrintWriter(new File("gr.json"));
            pw.write(s);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dw_graph_algorithms l=new DWGraph_Algo();
        l.load("gr.json");
        System.out.println(s);
        game.startGame();
        System.out.println(game.getAgents());
        //game.chooseNextEdge(0,1);
        // System.out.println(game.getAgents());
        //TimeUnit.SECONDS.sleep(2);
        Gson gson1=new Gson();
        Gson gson2=new Gson();
        String s23=game.move();
        JsonElement r=gson1.fromJson(s23,JsonElement.class);
        String[] pos1=r.getAsJsonObject().get("Agents").getAsJsonArray().get(0).getAsJsonObject().get("Agent").getAsJsonObject().get("pos").getAsString().split(",");
        double x2=Double.parseDouble(pos1[0]);
        double y2=Double.parseDouble(pos1[1]);
        geo_location a2=new GeoLocation(x2,y2,0);
        game.chooseNextEdge(0,1);
        TimeUnit.SECONDS.sleep(1);
        JsonElement r2=gson2.fromJson(game.move(),JsonElement.class);
        String[] pos2=r2.getAsJsonObject().get("Agents").getAsJsonArray().get(0).getAsJsonObject().get("Agent").getAsJsonObject().get("pos").getAsString().split(",");
        double x=Double.parseDouble(pos2[0]);
        double y=Double.parseDouble(pos2[1]);
        geo_location a=new GeoLocation(x,y,0);
        System.out.println(game.getAgents());
        System.out.println(a.distance(a2));
        System.out.println(game.getPokemons());
        // System.out.println(game.getAgents());
        // TimeUnit.SECONDS.sleep(3);
        game.stopGame();
        geo_location g4=new GeoLocation(2.3333333333,3,0.0);
        geo_location g3=new GeoLocation(5.3333333333,3,0.0);
        geo_location p222=new GeoLocation(3.3333333333,3,0);
        double dist222=3;
        double part= g4.distance(p222)+p222.distance(g3);
        System.out.println(dist222+" "+part);

    }


}
