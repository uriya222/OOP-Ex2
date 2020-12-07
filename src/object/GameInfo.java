package object;

public class GameInfo implements gameInfoInterface{
    private boolean isLoggedIn;
    private int getPokemon;
    private int moves;
    private int grade;
    private int gameLevel;
    private int maxUserLevel;
    private int id;
    private String graph;
    private int agents;

    public GameInfo(int getPokemon, boolean isLoggedIn, int moves, int grade,
                    int gameLevel, int maxUserLevel, int id, String graph, int agents){
        this.isLoggedIn = isLoggedIn;
        this.getPokemon = getPokemon;
        this.moves = moves;
        this.grade = grade;
        this.gameLevel = gameLevel;
        this.maxUserLevel = maxUserLevel;
        this.id = id;
        this.graph = graph;
        this.agents = agents;
    }

    @Override
    public int getPokemon(){
        return getPokemon;
    }

    @Override
    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    @Override
    public int moves(){
        return moves;
    }

    @Override
    public int grade(){
        return grade;
    }

    @Override
    public int gameLevel(){
        return gameLevel;
    }

    @Override
    public int maxUserLevel(){
        return maxUserLevel;
    }

    @Override
    public int id(){
        return id;
    }

    @Override
    public String graph(){
        return graph;
    }

    @Override
    public int agents(){
        return agents;
    }
}
