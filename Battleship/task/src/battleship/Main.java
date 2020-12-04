package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Ship[] ships = {
            new Ship(1, "Aircraft Carrier", 5),
            new Ship(2, "Battleship", 4),
            new Ship(3, "Submarine", 3),
            new Ship(4, "Cruiser", 3),
            new Ship(5, "Destroyer", 2)};
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Player player1 = new Player("Player 1", new Field());
        Player player2 = new Player("Player 2", new Field());
        Player current = player1;
        Player opponent = player2;


        initPlayersField(player1);
        enterPressed();
        initPlayersField(player2);
        enterPressed();

        boolean exit = false;
        while (!exit) {
            current.printState(opponent);
            System.out.printf("%s, it's your turn:", current.getName());
            do {
                String input = scanner.nextLine();
                int[] coordinates = validCoords(input);
                if (coordinates != null) {
                    char result = opponent.getField().tryToHit(coordinates);
                    switch (result) {
                        case 0:
                            System.out.println("Something went wrong");
                            break;
                        case 'M':
                            System.out.println("You missed.");
                            break;
                        default:
                            if (opponent.getField().isWon()) {
                                System.out.println("You sank the last ship. You won. Congratulations!");
                                exit = true;
                            } else {
                                if (opponent.getField().isSank(result)) {
                                    System.out.println("You sank a ship!");
                                } else {
                                    System.out.println("You hit a ship!");
                                }
                            }
                            break;
                    }
                    if (!exit) {
                        enterPressed();
                        opponent = current;
                        current = current == player1 ? player2 : player1;
                    }
                    break;
                }
            } while (true);
        }
    }

    public static int[] parseCoords(String str) {
        if (str.trim().length() != 0) {
            int[] coords = new int[2];
            try {
                coords[0] = Integer.parseInt(str.substring(1));
                coords[1] = str.toUpperCase().charAt(0);
                return coords;
            } catch (NumberFormatException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        }
        return null;
    }

    public static int[] validCoords(String str) {
        int[] rawCoords = parseCoords(str);
        if (rawCoords == null) {
            return null;
        }
        int x = getValidX(rawCoords[0]);
        int y = getValidY(rawCoords[1]);
        if (x == -1 || y == -1) {
            System.out.println("Error: coordinates should be between A-J, 1-10");
            return null;
        }
        return new int[]{x, y};
    }

    public static int[] validCoords(String startInput, String endInput, Ship ship) {
        int[] start = validCoords(startInput);
        int[] end = validCoords(endInput);
        if (start == null || end == null) {
            return null;
        }
        //validate value of coordinates
        int startX = Math.min(start[0], end[0]);
        int startY = Math.min(start[1], end[1]);
        int endX = Math.max(start[0], end[0]);
        int endY = Math.max(start[1], end[1]);
//        if (startX == -1 || startY == -1 || endX == -1 || endY == -1) {
//            System.out.println("Error: coordinates should be between A-J, 1-10");
//            return null;
//        }
        //validate range of coordinates
        int rowLength = endX - startX;
        int colLength = endY - startY;
        if (rowLength == 0) {
            if (colLength != ship.getNumCells() - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName());
                return null;
            }
        } else if (colLength == 0) {
            if (rowLength != ship.getNumCells() - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName());
                return null;
            }
        } else {
            System.out.println("Error: Wrong ship location! Try again:");
            return null;
        }
        return new int[]{startX, startY, endX, endY};
    }

    public static int getValidX(int num) {
        if (num >= 1 && num <=10) {
            return num - 1;
        }
        return -1;
    }

    public static int getValidY(int ch) {
        if (ch -'A' >= 0 && ch - 'A' <= 9) {
            return ch - 'A';
        }
        return -1;
    }

    public static void initPlayersField(Player player) {
        System.out.printf("%s, place your ships on the game field\n", player.getName());
        Field.printField(player.getField().getGameField());
        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getNumCells());
            do {
                String input = scanner.nextLine();
                String start = input.split(" ")[0];
                String end = input.split(" ")[1];
                int[] coordinates = validCoords(start, end, ship);
                if (coordinates != null) {
                    if (player.getField().placeShip(coordinates, ship)) {
                        Field.printField(player.getField().getGameField());
                        break;
                    }
                }
            } while (true);
        }
    }

    public static void enterPressed() {
        System.out.println("Press Enter and pass the move to another player");
        String input = scanner.nextLine();
        if (input.trim().length() == 0) {
            System.out.println();
        }
    }
}