package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.product.ChickenMeat;
import com.engicorp.oop.product.DiplodocusEgg;
import com.engicorp.oop.product.DiplodocusMeat;

import java.util.Random;

public class Diplodocus extends Animal implements MeatProducingAnimal, EggProducingAnimal
{

    public Diplodocus(Point _pos) {
        super(_pos, "Rawr XD", "animal/diplo.png", 7, 40, 40);
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
                Player.getInstance().getTas().addBarang(new DiplodocusMeat());
                World.getInstance().addMsg("Kamu mendapat daging diplodocus");
            }catch(TasKepenuhan T)
            {
                World.getInstance().addMsg("Tas sudah penuh, DiplodocusMeat di drop");
            }
        }
        World.getInstance().addMsg("1 Diplodocus Mati");

    }

    public void interact()
    {
        if(canInteract) {
            try {
                Player.getInstance().getTas().addBarang(new DiplodocusEgg());
                World.getInstance().addMsg("Kamu mendapat telur diplodocus");
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas sudah penuh, DiplodocusEgg di drop");
            }
            canInteract = false;
        }else{
            World.getInstance().addMsg("Tunggu binatangnya makan dulu");
        }
    }
}