public class Player {
    Point point;
    String name;
    int numOfWalls=10;

    public Player(Point point,String name) {
        this.point = point;
        this.name=name;
    }

    public Player copy(){
        return new Player(new Point(point.y, point.x), name);
    }

    public Point getPoint() {
        return point;
    }
}
