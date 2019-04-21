package com.engicorp.oop.product;

public class BeefRolade extends SideProduct{
    public BeefRolade(){
        super(1000, "BeefRolade");
        //harga bisa diganti sesuai sama .cpp nya
        ChickenEgg CE = new ChickenEgg();
        CowMeat CM = new CowMeat();
        addIngredients(CE);
        addIngredients(CM);
    }
}