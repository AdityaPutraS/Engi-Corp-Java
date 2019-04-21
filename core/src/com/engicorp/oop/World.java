package com.engicorp.oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.engicorp.oop.cell.*;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.livingbeing.*;
import com.engicorp.oop.misc.Point;
import com.engicorp.oop.product.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class World {
    static private World instance;
    private int width, height;
    private boolean[][] terisi;
    private Land[][] mapLand;
    private ArrayList<Animal> listAnimal;
    private ArrayList<Facility> listFacil;
    private ArrayList<String> listMsg;

    public World()
    {
        width = 0;
        height = 0;
    }

    public World(int _w, int _h)
    {
        width = _w;
        height = _h;
        terisi = new boolean[_h][_w];
        mapLand = new Land[_h][_w];
        //Default : Grassland
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapLand[y][x] = new Grassland(new Point(x, y),false);
            }
        }
        listAnimal = new ArrayList<Animal>();
        listFacil = new ArrayList<Facility>();
        listMsg  = new ArrayList<String>();
    }

    //FUNGSI DEBUG
    public void printWorld()
    {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(mapLand[y][x].hasGrass())
                {
                    System.out.print("V ");
                }else{
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    //////////////

    public void disposeAll()
    {
        for(Animal a : listAnimal)
        {
            a.dispose();
        }
        for(Facility f : listFacil)
        {
            f.dispose();
        }
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public static World getInstance()
    {
        return instance;
    }

    static public void init(int _w, int _h)
    {
        Showable.setWidth(_w);
        Showable.setHeight(_h);
        Showable.setTileSize(100);
        instance = new World(_w, _h);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                instance.setLand(new Point(x,y), new Barn(new Point(x,y),false));
            }
        }

        for (int y = 8; y < 11; y++) {
            for (int x = 0; x < 3; x++) {
                instance.setLand(new Point(x,y), new Coop(new Point(x,y),false));
            }
        }
        instance.setLand(new Point(0,0), new Barn(new Point(0,0), false));
        instance.setLand(new Point(_w-1, _h-1), new Grassland(new Point(_w-1, _h-1), false));
        instance.setLand(new Point(_w-1, 0), new Coop(new Point(_w-1,0), false));
        Mixer.initCatalog();
        instance.addAnimal(new Point(1,0), new Chicken(new Point(1,0)));
        instance.addAnimal(new Point(3,3), new Diplodocus(new Point(3,3)));
        instance.addAnimal(new Point(1,9), new Platypus(new Point(1,7)));
        instance.addAnimal(new Point(6,7), new Lamb(new Point(6,7)));
        instance.addAnimal(new Point(0,3), new LandSalmon(new Point(0,3)));
        instance.addAnimal(new Point(7,8), new Cow(new Point(7,8)));
        instance.addFacility(new Point(7, 10), new Well(new Point(7, 10)));
        instance.addFacility(new Point(6, 10), new Truck(new Point(6, 10)));
        instance.addFacility(new Point(5, 10), new Mixer(new Point(5, 10)));
        try {
            Player.getInstance().getTas().addBarang(new ChickenEgg());
            Player.getInstance().getTas().addBarang(new CowMeat());
            Player.getInstance().getTas().addBarang(new DiplodocusEgg());
            Player.getInstance().getTas().addBarang(new DiplodocusMeat());
            Player.getInstance().getTas().addBarang(new CowMilk());
            Player.getInstance().getTas().addBarang(new ChickenEgg());
            Player.getInstance().getTas().addBarang(new CowMeat());
            Player.getInstance().getTas().addBarang(new DiplodocusEgg());
            Player.getInstance().getTas().addBarang(new DiplodocusMeat());
            Player.getInstance().getTas().addBarang(new CowMilk());
            Player.getInstance().getTas().addBarang(new ChickenEgg());
            Player.getInstance().getTas().addBarang(new CowMeat());
            Player.getInstance().getTas().addBarang(new DiplodocusEgg());
            Player.getInstance().getTas().addBarang(new DiplodocusMeat());
            Player.getInstance().getTas().addBarang(new CowMilk());
        }catch(TasKepenuhan T)
        {

        }
    }

    public void setLand(Point pos, Land l)
    {
        mapLand[(int)pos.y][(int)pos.x] = l;
    }

    public void addAnimal(Point pos, Animal a)
    {
        a.setPos(pos);
        listAnimal.add(a);
        terisi[(int)pos.y][(int)pos.x] = true;
    }

    public void addFacility(Point pos, Facility f)
    {
        f.setPos(pos);
        listFacil.add(f);
        terisi[(int)pos.y][(int)pos.x] = true;
    }

    public void hapusAnimal(int i)
    {
        Animal a = listAnimal.get(i);
        terisi[(int)a.getPos().y][(int)a.getPos().x] = false;
        listAnimal.remove(i);
    }

    public void hapusAnimal(Animal a)
    {
        int i = 0;
        for(Animal tempA : listAnimal)
        {
            if(a.compareTo(tempA) == 0)
            {
                hapusAnimal(i);
                return;
            }else{
                i++;
            }
        }
    }

    public void hapusFacility(int i)
    {
        Facility a = listFacil.get(i);
        terisi[(int)a.getPos().y][(int)a.getPos().x] = false;
        listFacil.remove(i);
    }

    public Animal getAnimal(Point p)
    {
        for(Animal tempA : listAnimal)
        {
            if(tempA.getPos().compareTo(p) == 0)
            {
                return tempA;
            }
        }
        return null;
    }

    public Facility getFacility(Point p)
    {
        for(Facility tempF : listFacil)
        {
            if(tempF.getPos().compareTo(p) == 0)
            {
                return tempF;
            }
        }
        return null;
    }


    public void addMsg(String s)
    {
        if(listMsg.size() >= 20)
        {
            listMsg.remove(0);
        }
        if(s.length() > 47)
        {
            s = s.substring(0, 47);
            System.out.println(s);
        }
        listMsg.add(s);
        UI.getInstance().updateTableMsg(listMsg);
    }

    public Land getLand(Point pos)
    {
        return mapLand[(int)pos.y][(int)pos.x];
    }

    public void setTerisi(Point pos, boolean isi)
    {
        terisi[(int)pos.y][(int)pos.x] = isi;
    }

    public boolean isTerisi(Point pos)
    {
        return terisi[(int)pos.y][(int)pos.x];
    }

    public boolean isValidPoint(Point pos)
    {
        return (pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height);
    }
    //Harus dipanggil diantara batch begin dan batch end
    public void renderAll(SpriteBatch batch)
    {
        double xPosReal, yPosReal, zPosReal;
        //Gambar map
        for(int y = height-1; y >= 0; y--)
        {
            for(int x = width-1; x >= 0; x--)
            {
                mapLand[y][x].setPos(new Point(x, y, 0));
                mapLand[y][x].render(batch);
            }
        }
        //Gambar facility
        for(Facility f :listFacil)
        {
            f.render(batch);
        }
        //Gambar animal
        //Sort animal berdasar jarak
        Comparator<Animal> compareByJarak = new Comparator<Animal>()
        {
            @Override
            public int compare(Animal a1, Animal a2)
            {
                return a1.getPos().compareTo(a2.getPos());
            }
        };
        Collections.sort(listAnimal, compareByJarak);
        for(Animal a : listAnimal)
        {
            a.animate(batch);
        }
        //Gambar player
        Player.getInstance().animate(batch);
        //System.out.println(Player.getInstance().getPos().x +", " + Player.getInstance().getPos().y);
    }

    public void updateAll()
    {
        //Cek apakah ada yang mati
        ArrayList<Point> mati = new ArrayList<Point>();
        for(Animal a : listAnimal)
        {
            if(a.getHungerMeter() <= -5)
            {
                mati.add(a.getPos());
                a.die(true);
            }
        }
        for(Point p : mati)
        {
           hapusAnimal(getAnimal(p));
        }
        //Update kelaparan & gerak semua
        for(Animal a : listAnimal)
        {
            a.setHungerMeter(a.getHungerMeter()-1);
//            System.out.println("Ayam : "+a.getPos().x+", "+a.getPos().y);
            a.moveRandom();
//            System.out.println("AyamMove : "+a.getPos().x+", "+a.getPos().y);
        }
        //Cek apakah ada yang kelaparan & makan rumput jika ada
        for(Animal a : listAnimal)
        {
            if(a.getHungerMeter() <= 0)
            {
                if(getLand(a.getPos()).hasGrass())
                {
                    a.eat();
                    getLand(a.getPos()).setGrass(false);
                }
            }
            if(a.getHungerMeter() <= 0)
            {
//                    System.out.println("Animal lapar");
//                    System.out.println(a.getHungerMeter());
                a.setAnimatedHungry(true);
                a.setDefaultJumpInPlace();
            }else if(a.getHungerMeter() > 0)
            {
                a.setAnimatedHungry(false);
                a.setDefaultJump();
            }

        }
    }
}
