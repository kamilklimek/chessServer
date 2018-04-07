package com.chess.server.comparators;

import com.chess.server.models.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {
    public int compare(Point o1, Point o2) {
        int point1_x = o1.getPositionX();
        int point2_x = o2.getPositionX();

        int point1_y = o1.getPositionY();
        int point2_y = o2.getPositionY();

        if(point1_y < point2_y){
            return -1;
        }else if(point2_y < point1_y){
            return 1;
        }else if(point1_y == point2_y && point1_x < point2_x){
            return -1;
        }else if(point1_y == point2_y && point1_x > point2_x){
            return 1;
        }else{
            return 0;
        }


    }
}
