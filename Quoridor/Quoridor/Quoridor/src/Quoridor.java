import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Quoridor {
    boolean twoOrFour = true;
    boolean AIPlaybool = false;
    boolean AI_with_AI = false;
    static Quoridor q = new Quoridor();
    static sState state;


    public static Quoridor getQ() {
        return q;
    }

    public static void main(String[] args) {

        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.two player   2.four player   3.play with AI   4.AI with AI");
        int x = scanner.nextInt();

        while (x != 1 && x != 2 && x != 3 && x != 4) {
            System.out.println("Please Enter correct number");
            x = scanner.nextInt();
        }
        int numberofplayers = 0;

        switch (x) {
            case 1 : {
                q.twoOrFour = true;
                numberofplayers = 2;
            }
            break;
            case 2 : {
                q.twoOrFour = false;
                numberofplayers = 4;
            }
            break;
            case 3 : {
                q.AIPlaybool = true;
                numberofplayers = 2;
            }
            break;
            case 4 : {
                q.AI_with_AI = true;
                numberofplayers = 2;
            }
            break;
        }

        board = board.initialBoard(q.twoOrFour);


        // for initialize which player should move first
        Random rand = new Random();
        int turn;
        if (q.twoOrFour)
            turn = rand.nextInt(2);
        else
            turn = rand.nextInt(4);
        turn = 0;
        boolean turnBool = false;
        /////////////////////////////////////////////////***************


        if (q.AI_with_AI) {
            double[][] population = new double[16][3];
            Genetic genetic = new Genetic();
            population = genetic.creatPopulation();


            for (int numOfGeneration = 1; numOfGeneration < 3; numOfGeneration++) {


                boolean winBool = false;
                int[][] scors = new int[population.length][population.length];
                for (int i = 0; i < scors.length; i++) {
                    scors[i][i] = 0;
                }

                ;            //Integer round=0;
                for (int i = 0; i < 4; i++) {
                    for (int j = i * 4; j < i * 4 + 3; j++) {
                        for (int k = j; k < i * 4 + 3; k++) {


                            double[] factor1 = {population[j][0], population[j][1], population[j][2]};
                            double[] factor2 = {population[k + 1][0], population[k + 1][1], population[k + 1][2]};


                            int round = 0;
                            board = new Board();
                            board = board.initialBoard(q.twoOrFour);
                            while (!board.isGameOver() && !board.hasPlayerWin()) {

                                if (turn == 2) {
                                    turn = 0;
                                }
                                if (round > 200) {
                                    round = 1;
                                    break;
                                }


                                if (turn == 1) {
                                    turnBool = false;
                                    System.out.println("Walls of B : " + board.player_B.numOfWalls);
                                    state = new AlPlay(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, turnBool, 2, factor1).minimaxWithalphabeta();
                                } else {
                                    turnBool = true;
                                    System.out.println("Walls of A : " + board.player_A.numOfWalls);
                                    state = new AlPlay(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, turnBool, 2, factor2).minimaxWithalphabeta();
                                }

                                //   state= new AlPlay(board,0,Integer.MIN_VALUE,Integer.MAX_VALUE,turnBool,2,).minimaxWithalphabeta();
                                board = state.efficientPath.get(state.efficientPath.size() - 2);

                                board.displayBoard();
                                turn++;
                                round++;
                                if (board.hasPlayerWin()) {
                                    winBool = true;
                                }

                            }

                            if (winBool) {
                                scors[j][k + 1] = round;
                                scors[k + 1][j] = -round;
                            } else {
                                scors[j][k + 1] = -round;
                                scors[k + 1][j] = round;
                            }


                        }
                    }

                }
                int[] ranks = new int[population.length];
                for (int i = 0; i < scors.length; i++) {
                    int winNum = 0;
                    int moveToWin = 0;
                    for (int j = 0; j < scors.length; j++) {
                        if (scors[i][j] > 1) {
                            winNum++;
                            moveToWin += scors[i][j];
                        }
                    }
                    ranks[i] = winNum * 1000 + moveToWin;
                }

                for (int i = 0; i < scors.length; i++) {
                    for (int j = 0; j < scors.length; j++) {

                        System.out.println("Game is over \n player  score : " + scors[i][j]);

                    }
                    System.out.println("\n\n\n");

                }


                for (int i = 0; i < ranks.length; i++) {
                    System.out.println(ranks[i]);
                }
                System.out.println("\n\n\n");

                int[] sortRank = new int[8];

                for (int k = 0; k < 2; k++) {


                    for (int i = 0; i < ranks.length; i += 4) {
                        int max = 0;
                        int indexOfMax = 0;
                        for (int j = i; j < i + 4; j++) {
                            if (ranks[j] / 1000 > max / 1000) {
                                max = ranks[j];
                                indexOfMax = j;


                            } else if (ranks[j] / 1000 == max / 1000) {
                                if (ranks[j] < max) {
                                    max = ranks[j];
                                    indexOfMax = j;


                                }
                            }
                            if (j == i + 3 && max == 0) {
                                indexOfMax = j;
                            }
                        }
                        sortRank[k * 4 + i / 4] = indexOfMax;
                        ranks[indexOfMax] = 0;

                    }
                }

                for (int i = 0; i < sortRank.length; i++) {
                    System.out.println(sortRank[i]);
                }

                System.out.println("\n\n");

                double[][] newPopulation = new double[16][3];
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 3; j++) {
                        newPopulation[i][j] = 0;
                    }

                }
                for (int i = 0; i < population.length; i++) {
                    for (int j = 0; j < 3; j++) {
                        newPopulation[i][j] = population[i][j];
                    }
                }

                population = new double[16][3];
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 3; j++) {
                        population[i][j] = 0;
                    }

                }

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        population[i][j] = newPopulation[sortRank[i]][j];

                    }
                }
                for (int i = 4; i < 8; i++) {
                    for (int j = 0; j < 3; j++) {

                        population[i][j] = (newPopulation[sortRank[i - 4]][j] + newPopulation[sortRank[i]][j]) / 2;
                    }
                }
                for (int i = 8; i < 12; i++) {
                    for (int j = 0; j < 3; j++) {

                        population[i][j] = (newPopulation[sortRank[i - 8]][j] + newPopulation[sortRank[i - 7]][j]) / 2;
                    }
                }
                for (int i = 12; i < 16; i++) {
                    for (int j = 0; j < 3; j++) {

                        population[i][j] = (newPopulation[sortRank[i - 9]][j] + newPopulation[sortRank[i - 8]][j]) / 2;
                    }
                }


                if (rand.nextDouble() < 0.1) {
                    population[12][1] = rand.nextDouble();
                }
                System.out.println("\n\n\n");

                for (int i = 0; i < population.length; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.println(population[i][j]);
                    }
                    System.out.println();
                }
                String fileName = "List"+numOfGeneration+ ".txt";

                try {

                    FileWriter writer = new FileWriter(fileName);
                    for (int i = 0; i < population.length; i++) {
                        System.out.println();
                        writer.write(population[i][0] + "\n" + population[i][1] + "\n" + population[i][2] + "\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Something went wrong while creating AIs.");
                }
                System.out.println("New AIs created.");


            }
        } else {


            while (!board.isGameOver() && !board.hasPlayerWin()) {


                if (q.twoOrFour) {
                    if (turn == 2)
                        turn = 0;
                } else {
                    if (turn == 4)
                        turn = 0;
                }

                Player active_player = board.player_B;


                if (q.AIPlaybool) {
                    double[] factors = {0.5, 0.03, 0.04};

                    switch (turn) {
                        case 0:
                            active_player = board.player_A;
                            System.out.println("A turn/ Enter your number:");
                            break;
                        case 1:
                            active_player = board.player_B;
                            //  System.out.println("B turn/ Enter your number:");
                            break;
                    }

                    if (turn == 0) {
                        System.out.println("Number of player's walls: " + active_player.numOfWalls);
                        System.out.println("1.move   2.put wall");
                        int index = scanner.nextInt();
                        while (index != 1 && index != 2) {
                            System.out.println("Please enter correct number");
                            index = scanner.nextInt();
                        }


                        if (index == 1) {
                            int m = scanner.nextInt();
                            boolean boolMove;
                            boolMove = board.move(m, active_player, board);

                            if (boolMove)
                                turn++;

                        }
                        //puting wall
                        else {
                            int a = scanner.nextInt();
                            int b = scanner.nextInt();
                            Point point = new Point(a, b);
                            ///x=numberofplayers

                            Player[] players = new Player[numberofplayers];
                            players[0] = board.player_A;
                            players[1] = board.player_B;
                            if (numberofplayers == 4) {
                                players[2] = board.player_C;
                                players[3] = board.player_D;
                            }
                            boolean bool = board.wallMove(point, active_player, players, board.board);
                            if (bool) {
                                turn++;
                            }
                        }

                        board.displayBoard();

                    } else {

                        state = new AlPlay(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 2, factors).minimaxWithalphabeta();
                        board = state.efficientPath.get(state.efficientPath.size() - 2);

                        System.out.println("player B walls:" + board.player_B.numOfWalls);
                        board.displayBoard();
                        turn++;

                    }


                } else {


                    switch (turn) {
                        case 0:
                            active_player = board.player_A;
                            System.out.println("A turn/ Enter your number:");
                            break;
                        case 1:
                            active_player = board.player_B;
                            System.out.println("B turn/ Enter your number:");
                            break;
                        case 2:
                            active_player = board.player_C;
                            System.out.println("C turn/ Enter your number:");
                            break;
                        case 3:
                            active_player = board.player_D;
                            System.out.println("D turn/ Enter your number:");
                            break;
                    }

                    System.out.println("Number of player's walls: " + active_player.numOfWalls);
                    System.out.println("1.move   2.put wall");
                    int index = scanner.nextInt();
                    while (index != 1 && index != 2) {
                        System.out.println("Please enter correct number");
                        index = scanner.nextInt();
                    }


                    if (index == 1) {
                        int m = scanner.nextInt();
                        boolean boolmove;
                        boolmove = board.move(m, active_player, board);

                        if (boolmove)
                            turn++;

                    }
                    //puting wall
                    else {
                        int a = scanner.nextInt();
                        int b = scanner.nextInt();
                        Point point = new Point(a, b);
                        ///x=numberofplayers

                        Player[] players = new Player[numberofplayers];
                        players[0] = board.player_A;
                        players[1] = board.player_B;
                        if (numberofplayers == 4) {
                            players[2] = board.player_C;
                            players[3] = board.player_D;
                        }
                        boolean bool = board.wallMove(point, active_player, players, board.board);
                        if (bool) {
                            turn++;
                        }
                    }

                    board.displayBoard();

                }

            }
            if (board.hasPlayerWin())
                System.out.println("YOU WIN");
            else
                System.out.println("GAME OVER");
        }
    }
}
