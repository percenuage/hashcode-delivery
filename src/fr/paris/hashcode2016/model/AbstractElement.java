package fr.paris.hashcode2016.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Axel on 13/02/2016.
 */
public abstract class AbstractElement {

    private int id;
    private Cell cell;

    private Grid grid;

    public AbstractElement(int id, int row, int col, Grid grid) {
        this.id = id;
        this.cell = new Cell(row, col);
        this.grid = grid;
    }

    public boolean isInGrid(Cell cell) {
        return cell.getRow() >= 0 && cell.getRow() < grid.getRows()
                && cell .getCol() >= 0 && cell.getCol() < grid.getCols();
    }

    public int getIndex() {
        return cell.getRow() * grid.getCols() + cell.getCol();
    }

    public int getDistance(AbstractElement element) {
        int deltaRow = this.cell.getRow() - element.getCell().getRow();
        int deltaCol = this.cell.getCol() - element.getCell().getCol();
        return (int)Math.ceil(Math.hypot((double)deltaRow, (double)deltaCol));
    }

    public AbstractElement getNearestElement(List<? extends AbstractElement> elements) {
        Map<Integer, AbstractElement> map = new HashMap<>();
        for (AbstractElement e : elements) {
            map.put(this.getDistance(e), e);
        }
        return map.get(Collections.min(map.keySet()));
    }

    public String getElementString() {
        Character c = '?';
        if (this instanceof Drone) {
            c = 'D';
        }
        if (this instanceof Warehouse) {
            c = 'W';
        }
        if (this instanceof Order) {
            c = 'O';
        }
        return c.toString() + id;
    }

    @Override
    public String toString() {
        return id + " " + cell;
    }

    public int getId() {
        return id;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
