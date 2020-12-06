package api;

import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import object.Agent;
import object.AgentsInterface;
import object.Pokemon;
import object.PokemonInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainManager{
    game_service serverString;
    private String info;
    dw_graph_algorithms algo;
    private List<AgentsInterface> agents;
    private ArrayList<PokemonInterface> pokemons;
    public static final double EPS1 = 0.0000001;
    
    public MainManager(int scenario){
        serverString = Game_Server_Ex2.getServer(scenario);
        info=serverString.toString();
        algo = new DWGraph_Algo();
        try {
            String s=serverString.getGraph();
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        algo.load("GServer.json");
        this.pokemons=json2Pokemons(serverString.getPokemons());
        initAgent();
    }

    /**
     * init all agent in graph in particular order.
     */
    private void initAgent() {
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        JsonElement infoS = gson.fromJson(getInfo(),JsonElement.class);
        int numOfAgent = infoS.getAsJsonObject().get("GameServer").getAsJsonObject().get("agents").getAsInt();
        this.agents= new ArrayList<>();
        for (PokemonInterface p:this.pokemons) {
            AgentsInterface a=new Agent(this.algo.getGraph(),p.getEdge().getSrc());
            this.agents.add(a);
            if (this.agents.size()==numOfAgent) return;
        }
        for (int i = this.agents.size(); i <numOfAgent ; i++) {
            AgentsInterface a=new Agent(this.algo.getGraph(),0);//maybe put a random node from node_list
            this.agents.add(a);
        }
    }

    public List<PokemonInterface> getPokemons() {
        return pokemons;
    }

    public String getInfo() {return info;}
    public directed_weighted_graph getGraph(){return this.algo.getGraph();}

    /**
     * convert location of given pokemon to particular edge in graph and save the result
     * in Pokemon class variable called "edge"
     * this is used only in the be
     * @param p the pokemon
     */
    private void convertGeoToEdge(PokemonInterface p){
        for (node_data x:getGraph().getV()){
            for (edge_data ed:getGraph().getE(x.getKey())){
                boolean f = isOnEdge(ed,p.getType(),p.getPos());
                if(f) {p.setEdge(ed);}
            }
        }
    }
    private boolean isOnEdge(edge_data e, int type,geo_location gl) {
        int src = getGraph().getNode(e.getSrc()).getKey();
        int dest = getGraph().getNode(e.getDest()).getKey();
        if(type<0 && dest>src) {return false;}
        if(type>0 && src>dest) {return false;}
        geo_location src2 = getGraph().getNode(src).getLocation();
        geo_location dest2 = getGraph().getNode(dest).getLocation();
        double dist = src2.distance(dest2);
        double d1 = src2.distance(gl) + gl.distance(dest2);
        if(dist>d1-EPS1&&dist<d1+EPS1) {return true;}
        return false;
    }

    /**
     * convert String to List of Pokemons
     * this method need operation when a given pokemon has been eaten.
     * @param fs
     * @return
     */
    public ArrayList<PokemonInterface> json2Pokemons(String fs) {
        ArrayList<PokemonInterface> ans = new  ArrayList<PokemonInterface>();
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        JsonElement Po = gson.fromJson(fs,JsonElement.class);
        JsonArray ags = Po.getAsJsonObject().get("Pokemons").getAsJsonArray();
        for(int i=0;i<ags.size();i++) {
            String send = ags.get(i).toString();
            PokemonInterface p=new Pokemon(send);
            convertGeoToEdge(p);
            ans.add(p);
        }
        return ans;
    }
   // public void UpdatePokemons()
    public static List<Agent> getAgents(String aa, directed_weighted_graph gg) {
        ArrayList<Agent> ans = new ArrayList<Agent>();
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        JsonElement Ag= gson.fromJson(aa,JsonElement.class);
        JsonArray ags=Ag.getAsJsonObject().get("Agents").getAsJsonArray();
        for(int i=0;i<ags.size();i++) {
                Agent c = new Agent(gg,0);
                c.update(ags.get(i).getAsString());
                ans.add(c);
        }
        return ans;
    }

}

