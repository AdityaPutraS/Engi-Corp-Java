package com.engicorp.oop.product;

import java.util.Vector;

public abstract class SideProduct extends Product{
    protected Vector<FarmProduct> ingredients;
    public SideProduct(int _price, String _name){
        super(_price, _name);
        ingredients = new Vector<FarmProduct>();
    }

    public void setIngredients(Vector<FarmProduct> FP)
    {
        ingredients = FP;
    }

    public void addIngredients(FarmProduct FP)
    {
        ingredients.add(FP);
    }
    public Vector<FarmProduct> getIngredients(){
        return ingredients;
    }

    public boolean canMake(Vector<Product> bahan){
        boolean sukses = true;
        boolean[] terpakai = new boolean[bahan.size()];   //All false
        //Cek apakah semua ingredients ada di bahan
        for(FarmProduct fp : ingredients)
        {
            boolean test = false;
            for(int i = 0; i < bahan.size(); i++)
            {
                if(!terpakai[i])
                {
                    if(bahan.get(i).compareTo(fp) == 0)
                    {
                        terpakai[i] = true;
                        test = true;
                    }
                }
            }
            if(!test)
            {
                sukses = false;
            }
        }
        return sukses;
    }
}