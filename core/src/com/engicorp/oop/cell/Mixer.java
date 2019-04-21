package com.engicorp.oop.cell;

import java.util.*;

import com.engicorp.oop.MixerUI;
import com.engicorp.oop.product.*;
import com.engicorp.oop.misc.Point;

public class Mixer extends Facility
{
    static ArrayList<SideProduct> catalog = new ArrayList<SideProduct>();
    
    public Mixer(Point pos)
    {
        super(pos, "facility/mixer.png");
    }

    static public ArrayDeque<SideProduct> mix(Vector<Product> bahan)
    {
        ArrayDeque<SideProduct> hasil = new ArrayDeque<SideProduct>();
        for(SideProduct sp : catalog)
        {
            if(sp.canMake(bahan))
            {
                hasil.push(sp);
            }
        }
        return hasil;
    }

    static public void initCatalog()
    {
        catalog.add(new BeefRolade());
        catalog.add(new BlueFeather());
        catalog.add(new CheeseSteakOmelette());
        catalog.add(new CremeBrulee());
        catalog.add(new Lasagna());
        catalog.add(new STMJ());
    }

    @Override
    public void interact()
    {
        MixerUI.getInstance().setShow(true);

    }

    @Override
    public boolean isMixer()
    {
        return true;
    }
}