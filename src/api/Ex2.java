package api;

import GUI.GUI;
import object.PokemonInterface;

public class Ex2{

    public static void main(String[] args) throws InterruptedException {
        MainManager m=new MainManager(0);
        GUI gui=new GUI(m);
        m.addAgent(9);
        m.startGame();
          PokemonInterface p=m.getPokemonList().get(0);
        System.out.println(p);

//        System.out.println(m.getGame().move());
//        System.out.println(m.getGame().getGraph());
       // "w":1.8526880332753517
       // System.out.println(m.getGame().getAgents());
        //System.out.println(m.getGraph().getNode(8).getLocation());
//time that took = 9--->8  1455


    }

}
