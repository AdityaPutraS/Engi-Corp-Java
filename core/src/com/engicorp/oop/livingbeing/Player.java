package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.generik.Tas;
import com.engicorp.oop.misc.*;
import com.engicorp.oop.product.Product;

public class Player extends LivingBeing {
    private int water;
    private int money;
    private Tas<Product> tas;
    private static Player instance = new Player(new Point(0,0), 100, 0);

    public Player() {
        super(new Point(0, 0), "player/Man.png", 32, 48);
        setPos(new Point(0,0, 1));
        setOffsetPos(new Point(0.5, 0.5, 0));
        tas = new Tas<Product>();
        water = 100;
        money = 0;
    }

    public Player(Point _pos, int _water, int _money) {
        super(_pos, "player/Man.png", 32, 48);
        tas = new Tas<Product>();
        setPos(new Point(_pos.x,pos.y, 1));
        setOffsetPos(new Point(1.25, 0.25, 1));
        water = _water;
        money = _money;
    }

    public int getWater() {
        return (water);
    }

    public int getMoney() {
        return (money);
    }

    public void setWater(int _water) {
        water = _water;
    }

    public void setMoney(int _money) {
        money = _money;
    }

    public static Player getInstance() {
        return instance;
    }

    public void kill(Animal animalia) {
        animalia.die(false);
        World.getInstance().hapusAnimal(animalia);
    }

    public Tas<Product> getTas() {
        return tas;
    }
}
