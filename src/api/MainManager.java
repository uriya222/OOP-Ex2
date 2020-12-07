package api;

import Server.Game_Server_Ex2;
import object.AgentsInterface;
import object.PokemonInterface;
import object.gameInfoInterface;

import java.util.HashMap;
import java.util.List;

public class MainManager{
    game_service game;
    private String info;
    dw_graph_algorithms algo;
    private HashMap<Integer,AgentsInterface> agents;
    private List<PokemonInterface> pokemons;
    public static final double EPS1 = 0.0000001;
    long last_update;
    long last_move;
    HashMap<Integer,Long> last_moveHash;
    private gameInfoInterface gameInfo;

    public MainManager(game_service game){
        this.game = game;
        info= game.toString();
        algo = (new jsonToObject()).jsonToGraph(game.getGraph());
        this.agents=new HashMap<>();
        this.pokemons=(new jsonToObject()).jsonToPokemonList(game.getPokemons());
        ConvertGeoToEdge();
        this.gameInfo = new jsonToObject().jsonToGameInfo(game);
        last_moveHash = new HashMap<Integer,Long>();
    }

    public MainManager(int scenario){
        game = Game_Server_Ex2.getServer(scenario);
        info= game.toString();
        algo = (new jsonToObject()).jsonToGraph(game.getGraph());
        this.agents=new HashMap<>();
        this.pokemons=(new jsonToObject()).jsonToPokemonList(game.getPokemons());
        ConvertGeoToEdge();
        this.gameInfo = new jsonToObject().jsonToGameInfo(game);
        last_moveHash = new HashMap<Integer,Long>();
    }

    public gameInfoInterface getGameInfo(){
        gameInfo = new jsonToObject().jsonToGameInfo(game);
        return gameInfo;
    }

    /**
     * adding agent to the list of agent, and to the server.
     * return true only if the server is say true
     */
    public boolean addAgent(int start_node) {
        boolean addAgent = game.addAgent(start_node);
        if (addAgent){
            String s=game.getAgents();
            this.agents=(new jsonToObject()).jsonToAgentHash(s);
        }
        return addAgent;
    }

    public HashMap<Integer,AgentsInterface> getAgentList() {
        return this.agents;
    }

    public List<PokemonInterface> getPokemonList() {
        return this.pokemons;
    }

    public String getServerGameInfo() {
        return info;
    }

    public directed_weighted_graph getGraph(){
        return this.algo.getGraph();
    }

    /**
     * convert location of given pokemon to particular edge in graph and save the result
     * in Pokemon class variable called "edge"
     * this is used only in the be
     * @param p the pokemon
     */
    private void ConvertGeoToEdge(PokemonInterface p){
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
     * convert all location in the list of pokemons to edge data
     * (this method need operation when a given pokemon has been eaten)
     */
    public void ConvertGeoToEdge() {
        for(int i=0;i<this.pokemons.size();i++) {
            PokemonInterface p=this.pokemons.get(i);
            ConvertGeoToEdge(p);
        }
    }

    public synchronized long chooseNextEdge(int id, int next_node){  //need algorithms to operate on this method
        long temp = game.chooseNextEdge(id,next_node);
        if (temp!=-1) {
            last_move = temp;
            last_moveHash.put(id,temp);
        }
        if (agents.get(id).getDest() == -1 && temp!=-1)
        agents.get(id).setDest(next_node);
        return temp;
    }

    public long timeToEnd(){
        return game.timeToEnd();
    }
    public long lastMove(int id){
        return last_moveHash.get(id);
    }

    public long lastMove(){
        return last_move;
    }

    public synchronized void move(){
        game.move();
        this.agents=(new jsonToObject()).jsonToAgentHash(game.getAgents());
        this.pokemons=(new jsonToObject()).jsonToPokemonList(game.getPokemons());
        ConvertGeoToEdge();
        last_update = System.currentTimeMillis();
    }
    public long getLast_move(){
        return this.last_move;
    }
    public long getLast_update(){
        return this.last_update;
    }
    public long startGame(){
        this.last_move=game.startGame();
        last_update = System.currentTimeMillis();
        gameInfo = new jsonToObject().jsonToGameInfo(game);
        return getLast_update();
    }

    public long StopGame(){
        return game.stopGame();
    }

    public boolean isRunning(){
        return game.isRunning();
    }

}

