package com.engicorp.oop.cell;


import com.engicorp.oop.misc.Point;

public class Barn extends Land {
    public Barn(Point _pos, Boolean _grass) {
        super(_pos, "tile/100x100/isometric_0072.png", _grass);
        if (_grass){
            this.setTexPath("tile/grass/barn_grass_1.png");
        }
    }

    @Override
    public boolean isBarn() {
        return true;
    }

    @Override
    public void setGrass(boolean hasGrass) {
        this.grass = hasGrass;
        if (hasGrass) {
            this.setTexPath("tile/grass/barn_grass_1.png");
        }else{
            this.setTexPath("tile/100x100/isometric_0072.png");
        }
    }
}