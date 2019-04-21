package com.engicorp.oop.misc;

import com.engicorp.oop.misc.Point;

public interface UpdatePosition {
    Point update(Point before, float dT, float epsTime, float animTime);
}
