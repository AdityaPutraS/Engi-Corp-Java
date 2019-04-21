package com.engicorp.oop.product;

public class CheeseSteakOmelette extends SideProduct{
    public CheeseSteakOmelette(){
        super(1000, "CheeseSteakOmelette");
        //harga bisa diganti sesuai sama .cpp nya
        DiplodocusEgg DE = new DiplodocusEgg();
        DiplodocusMeat DM = new DiplodocusMeat();
        addIngredients(DE);
        addIngredients(DM);
        
    }
}