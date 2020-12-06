package object;

import api.edge_data;
import api.geo_location;

public interface PokemonInterface{

    public double getValue();
    public int getType();
    public geo_location getPos();
    public edge_data getEdge();//getting the edge which this Pokemon is on it (for easy calculation)
    public void setPosition(geo_location gl);
    public void setEdge(edge_data ed);

    /**
     * this function allow you to init status of a pokemon by sending a json string represent a pokemon
     * @param json - example: {"Pokemon":{"value":5.0,"type":-1,"pos":"35.20273974670703,32.10439601193746,0.0"}}
     */
    public void InitFromJson(String json);


/*
{"Pokemons":[{"Pokemon":{"value":5.0,"type":-1,"pos":"35.20273974670703,32.10439601193746,0.0"}},{"Pokemon":{"value":8.0,"type":-1,"pos":"35.189541903742466,32.10714473742062,0.0"}},{"Pokemon":{"value":13.0,"type":1,"pos":"35.198546018801096,32.10442041371198,0.0"}},{"Pokemon":{"value":5.0,"type":-1,"pos":"35.20418622066997,32.10618391544376,0.0"}},{"Pokemon":{"value":9.0,"type":-1,"pos":"35.207511563168026,32.10516145234799,0.0"}},{"Pokemon":{"value":12.0,"type":-1,"pos":"35.19183431463849,32.106897389061444,0.0"}}]}
 */
}
