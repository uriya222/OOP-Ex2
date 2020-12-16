# Directed Weighted Graph and game

## Contributors:
* ishay320
* uriya222

## about the project
this is a university project about directed weighted graph and a game.  
In the game there is a random graph and on the edges of the graph there is "pokemons",  
 if your agent to through the pokemon you get points.
There are to factors that compute the score, the first is how many points collected during the run, 
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

## why we choose
We chose to build the graph from hashmap because almost all the objects
 have a key field.
We chose dijkstras algorithm because it's fast.


## How to contribute:
 If you want you can issue an idea or a bug. 
