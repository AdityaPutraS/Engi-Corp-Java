package com.engicorp.oop.livingbeing;

import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.World;
import com.engicorp.oop.product.PlatypusEgg;

import java.util.Random;

public class Platypus extends Animal implements EggProducingAnimal
{

    public Platypus(Point _pos) {
        super(_pos, "Purrr", "animal/platypus.png", 15, 40, 40);
        setOffsetPos(new Point(1.25, 0.25, 1));
        setAtmt(new AbleToMoveThere() {
            @Override
            public boolean canGoTo(Point goalPos) {
                return World.getInstance().getLand(goalPos).isCoop();
            }
        });
    }

    public void die(boolean diedOfHunger)
    {
        World.getInstance().addMsg("1 Platypus Mati");
    }

    public void interact()
    {
        if(canInteract) {
            try {
                Player.getInstance().getTas().addBarang(new PlatypusEgg());
                World.getInstance().addMsg("Kamu mendapat telur platypus");
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas sudah penuh, PlatypusEgg di drop");
            }
            canInteract = false;
        }else{
            World.getInstance().addMsg("Tunggu binatangnya makan dulu");
        }
    }
}