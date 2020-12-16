package api;


import GUI.GUI;
import Server.Game_Server_Ex2;
import algorithm.algoManager;

/**
 * from this class we starting the game.
 */

public class Ex2{
    public static void main(String[] args) {

        //letting the GUI do all the work
        new GUI();

       /* // for playing with the commands for starting the game
        game_service game = Game_Server_Ex2.getServer(11);
        MainManager main = new MainManager(game);
        GUI graphic = new GUI(main);
        Thread algoManager = new algoManager(main);
        algoManager.start();
        */
    }
}
