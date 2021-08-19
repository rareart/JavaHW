package task1;

enum Orientation {
    NORTH {
        @Override
        public Orientation turn() {return EAST;}
        public int[] move(int[] field) {return new int[] {field[0], field[1] + 1};}
    },
    EAST {
        @Override
        public Orientation turn() {return SOUTH;}
        public int[] move(int[] field) {return new int[] {field[0] + 1, field[1]};}
    },
    SOUTH {
        @Override
        public Orientation turn() {return WEST;}
        public int[] move(int[] field) {return new int[] {field[0], field[1] - 1};}
    },
    WEST {
        @Override
        public Orientation turn() {return NORTH;}
        public int[] move(int[] field) {return new int[] {field[0] - 1, field[1]};}
    };

    public abstract Orientation turn();
    public abstract int[] move(int[] field);
}
