package com.engicorp.oop.product;

public abstract class Product implements Comparable<Product>{
    protected int price;
    protected String name;

    public Product(int _price, String _name){
        price = _price;
        name = _name;
    }
    public int getPrice(){
        return price;
    }
    public void setPrice(int _price){
        price = _price;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String _name){
        name = _name;
    }

    public int compareTo(Product P){
        //nama sama otomatis harga sama, nama beda baru dicompare
        if(name==P.getName()){
            return 0;
        }else{
            if(price>P.getPrice()){
                return 1;
            }else{
                return -1;
            }
        }
    }
    
}