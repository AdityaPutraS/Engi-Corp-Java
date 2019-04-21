package com.engicorp.oop.livingbeing;

import com.engicorp.oop.World;
import com.engicorp.oop.misc.Point;

import java.util.ArrayList;
import java.util.Random;

public abstract class Animal extends LivingBeing implements Comparable<Animal>
{

    protected int maxHunger;
    protected int hungerMeter;
    protected String animalSound;
    protected boolean isAlive;
    protected AbleToMoveThere atmt;
    protected boolean canInteract;

    public Animal(Point _pos, String _sound, String texPath, int _maxHunger, int wTile, int hTile)
    {
        super(_pos, texPath, wTile, hTile);
        maxHunger = _maxHunger;
        hungerMeter = _maxHunger;
        canInteract = true;
        animalSound = _sound;
    }

    public int getHungerMeter()
    {
        return hungerMeter;
    }

    public int getMaxHunger()
    {
        return maxHunger;
    }

    public String getAnimalSound() {
        return animalSound;
    }

    public void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;
    }

    public void setHungerMeter(int hungerMeter) {
        this.hungerMeter = hungerMeter;
    }

    public void setAnimalSound(String animalSound) {
        this.animalSound = animalSound;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void eat() {
        this.hungerMeter = this.maxHunger;
        canInteract = true;
    }

    public int compareTo(Animal a)
    {
        if(pos.compareTo(a.pos) == 0 && animalSound == a.animalSound)
        {
            return 0;
        }else{
            return pos.compareTo(a.pos);
        }
    }

    public void setAtmt(AbleToMoveThere atmt) {
        this.atmt = atmt;
    }

    static public Point createPointTujuan(Point awal, int dir)
    {
        Point cek = new Point(awal.x, awal.y, awal.z);
        switch (dir) {
            case 0:
                cek.x += 1;
                break;
            case 1:
                cek.x += -1;
                break;
            case 2:
                cek.y += 1;
                break;
            case 3:
                cek.y += -1;
                break;
        }
        return cek;
    }

    public boolean moveRandom()
    {
        int random = 4;
        Point testPos = new Point(pos.x, pos.y);
        ArrayList<Integer> possibleDir = new ArrayList<Integer>();
        possibleDir.add(0); //atas
        possibleDir.add(1); //bawah
        possibleDir.add(2); //kiri
        possibleDir.add(3); //kanan

        Random rand = new Random();
        while(possibleDir.size() > 0 && random == 4)
        {
            //Ambil 1 arah dari listDir yang mungkin
            int i = rand.nextInt(possibleDir.size());
//            System.out.println("Dapat : "+i);
            //Cek apakah bisa
            testPos = Animal.createPointTujuan(pos, possibleDir.get(i));
            if(World.getInstance().isValidPoint(testPos) && !World.getInstance().isTerisi(testPos))
            {
                if(atmt.canGoTo(testPos))
                {
                    random = i;
                }
            }
            possibleDir.remove(i);
        }
        if(random != 4)
        {
            this.move(random);
        }
        return false;
    }

    public void move(int _dir)
    {
        if(_dir != 4) {
            this.setDir(_dir);
            Point posBaru = getPointDir();
            System.out.println("Pos Baru Ayam : "+posBaru.x +", "+posBaru.y);
            this.setLenMove(1);

            this.setDefaultJump();

            if (!this.isAnimating()) {
                if (World.getInstance().isValidPoint(posBaru)) {
                    //System.out.println("Cek terisi : "+posBaru.x+", "+posBaru.y);
                    if (!World.getInstance().isTerisi(posBaru) && atmt.canGoTo(posBaru)) {
                        World.getInstance().setTerisi(this.getPos(), false);
                        World.getInstance().setTerisi(posBaru, true);
                        this.startAnimate(posBaru);
                    }
                }
            }
        }else{
            this.setLenMove(1);
            this.setDefaultJumpInPlace();
        }
    }

    public abstract void die(boolean diedOfHunger);
    public abstract void interact();
}