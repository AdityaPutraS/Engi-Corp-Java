package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.product.ChickenEgg;
import com.engicorp.oop.product.ChickenMeat;

import java.util.Random;

public class Chicken extends Animal implements MeatProducingAnimal, EggProducingAnimal
{

    public Chicken(Point _pos) {
        super(_pos, "Kokokpetok", "animal/ayam.png", 7, 40, 40);
        setOffsetPos(new Point(1.25, 0.25, 1));
        setAtmt(new AbleToMoveThere() {
            @Override
            public boolean canGoTo(Point goalPos) {
                return World.getInstance().getLand(goalPos).isBarn() || World.getInstance().getLand(goalPos).isCoop();
            }
        });
    }

    public void die(boolean diedOfHunger)
    {
        if(!diedOfHunger)
        {
            try {
                Player.getInstance().getTas().addBarang(new ChickenMeat());
                World.getInstance().addMsg("Kamu mendapat daging ayam");
            }catch(TasKepenuhan T)
            {
                World.getInstance().addMsg("Tas sudah penuh, ChickenMeat di drop");
            }
        }
        World.getInstance().addMsg("1 Chicken Mati");
    }

    public void interact()
    {
        if(canInteract) {
            try {
                Player.getInstance().getTas().addBarang(new ChickenEgg());
                World.getInstance().addMsg("Kamu mendapat telur ayam");
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas sudah penuh, ChickenMeat di drop");
            }
            canInteract = false;
        }else{
                World.getInstance().addMsg("Tunggu binatangnya makan dulu");
            }
    }
}