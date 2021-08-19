package task1;

public class Tractor {
    private int[] position = new int[] { 0, 0 };
    private final int[] field = new int[] { 5, 5 };
    private Orientation orientation = Orientation.NORTH;

    public void move(String command) {
        if (command.equals("F")) {
            moveForwards();
        } else if (command.equals("T")) {
            turnClockwise();
        }
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public Orientation getOrientation() {
        return Orientation.valueOf(orientation.name());
    }

    private void moveForwards() {
        position = orientation.move(position);
        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }

    private void turnClockwise() {
        orientation = orientation.turn();
    }
}
