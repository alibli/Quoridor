public class Wall {
    Point point;
    boolean isVertical;
    String s="";


    public Wall(Point point, boolean isvertical) {
        this.point = point;
        this.isVertical = isvertical;
        if(isVertical)
            s="|";
        else
            s="__";
    }
}
