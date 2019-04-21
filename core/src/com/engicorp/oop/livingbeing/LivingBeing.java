package com.engicorp.oop.livingbeing;

import com.engicorp.oop.Showable;
import com.engicorp.oop.World;
import com.engicorp.oop.misc.Point;

public abstract class LivingBeing extends Showable
{

    public LivingBeing(Point _pos, String texPath, int wTile, int hTile)
    {
        super(_pos, texPath, true, wTile, hTile);
    }
}