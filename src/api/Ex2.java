package api;

import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Ex2{
    directed_weighted_graph mainGraph;
    public Ex2(){
        init();
    }
    public directed_weighted_graph getG(){return this.mainGraph;}
    public void init(){
        game_service game= Game_Server_Ex2.getServer(11);
        String s = game.getGraph();
        try {
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dw_graph_algorithms al = new DWGraph_Algo();
        al.load("GServer.json");
        this.mainGraph= al.getGraph();
    }
    public static void main(String[] args) throws InterruptedException {
        MainManager m=new MainManager(1);
        System.out.println(m.getAgentList());
        int scenario_num = 1;
        game_service game = Game_Server_Ex2.getServer(scenario_num);
        System.out.println(game.toString());
        System.out.println(game.getPokemons());
        game.addAgent(9);
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
        game.startGame();
        game.move();
        System.out.println( game.chooseNextEdge(0,8));
        TimeUnit.SECONDS.sleep(3);
        game.move();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(game.getPokemons());
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
        game.chooseNextEdge(0,9);
        Thread.sleep(1);
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
