package com.engicorp.oop.cell;

import com.engicorp.oop.World;
import com.engicorp.oop.cell.Facility;
import com.engicorp.oop.livingbeing.Player;
import com.engicorp.oop.misc.Point;

public class Truck extends Facility {
    public Truck(Point pos) {
        super(pos, "facility/truck.png");
    }

    @Override
    public void interact() {
        int totalHargaTas = Player.getInstance().getTas().getTotalHarga();
        Player.getInstance().setMoney(Player.getInstance().getMoney()+totalHargaTas);
        World.getInstance().addMsg("Kamu menjual semua item di tas mu");
        World.getInstance().addMsg("Kamu mendapat uang sebanyak "+totalHargaTas);
        Player.getInstance().getTas().removeAll();
    }

}