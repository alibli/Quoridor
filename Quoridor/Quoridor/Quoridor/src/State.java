import java.util.LinkedList;
import java.util.List;

public class State {
    Board boardState;
    Point playerPoint;
    Point competitorPoint;
    int playerNumOfWalls;
    int competitorNumOfWalls;
//    List<State> children=new LinkedList<>();

    public State(Board boardState,Point playerPoint,Point competitorPoint,int playerNumOfWalls,int competitorNumOfWalls) {
        this.boardState = boardState;
        this.playerPoint=playerPoint;
        this.competitorPoint=competitorPoint;
        this.playerNumOfWalls=playerNumOfWalls;
        this.competitorNumOfWalls=competitorNumOfWalls;
    }



}




