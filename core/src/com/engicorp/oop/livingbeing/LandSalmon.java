package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.product.ChickenEgg;
import com.engicorp.oop.product.ChickenMeat;
import com.engicorp.oop.product.SalmonEgg;
import com.engicorp.oop.product.SalmonMeat;

import java.util.Random;

public class LandSalmon extends Animal implements MeatProducingAnimal, EggProducingAnimal
{

    public LandSalmon(Point _pos) {
        super(_pos, "BlubBlubBlub", "animal/salmon.png", 7, 40, 40);
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
                Player.getInstance().getTas().addBarang(new SalmonMeat());
                World.getInstance().addMsg("Kamu mendapat daging salmon darat");
            }catch(TasKepenuhan T)
            {
                World.getInstance().addMsg("Tas sudah penuh, SalmonMeat di drop");
            }
        }
        World.getInstance().addMsg("1 Land Salmon Mati");
    }

    public void interact()
    {
        if(canInteract) {
            try {
                Player.getInstance().getTas().addBarang(new SalmonEgg());
                World.getInstance().addMsg("Kamu mendapat telur salmon darat");
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas sudah penuh, SalmonEgg di drop");
            }
            canInteract = false;
        }else{
            World.getInstance().addMsg("Tunggu binatangnya makan dulu");
        }
    }
}