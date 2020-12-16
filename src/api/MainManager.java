package api;

import Server.Game_Server_Ex2;
import object.AgentsInterface;
import object.PokemonInterface;
import object.gameInfoInterface;

import java.util.HashMap;
import java.util.List;

public class MainManager{
    private game_service game;
    private String info;
    private dw_graph_algorithms algo;
    private HashMap<Integer,AgentsInterface> agents;
    private HashMap<Integer,AgentsInterface> agents_client;
    private List<PokemonInterface> pokemons;
    public static final double EPS1 = 0.0000001;
    private long last_update = 0;
    private long last_client_update = System.currentTimeMillis();;
    private long last_move = 0;
    private HashMap<Integer,Long> last_moveHash;
    private gameInfoInterface gameInfo;
    public boolean isSet = true;

    /**
     * constructor by pointer
     * @param game
     */
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

    /**
     * constructor by level
     *
     * @param scenario
     */
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

    /**
     * empty constructor for integration
     *          WARNING:
     * use only if you know the risks
     */
    public MainManager(){
        isSet = false;
    }

    /**
     * @param level
     * @return if true if started and false if already running
     */
    public boolean startup(int level){
        if (info!=null) return false;
        game = Game_Server_Ex2.getServer(level);
        info= game.toString();
        algo = (new jsonToObject()).jsonToGraph(game.getGraph());
        this.agents=new HashMap<>();
        this.pokemons=(new jsonToObject()).jsonToPokemonList(game.getPokemons());
        ConvertGeoToEdge();
        this.gameInfo = new jsonToObject().jsonToGameInfo(game);
        last_moveHash = new HashMap<Integer,Long>();
        isSet = true;
        return true;
    }

    /**
     * @return the game info object
     */
    public gameInfoInterface getGameInfo(){
        gameInfo = new jsonToObject().jsonToGameInfo(game);
        return gameInfo;
    }

    /**
     * adding agent to the list of agent, and to the server.
     * @return true only if the server is say true
     */
    public boolean addAgent(int start_node) {
        boolean addAgent = game.addAgent(start_node);
        if (addAgent){
            String s=game.getAgents();
            this.agents=(new jsonToObject()).jsonToAgentHash(s);
        }
        return addAgent;
    }

    /**
     * @return Hashmap of the agents
     */
    public HashMap<Integer,AgentsInterface> getAgentList() {
        return this.agents;
    }

    /**
     * @return list with all the pokemons
     */
    public List<PokemonInterface> getPokemonList() {
        return this.pokemons;
    }

    /**
     * @return the server info
     */
    public String getServerGameInfo() {
        return info;
    }

    /**
     * @return the graph
     */
    public directed_weighted_graph getGraph(){
        if (algo == null) return null;
        return this.algo.getGraph();
    }

    /**
     * convert location of given pokemon to particular edge in graph and save the result
     * in Pokemon class variable called "edge"
     * this method is used only in ConvertGeoToEdge()
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

    /**
     * this method choose the next destination to to the agents,
     * and saving the time that return from the server
     * @param id- id of the agent
     * @param next_node
     * @return
     */
    public synchronized long chooseNextEdge(int id, int next_node) {
        long temp = game.chooseNextEdge(id,next_node);
        if (temp!=-1) {
            last_move = temp;
            last_moveHash.put(id,temp);
            agents.get(id).setTiming(temp);
        }
        if (agents.get(id).getDest() == -1 && temp!=-1) {
            agents.get(id).setDest(next_node);
            agents.get(id).setTiming(temp);
        }
        return temp;
    }

    /**
     * @return game_service object of the server
     */
    public game_service getGame(){return game;}

    /**
     * @return time to end of the game
     */
    public long timeToEnd(){
        return game.timeToEnd();
    }

    /**
     * @param id
     * @return the time of the choose next edge of the given agent
     */
    public long lastMove(int id){
        return last_moveHash.get(id);
    }

    /**
     * this method telling the server to make a move and updating the list of
     * agents and pokemon as needed.
     */
    public synchronized void move(){
        game.move();
        this.pokemons=(new jsonToObject()).jsonToPokemonList(game.getPokemons());
        this.agents=(new jsonToObject()).jsonToAgentHash(game.getAgents());
        ConvertGeoToEdge();
        last_update = System.currentTimeMillis();
    }

    /**
     * @return time of the last choose next edge of the server
     */
    public long getLast_move(){
        return this.last_move;
    }

    /**
     * @return the last update to the client by move command
     */
    public long getLast_update(){
        return this.last_update;
    }

    /**
     * start the game command to the server
     * @return the time started
     */
    public long startGame(){
        this.last_move=game.startGame();
        last_update = System.currentTimeMillis();
        gameInfo = new jsonToObject().jsonToGameInfo(game);
        return getLast_update();
    }

    /**
     * stop the game
     * @return  time of stop
     */
    public long StopGame(){
        return game.stopGame();
    }

    /**
     * @return if running
     */
    public boolean isRunning(){
        return game.isRunning();
    }

    /**
     * send the server the id
     * @param id  - the id of the runner
     * @return
     */
    public boolean login(long id){
        return game.login(id);
    }

}

