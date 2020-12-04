package battleship;

import java.util.Arrays;

public class Field {
    private char[][] gameField;
    private char[][] gameFog;
    private char[][] gameShipTypes;

    public Field() {
        gameField = initField();
        gameFog = initField();
        gameShipTypes = initField();
    }

    public char[][] initField() {
        char[][] arr = new char[10][10];
        for (char[] row : arr) {
            Arrays.fill(row, '~');
        }
        return arr;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public char[][] getGameFog() {
        return gameFog;
    }

    public static void printField(char[][] fieldOrFog) {
        StringBuilder output = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        char rowNum = 'A';
        for (char[] row : fieldOrFog) {
            output.append(rowNum++);
            for (char cell : row) {
                output.append(" ").append(cell);
            }
            output.append("\n");
        }
        System.out.print(output);
    }

    public boolean placeShip(int[] coordinates, Ship ship) {
        if (coordinates == null) {
            return false;
        }
        if (isPlaceOccupied(coordinates)) {
            System.out.println("Error: This place is already taken");
            return false;
        }
        if (isCloseToAnother(coordinates)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }
        boolean inRow = coordinates[0] - coordinates[2] != 0;
        if (inRow) {
            for (int i = coordinates[0]; i <= coordinates[2]; i++) {
                gameField[coordinates[1]][i] = 'O';
                gameShipTypes[coordinates[1]][i] = (char) ship.getId();
            }
        } else {
            for (int i = coordinates[1]; i <= coordinates[3]; i++) {
                gameField[i][coordinates[0]] = 'O';
                gameShipTypes[i][coordinates[0]] = (char) ship.getId();
            }
        }
        return true;
    }

    public boolean isPlaceOccupied(int[] coordinates) {
        boolean inRow = coordinates[0] - coordinates[2] != 0;
        if (inRow) {
            for (int i = coordinates[0]; i <= coordinates[2]; i++) {
                if (gameField[coordinates[1]][i] == 'O') {
                    return true;
                }
            }
        } else {
            for (int i = coordinates[1]; i <= coordinates[3]; i++) {
                if (gameField[i][coordinates[0]] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCloseToAnother(int[] coordinates) {
        boolean inRow = coordinates[0] - coordinates[2] != 0;
        if (inRow) {
            if (isCellO(coordinates[0] - 1, coordinates[1])) {
                return true;
            }
            if (isCellO(coordinates[2] + 1, coordinates[1])) {
                return true;
            }
            for (int i = coordinates[0]; i <= coordinates[2]; i++) {
                if (isCellO(i, coordinates[1] + 1)) {
                    return true;
                }
                if (isCellO(i, coordinates[1] - 1)) {
                    return true;
                }
            }
        } else {
            if (isCellO(coordinates[0], coordinates[1] - 1)) {
                return true;
            }
            if (isCellO(coordinates[2], coordinates[3] + 1)) {
                return true;
            }
            for (int i = coordinates[1]; i <= coordinates[3]; i++) {
                if (isCellO(coordinates[0] + 1, i)) {
                    return true;
                }
                if (isCellO(coordinates[0] - 1, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCellO(int x, int y) {
        try {
            return gameField[y][x] == 'O';
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return false;
    }

    public char tryToHit(int[] target) {
        char answer = 0;
        int x = target[0];
        int y = target[1];
        if (gameField[y][x] == '~') {
            gameField[y][x] = 'M';
            gameFog[y][x] = 'M';
            answer = 'M';
        }
        if (gameField[y][x] == 'O') {
            gameField[y][x] = 'X';
            gameFog[y][x] = 'X';
            answer = gameShipTypes[y][x];
            gameShipTypes[y][x] = 'X';
        }
        return answer;
    }

    public boolean isWon() {
        for (char[] row : gameField) {
            for (char val : row) {
                if (val == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSank(char id) {
        for (char[] row : gameShipTypes) {
            for (char val : row) {
                if (val == id) {
                    return false;
                }
            }
        }
        return true;
    }
}