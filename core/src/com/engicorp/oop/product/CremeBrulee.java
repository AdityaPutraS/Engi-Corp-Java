package com.engicorp.oop.product;

public class CremeBrulee extends SideProduct{
    public CremeBrulee(){
        super(1000, "CremeBrulee");
        //harga bisa diganti sesuai sama .cpp nya
        addIngredients(new LambMilk());
        addIngredients(new PlatypusEgg());
        
    }
}