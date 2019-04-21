package com.engicorp.oop.misc;

public class Point implements Comparable<Point> {
    public double x, y, z;

    public Point()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point(double _x, double _y)
    {
        this.x = _x;
        this.y = _y;
        this.z = 0;
    }

    public Point(double _x, double _y, double _z)
    {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public int compareTo(Point p)
    {
        if(x == p.x && y == p.y && z == p.z)
        {
            return 0;
        }else{
            if(Math.sqrt(x*x+y*y+z*z) > Math.sqrt(p.x*p.x+p.y*p.y+p.z*p.z))
            {
                return 1;
            }else{
                return -1;
            }
        }
    }

    public Point add(Point P)
    {
        return new Point(x+P.x , y+P.y, z+P.z);
    }

    public Point sub(Point P)
    {
        return new Point(x-P.x , y-P.y, z-P.z);
    }

    public Point multiply(double i)
    {
        return new Point(i*x, i*y, i*z);
    }

    public double length()
    {
        return Math.sqrt(x*x+y*y+z*z);
    }
}
