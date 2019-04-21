package com.engicorp.oop.product;

public class STMJ extends SideProduct{
    public STMJ(){
        super(1000, "STMJ");
        //harga bisa diganti sesuai sama .cpp nya
        addIngredients(new ChickenEgg());
        addIngredients(new CowMilk());
        
    }
}