import java.util.ArrayList;

public class sState {
    double value;
    ArrayList<Board> efficientPath =new ArrayList<>();

    public sState(double value, ArrayList<Board> efficientPath) {
        this.value = value;
        this.efficientPath = efficientPath;
    }
}
