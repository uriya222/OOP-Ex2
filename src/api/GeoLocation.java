package api;

public class GeoLocation implements geo_location{
    private double x;
    private double y;
    private double z;

    public GeoLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GeoLocation(geo_location location){
        if (location==null)return;
        x = location.x();
        y = location.y();
        z = location.z();
    }

    /**
     * @return x
     */
    @Override
    public double x(){
        return x;
    }

    /**
     * @return y
     */
    @Override
    public double y(){
        return y;
    }

    /**
     * @return z
     */
    @Override
    public double z(){
        return z;
    }

    /**
     * @param g
     * @return the distance between two locations
     */
    @Override
    public double distance(geo_location g){
        return Math.sqrt(Math.pow(x-g.x(),2)+Math.pow(y-g.y(),2));
    }

    /**
     * @param o
     * @return if equals
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof GeoLocation){
            GeoLocation tmp= (GeoLocation) o;
            if (tmp.x()==x()&&tmp.y()==y()&&tmp.z()==z())  return true;
            return false;
        }
        throw new ClassCastException("you tried to compare different object");
    }

    /**
     * @return the object in string form
     */
    public String toString(){
        return  ""+this.x+","+this.y+","+this.z;
    }
}
