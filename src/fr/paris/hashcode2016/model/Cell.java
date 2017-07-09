package fr.paris.hashcode2016.model;

/**
 * Created by Axel on 13/02/2016.
 */
public class Cell {

    private int row;
    private int col;

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Cell getAdjacent(int direction) {
        int row = this.row;
        int col = this.col;
        switch (direction) {
            case UP:
                row++;
                break;
            case RIGHT:
                col++;
                break;
            case DOWN:
                row--;
                break;
            case LEFT:
                col--;
                break;
        }
        return new Cell(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ";" + col + ")";
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
