package api;

public class NodeData implements node_data{
    private int _key;
    private geo_location _geoLocation;
    private double _weight;
    private String _info;
    private int _tag;

    /**
     *  constructor
     * @param key
     */
    NodeData(int key){
        _key = key;
    }
    /**
     * Returns the key (id) associated with this node.
     *
     * @return the key of the node
     */
    @Override
    public int getKey(){
        return _key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation(){
///what if null????
        return _geoLocation;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p){
        _geoLocation = p;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return the weight of the node
     */
    @Override
    public double getWeight(){
        return _weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w){
        _weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return the info in the node
     */
    @Override
    public String getInfo(){
        return _info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s){
        _info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return the tag of the node
     */
    @Override
    public int getTag(){
        return _tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t){
        _tag = t;
    }
}
