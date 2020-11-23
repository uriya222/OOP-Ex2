package api;

public class GeoLocation implements geo_location{
    private double x;
    private double y;
    private double z;
    GeoLocation(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public double x(){
        return x;
    }

    @Override
    public double y(){
        return y;
    }

    @Override
    public double z(){
        return z;
    }

    @Override
    public double distance(geo_location g){
        ///////need to return distance between two points by pitagoras
        return 0;
    }
}
