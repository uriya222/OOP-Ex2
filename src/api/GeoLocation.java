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
        return Math.sqrt(Math.pow(x-g.x(),2)+Math.pow(y-g.y(),2));
    }

    public String toString(){
        return  ""+this.x+","+this.y+","+this.z;
    }
}
