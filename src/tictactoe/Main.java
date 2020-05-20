package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Hiermann Alexander
 */
public class Main {

    /**
     * in this char[][] you can find all game data (_'s, X's and / or O's)
     */
    private static final char[][] field = new char[3][3];
    /**
     * this is the char, that the player will use in game
     */
    private static char player1Symbol;
    /**
     * this is the char, that the ai will use in game
     */
    private static char player2Symbol;
    /**
     * if this boolean is false, the other player moves
     */
    private static boolean anotherOneMoves;
    /**
     * if this boolean is true, only AI moves
     */
    private static boolean onlyAI;
    /**
     * if this boolean is true, only users move
     */
    private static boolean onlyHumans;
    /**
     * initializes user or ai (difficulty)
     */
    private static String player1;
    /**
     * initializes user or ai (difficulty)
     */
    private static String player2;
    /**
     * this boolean is just for information
     */
    private static boolean isPlayer1;

    public static void main(String[] args) {
        menu();
    }

    /**
     * starting menu for the type of game and difficulty
     */
    public static void menu() {

        player1Symbol = 'X';
        player2Symbol = 'O';
        anotherOneMoves = true;
        onlyAI = false;
        onlyHumans = false;
        isPlayer1 = true;

        System.out.print("Input command: ");
        Scanner in = new Scanner(System.in);
        String input;

        loop:
        while (true) {
            input = in.next();

            if (input.equals("exit")) System.exit(0);

            if (input.equals("start")) {
                player1 = in.next();
                player2 = in.next();

                if (player1.equals("user") && player2.equals("user")) {
                    onlyHumans = true;
                    break;
                }

                if (!player1.equals("user") && player2.equals("user")) anotherOneMoves = !anotherOneMoves;

                boolean validPlayer1 = false;
                boolean hasHuman = false;
                switch (player1) {
                    case "user":
                        hasHuman = true;
                    case "medium":
                    case "easy":
                    case "hard":
                        validPlayer1 = true;
                        break;

                    default:
                        System.err.println("Bad parameters!");
                }
                switch (player2) {
                    case "user":
                        hasHuman = true;
                    case "medium":
                    case "easy":
                    case "hard":
                        if (!hasHuman) onlyAI = true;
                        if (validPlayer1) break loop;

                    default:
                        System.err.println("Bad parameters!");
                }
            } else {
                System.err.println("Bad parameters!");
            }
        }

        for (int i = 0; i < 3; i++) {
            Arrays.fill(field[i], ' ');
        }

        drawField();
    }

