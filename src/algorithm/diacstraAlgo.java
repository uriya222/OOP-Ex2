package algorithm;

import api.MainManager;
import api.agentPosAlgo;
import api.dw_graph_algorithms;
import api.node_data;
import object.PokemonInterface;

import java.util.List;


public class diacstraAlgo extends Thread {
    MainManager main;
    int agent;
    dw_graph_algorithms d;
    public diacstraAlgo(MainManager main,int agent,dw_graph_algorithms d){
        super();
        this.main = main;
        this.agent = agent;
        this.d=d;
    }

    public void run() {
        //       super.run();
        while (main.isRunning()) {
            PokemonInterface res = ChoosePokemon();
            while (res == null) res = ChoosePokemon();
            int k=0;
            int srcP = res.getEdge().getSrc();
            int destP = res.getEdge().getDest();
            agentPosAlgo apa=new agentPosAlgo(main);
            if(srcP != main.getAgentList().get(this.agent).getSrc()) {
                List<node_data> l = d.shortestPath(main.getAgentList().get(this.agent).getSrc(), srcP);
                for (node_data x : l) {
                    System.out.println(x.getKey());
                }
                if(l.get(0).getKey()==main.getAgentList().get(agent).getSrc()) k=1;
                for (int i = k; i < l.size(); i++) {
                    main.chooseNextEdge(agent, l.get(i).getKey());
                    System.out.println(main.getAgentList().get(agent));
                    long time = apa.timeForEdge(main.getAgentList().get(agent));
                    while (time==-1) time = apa.timeForEdge(main.getAgentList().get(agent));
                    main.move();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    main.move();
                }
                main.chooseNextEdge(agent, destP);
                System.out.println(main.getAgentList().get(agent));
                long all_time = new agentPosAlgo(main).timeForEdge(main.getAgentList().get(agent));
                while (all_time==-1) all_time = apa.timeForEdge(main.getAgentList().get(agent));
                long till_pok = apa.timeForPokemon(main.getAgentList().get(agent), res)-200;
                long to_end = all_time - till_pok;
                main.move();
                for (int i = 0; i <till_pok/10 ; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    main.move();
                }
                try {
                    Thread.sleep(to_end);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
            } else {
                main.chooseNextEdge(agent, destP);
                System.out.println(main.getAgentList().get(agent));
                long all_time=new agentPosAlgo(main).timeForEdge(main.getAgentList().get(agent));
                while (all_time==-1) all_time = new agentPosAlgo(main).timeForEdge(main.getAgentList().get(agent));
                long till_pok=new agentPosAlgo(main).timeForPokemon(main.getAgentList().get(agent),res);
                long to_end=all_time-till_pok;
                main.move();
                try {
                    Thread.sleep(till_pok);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
                try {
                    Thread.sleep(to_end);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
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
