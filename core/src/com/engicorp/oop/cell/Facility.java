package com.engicorp.oop.cell;

import com.engicorp.oop.cell.Cell;
import com.engicorp.oop.misc.Point;

public abstract class Facility extends Cell
{
    public Facility(Point pos, String texPath)
    {
        super(pos, texPath);
        setOffsetPos(new Point(0.85, 0.1, 1));
    }

    public void interact(){}
}