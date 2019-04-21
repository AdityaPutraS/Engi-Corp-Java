package com.engicorp.oop.cell;

import com.engicorp.oop.misc.Point;

import java.util.Random;

public class Grassland extends Land{
    public Grassland(Point _pos, Boolean _grass){
        super(_pos, "tile/100x100/isometric_0027.png", _grass);
        if (_grass){
            Random rand = new Random();
            int noGrass = rand.nextInt(2) + 1;
            this.setTexPath("tile/grass/grassland_grass_"+noGrass+".png");
        }
    }

    public final boolean isGrassland(){
        return true;
    }

    @Override
    public void setGrass(boolean hasGrass) {
        this.grass = hasGrass;
        if (hasGrass) {
            Random rand = new Random();
            int noGrass = rand.nextInt(2) + 1;
            this.setTexPath("tile/grass/grassland_grass_"+noGrass+".png");
        }else{
            this.setTexPath("tile/100x100/isometric_0027.png");
        }
    }
}