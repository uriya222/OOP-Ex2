package api;

public class EdgeFromPosition {
    private geo_location gl;
    private int type;
    private edge_data e;
    private directed_weighted_graph g;
    public EdgeFromPosition(geo_location tmp, int t){
      this.gl=tmp;
      this.g=new Ex2().getG();
      this.type=t;
      this.e=null;
      convert();
    }
    public edge_data getEdge(){
        return this.e;
    }
    public void convert(){
        int m,b;
        for (node_data x:this.g.getV()){
            for (edge_data ed:this.g.getE(x.getKey())){
                boolean f = isOnEdge(ed);
                if(f) {set_edge(ed);}
            }
        }
    }

    private boolean isOnEdge(edge_data e) {
        int src = this.g.getNode(e.getSrc()).getKey();
        int dest = this.g.getNode(e.getDest()).getKey();
        if(this.type<0 && dest>src) {return false;}
        if(this.type>0 && src>dest) {return false;}
        geo_location src2 = g.getNode(src).getLocation();
        geo_location dest2 = g.getNode(dest).getLocation();
        double dist = src2.distance(dest2);
        double d1 = src2.distance(this.gl) + this.gl.distance(dest2);
        if(dist>d1-(0.001*0.001)) {return true;}
        return false;
    }

    private void set_edge(edge_data e) {this.e=e;}
}
