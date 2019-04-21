package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.product.CowMeat;
import com.engicorp.oop.product.CowMilk;
import com.engicorp.oop.product.LambMeat;
import com.engicorp.oop.product.LambMilk;

import java.util.Random;

public class Lamb extends Animal implements MeatProducingAnimal, MilkProducingAnimal
{

    public Lamb(Point _pos) {
        super(_pos, "Baaa", "animal/domba.png", 8, 40, 40);
        setOffsetPos(new Point(1.25, 0.25, 1));
        setAtmt(new AbleToMoveThere() {
            @Override
            public boolean canGoTo(Point goalPos) {
                return World.getInstance().getLand(goalPos).isBarn() || World.getInstance().getLand(goalPos).isGrassland();
            }
        });
    }

    public void die(boolean diedOfHunger)
    {
        if(!diedOfHunger)
        {
            try {
                Player.getInstance().getTas().addBarang(new LambMeat());
                World.getInstance().addMsg("Kamu mendapat daging kambing");
            }catch(TasKepenuhan T)
            {
                World.getInstance().addMsg("Tas sudah penuh, LambMeat di drop");
            }
        }
        World.getInstance().addMsg("1 lamb Mati");

    }

    public void interact()
    {
        if(canInteract) {
            try {
                Player.getInstance().getTas().addBarang(new LambMilk());
                World.getInstance().addMsg("Kamu mendapat susu kambing");
            } catch (TasKepenuhan T) {
                World.getInstance().addMsg("Tas sudah penuh, LambMilk di drop");
            }
            canInteract = false;
        }else{
                World.getInstance().addMsg("Tunggu binatangnya makan dulu");
            }
    }
}