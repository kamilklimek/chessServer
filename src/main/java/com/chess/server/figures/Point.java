package com.chess.server.figures;

import java.util.Objects;

/**
 * Created by kamil.klimek on 20.03.2018.
 */
public class Point {

    private int positionX;
    private int positionY;

    public Point(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public int[] getPosition(){
        return new int[]{positionX, positionY};
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return positionX == point.positionX &&
                positionY == point.positionY;
    }

    @Override
    public int hashCode() {

        return Objects.hash(positionX, positionY);
    }



    @Override
    public String toString() {
        return "[" + positionX + ","  + positionY +
                "]";
    }
}
