package com.engicorp.oop.product;

public class Lasagna extends SideProduct{
    public Lasagna(){
        super(1000, "Lasagna");
        //harga bisa diganti sesuai sama .cpp nya
        addIngredients(new LambMilk());
        addIngredients(new LambMeat());
        
    }
}