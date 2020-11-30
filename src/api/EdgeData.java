package api;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class EdgeData implements edge_data{
    @SerializedName("src")
    private int _src;
    @SerializedName("dest")
    private int _dest;
    @SerializedName("w")
    private double _weight;
    private String _info;
    private int _tag;

    public EdgeData(int src, int dest, double weight){
        _src = src;
        _dest = dest;
        _weight = weight;
        _tag=0;
    }

    /**
     * copy constructor
     * @param e
     */
    public EdgeData(edge_data e){
        _src = e.getSrc();
        _dest = e.getDest();
        _tag = e.getTag();
        _weight = e.getWeight();
        _info = e.getInfo();
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof EdgeData)) return false;

        EdgeData edgeData = (EdgeData) o;

        if (_src != edgeData._src) return false;
        if (_dest != edgeData._dest) return false;
        if (Double.compare(edgeData._weight, _weight) != 0) return false;
        return Objects.equals(_info, edgeData._info);
    }

    @Override
    public int hashCode(){//TODO dont know, dont care.
        int result;
        long temp;
        result = _src;
        result = 31 * result + _dest;
        temp = Double.doubleToLongBits(_weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (_info != null ? _info.hashCode() : 0);
        return result;
    }
}
