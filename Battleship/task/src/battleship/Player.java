package battleship;

public class Player {
    private String name;
    private Field field;

    public Player(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public void printState(Player opponent) {
        Field.printField(opponent.getField().getGameFog());
        System.out.println("---------------------");
        Field.printField(field.getGameField());
    }

//    public boolean addShip(Ship ship, String input) {
////        System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getNumCells());
//
//        int[] coordinates = Main.validCoords(input.split(" ")[0], , ship);
//        if (coordinates != null) {
//            if (field.placeShip(coordinates, ship)) {
//                Field.printField(field.getGameField());
//                break;
//            }
//        }
//        for (Ship ship : ships) {
//            do {
//                String start = scanner.next();
//                String end = scanner.next();
//                int[] coordinates = validCoords(start, end, ship);
//                if (coordinates != null) {
//                    if (field.placeShip(coordinates, ship)) {
//                        Field.printField(field.getGameField());
//                        break;
//                    }
//                }
//            } while (true);
//        }
//    }
}
