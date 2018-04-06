package com.chess.server.models.figures;


import com.chess.server.models.Figure;
import com.chess.server.models.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Figure {

    private boolean isWhite;

    public Pawn(Point point, boolean isWhite) {
        super(point, "PAWN");
        this.isWhite = isWhite;
    }

    @Override
    public List<Point> calculatePawnMovements(){
        List<Point> movements = new ArrayList<Point>();

        int pawnPositionX = position.getPositionX();
        int pawnPositionY = position.getPositionY();

        List<Point> mask = new ArrayList<>(Arrays.asList(
                new Point(0,1),
                new Point(0,2),
                new Point(1,1),
                new Point(-1,1)
        ));

        //jezeli pionek ruszyl sie ze swojej pozycji to usun z maski mozliwosc ruchu o 2
        if((pawnPositionY > 2 && isWhite) || (!isWhite && pawnPositionX < 6)){
            mask.remove(new Point(0,2));
        }

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                if(isWhite){

                    for(Point point : mask){
                        boolean maskX = pawnPositionX + point.getPositionX() == j;
                        boolean maskY = pawnPositionY + point.getPositionY() == i;

                        if(maskX && maskY){
                            movements.add(new Point(j,i));
                        }
                    }

                }else{
                    for(Point point : mask){
                        boolean maskX = pawnPositionX + point.getPositionX() == j;
                        boolean maskY = pawnPositionY - point.getPositionY() == i;

                        if(maskX && maskY){
                            movements.add(new Point(j,i));
                        }
                    }
                }


            }
        }


        return movements;
    }
}
