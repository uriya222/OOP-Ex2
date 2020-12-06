package api;


import api.object.AgentsInterface;
import api.object.GameServer;
import api.object.PokemonInterface;

import java.util.HashMap;

public interface ClientManager{ //probably extends game service
    //pointer->    do it in builder or in init
//    private game_service _game;
//    default GameServer(game_service game){
//        _game = game;
//    }

     public HashMap<Integer, AgentsInterface> getAgentsList();
     public HashMap<edge_data, PokemonInterface> getPokemonList();
     public DWGraph_DS getGraph();
     public GameServer getServerInfo();
     public boolean setGameNumber(int gameNumber);
     public void move();
     //public void chooseNextEdge();



}
