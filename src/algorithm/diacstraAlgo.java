package algorithm;

import api.*;
import object.PokemonInterface;

import java.util.List;

/**
 * this algorithm is the brain behind the most scenario(except levels 11,20,23)
 * the stages for this algo per agent:
 * 1-choose pokemon that have been not chosen yet
 * 2-find the shortest path between the agent and the source of the pokemon
 * 3-go there(if he is already there skip on this stage)
 * 4-go to the destination of the pokemon wheres you eat it
 * 5-go back to stage 1
 */
public class diacstraAlgo extends Thread {
    MainManager main;
    int agent;
    dw_graph_algorithms d;

    public diacstraAlgo(MainManager main, int agent, dw_graph_algorithms d) {
        super();
        this.main = main;
        this.agent = agent;
        this.d = d;
    }

    public void run() {
        while (main.isRunning()) {
            PokemonInterface res = ChoosePokemon();
            while (res == null) res = ChoosePokemon();
            int k = 0;
            int srcP = res.getEdge().getSrc();
            int destP = res.getEdge().getDest();
            agentPosAlgo apa = new agentPosAlgo(main);
            if (srcP != main.getAgentList().get(this.agent).getSrc()) {
                List<node_data> l = d.shortestPath(main.getAgentList().get(this.agent).getSrc(), srcP);
                if (l.get(0).getKey() == main.getAgentList().get(agent).getSrc()) k = 1;
                for (int i = k; i < l.size(); i++) {
                    main.chooseNextEdge(agent, l.get(i).getKey());
                    long[] time = apa.timeForEdge(main.getAgentList().get(agent));
                    while (time[0] == -1) time = apa.timeForEdge(main.getAgentList().get(agent));
                    main.move();
                    try {
                        if (time[0] != -1) {
                            if (time[0] < 0) time[0] = 0;
                            if (time[1] < 0) time[1] = 0;
                            else {
                                while (time[1] > 10000) {
                                    time[1] /= 10;
                                }
                            }
                            Thread.sleep(time[0], (int) (time[1]));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    main.move();
                }
                main.chooseNextEdge(agent, destP);
                long[] all_time = new agentPosAlgo(main).timeForEdge(main.getAgentList().get(agent));
                while (all_time[0] == -1) all_time = apa.timeForEdge(main.getAgentList().get(agent));
                long[] till_pok = apa.timeForPokemon(main.getAgentList().get(agent), res);
                long[] to_end = new long[2];
                to_end[0] = all_time[0] - till_pok[0];
                to_end[1] = all_time[1] - till_pok[1];
                main.move();
                try {
                    if (till_pok[0] != -1) {
                        if (till_pok[0] < 0) till_pok[0] = 0;
                        if (till_pok[1] < 0) till_pok[1] = 0;
                        else {
                            while (till_pok[1] > 10000) {
                                till_pok[1] /= 10;
                            }
                        }
                        Thread.sleep(till_pok[0], (int) (till_pok[1]));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
                res.getEdge().setTag(0);
                try {
                    if (to_end[0] != -1) {
                        if (to_end[0] < 0) to_end[0] = 0;
                        if (to_end[1] < 0) to_end[1] = 0;
                        else {
                            while (to_end[1] > 10000) {
                                to_end[1] /= 10;
                            }
                        }
                        Thread.sleep(to_end[0], (int) (to_end[1]));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
            } else {
                main.chooseNextEdge(agent, destP);
                long[] all_time = new agentPosAlgo(main).timeForEdge(main.getAgentList().get(agent));
                while (all_time[0] == -1) all_time = apa.timeForEdge(main.getAgentList().get(agent));
                long[] till_pok = apa.timeForPokemon(main.getAgentList().get(agent), res);
                long[] to_end = new long[2];
                to_end[0] = all_time[0] - till_pok[0];
                to_end[1] = all_time[1] - till_pok[1];
                main.move();
                try {
                    if (till_pok[0] != -1) {
                        if (till_pok[0] < 0) till_pok[0] = 0;
                        if (till_pok[1] < 0) till_pok[1] = 0;
                        else {
                            while (till_pok[1] > 10000) {
                                till_pok[1] /= 10;
                            }
                        }
                        Thread.sleep(till_pok[0], (int) (till_pok[1]));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
                res.getEdge().setTag(0);
                try {
                    if (to_end[0] != -1) {
                        if (to_end[0] < 0) to_end[0] = 0;
                        if (to_end[1] < 0) to_end[1] = 0;
                        else {
                            while (to_end[1] > 10000) {
                                to_end[1] /= 10;
                            }
                        }
                        Thread.sleep(to_end[0], (int) (to_end[1]));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.move();
            }
        }
    }

    private synchronized PokemonInterface ChoosePokemon () {
        double min = Double.MAX_VALUE;
        PokemonInterface tmpP = null;
        for (PokemonInterface p : main.getPokemonList()) {
            if (p.getEdge() != null && p.getEdge().getTag() == 0) {
                double dist = d.shortestPathDist(main.getAgentList().get(this.agent).getSrc(), p.getEdge().getSrc());
                if (dist < min) {
                    min = dist;
                    tmpP = p;
                }
            }
        }
        if (tmpP != null) tmpP.getEdge().setTag(1);
        return tmpP;
    }
}
