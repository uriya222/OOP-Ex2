package api;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph g;
    public DWGraph_Algo(){
       this.g=new DWGraph_DS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) { this.g=g; }
    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(getGraph());
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (getGraph().getV().isEmpty()) return true;
        for (node_data x : getGraph().getV()) {
            if (x != null) x.setTag(0);
        }
        for (node_data x : getGraph().getV()) {
            DFS(x);
            for (node_data y : getGraph().getV()) {
                if (y.getTag() == 0) return false;
            }
            for (node_data y : getGraph().getV()) {
                y.setTag(0);
            }
        }
        return true;
    }
    private void DFS(node_data n) {
        Queue<node_data> q=new LinkedList<node_data>();
        q.add(n);
        n.setTag(1);
        while(!(q.isEmpty())) {
            for(edge_data x: getGraph().getE(n.getkey())) {
                node_data tmp= getGraph().getNode(x.getDest());
                if(tmp.getTag()==0) {
                    q.add(tmp);
                    tmp.setTag(1);
                }
            }
            q.remove();
        }
    }


    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(getGraph()==null) return -1;
        if(getGraph().getNode(src)==null||g.getNode(dest)==null) return -1;  //check method getNode (return null)
        if(src==dest) return 0;
        int size=-1;
        for(node_data x: getGraph().getV()) {
            if(x!=null)
                if(x.getkey()>size) size=x.getkey();
        }
        size++;
        int [] prev=new int[size];
        double [] ShortestFromA=new double[size];
        dijkstras_algo( src, dest, size, prev,ShortestFromA);
        if(ShortestFromA[dest]==Double.MAX_VALUE) return -1;
        return ShortestFromA[src];
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(getGraph()==null) return null;
        if(getGraph().getNode(src)==null||getGraph().getNode(dest)==null) return null;
        LinkedList<node_data> path=new LinkedList<node_data>();
        Stack<node_data> st= new Stack<node_data>();
        if(src==dest) return path;
        int size=-1;
        for(node_data x: getGraph().getV()) {
            if(x!=null)
                if(x.getkey()>size) size=x.getkey();
        }
        size++;
        int [] prev=new int[size];
        double [] ShotrestFromA=new double[size];
        dijkstras_algo(src, dest, size, prev, ShotrestFromA);
        if(ShotrestFromA[dest]==Integer.MAX_VALUE) return path;
        int tmp=dest;
        while(tmp!=-1) {
            st.push(getGraph().getNode(tmp));
            tmp=prev[tmp];
        }
        while(!st.isEmpty()) {
            path.add(st.pop());
        }
        return path;
    }
    /**
     * The dijkstras algorithm:
     * return true iff there is a path between source node to destination node.
     * @param src the i_d of source node.
     * @param dest the i_d of destination node.
     * @param size the biggest key the graph contain.
     * @param prev is Array[size] which contain the keys that lead to destination in the shortest way.
     * @param S is Array[size] which contain the smallest path (calculating by weight) from every node to node src.
     * @return
     */
    private void dijkstras_algo(int src, int dest, int size, int[] prev, double[] S){
        double dist=0;
        int tmp2=0;
        boolean visited[] = new boolean[size];
        Queue<Integer> q=new LinkedList<Integer>();
        for (int i = 0; i < size; i++) {
            visited[i] = false;
            S[i]=Double.MAX_VALUE;
            prev[i] = -1;
        }
        visited[src]=true;
        S[src]=0;
        q.add(src);
        while(!q.isEmpty()) {
            int u=q.remove();
            for (edge_data x:g.getE(u)) {
                if (visited[x.getDest()] == false) {
                    dist=S[x.getDest()];
                    S[x.getDest()] = Math.min(S[x.getDest()], S[u]+x.getWeight());
                    if(S[x.getDest()]!=dist) prev[x.getDest()] = u;
                }
            }
            visited[u] = true;
            tmp2=SmallestW(S,visited);
            if(tmp2>-1) q.add(tmp2);
        }
    }
    /**
     * simple method part of the dijkstras algorithm :
     * finding the minimum in array S with some limits:
     * limit 1- at the same place in visited array it must be false
     * limit 2- with giving place in S the same index must be a key to a node in my graph(for NullPointerExeption)
     * @param S
     * @param visited
     * @return
     */
    private int SmallestW(double [] S, boolean[] visited) {
        double min=Double.MAX_VALUE;
        int indexMin=-1;
        for (int i = 0; i < S.length; i++) {
            if((S[i]<min)&&(!visited[i])&&(getGraph().getNode(i)!=null)) {
                min=S[i];
                indexMin=i;
            }
        }
        return indexMin;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }
}


