package api;

public class Ex2{

    public static void main(String[] args) throws InterruptedException{
        MainManager m = new MainManager(0);
        //GUI gui=new GUI(m);
        m.addAgent(8);
        m.startGame();
        // while (m.isRunning()){
//        m.move();
        m.chooseNextEdge(0, 9);
        m.getGame().move();
        Thread.sleep(1400);
        double speed = m.getAgentList().get(0).getSpeed();
        double way = (m.getGraph().getEdge(8, 9).getWeight()) * 1000;
        double time = way / speed;
        System.out.println(time);
//        System.out.println(m.getGame().move());
//        System.out.println(m.getGame().getGraph());
        // "w":1.8526880332753517
        // System.out.println(m.getGame().getAgents());
        //System.out.println(m.getGraph().getNode(8).getLocation());
//time that took = 9--->8  1455


//from the graphic branch:

// import GUI.GUI;
// import GUI.myFrame;
// import Server.Game_Server_Ex2;
// import algorithm.algoManager;

// public class Ex2{

//     public static void main(String[] args) throws InterruptedException {
//       //  game_service game = Game_Server_Ex2.getServer(20);
//       // MainManager main = new MainManager(game);
//      //  Thread a = new algoManager(main);
//         GUI p = new GUI();
//        // a.start();
//     }

    }
}
