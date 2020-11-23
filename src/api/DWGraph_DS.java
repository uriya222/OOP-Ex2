package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    HashMap<Integer,node_data> _nodesList;
    HashMap<Integer, NeighborList> _edgeList;
    int _nodes = 0;
    int _edges = 0;
    int MC = 0;
    /**
     * the list of edges object
     */
    private class NeighborList{
        //first is key of src second is the edge
        private HashMap<Integer, edge_data> _edgeNeighbors = new HashMap<>();
        /**
         * @param dest of the node
         * @return the edge
         */
        private edge_data getEdgeNeighbors(int dest){
            return _edgeNeighbors.get(dest);
        }

        /**
         * insert a new edge to the list
         * @param edge
         * @return true if the new edge is unique
         */
        private boolean putEdgeNeighbor(edge_data edge){
            if(_edgeNeighbors.put(edge.getDest(),edge)==null)return true;
            return false;
        }
        private Collection<edge_data> getECollection(){
            return _edgeNeighbors.values();
        }
        private edge_data remove(int dest){
            return _edgeNeighbors.remove(dest);
        }
    }
    public DWGraph_DS(){
        _nodesList = new HashMap<Integer, node_data>();
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
        return _edgeList.get(src).getEdgeNeighbors(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
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
        if(_nodesList.containsKey(src)&_nodesList.containsKey(dest)&w>=0){ //out of bound
            if (!_edgeList.containsKey(src)){ //if not init
                _edgeList.put(src,new NeighborList());
            }
            if(_edgeList.get(src).putEdgeNeighbor(new EdgeData(src,dest,w)))_edges++;
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
        return _edgeList.get(node_id).getECollection();
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

        ///needs work
        _nodes--;
        MC++;
        return null;
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
        edge_data e = _edgeList.get(src).remove(dest);
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
}
