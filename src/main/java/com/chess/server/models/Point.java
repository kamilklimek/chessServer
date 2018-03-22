package com.chess.server.models;

/**
 * Created by kamil.klimek on 20.03.2018.
 */
public class Point {

    private Integer positionX;
    private Integer positionY;

    public Point(Integer x, Integer y){
        this.positionX = x;
        this.positionY = y;
    }

    public Integer getPositionX(){
        return positionX;
    }

    public Integer getPositionY(){
        return positionY;
    }

    public Integer[] getPosition(){
        return new Integer[]{positionX, positionY};
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (positionX != null ? !positionX.equals(point.positionX) : point.positionX != null) return false;
        return positionY != null ? positionY.equals(point.positionY) : point.positionY == null;
    }

    @Override
    public int hashCode() {
        int result = positionX != null ? positionX.hashCode() : 0;
        result = 31 * result + (positionY != null ? positionY.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}
