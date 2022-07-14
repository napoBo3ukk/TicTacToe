package Lesson4;

import java.util.Random;
import java.util.Scanner;


public class MainforLesson4 {
    private static int rownumber;
    private static int columnnumber;
    private static final int SIZE = 7;
    private static int winStreak;

    private static final char DOT_EMPTY = '•';
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';

    public static final String HEADER_FIRST_SYMBOL = "0";
    public static final String SPACE_MAP = " ";

    private static char[][] MAP = new char[SIZE][SIZE];

    private static Scanner in = new Scanner(System.in);
    private static Random random = new Random();

    private static int turnsCount;

    public static void start() {
        do {
            System.out.println("\n\nИгра Начинается!");
            init();
            printmap();
            playGame();
        } while (isContinueGame());
        endGame();
    }

    private static void init() {
        turnsCount = 0;
        initwinstreak();
        initmap();
    }

    private static void initwinstreak() {
        if (SIZE >= 3 && SIZE <= 6) {
            winStreak = 3;
        } else if (SIZE >= 7 && SIZE <= 10) {
            winStreak = 4;
        } else if (SIZE >= 11) {
            winStreak = 5;
        }
        System.out.printf("Необходимо собрать %d символа в ряд\n", winStreak);
    }

    private static void initmap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MAP[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printmap() {
        printMapHeader();
        printMapBody();
    }

    private static void printMapHeader() {
        System.out.print(HEADER_FIRST_SYMBOL + SPACE_MAP);
        for (int i = 0; i < SIZE; i++) {
            printmapNumber(i);
        }
        System.out.println();
    }

    private static void printmapNumber(int i) {
        System.out.print(i + 1 + SPACE_MAP);
    }

    private static void printMapBody() {
        for (int i = 0; i < SIZE; i++) {
            printmapNumber(i);
            for (int j = 0; j < SIZE; j++) {
                System.out.print(MAP[i][j] + SPACE_MAP);
            }
            System.out.println();
        }
    }

    public static void playGame() {
        while (true) {
            humanTurn();
            printmap();
            if (checkEnd(DOT_HUMAN)) {
                break;
            }
            aiTurn();
            printmap();
            if (checkEnd(DOT_AI)) {
                break;
            }
        }
    }

    public static void humanTurn() {
        System.out.println("\n Твой ход!");

        int rowNumber;
        int columnNumber;

        while (true) {
            System.out.println("Выбери номер строки:");
            rowNumber = getValidNumberFromScanner() - 1;

            System.out.println("Выбери номер столбца");
            columnNumber = getValidNumberFromScanner() - 1;

            if (isCellFree(rowNumber, columnNumber)) {
                break;
            }
            System.out.printf("ОШИБКА! Ячейка с координатами %s:%s уже используется%n%n", rowNumber + 1,
                    columnNumber + 1);
        }
        MAP[rowNumber][columnNumber] = DOT_HUMAN;
        turnsCount++;
    }

    private static int getValidNumberFromScanner() {
        while (true) {
            if (in.hasNextInt()) {
                int n = in.nextInt();
                if (inNumberValid(n)) {
                    return n;
                }
                System.out.println("ОШБИКА! Проверьте значение координаты. Допускается от 1 до " + SIZE);
            } else {
                System.out.println("ОШИБКА! Ввод допускает лишь целые числа!");
                in.next();
            }
        }
    }

    private static boolean inNumberValid(int n) {
        return n >= 1 && n <= SIZE;
    }

    private static boolean isCellFree(int rowNumber, int columnNumber) {
        return MAP[rowNumber][columnNumber] == DOT_EMPTY;
    }

    private static boolean checkEnd(char symbol) {
        if (checkWinVertical(symbol) || checkWinHorizontal(symbol) || checkWinDiagonal(symbol) || checkWinDiagonal2(symbol)) {
            if (symbol == DOT_HUMAN) {
                System.out.println("Ты победил!");
            } else {
                System.out.println("Тебя одолела программа");
            }
            return true;
        }
        if (CheckDraw()) {
            System.out.println("Твои способности на уровне железяки");
            return true;
        }
        return false;
    }

    private static boolean CheckDraw() {
        return turnsCount >= SIZE * SIZE;
    }

    public static void aiTurn() {
        System.out.println("\n Ходит компьютер");

        int rowNumber;
        int columnNumber;

        do {
            rowNumber = random.nextInt(SIZE);
            columnNumber = random.nextInt(SIZE);
        } while (!isCellFree(rowNumber, columnNumber));

        MAP[rowNumber][columnNumber] = DOT_AI;
        turnsCount++;
    }

    private static boolean isContinueGame() {
        while (true) {
            System.out.println("Хотите продолжить? \n1. Играем\n2. Выход");
            int answer = in.nextInt();
            if (answer == 1) {
                return true;
            } else if (answer == 2) {
                return false;
            } else {
                System.out.println("Введи корректное число");
            }
        }

    }

    private static void endGame() {
        in.close();
        System.out.println("Ты заходи, если что");
    }

    private static boolean checkWinHorizontal(char symbol) {
        int countOfWin = 0;
        for (int i = 0; i <SIZE; i++) {
            if (MAP[rownumber][i] == symbol) {
                countOfWin++;
                if (countOfWin ==winStreak){
                    return true;
                }
            }
            else countOfWin = 0;
        }
        return false;
    }

    private static boolean checkWinVertical(char symbol) {
        int countOfWin = 0;
        for (int i = 0; i <SIZE; i++) {
            if (MAP[i][columnnumber] == symbol) {
                countOfWin++;
                if (countOfWin ==winStreak){
                    return true;
                }
            }
            else countOfWin = 0;
        }
        return false;
    }

    private static boolean checkWinDiagonal(char symbol) {
        int countOfWin = 0;
        int b = columnnumber + rownumber;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (b == i + j) {
                    if (MAP[i][j] == symbol){
                        countOfWin ++;
                    }
                    else countOfWin = 0;
                }
                if (countOfWin == winStreak){
                    return true;
                }
            }
        }
        return false;
    }
/*        int countOfWin = 0;
        for (int i = 0; i <SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
            if (MAP[rownumber][columnnumber] == symbol) {
                countOfWin++;
            } else countOfWin = 0;
            if (countOfWin == winStreak){
                    return true;
                }
            }
        }
        return false;
    }*/

    private static boolean checkWinDiagonal2(char symbol) {
        int countOfWin = 0;

        do {
            if (rownumber != 0 && columnnumber != 0) {
                rownumber--;
                columnnumber--;
            }
            else break;
        }
        while (true);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == rownumber && j == columnnumber) {
                    if (MAP[rownumber][columnnumber] == symbol){
                        countOfWin++;
                    }
                    else {
                        countOfWin = 0;
                    }
                    columnnumber++;
                    rownumber++;
                    if (countOfWin == winStreak){
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
