package com.engicorp.oop.cell;

import com.engicorp.oop.World;
import com.engicorp.oop.livingbeing.Player;
import com.engicorp.oop.misc.Point;

public class Well extends Facility
{
    public Well(Point pos)
    {
        super(pos, "facility/well.png");
    }

    @Override
    public void interact()
    {
        Player.getInstance().setWater(100);
        World.getInstance().addMsg("Penyiram tanaman berhasil dipenuhi");
    }

}