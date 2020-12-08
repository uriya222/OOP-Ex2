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
    public void run(){
        super.run();
        while (true) {
            double min = Double.MAX_VALUE;
            int srcP = 0;
            int destP = 0;
            synchronized (this) {
                for (PokemonInterface p : main.getPokemonList()) {
                    System.out.println(Thread.currentThread().getName());
                    double dist = d.shortestPathDist(main.getAgentList().get(this.agent).getSrc(), p.getEdge().getSrc());
                    if ((dist < min) && (p.getTag() == 0)) {
                        min = dist;
                        srcP = p.getEdge().getSrc();
                        destP = p.getEdge().getDest();
                        p.setTag(1);
                    }
                }

                if (srcP != main.getAgentList().get(this.agent).getSrc()) {
                    List<node_data> l = d.shortestPath(main.getAgentList().get(this.agent).getSrc(), srcP);
                    for (node_data x : l) {
                        main.chooseNextEdge(agent, x.getKey());
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        main.move();
                    }
                    main.chooseNextEdge(agent, destP);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    main.move();
                } else {
                    main.chooseNextEdge(agent, destP);
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    main.move();
                }
            }
        }
    }
}
