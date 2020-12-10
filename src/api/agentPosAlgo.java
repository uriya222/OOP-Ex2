package api;

import object.Agent;
import object.AgentsInterface;
import object.PokemonInterface;

import java.util.HashMap;

public class agentPosAlgo{
    MainManager main;
    directed_weighted_graph graph;
    long last_update;
    private HashMap<Integer,AgentsInterface> agents_client;

    public agentPosAlgo(MainManager main){
        this.main = main;
        this.graph = main.getGraph();
        this.last_update = System.currentTimeMillis();
        this.agents_client = main.getAgentList();
    }

    public synchronized HashMap<Integer, AgentsInterface> getAgents_client(){
        if(last_update-main.getLast_update()<0)
        {
            agents_client = new HashMap<>();
            for (AgentsInterface a: main.getAgentList().values()
            ) {
                agents_client.put(a.getId(),new Agent(a));
            }
        }
        if (agents_client!=null)
            for(AgentsInterface a:agents_client.values()
            )
            {
                if (a.getDest() != -1) {
                    double edgeTime = timeForEdge(a);
                    double timeDiffSinceUpdate = last_update-main.getLast_update();
                    double dx = graph.getNode(a.getSrc()).getLocation().x()-graph.getNode(a.getDest()).getLocation().x();
                    double dy = graph.getNode(a.getSrc()).getLocation().y()-graph.getNode(a.getDest()).getLocation().y();
                    double timePercent = -(timeDiffSinceUpdate/edgeTime);
                    double x = main.getAgentList().get(a.getId()).getPos().x() +(dx*timePercent);
                    double y = main.getAgentList().get(a.getId()).getPos().y() +(dy*timePercent);
                    if (Math.abs(a.getPos().distance(graph.getNode(a.getDest()).getLocation()))>0.0001){
                        geo_location newPos = new GeoLocation(x, y, 0);
                        agents_client.get(a.getId()).setPos(newPos);
                    }
                }
            }

        last_update = System.currentTimeMillis();
        return agents_client;
    }

    public double walkedPercent(AgentsInterface a){
        if (a.getDest() != -1){
            return a.getPos().x()-graph.getNode(a.getSrc()).getLocation().x();
        }
        return -1;
    }

    public long timeForPokemon(AgentsInterface a, PokemonInterface p){
        double DtoP = p.getPos().x()-graph.getNode(a.getSrc()).getLocation().x();
        double edgeXSize = graph.getNode(a.getDest()).getLocation().x()-graph.getNode(a.getSrc()).getLocation().x();
        return (long)((double)timeForEdge(a)*DtoP/edgeXSize);
    }

    public long timeForEdge(AgentsInterface a){
        if (a.getDest() != -1)
            return (long)(graph.getEdge(a.getSrc(),a.getDest()).getWeight()*1000);
        return -1;
    }
}
