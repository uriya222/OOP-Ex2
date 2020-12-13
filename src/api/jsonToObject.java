package api;

import com.google.gson.*;
import object.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class jsonToObject{
   public jsonToObject(){}

    /**
     * this method getting json String represent a weighted (directed) graph of the game,
     * save it and lode it to DWGraph_Algo class using existing method.
     * @param j json String
     * @return new DWGraph_Algo class
     */
   public dw_graph_algorithms jsonToGraph(String j){
       dw_graph_algorithms ans=new DWGraph_Algo();
       try { //output to a file for the algo
           PrintWriter t = new PrintWriter(new File("GServer.json"));
           t.write(j);
           t.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       ans.load("GServer.json");
       return ans;
   }

    /**
     * this method getting json String represent a list of agents, turn it to HashMap of agents.
     * @param j json String
     * @return new HashMap of agents.
     */
    public HashMap<Integer, AgentsInterface> jsonToAgentHash(String j){
        HashMap<Integer,AgentsInterface> agents=new HashMap<>();
        GsonBuilder b=new GsonBuilder();
        Gson gson=b.create();
        JsonObject r=gson.fromJson(j,JsonObject.class);
        JsonArray arr=r.getAsJsonObject().get("Agents").getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject r2=arr.get(i).getAsJsonObject().get("Agent").getAsJsonObject();
                int id=r2.get("id").getAsInt();
                String[] pos1 = r2.get("pos").getAsString().split(",");
                double x = Double.parseDouble(pos1[0]);
                double y = Double.parseDouble(pos1[1]);
                double z = Double.parseDouble(pos1[2]);
                geo_location pos = new GeoLocation(x, y, z);
                double v=r2.get("value").getAsDouble();
                int src=r2.get("src").getAsInt();
                int dest=r2.get("dest").getAsInt();
               double speed=r2.get("speed").getAsDouble();
               AgentsInterface a=new Agent(id,v,src,dest,pos,speed);
               agents.put(id,a);
        }
        return agents;
    }

    /**
     * this method getting json String represent a list of pokemons, turn it to ArrayList of pokemons.
     * @param j json String
     * @return new ArrayList of pokemons.
     */
    public List<PokemonInterface> jsonToPokemonList(String j){
        List<PokemonInterface> ans = new  ArrayList<PokemonInterface>();
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        JsonElement Po = gson.fromJson(j,JsonElement.class);
        JsonArray ags = Po.getAsJsonObject().get("Pokemons").getAsJsonArray();
        for(int i=0;i<ags.size();i++) {
            JsonObject r2=ags.get(i).getAsJsonObject().get("Pokemon").getAsJsonObject();
            String[] pos2=r2.get("pos").getAsString().split(",");
            double x=Double.parseDouble(pos2[0]);
            double y=Double.parseDouble(pos2[1]);
            double z=Double.parseDouble(pos2[2]);
            geo_location pos=new GeoLocation(x,y,z);
            double v=r2.get("value").getAsDouble();
            int t=r2.get("type").getAsInt();
            PokemonInterface p=new Pokemon(v,t,pos);
            ans.add(p);
        }
        return ans;
    }

    /**
     * this method getting game_service, take the json represent the game information and turn it to GameInfo class.
     * @param game the game_service
     * @return new GameInfo.
     */
    public gameInfoInterface jsonToGameInfo(game_service game){
       String j = game.toString();
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        JsonObject GameServer = gson.fromJson(j,JsonElement.class).getAsJsonObject().get("GameServer").getAsJsonObject();
       return new GameInfo(GameServer.get("pokemons").getAsInt(),GameServer.get("is_logged_in").getAsBoolean(),GameServer.get("moves").getAsInt(),
               GameServer.get("grade").getAsInt(),GameServer.get("game_level").getAsInt(),GameServer.get("max_user_level").getAsInt(),GameServer.get("id").getAsInt(),
               GameServer.get("graph").getAsString(), GameServer.get("agents").getAsInt());
    }
}

