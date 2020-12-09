package algorithm;

import api.MainManager;
import api.dw_graph_algorithms;
import api.node_data;
import object.PokemonInterface;

import java.util.List;


public class improveDijAlgo extends Thread {
    MainManager main;
    int agent;
    dw_graph_algorithms d;
    public improveDijAlgo(MainManager main,int agent,dw_graph_algorithms d){
        super();
        this.main = main;
        this.agent = agent;
        this.d=d;
    }
    @Override
    public void run() {
        super.run();
        while (main.isRunning()) {
            System.out.println("2");
            PokemonInterface res=ChoosePokemon();
            while (res==null){
                res=ChoosePokemon();
            }
            int srcP = res.getEdge().getSrc();
            int destP = res.getEdge().getDest();
            System.out.println(destP);
            if (srcP != main.getAgentList().get(this.agent).getSrc()) {
                System.out.println(main.getGame().getPokemons());
                List<node_data> l = d.shortestPath(main.getAgentList().get(this.agent).getSrc(), srcP);
                for (node_data x : l) {
                    main.CA_moveIfNoTPokemon(agent, x.getKey());
                }
                main.CA_moveIfPokemon(agent, destP,res);
            }
            else {
                main.CA_moveIfPokemon(agent, destP,res);
                System.out.println("hi");
            }
        }
    }

    private synchronized PokemonInterface ChoosePokemon() {
        int srcP = 0;
        int destP = 0;
        double min = Double.MAX_VALUE;
        PokemonInterface tmpP=null;
        for (PokemonInterface p : main.getPokemonList()) {
            if(p.getEdge()!=null&&p.getTag()==0)
            {
                double dist = d.shortestPathDist(main.getAgentList().get(this.agent).getSrc(), p.getEdge().getSrc());
                if (dist < min) {
                    min = dist;
                    srcP = p.getEdge().getSrc();
                    destP = p.getEdge().getDest();
                    tmpP=p;
                }
            }
        }
        if(tmpP!=null) tmpP.setTag(1);
        return tmpP;
    }
}
