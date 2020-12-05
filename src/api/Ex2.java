package api;

import Server.Game_Server_Ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
    public static void main(String[] args){
        game_service g = Game_Server_Ex2.getServer(11);
        System.out.println(g.getGraph());
        String s = g.getGraph();
        try {
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dw_graph_algorithms al = new DWGraph_Algo();
        al.load("GServer.json");
        directed_weighted_graph graph = al.getGraph();
        g.addAgent(1);
        System.out.println(g.getAgents());
        g.chooseNextEdge(1,2);
        g.chooseNextEdge(2,5);
        g.move();
        g.getAgents();
        g.getPokemons();
    }
}
