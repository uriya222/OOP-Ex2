# Directed Weighted Graph and game

## Contributors:
* ishay320
* uriya222

## about the project
this is a university project about directed weighted graph and a game.  
In the game there is a random graph and on the edges of the graph there is "pokemons",  
 if your agent go through the pokemon you get points.
There are two factors that compute the score, the first is how many points collected during the run, 
 the second is how many times you update the server to move your agent.  

## How to use
download and put in your favorite IDE :)  
in src/api/ in ex2 you can use the GUI or to code the game you want in the main.  
in src/algorithm/ in algoManager you can change the running algo with one of your own.  
in src/GUI in myPanel you can add your own screens.

## how its built
 (the project tree)  
![project tree](/gameAssets/project_tree.jpg)

## commands:
<details>
  <summary>DWGraph_ds</summary>
  
  ```java
DWGraph_DS(directed_weighted_graph graph_ds); //copy constructor
DWGraph_DS();//constuctor
node_data getNode(int key);//return the node_data by the node_id, null if none
edge_data getEdge(int src, int dest); //returns the data of the edge (src,dest), null if none
void addNode(node_data n); //adds a new node to the graph with the given node_data
void connect(int src, int dest, double w); //Connects an edge with weight w between node src to node dest
Collection<node_data> getV(); //returns a pointer (shallow copy) for the collection representing all the nodes in the graph
Collection<edge_data> getE(int node_id); //method returns a pointer (shallow copy) for the collection representing all the edges getting out of the given node (all the edges starting (source) at the given node)
node_data removeNode(int key); //Deletes the node (with the given ID) from the graph
edge_data removeEdge(int src, int dest); //Deletes the edge from the graph
int nodeSize(); // returns the number of vertices (nodes) in the graph
int edgeSize(); // returns the number of edges
int getMC(); // returns the Modify Count 
boolean equals(Object o); //return if equals

  ```
  </details>

  <details>
 
   <summary>DWGraph_Algo</summary>
   
   ```java
 DWGraph_Algo();//constuctor
 void init(directed_weighted_graph g); //init the graph on which this set of algorithms operates on
 directed_weighted_graph getGraph(); //return the underlying graph of which this class works
 directed_weighted_graph copy(); //compute a deep copy of this weighted graph
 boolean isConnected(); //returns true if and only if there is a valid path from each node to each other node
 double shortestPathDist(int src, int dest); //returns the length of the shortest path between src to dest
 List<node_data> shortestPath(int src, int dest); //returns the the shortest path between src to dest - as an ordered List of nodes
 boolean save(String file); //saves this weighted directed graph to the given file name in JSON format using GSON
 boolean load(String file); //This method load a graph to this graph algorithm from a file
 boolean equals(Object o); //return if equals
   ```
 </details>
 
  <details>
 
   <summary>MainManager</summary>
   
   ```java
MainManager();//empty constructor for integration
MainManager(int scenario); //constructor by level
MainManager(game_service game); //constructor by pointer to game
boolean startup(int level); //startup command for integration
gameInfoInterface getGameInfo(); //return the game info object
boolean addAgent(int start_node); //adding agent to the list of agent, and to the server
HashMap<Integer,AgentsInterface> getAgentList(); //return Hashmap of the agents
List<PokemonInterface> getPokemonList(); //return list with all the pokemons
String getServerGameInfo(); //return the server info
directed_weighted_graph getGraph(); //return the graph
void ConvertGeoToEdge(); //convert all location in the list of pokemons to edge data
long chooseNextEdge(int id, int next_node); //this method choose the next destination to to the agents
game_service getGame(); //return game_service object of the server
long timeToEnd(); //return time to end of the game
long lastMove(int id); //return the time of the choose next edge of the given agent
long getLast_move(); //return the time of the choose next edge of the server
void move(); //this method telling the server to make a move and update the client
long getLast_update(); //return the last update to the client by move command 
long startGame(); //start the game command to the server return the time started
long StopGame(); //stop the game
boolean isRunning(); //return if running
boolean login(long id); //send the server the id

   ```
 </details>

  <details>
 
   <summary>GUI</summary>
   
   ```java
GUI(); //constructor that start the GUI main screen without starting the game but GUI start it with menu
GUI(MainManager main); //constructor that start the GUI of the game(the graph and the agents) without the GUI main menu
GUI(MainManager main, int refreshRateInMillis ); // same but you can choose refresh rate
   ```
 </details>
 
## why we choose
We chose to build the graph from hashmap because almost all the objects
 have a key field.  
We chose dijkstras algorithm because it's fast.


## How to contribute:
 If you want you can issue an idea or a bug. 
