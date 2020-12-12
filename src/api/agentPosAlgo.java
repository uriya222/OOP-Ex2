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

    /**
     * constructor that takes MainManager pointer
     * @param main
     */
    public agentPosAlgo(MainManager main){
        this.main = main;
        this.graph = main.getGraph();
        this.last_update = System.currentTimeMillis();
        this.agents_client = main.getAgentList();
    }

    /**
     * this is function that return the approximated pos of the agent on the graph
     *
     * @return  hash with all the agents
     */
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
                    double timeDiffSinceUpdate = last_update-main.getLast_update();
                    if (a.getTiming()!=-1){ //TODO: check for bugs
                        timeDiffSinceUpdate = a.getTiming()-main.getLast_update();
                        a.setDest(main.getAgentList().get(a.getId()).getDest());
                    }
                    long [] t_f_e=timeForEdge(a);
                    String d1=t_f_e[0]+"."+t_f_e[1];
                    double edgeTime=Double.parseDouble(d1);
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

    /**
     * @param a
     * @return how much in percent the agent crossed the edge
     */
    public double walkedPercent(AgentsInterface a){
        if (a.getDest() != -1){
            return a.getPos().x()-graph.getNode(a.getSrc()).getLocation().x();
        }
        return -1;
    }

    /**
     * @param a
     * @param p
     * @return the time that the agent need to sleep from src to pokemon
     */
    public long[] timeForPokemon(AgentsInterface a, PokemonInterface p){
        double DtoP = p.getPos().x()-graph.getNode(a.getSrc()).getLocation().x();
        double edgeXSize = graph.getNode(a.getDest()).getLocation().x()-graph.getNode(a.getSrc()).getLocation().x();
        long [] t_f_e=timeForEdge(a);
        String d1=t_f_e[0]+"."+t_f_e[1];
        double d2=Double.parseDouble(d1);
        double tmp=(d2*DtoP/edgeXSize);
        String t=tmp+"";
        String [] x=t.split("\\.");
        long [] ans =new long[2];
        long f=Long.parseLong(x[0]);
        long s=Long.parseLong(x[1]);
        ans[0]=f;
        ans[1]=s;
        return ans;
    }

    /**
     * @param a
     * @return the time that will take the agent to cross the
     *         edge that he stand on, -1 if there is no dest
     */
    public long[] timeForEdge(AgentsInterface a){
        long [] ans =new long[2];
        if (a.getDest() != -1) {
            double tmp= ((graph.getEdge(a.getSrc(), a.getDest()).getWeight() * 1000) / a.getSpeed());
            String t=tmp+"";
            String [] x=t.split("\\.");
            long f=Long.parseLong(x[0]);
            long s=Long.parseLong(x[1]);
            ans[0]=f;
            ans[1]=s;
        }
        return ans;
    }
}
