package com.engicorp.oop.generik;

import com.engicorp.oop.exception.IndexNotValid;
import com.engicorp.oop.exception.ItemNotExists;
import com.engicorp.oop.exception.TasKepenuhan;
import com.engicorp.oop.product.Product;


import java.util.LinkedList;
import java.util.Iterator;

public class Tas<T extends Product> implements Iterable<T> {
    private LinkedList<T> backend;
    private int totalHarga;

    public Tas()
    {
        backend = new LinkedList<T>();
        totalHarga = 0;
    }

    public void addBarang(T b) throws TasKepenuhan
    {
        if(backend.size() < 20) {
            backend.add(b);
            totalHarga += b.getPrice();
        }else{
            throw new TasKepenuhan();
        }
    }

    public void removeBarang(int idx) throws IndexNotValid
    {
        if(idx < 0 || idx >= backend.size())
        {
            throw new IndexNotValid(idx);
        }else {
            totalHarga -= backend.get(idx).getPrice();
            backend.remove(idx);
        }
    }

    public void removeBarang(T b) throws ItemNotExists
    {
        try{
            backend.remove(searchBarang(b));
        }catch (ItemNotExists i)
        {
            throw new ItemNotExists(b.getName());
        }
    }

    public T getBarang(int idx) throws IndexNotValid
    {
        if(idx < 0 || idx >= backend.size())
        {
            throw new IndexNotValid(idx);
        }else {
            return backend.get(idx);
        }
    }

    public T getBarang(T b) throws ItemNotExists
    {
        try{
            return backend.get(searchBarang(b));
        }catch (ItemNotExists i)
        {
            throw new ItemNotExists(b.getName());
        }
    }

    public int searchBarang(T b) throws ItemNotExists
    {
        int index;
        boolean ketemu = false;
        for (index = 0; index < backend.size() && !ketemu; index++) {
            if(backend.get(index).compareTo(b) == 0)
            {
                ketemu = true;
            }
        }
        if(ketemu)
        {
            return index;
        }else{
            throw new ItemNotExists(b.getName());
        }
    }

    public int getTotalHarga()
    {
        return totalHarga;
    }

    public void removeAll()
    {
        backend.clear();
        totalHarga = 0;
    }

    public int getSize()
    {
        return backend.size();
    }
    @Override
    public Iterator<T> iterator()
    {
        return this.backend.iterator();
    }



}
