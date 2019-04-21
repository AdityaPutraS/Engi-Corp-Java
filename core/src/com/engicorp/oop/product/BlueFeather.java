package com.engicorp.oop.product;

public class BlueFeather extends SideProduct{
    public BlueFeather(){
        super(1000, "BlueFeather");
        //harga bisa diganti sesuai sama .cpp nya
        ChickenEgg CE = new ChickenEgg();
        CowMeat CM = new CowMeat();
        ChickenMeat CMe = new ChickenMeat();
        CowMilk CM1 = new CowMilk();
        DiplodocusEgg DE = new DiplodocusEgg();
        DiplodocusMeat DM = new DiplodocusMeat();
        LambMeat LM = new LambMeat();
        LambMilk LM1 = new LambMilk();
        PlatypusEgg PE = new PlatypusEgg();
        SalmonEgg SE = new SalmonEgg();
        SalmonMeat SM = new SalmonMeat();
        addIngredients(CE);
        addIngredients(CMe);
        addIngredients(CM);
        addIngredients(CM1);
        addIngredients(DE);
        addIngredients(DM);
        addIngredients(LM);
        addIngredients(LM1);
        addIngredients(PE);
        addIngredients(SE);
        addIngredients(SM);

    }
}