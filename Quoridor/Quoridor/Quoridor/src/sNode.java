import java.util.ArrayList;

public class sNode {
    Point point;
    ArrayList<sNode> arrayList=new ArrayList();
    boolean isEnd=false;

    public sNode(Point point) {
        this.point = point;
    }
}
