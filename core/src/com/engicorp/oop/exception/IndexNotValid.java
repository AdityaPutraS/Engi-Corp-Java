package com.engicorp.oop.exception;

public class IndexNotValid extends Exception {
    private int idx;
    public IndexNotValid(int _idx)
    {
        idx = _idx;
    }
    public String toString()
    {
        return "Index : "+idx+" is not valid";
    }
}