    /**
     * in this method, the char[][] field will get initialized from the player's input String
     */
    public static void getField() {
        Scanner in = new Scanner(System.in);
        String allowedChars = "[_XO]+";
        String input;

        System.out.print("Enter cells: ");

        while (true) {
            input = in.next();

            if (input.equalsIgnoreCase("exit")) System.exit(0);
            if (input.length() != 9) {
                System.err.println("You should enter 9 cells!");
            } else if (!input.matches(allowedChars)) {
                System.err.println("You should only enter '_', 'X' or 'O'!");
            } else {
                break;
            }
        }
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = input.charAt(counter);
                if (field[i][j] == '_') field[i][j] = ' ';
                counter++;
            }
        }
        drawField();
    }

    /**
     * in this method, the char[][] field will be drawn, but nicely formatted
     */
    public static void drawField() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println("| " + field[i][0] + " " + field[i][1] + " " + field[i][2] + " |");
        }
        System.out.println("---------");
        printGameState();
    }

    /**
     * in this method, the player use coordinates for his next move
     */
    public static void playerMove() {
        anotherOneMoves = !anotherOneMoves;

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the coordinates: ");
        String input;
        char inputX;
        char inputY;
        int xPos = 0;
        int yPos = 0;

        while (true) {
            input = in.nextLine();

            if (input.equalsIgnoreCase("exit")) System.exit(1);
            if (input.length() > 1 && input.charAt(0) == ' ') input = input.substring(1);

            if (input.length() == 3) {
                inputX = input.charAt(0);
                inputY = input.charAt(2);
                if (Character.digit(inputX, 10) > 3 || Character.digit(inputX, 10) < 1 || Character.digit(inputY, 10) > 3 || Character.digit(inputY, 10) < 1) {
                    System.err.println("Coordinates should be from 1 to 3!");
                }
                for (int i = 0; i < 3; i += 2) {
                    if (Character.isAlphabetic(input.charAt(i))) System.err.println("You should enter numbers!");
                }

                if (inputX == '1') xPos = 0;
                if (inputX == '2') xPos = 1;
                if (inputX == '3') xPos = 2;

                if (inputY == '1') yPos = 2;
                if (inputY == '2') yPos = 1;
                if (inputY == '3') yPos = 0;

                if (field[yPos][xPos] == ' ') {
                    if (isPlayer1) {
                        field[yPos][xPos] = player1Symbol;
                    } else {
                        field[yPos][xPos] = player2Symbol;
                    }
                    break;
                } else {
                    System.err.println("This cell is occupied! Choose another one!");
                }
            } else System.err.println("You should enter numbers!");
        }
        isPlayer1 = !isPlayer1;
        drawField();
    }

    /**
     * gets the data from gameStatus() and prints it if the game finishes
     */
    public static void printGameState() {
        if (gameStatus().equals("Game not finished")) {
            if (onlyAI) {
                if (isPlayer1) {
                    obtainDifficulty(player1);
                } else {
                    obtainDifficulty(player2);
                }
            }
            if (onlyHumans) playerMove();

            if (anotherOneMoves) {
                playerMove();
            } else {
                if (isPlayer1) {
                    obtainDifficulty(player1);
                } else {
                    obtainDifficulty(player2);
                }
            }
        } else if (gameStatus().equals("Unknown error, please restart the game!")) {
            System.err.println(gameStatus());
            System.exit(-1);
        } else {
            System.out.println(gameStatus());
            menu();
        }
    }

    /**
     * @return the information from checkLanes nicely formatted
     */
    public static String gameStatus() {
        switch (checkLanes()) {
            case "player":
                return player1Symbol + " wins";

            case "ai":
                return player2Symbol + " wins";

            case "draw":
                return "Draw";

            case "nothing":
                return "Game not finished";
        }
        return "Unknown error, please restart the game!";
    }

    /**
     * @return one of the possible gameStates (player, ai, draw, nothing)
     */
    public static String checkLanes() {
        //check if there are only X's or O's
        int playerCounter = 0;
        int aiCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == player1Symbol) playerCounter++;
                if (field[i][j] == player2Symbol) aiCounter++;
            }
        }
        if (playerCounter > 5) return "player";
        if (aiCounter > 5) return "ai";

        //checker for player's each lane
        int[] player1LaneChecksH = new int[5];
        int[] player2LaneChecksH = new int[5];
        int[] player1LaneChecksV = new int[3];
        int[] player2LaneChecksV = new int[3];

        for (int i = 0; i < 3; i++) {
            checkAllLanes(field, player1LaneChecksH, player2LaneChecksH, player1LaneChecksV, player2LaneChecksV, i);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (player1LaneChecksH[i] == 3 && player2LaneChecksH[j] == 3) return "draw";
            }

            if (i < 3) {
                for (int j = 0; j < 3; j++) {
                    if (player1LaneChecksV[i] == 3 && player2LaneChecksV[j] == 3) return "draw";
                }

                if (player1LaneChecksV[i] == 3) return "player";
                if (player2LaneChecksV[i] == 3) return "ai";
            }

            if (player1LaneChecksH[i] == 3) return "player";
            if (player2LaneChecksH[i] == 3) return "ai";
        }

        if (playerCounter + aiCounter != 9) return "nothing";
        return "draw";
    }

    /**
     * in this method, the player use coordinates for his next move
     */
    public static void easyAIMove() {
        anotherOneMoves = !anotherOneMoves;
        System.out.println("Making move level \"easy\"");

        while (true) {
            int xPos = (int) (Math.random() * 3);
            int yPos = (int) (Math.random() * 3);

            if (field[yPos][xPos] == ' ') {
                if (isPlayer1) {
                    field[yPos][xPos] = player1Symbol;
                } else {
                    field[yPos][xPos] = player2Symbol;
                }
                break;
            }
        }
        isPlayer1 = !isPlayer1;
        drawField();
    }

    /**
     * in this method, the player use coordinates for his next move
     */
    public static void mediumAIMove() {
        anotherOneMoves = !anotherOneMoves;
        System.out.println("Making move level \"medium\"");

        checkerLoop:
        while (true) {

            //checker for player's each lane
            int[] player1LaneChecksH = new int[5];
            int[] player2LaneChecksH = new int[5];
            int[] player1LaneChecksV = new int[3];
            int[] player2LaneChecksV = new int[3];

            for (int i = 0; i < 3; i++) {
                checkAllLanes(field, player1LaneChecksH, player2LaneChecksH, player1LaneChecksV, player2LaneChecksV, i);
                if (isPlayer1) {
                    if (doWinOrBlock(player1LaneChecksH, player2LaneChecksH, player1LaneChecksV, player2LaneChecksV, i, player1Symbol)) {
                        break checkerLoop;
                    }
                } else {
                    if (doWinOrBlock(player2LaneChecksH, player1LaneChecksH, player2LaneChecksV, player1LaneChecksV, i, player2Symbol)) {
                        break checkerLoop;
                    }
                }
            }

            while (true) {
                int xPos = (int) (Math.random() * 3);
                int yPos = (int) (Math.random() * 3);

                if (field[yPos][xPos] == ' ') {
                    if (isPlayer1) {
                        field[yPos][xPos] = player1Symbol;
                    } else {
                        field[yPos][xPos] = player2Symbol;
                    }
                    break checkerLoop;
                }
            }
        }
        isPlayer1 = !isPlayer1;
        drawField();
    }

    /**
     * in this method, the player use coordinates for his next move
     */
    private static void hardAIMove() {
        anotherOneMoves = !anotherOneMoves;
        System.out.println("Making move level \"hard\"");

        int miniMaxResult = 0;
        if (isPlayer1) {
            miniMaxResult = miniMaxAlgorithm(field, player1Symbol)[0];
        } else {
            miniMaxResult = miniMaxAlgorithm(field, player2Symbol)[0];
        }

        int cellCounter = 0;
        int xPos = 0;
        int yPos = 0;

        checkerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cellCounter == miniMaxResult) {
                    xPos = i;
                    yPos = j;
                    break checkerLoop;
                }
                cellCounter++;
            }
        }

        if (isPlayer1) {
            field[xPos][yPos] = player1Symbol;
        } else {
            field[xPos][yPos] = player2Symbol;
        }
        isPlayer1 = !isPlayer1;
        drawField();
    }

    /**
     * @param field your field
     * @param player1LaneChecksH and Array with length 5 for each horizontal lane an both diagonals for player1Symbol
     * @param player2LaneChecksH and Array with length 5 for each horizontal lane an both diagonals for player2Symbol
     * @param player1LaneChecksV and Array with length 3 for each vertical lane for player1Symbol
     * @param player2LaneChecksV and Array with length 3 for each vertical lane for player2Symbol
     * @param i an integer for a loop to go threw every single lane (expected < 3)
     */
    private static void checkAllLanes(char[][] field, int[] player1LaneChecksH, int[] player2LaneChecksH, int[] player1LaneChecksV, int[] player2LaneChecksV, int i) {
        for (int j = 0; j < 3; j++) {
            //horizontal
            if (field[i][j] == player1Symbol) player1LaneChecksH[i]++;
            if (field[i][j] == player2Symbol) player2LaneChecksH[i]++;
            //vertical
            if (field[j][i] == player1Symbol) player1LaneChecksV[i]++;
            if (field[j][i] == player2Symbol) player2LaneChecksV[i]++;
        }
        //diagonal player1
        if (field[i][i] == player1Symbol) player1LaneChecksH[3]++;
        if (field[2 - i][i] == player1Symbol) player1LaneChecksH[4]++;
        //diagonal player2
        if (field[i][i] == player2Symbol) player2LaneChecksH[3]++;
        if (field[2 - i][i] == player2Symbol) player2LaneChecksH[4]++;
    }

    /**
     * @param player1LaneChecksH and Array with length 5 for each horizontal lane an both diagonals for player1Symbol
     * @param player2LaneChecksH and Array with length 5 for each horizontal lane an both diagonals for player2Symbol
     * @param player1LaneChecksV and Array with length 3 for each vertical lane for player1Symbol
     * @param player2LaneChecksV and Array with length 3 for each vertical lane for player2Symbol
     * @param i an integer for a loop to go threw every single lane (expected < 3)
     * @param playerSymbol the symbol of the player that's playing right now
     * @return true of false if the player could win next round or block the other player to prohibit his win
     */
    private static boolean doWinOrBlock(int[] player1LaneChecksH, int[] player2LaneChecksH, int[] player1LaneChecksV, int[] player2LaneChecksV, int i, char playerSymbol) {
        if (player1LaneChecksH[i] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    field[i][j] = playerSymbol;
                    return true;
                }
            }
        } else if (player1LaneChecksV[i] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[j][i] == ' ') {
                    field[j][i] = playerSymbol;
                    return true;
                }
            }
        } else if (player1LaneChecksH[3] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[i][i] == ' ') {
                    field[i][i] = playerSymbol;
                    return true;
                }
            }
        } else if (player1LaneChecksH[4] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[2 - i][i] == ' ') {
                    field[2 - i][i] = playerSymbol;
                    return true;
                }
            }
        } else if (player2LaneChecksH[i] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    field[i][j] = playerSymbol;
                    return true;
                }
            }
        } else if (player2LaneChecksV[i] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[j][i] == ' ') {
                    field[j][i] = playerSymbol;
                    return true;
                }
            }
        } else if (player2LaneChecksH[3] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[j][j] == ' ') {
                    field[j][j] = playerSymbol;
                    return true;
                }
            }
        } else if (player2LaneChecksH[4] == 2) {
            for (int j = 0; j < 3; j++) {
                if (field[2 - j][j] == ' ') {
                    field[2 - j][j] = playerSymbol;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param player the player that's playing ("user", "easy", "medium", "hard")
     */
    private static void obtainDifficulty(String player) {
        switch (player) {
            case "easy":
                easyAIMove();
                break;
            case "medium":
                mediumAIMove();
                break;
            case "hard":
                hardAIMove();
                break;
        }
    }

    /**
     * @param board your field
     * @param playerSymbol the symbol of the player that's playing right now
     * @return true of false if the player with playerSymbol has already won
     */
    private static boolean checkIfWin(char[][] board, char playerSymbol) {
        //checker for player's each lane
        int[] player1LaneChecksH = new int[5];
        int[] player2LaneChecksH = new int[5];
        int[] player1LaneChecksV = new int[3];
        int[] player2LaneChecksV = new int[3];

        for (int i = 0; i < 3; i++) {
            checkAllLanes(board, player1LaneChecksH, player2LaneChecksH, player1LaneChecksV, player2LaneChecksV, i);
        }

        for (int i = 0; i < 5; i++) {
            if (playerSymbol == 'X') {
                if (player1LaneChecksH[i] == 3) {
                    return true;
                } else if (i < 3) {
                    if (player1LaneChecksV[i] == 3) {
                        return true;
                    }
                }
            } else {
                if (player2LaneChecksH[i] == 3) {
                    return true;
                } else if (i < 3) {
                    if (player2LaneChecksV[i] == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param field your field
     * @param playerSymbol the symbol of the player that's playing right now
     * @return a int[] with length of 2 where you can find the best move possible for the ai
     */
    private static int[] miniMaxAlgorithm(char[][] field, char playerSymbol) {
        List<Integer> availableIndexes = new ArrayList<>();
        int indexCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    availableIndexes.add(indexCounter);
                }
                indexCounter++;
            }
        }

        if (isPlayer1) {
            if (checkIfWin(field, player1Symbol)) {
                return new int[]{0, 10};
            } else if (checkIfWin(field, player2Symbol)) {
                return new int[]{0, -10};
            }
        } else {
            if (checkIfWin(field, player2Symbol)) {
                return new int[]{0, 10};
            } else if (checkIfWin(field, player1Symbol)) {
                return new int[]{0, -10};
            }
        }
        if (availableIndexes.size() == 0) {
            return new int[]{0, 0};
        }

        List<Integer> moves = new ArrayList<>();
        for (int cellNumber : availableIndexes) {
            int move = 0;
            int xPos = 0;
            int yPos = 0;
            loop:
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cellNumber == move) {
                        xPos = i;
                        yPos = j;
                        break loop;
                    }
                    move++;
                }
            }
            moves.add(cellNumber);
            field[xPos][yPos] = playerSymbol;

            int[] miniMaxResult;
            int score;
            if (playerSymbol == player1Symbol) {
                miniMaxResult = miniMaxAlgorithm(field, player2Symbol);
            } else {
                miniMaxResult = miniMaxAlgorithm(field, player1Symbol);
            }
            score = miniMaxResult[1];
            field[xPos][yPos] = ' ';

            moves.add(score);
        }
        //System.out.println(moves);
        int[] bestScenario = new int[2];
        if (isPlayer1) {
            bestScenario[0] = getBestMove(playerSymbol, moves, bestScenario, player1Symbol);
        } else {
            bestScenario[0] = getBestMove(playerSymbol, moves, bestScenario, player2Symbol);
        }
        return bestScenario;
    }

    /**
     * @param playerSymbol the symbol of the player that's playing right now
     * @param moves a list of Integers with all possible moves for the ai
     * @param bestScenario a int[] with length of 2 where the best move with the highest score is written in
     * @param player playerSymbol for player1 or player2
     * @return int with the best possible move and the int[] bestScenario will get filled with the best move and highest score
     */
    private static int getBestMove(char playerSymbol, List<Integer> moves, int[] bestScenario, char player) {
        int bestScore;
        int bestMove = 0;

        if (playerSymbol == player) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i += 2) {
                if (moves.get(i + 1) > bestScore) {
                    bestScore = moves.get(i + 1);
                    bestScenario[1] = bestScore;
                    bestMove = moves.get(i);
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i += 2) {
                if (moves.get(i + 1) < bestScore) {
                    bestScore = moves.get(i + 1);
                    bestScenario[1] = bestScore;
                    bestMove = moves.get(i);
                }
            }
        }
        return bestMove;
    }
}