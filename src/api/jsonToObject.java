package api;

import com.google.gson.*;
import object.Agent;
import object.AgentsInterface;
import object.Pokemon;
import object.PokemonInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class jsonToObject{
   // {"Agents":[{"Agent":{"id":0,"value":0.0,"src":8,"dest":9,"speed":1.0,"pos":"35.19928484854374,32.10227955082914,0.0"}}]}
// {"Pokemons":[{"Pokemon":{"value":5.0,"type":-1,"pos":"35.197656770719604,32.10191878639921,0.0"}},{"Pokemon":{"value":8.0,"type":-1,"pos":"35.199963710098416,32.105723673136964,0.0"}}]}
   public jsonToObject(){}

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

    public HashMap<Integer, AgentsInterface> jsonToAgentHash(String j){
        HashMap<Integer,AgentsInterface> agents=new HashMap<>();
        GsonBuilder b=new GsonBuilder();
        Gson gson=b.create();
        JsonElement r=gson.fromJson(j,JsonElement.class);
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

    //public gameInfo jsonToGameInfo(JsonObject j);

    // marge all the graph stuff -- json


}

