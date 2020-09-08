import java.util.*;

class AlPlay {
    private HashSet<String> map = new HashSet<>();
    private Board board;
    private int depth;
    private double alpha;
    private double beta;
    private boolean isMax;
    private int h;
    private double[] factor1;
    private double maxEval;
    private double minEval;
    private ArrayList<Board> efficientPath;

    AlPlay(Board board, int depth, double alpha, double beta, boolean isMax, int h,double[] factor1) {
        this.board = board;
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.isMax = isMax;
        this.h = h;
        this.factor1=factor1;
        maxEval = Integer.MIN_VALUE;
        minEval = Integer.MAX_VALUE;
        efficientPath = new ArrayList<>();
    }

    sState minimaxWithalphabeta() {
        if (depth == h || board.isGameOver() || board.hasPlayerWin()) {
            ArrayList<Board> singlePath = new ArrayList<>();
            singlePath.add(board);
            return new sState(evaluation(board,factor1), singlePath);
        }
        Player player;
        Player competitor;
        Player[] players = {board.player_A, board.player_B};
        if (isMax) {
            player = board.player_A;
            competitor = board.player_B;
        } else {
            player = board.player_B;
            competitor = board.player_A;
        }
        Point previousPoint = new Point(player.point.y, player.point.x);
        if (board.Up(player, board)) {

            sState childResult = computeChild();
            board.board[previousPoint.y][previousPoint.x] = player.name;
            board.board[player.point.y][player.point.x] = "*";
            player.point=new Point(previousPoint.y,previousPoint.x);
            if (childResult != null)
                return childResult;
        }
        if (board.Down(player, board) ){

            sState childResult = computeChild();
//            player.point = new Point(previousPoint.y, previousPoint.x);
            board.board[previousPoint.y][previousPoint.x] = player.name;
            board.board[player.point.y][player.point.x] = "*";
            player.point=new Point(previousPoint.y,previousPoint.x);
            if (childResult != null)
                return childResult;
        }
        if (board.Right(player, board)) {
            sState childResult = computeChild();
            board.board[previousPoint.y][previousPoint.x] = player.name;
            board.board[player.point.y][player.point.x] = "*";
            player.point=new Point(previousPoint.y,previousPoint.x);
            if (childResult != null)
                return childResult;
        }
        if (board.Left(player, board)) {
            sState childResult = computeChild();
            board.board[previousPoint.y][previousPoint.x] = player.name;
            board.board[player.point.y][player.point.x] = "*";
            player.point=new Point(previousPoint.y,previousPoint.x);
            if (childResult != null)
                return childResult;
        }

        sNode competitorNode = board.searchWay(competitor, board.board);
        int size=competitorNode.arrayList.size();
        for (int i = size- 1; i >0 ; i--) {
            if(player.numOfWalls==0)
                break;
            int xp = competitorNode.arrayList.get(i).point.x;
            int yp = competitorNode.arrayList.get(i).point.y;
            int yp2 = competitorNode.arrayList.get(i - 1).point.y;
            int xp2 = competitorNode.arrayList.get(i - 1).point.x;
            Point pp = new Point((yp + yp2) / 2, xp);
            boolean y_bool = false;
            boolean x_bool = false;
            boolean wallbool = false;
            if (yp != yp2) {
                y_bool = true;
                pp = new Point((yp + yp2) / 2, xp);
                wallbool = board.wallMove(pp, player, players, board.board);
            } else if (xp != xp2) {
                x_bool = true;
                pp = new Point(yp, (xp + xp2) / 2);
                wallbool = board.wallMove(pp, player, players, board.board);
            }
            if (!wallbool && y_bool) {
                pp.x -= 2;
                wallbool = board.wallMove(pp, player, players, board.board);
            } else if (!wallbool && x_bool) {
                pp.y -= 2;
                wallbool = board.wallMove(pp, player, players, board.board);
            }
            if (wallbool) {

                sState childResult = computeChild();
                if (childResult != null)
                    return childResult;
                player.numOfWalls++;
                board.board[pp.y][pp.x] = " ";
                if (y_bool)
                    board.board[pp.y][pp.x + 2] = " ";
                else
                    board.board[pp.y + 2][pp.x] = " ";
            }

        }


/*
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                Point point = new Point(i, j);
                boolean canDrawWall = board.wallMove(point, player, players, board.board);
                boolean isVertical = canDrawWall && ((j % 2 == 1 && i % 2 == 0) || (j % 2 == 0 && i % 2 == 1));
                if (!canDrawWall) {
                    point.x -= 2;
                    isVertical = true;
                    canDrawWall = board.wallMove(point, player, players, board.board);
                    if (!canDrawWall) {
                        isVertical = false;
                        point.y -= 2;
                        canDrawWall = board.wallMove(point, player, players, board.board);
                    }

                }
                if(canDrawWall) {
                    sState childResult = computeChild();
                    if (childResult != null)
                        return childResult;
                    player.numOfWalls++;
                    board.board[point.y][point.x] = " ";
                    if (isVertical)
                        board.board[point.y][point.x + 2] = " ";
                    else
                        board.board[point.y + 2][point.x] = " ";
                }
            }
        }*/
        if (isMax)
            return new sState(maxEval, efficientPath);
        else
            return new sState(minEval, efficientPath);
    }

    private sState computeChild() {
        if (!map.contains(boardToString(board.board, isMax))) {
            map.add(boardToString(board.board, isMax));
            Board b = board.copy(board);
            sState eval = new AlPlay(b, depth + 1, alpha, beta, !isMax, h,factor1).minimaxWithalphabeta();
            if (isMax) {
                if (eval.value > maxEval) {
                    efficientPath = eval.efficientPath;
                    eval.efficientPath.add(board);
                }
                maxEval = Math.max(eval.value, maxEval);
                alpha = Math.max(alpha, eval.value);
            } else {
                if (eval.value < minEval) {
                    efficientPath = eval.efficientPath;
                    eval.efficientPath.add(board);
                }
                minEval = Math.min(eval.value, minEval);
                alpha = Math.min(beta, eval.value);
            }
            if (beta <= alpha) {
                if (isMax)
                    return new sState(maxEval, efficientPath);
                else
                    return new sState(minEval, efficientPath);
            }
        }
        return null;
    }


    private double evaluation(Board board,double[] factor1) {
        Player player = board.player_A;
        Player competitor = board.player_B;
        sNode playerNode;
        playerNode = board.searchWay(player, board.board);
        sNode competitorNode;
        competitorNode = board.searchWay(competitor, board.board);
        int manhattan_D_Player=board.player_A.point.y;
        int manhattan_D_competitor=16-board.player_B.point.y;

        // playerNode=state.boardState.searchWay()
        double evalue = (competitorNode.arrayList.size()  - playerNode.arrayList.size() )*factor1[0]
                +(manhattan_D_competitor - manhattan_D_Player)*factor1[1]
                + (player.numOfWalls  - competitor.numOfWalls )*factor1[2];

        return evalue;

    }


    private String boardToString(String[][] strings, boolean isMax) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                str.append(strings[i][j]);
            }

        }
        return str.append(isMax).toString();
    }
}

