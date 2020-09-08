import java.util.*;

public class Board {

    Map<String,Integer> map=new HashMap<>();
    Queue<sNode> queue = new LinkedList<>();

    Point pA = new Point(16, 8);
    public Player player_A = new Player(pA, "A");

    Point pB = new Point(0, 8);
    public Player player_B = new Player(pB, "B");

    Point pC = new Point(8, 0);
    public Player player_C = new Player(pC, "C");

    Point pD = new Point(8, 16);
    public Player player_D = new Player(pD, "D");

    String[][] board = new String[17][17];


    public Board initialBoard(boolean twoSome) {

        if (!twoSome) {
            player_A.numOfWalls = 5;
            player_B.numOfWalls = 5;
            player_C.numOfWalls = 5;
            player_D.numOfWalls = 5;
        }

        Board b = new Board();

        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (i % 2 == 0 && j % 2 == 0)
                    b.board[i][j] = "*";
                else
                    b.board[i][j] = " ";


                if (i == 0 && j == 8)
                    b.board[i][j] = player_B.name;
                if (i == 16 && j == 8)
                    b.board[i][j] = player_A.name;

                if (!twoSome) {
                    if (i == 8 && j == 0)
                        b.board[i][j] = "C";
                    if (i == 8 && j == 16)
                        b.board[i][j] = "D";
                }
                System.out.print(b.board[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();

        return b;
    }


    public boolean isGameOver() {
        Quoridor q = Quoridor.getQ();
        if (q.twoOrFour) {
            if (player_B.point.y == 16) {
                return true;
            }
        }
        if (!q.twoOrFour) {
            if (player_B.point.y == 16 || player_C.point.x == 16 || player_D.point.x == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayerWin() {
        if (player_A.point.y == 0) {
            return true;
        }
        return false;
    }


    public boolean[] getAvailableCells(Point point, String[][] new_board) {
        boolean[] array = {false, false, false, false};//0=up  1=down  2=left  3=right

        if (point.y > 0 && point.x >= 0 && point.x <= 16 && point.y <= 16 && !new_board[point.y - 1][point.x].equalsIgnoreCase("-")) {
            array[0] = true;
        }
        if (point.y >= 0 && point.x >= 0 && point.x <= 16 && point.y < 16 && !new_board[point.y + 1][point.x].equalsIgnoreCase("-")) {
            array[1] = true;
        }
        if (point.y >= 0 && point.x > 0 && point.x <= 16 && point.y <= 16 && !new_board[point.y][point.x - 1].equalsIgnoreCase("|")) {
            array[2] = true;
        }
        if (point.y >= 0 && point.x >= 0 && point.x < 16 && point.y <= 16 && !new_board[point.y][point.x + 1].equalsIgnoreCase("|")) {
            array[3] = true;
        }
        return array;

    }

    public boolean Up(Player player, Board new_board) {
        if(!(player.point.x>=0  && player.point.y>=0  && player.point.x<=16  && player.point.y<=16 ))
            return false;
        boolean bool = getAvailableCells(player.point, new_board.board)[0];
        if (bool) {
            if (new_board.board[player.point.y - 2][player.point.x] == "A"
                    || new_board.board[player.point.y - 2][player.point.x] == "B"
                    || new_board.board[player.point.y - 2][player.point.x] == "C"
                    || new_board.board[player.point.y - 2][player.point.x] == "D") {
                new_board.board[player.point.y][player.point.x] = "*";
                if (player.point.y > 2)
                    player.point.y -= 4;
                else
                    player.point.y -= 2;
                new_board.board[player.point.y][player.point.x] = player.name;

                return true;
            }
            new_board.board[player.point.y][player.point.x] = "*";
            player.point.y -= 2;
            new_board.board[player.point.y][player.point.x] = player.name;
            return true;
        }
        return false;
    }

    public boolean Down(Player player, Board new_board) {
        if(!(player.point.x>=0  && player.point.y>=0  && player.point.x<=16  && player.point.y<=16 ))
            return false;
        boolean bool = getAvailableCells(player.point, new_board.board)[1];
        if (bool) {

            if (new_board.board[player.point.y + 2][player.point.x] == "A"
                    || new_board.board[player.point.y + 2][player.point.x] == "B"
                    || new_board.board[player.point.y + 2][player.point.x] == "C"
                    || new_board.board[player.point.y + 2][player.point.x] == "D") {
                new_board.board[player.point.y][player.point.x] = "*";
                if (player.point.y < 14)
                    player.point.y += 4;
                else
                    player.point.y += 2;
                new_board.board[player.point.y][player.point.x] = player.name;

                return true;
            }

            new_board.board[player.point.y][player.point.x] = "*";
            player.point.y += 2;
            new_board.board[player.point.y][player.point.x] = player.name;
            return true;
        }
        return false;
    }

    public boolean Left(Player player, Board new_board) {
        if(!(player.point.x>=0  && player.point.y>=0  && player.point.x<=16  && player.point.y<=16 ))
            return false;
        boolean bool = getAvailableCells(player.point, new_board.board)[2];
        if (bool) {

            if (new_board.board[player.point.y][player.point.x - 2] == "A"
                    || new_board.board[player.point.y][player.point.x - 2] == "B"
                    || new_board.board[player.point.y][player.point.x - 2] == "C"
                    || new_board.board[player.point.y][player.point.x - 2] == "D") {
                new_board.board[player.point.y][player.point.x] = "*";
                if (player.point.x > 2)
                    player.point.x -= 4;
                else
                    player.point.x -= 2;
                new_board.board[player.point.y][player.point.x] = player.name;

                return true;
            }

            new_board.board[player.point.y][player.point.x] = "*";
            player.point.x -= 2;
            new_board.board[player.point.y][player.point.x] = player.name;
            return true;
        }
        return false;
    }

    public boolean Right(Player player, Board new_board) {
        if(!(player.point.x>=0  && player.point.y>=0  && player.point.x<=16  && player.point.y<=16 ))
            return false;
        boolean bool = getAvailableCells(player.point, new_board.board)[3];
        if (bool) {

            if (new_board.board[player.point.y][player.point.x + 2] == "A"
                    || new_board.board[player.point.y][player.point.x + 2] == "B"
                    || new_board.board[player.point.y][player.point.x + 2] == "C"
                    || new_board.board[player.point.y][player.point.x + 2] == "D") {
                new_board.board[player.point.y][player.point.x] = "*";
                if (player.point.x < 14)
                    player.point.x += 4;
                else
                    player.point.x += 2;
                new_board.board[player.point.y][player.point.x] = player.name;
                return true;
            }

            new_board.board[player.point.y][player.point.x] = "*";
            player.point.x += 2;
            new_board.board[player.point.y][player.point.x] = player.name;

            return true;
        }
        return false;

    }





    public void displayBoard() {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    public boolean move(int m, Player p,Board boardd) {
        boolean bool = false;
        switch (m) {
            case 8:
                bool = Up(p, boardd);
                if (!bool)
                    System.out.println("You can't go up");
                break;
            case 4:
                bool = Left(p, boardd);
                if (!bool)
                    System.out.println("You can't go left");
                break;
            case 6:
                bool = Right(p, boardd);
                if (!bool)
                    System.out.println("You can't go right");
                break;
            case 2:
                bool = Down(p, boardd);
                if (!bool)
                    System.out.println("You can't go down");
                break;

//                default:
//                System.out.println("Enter correct number...........");
        }
        return bool;
    }

    //Player must select a point that contains (even,odd) or (odd , even)
    //notice that by choosing point if automatically .....
    public boolean wallMove( Point point ,Player p,Player[] players,String[][] boardd ){
        if(!(point.x<=16 && point.x>=0 && point.y<=16 && point.y>=0) )
            return false;
        if(p.numOfWalls==0){
         //   System.out.println("You are ran out of walls");
            return false;
        }
        boolean bool = false;
        if ( ((point.x % 2 == 1 && point.y % 2 == 0) || (point.x % 2 == 0 && point.y % 2 == 1))
                && point.x<=15 && point.y<=15 ){
            //horizontal
            if (point.y % 2 == 1 && point.x % 2 == 0){
                if (boardd[point.y][point.x].equalsIgnoreCase(" ")
                        && boardd[point.y][point.x+2].equalsIgnoreCase(" ") ) {

                    boardd[point.y][point.x] = "-";
                    boardd[point.y][point.x+2] = "-";

                    for (int i = 0; i <players.length ; i++) {
                        if(!searchWay(players[i],boardd).isEnd){

                            boardd[point.y][point.x] = " ";
                            boardd[point.y][point.x+2] = " ";
         //                   System.out.println("by putting wall there player will be blocked please choose another location");
                            return false;
                        }
                    }
                    p.numOfWalls--;
                    bool = true;
                }
           //     else
          //          System.out.println("you can't  put the wall there");

            }
            //vertical
            else if (point.x % 2 == 1 && point.y % 2 == 0){
                if (boardd[point.y][point.x].equalsIgnoreCase(" ")
                        && boardd[point.y+2][point.x].equalsIgnoreCase(" ") ){

                    boardd[point.y][point.x] = "|";
                    boardd[point.y+2][point.x] = "|";
                    for (int i = 0; i <players.length ; i++) {
                        if(!searchWay(players[i],boardd).isEnd){
                            boardd[point.y][point.x] = " ";
                            boardd[point.y+2][point.x] = " ";
          //                  System.out.println("by putting wall there player will be blocked please choose another location");
                            return false;
                        }
                    }
                    p.numOfWalls--;
                    bool = true;
                }
           //     else
            //        System.out.println("you can't  put the wall there");
            }
        }
        else {
         //   System.out.println("Please enter correct point");
        }
        return bool;
    }
    public void numerOfWalls(Player p){
        System.out.println(p.numOfWalls);
    }



    public  String toString(Point p){
        String str=p.x+"!"+p.y;

        return str;
    }


    public void add(sNode node,int n){
        if(!map.containsKey(toString(node.point))){

            map.put(toString(node.point),n);
            queue.add(node);
        }
    }

    public sNode searchWay(Player player,String[][] boardd){

        String[][] new_board=boardd;
        //Point point;
        sNode node=new sNode(player.point);

        map.clear();
        queue.clear();

        add(node,0);

        while(!queue.isEmpty()){

            node=queue.poll();


            if(player==player_A) {
                if(node.point.y==0){
                    node.arrayList.add(node);
                    node.isEnd=true;
                    return node;
                }
            }
            else if(player==player_B){
                if(node.point.y==16) {
                    node.arrayList.add(node);
                    node.isEnd=true;
                    return node;
                }


            }else if(player==player_C){
                if(node.point.x==16){
                    node.arrayList.add(node);
                    node.isEnd=true;
                    return node;
                }

            }else if(player==player_D){
                if(node.point.x==0){
                    node.arrayList.add(node);
                    node.isEnd=true;
                    return node;
                }
            }

            boolean hp=true;

            Point pp=node.point;
            sNode ssnode;
            hp=wallUp(node.point, new_board);
            int value=map.get(toString(node.point));
            if(hp){
                node.point.y-=2;
                pp=new Point(node.point.getY(),node.point.getX());
                ssnode=new sNode(pp);
                ssnode.arrayList.addAll(node.arrayList);
                ssnode.arrayList.add(node);

                add(ssnode,value+1);
                node.point.y+=2;
            }
            hp=wallDown(node.point, new_board);
            if(hp){
                node.point.y+=2;
                pp=new Point(node.point.getY(),node.point.getX());
                ssnode=new sNode(pp);
                ssnode.arrayList.addAll(node.arrayList);
                ssnode.arrayList.add(node);

                add(ssnode,value+1);
                node.point.y-=2;
            }
            hp=wallRight(node.point, new_board);
            if(hp){
                node.point.x+=2;
                pp=new Point(node.point.getY(),node.point.getX());
                ssnode=new sNode(pp);
                ssnode.arrayList.addAll(node.arrayList);
                ssnode.arrayList.add(node);

                add(ssnode,value+1);
                node.point.x-=2;
            }
            hp=wallLeft(node.point, new_board);
            if(hp){
                node.point.x-=2;
                pp=new Point(node.point.getY(),node.point.getX());
                ssnode=new sNode(pp);
                ssnode.arrayList.addAll(node.arrayList);
                ssnode.arrayList.add(node);

                add(ssnode,value+1);
                node.point.x+=2;
            }

        }
        return node;
    }
    public boolean wallUp(Point point,String[][] strings){
        boolean bool = getAvailableCells(point,strings)[0];
        if(bool ){
            return true;
        }
        return false;
    }
    public boolean wallDown(Point point,String[][] strings){
        boolean bool = getAvailableCells(point,strings)[1];
        if(bool ){
            return true;
        }
        return false;
    }
    public boolean wallRight(Point point,String[][] strings){
        boolean bool = getAvailableCells(point,strings)[3];
        if(bool ){
            return true;
        }
        return false;
    }
    public boolean wallLeft(Point point,String[][] strings){
        boolean bool = getAvailableCells(point,strings)[2];
        if(bool ){
            return true;
        }
        return false;
    }

    public Board copy(Board boardd){

        Board board1=new Board();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                board1.board[i][j]=board[i][j];
            }

        }
        board1.player_A = boardd.player_A.copy();
        board1.player_A.numOfWalls=boardd.player_A.numOfWalls;

        board1.player_B = boardd.player_B.copy();
        board1.player_B.numOfWalls=boardd.player_B.numOfWalls;

        board1.player_C = boardd.player_C.copy();
        board1.player_C.numOfWalls=boardd.player_C.numOfWalls;

        board1.player_D = boardd.player_D.copy();
        board1.player_D.numOfWalls=boardd.player_D.numOfWalls;

        return board1;
    }


}