package api;

import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


public class Agent {
    private int id;
    private static int num_of_agents=0;
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
        this.id=num_of_agents++;
        ga=new DWGraph_DS(g);
        this.value=0;
        this.curr_node=ga.getNode(startNode);
        setSpeed(0);
        this.src=0;
        this.dest=-1;
        curr_edge=null;
        pos=new GeoLocation(ga.getNode(startNode).getLocation());
    }

    public void setSpeed(double i) {this.speed=i;}

    public static void main(String[] args) throws InterruptedException {
        System.out.println(0.001*0.001);
        int scenario_num = 0;
        game_service game = Game_Server_Ex2.getServer(scenario_num);
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
       // System.out.println(game.move()+"first");
        //System.out.println(game.move()+"2");
        game.chooseNextEdge(0,1);
        TimeUnit.SECONDS.sleep(1);
        //System.out.println(game.move()+"3");
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
    }


}
