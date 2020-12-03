package api;

import Server.Game_Server_Ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GraphGetter{
    dw_graph_algorithms algo = new DWGraph_Algo();
    int gameNumber;
    /**
     * constructor
     */
    GraphGetter(int gameNumber){
        this.gameNumber=gameNumber;
        try {
            game_service serverString = Game_Server_Ex2.getServer(gameNumber);
            String s = serverString.getGraph();
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        algo.load("GServer.json");
    }

    /**
     * update the graph
     * @return if successfully updated
     */
    boolean update(){
        game_service serverString = Game_Server_Ex2.getServer(gameNumber);
        try {
            String s = serverString.getGraph();
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return algo.load("GServer.json");
    }

    /**
     * @return the graph
     */
    directed_weighted_graph getGraph(){
        return algo.getGraph();
    }

    /**
     * sets the game number
     * --warning--
     * can be bad :)
     * @param gameNumber
     */
    void setGameNumber(int gameNumber){
        this.gameNumber=gameNumber;
    }
}
