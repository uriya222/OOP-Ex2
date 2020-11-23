package api;

public class EdgeData implements edge_data{
    private int _src;
    private int _dest;
    private double _weight;
    private String _info;
    private int _tag;

    EdgeData(int src, int dest, double weight){
        _src = src;
        _dest = dest;
        _weight = weight;
    }
    /**
     * The id of the source node of this edge.
     *
     * @return the source node id
     */
    @Override
    public int getSrc(){
        return _src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return  the destination node id
     */
    @Override
    public int getDest(){
        return _dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight(){
        return _weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return the info of the edge
     */
    @Override
    public String getInfo(){
        return _info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s - the info to be set
     */
    @Override
    public void setInfo(String s){
        _info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return the tag of the edge
     */
    @Override
    public int getTag(){
        return _tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t){
        _tag = t;
    }
}
