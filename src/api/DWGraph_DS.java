package api;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph{

    /**
     * the list of edges object
     */
    private class EdgeHandler{
        //(srcKey,Hash)-->Hash is all the edges that the node connect to-> (dest,edge)
        private HashMap<Integer,HashMap<Integer,edge_data>> _srcList = new HashMap<Integer,HashMap<Integer,edge_data>>();
        //(destKey,Hash)-->Hash is all the edges that the node connect to her-> (src,edge)
        private HashMap<Integer,HashMap<Integer,edge_data>> _destList = new HashMap<Integer,HashMap<Integer,edge_data>>();

        /**
         * function for putting new edge
         *
         * @param edge
         * @return if only replaced the edge - False  if added new edge - True
         */
        private boolean pushEdge(edge_data edge){
            if (edge==null)return false; //will be removed
            boolean newEdge = false;
            if (!_srcList.containsKey(edge.getSrc())) {//if src node didnt init
                _srcList.put(edge.getSrc(),new HashMap<>());
            }
            if (_srcList.get(edge.getSrc()).put(edge.getDest(),edge)==null){ //if replace
                newEdge = true;
            }

            if (!_destList.containsKey(edge.getDest())) {
                _destList.put(edge.getDest(),new HashMap<>());
            }
            if (_destList.get(edge.getDest()).put(edge.getSrc(), edge) == null) { //if replace
                newEdge = true;
            }
            return newEdge;
        }

        /**
         * @param src
         * @return collection of edges that start from src
         */
        private Collection<edge_data> getECollection(int src){
            if(_srcList.get(src)==null) return null;
            Collection<edge_data> collect = _srcList.get(src).values();
            if (collect.size()==0)return null;
            return collect;
        }

        /**
         * @param nodeKey
         * @return the number of edges deleted
         */
        private int remove(int nodeKey){
            int eNum = 0;
            if (_srcList.containsKey(nodeKey)) {
                eNum+=_srcList.get(nodeKey).size();
                for (edge_data e :_srcList.get(nodeKey).values()
                ) {
                    _destList.get(e.getDest()).remove(nodeKey);
                    if (_destList.get(e.getDest()).values().size()==0)_destList.remove(e.getDest()); //cleanup - if need speed remove
                }
                _srcList.remove(nodeKey);
            }
            if (_destList.containsKey(nodeKey)){
                eNum+=_destList.get(nodeKey).size();
                for (edge_data e:_destList.get(nodeKey).values()
                ) {
                    _srcList.get(e.getSrc()).remove(nodeKey);
                    if (_srcList.get(e.getSrc()).values().size()==0)_srcList.remove(e.getSrc()); //cleanup - if need speed remove

                }
                _destList.remove(nodeKey);
            }
            return eNum;
        }

        /**
         * @param src
         * @param dest
         * @return the edge between src and dest, if nun return null
         */
        private edge_data getEdge(int src,int dest){
            if(src==dest) return null;
            if (!_srcList.containsKey(src)) return null;
            return _srcList.get(src).get(dest);
        }
        private edge_data removeEdge(int src,int dest){
            if (!_srcList.containsKey(src)) return null;
            _srcList.get(src).remove(dest);
            if (!_destList.containsKey(dest))return null;
            return _destList.get(dest).remove(src);
        }

    }

    @SerializedName("Nodes")
    private HashMap<Integer,node_data> _nodesList = new HashMap<Integer, node_data>();
    @SerializedName("Edges")
    private EdgeHandler _edgeList= new EdgeHandler();
    private int _nodes = 0;
    private int _edges = 0;
    private int MC = 0;

    /**
     * copy constructor
     * @param graph_ds
     */
    public DWGraph_DS(directed_weighted_graph graph_ds){
        if (graph_ds.getV()==null) {
            MC++;
            return;
        }
        for (node_data n:graph_ds.getV()
        ) {
            node_data copy = new NodeData(n);
            addNode(n);
        }
        for (node_data n:graph_ds.getV()
        ) {
            if (graph_ds.getE(n.getKey())==null) continue;
            for (edge_data e:graph_ds.getE(n.getKey())
            ) {
                _edgeList.pushEdge(new EdgeData(e)); // via connect
            }
        }
        _edges = graph_ds.edgeSize();
    }

    /**
     * constructor
     */
    public DWGraph_DS(){
        MC++;
    }


    /**
     * returns the node_data by the node_id
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key){
        return _nodesList.get(key);
    }
    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method runs at O(1) time.
     *
     * @param src
     * @param dest
     * @return edge between src and dest
     */
    @Override
    public edge_data getEdge(int src, int dest){
        return _edgeList.getEdge(src,dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method runs at O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n){
        if (_nodesList.put(n.getKey(),n)==null){
            _nodes++;
            MC++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w){
        if(_nodesList.containsKey(src)&_nodesList.containsKey(dest)&w>=0){ //if not out of bound
            if (_edgeList.pushEdge(new EdgeData(src,dest,w)))_edges++;
            MC++;
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV(){
        return _nodesList.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id){
        return _edgeList.getECollection(node_id);
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key){
        if (!_nodesList.containsKey(key))return null;
        _edges-=_edgeList.remove(key);
        _nodes--;
        MC++;
        return _nodesList.remove(key);
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest){
        edge_data e = _edgeList.removeEdge(src, dest);
        if (e!=null){
            MC++;
            _edges--;
        }
        return e;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize(){
        return _nodes;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize(){
        return _edges;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC(){
        return MC;
    }


    /**
     * @param o
     * @return if equals
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof DWGraph_DS)) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        if (_nodes != that.nodeSize() || _edges != that.edgeSize()){
            return false;
        }
        for (node_data n:that.getV()
             ) {
            if (!n.equals(getNode(n.getKey()))){
                return false;
            }
            if (that.getE(n.getKey())!=null) {
                for (edge_data e : that.getE(n.getKey())
                ) {
                    if (!e.equals(getEdge(e.getSrc(), e.getDest()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
