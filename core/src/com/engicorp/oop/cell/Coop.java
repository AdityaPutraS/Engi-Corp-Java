package com.engicorp.oop.cell;


import com.engicorp.oop.misc.Point;

import java.util.Random;

public class Coop extends Land{
    public Coop(Point _pos, Boolean _grass){
        super(_pos, "tile/100x100/isometric_0023.png", _grass);
        if (_grass){
            Random rand = new Random();
            int noGrass = rand.nextInt(3) + 1;
            this.setTexPath("tile/grass/coop_grass_"+noGrass+".png");
        }
    }

    @Override
    public final boolean isCoop(){
        return true;
    }

    @Override
    public void setGrass(boolean hasGrass) {
        this.grass = hasGrass;
        if (hasGrass) {
            Random rand = new Random();
            int noGrass = rand.nextInt(3) + 1;
            this.setTexPath("tile/grass/coop_grass_"+noGrass+".png");
        }else{
            this.setTexPath("tile/100x100/isometric_0023.png");
        }
    }
}