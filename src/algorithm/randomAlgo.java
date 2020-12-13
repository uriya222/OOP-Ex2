package algorithm;

import api.MainManager;
import api.edge_data;
//TODO maybe remove this class
public class randomAlgo extends Thread{
    MainManager main;
    int agent;
    public randomAlgo(MainManager main,int agent){
        this.main = main;
        this.agent = agent;
    }

    @Override
    public void run(){
        super.run();
        while (true){
            int[] edges = new int[main.getGraph().getE(main.getAgentList().get(agent).getSrc()).size()];
            int i = 0;
            for (edge_data e:main.getGraph().getE(main.getAgentList().get(agent).getSrc())
                 ) {
                edges[i++] = e.getDest();
                //System.out.println(e.getDest());
            }
            /*for (int j = 0; j < edges.length; j++) {
                System.out.println(edges[j]);
            }*/
            //System.out.println());
            main.chooseNextEdge(agent,random(edges));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //main.move();
        }
    }
    private synchronized int random(int[] range){
        int num = (int)(Math.random()*(range.length));
        return range[num];
    }

}
