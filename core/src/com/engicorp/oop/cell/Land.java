package com.engicorp.oop.cell;

import com.engicorp.oop.misc.Point;
import com.engicorp.oop.cell.Cell;

public class Land extends Cell{
    protected boolean grass;

    /**
     * \brief Konstruktor berparameter Land
     * \param _pos Posisi dari Land yang ingin dibentuk
     * \param _renderChar Karakter yang nanti akan dirender
     * \param _grass Apakah Land yang akan dibentuk mempunyai rumput
     */
    public Land(Point _pos, String texPath, boolean _grass){
        super(_pos, texPath);
        grass = _grass;
    }

    /**
     * \brief Mengembalikan nilai true jika Land yang sedang ditunjuk
     * mempunyai rumput
     */
    public final boolean hasGrass(){
        return grass;
    }

    /**
     * \brief Mengubah nilai grass
     * \param _grass true atau false
     */
    public void setGrass(boolean _grass){
        grass = _grass;
    }
}