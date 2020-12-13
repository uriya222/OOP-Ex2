package object;

import api.edge_data;
import api.geo_location;

public interface PokemonInterface{

    public double getValue();
    public int getType();
    public geo_location getPos();
    public edge_data getEdge();
    public void setPosition(geo_location gl);
    public void setEdge(edge_data ed);

}
