package com.engicorp.oop.exception;

public class ItemNotExists extends Exception {
    private String nama;

    public ItemNotExists(String namaBarang)
    {
        nama = namaBarang;
    }

    public String toString()
    {
        return "Item : "+nama+" does not exists";
    }
}
