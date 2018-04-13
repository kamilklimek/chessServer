package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.*;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private Figure[][]boardFields;
    private String nameFile;


    public Board() {
        boardFields = new Figure[8][8];
        nameFile = "boards/standard.game";
        initGameFromFile(nameFile);
    }

    public Board(String fileName) {
        boardFields = new Figure[8][8];
        nameFile = fileName;
        initGameFromFile(nameFile);
    }

    private void initGameFromFile(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            for(int i=0;i<8;i++){
                line = bufferedReader.readLine();

                for(int j=0;j<8;j++){
                    boolean isRook = Character.toUpperCase(line.charAt(j)) == 'R';
                    boolean isKnight = Character.toUpperCase(line.charAt(j)) == 'N';
                    boolean isQueen = Character.toUpperCase(line.charAt(j)) == 'Q';
                    boolean isKing = Character.toUpperCase(line.charAt(j)) == 'K';
                    boolean isPawn = Character.toUpperCase(line.charAt(j)) == 'P';
                    boolean isEmpty = Character.toUpperCase(line.charAt(j)) == 'X';
                    boolean isBishop = Character.toUpperCase(line.charAt(j)) == 'B';

                    boolean isWhite = true;

                    if(Character.isLowerCase(line.charAt(j)))
                        isWhite = true;
                    else
                        isWhite = false;

                    if(isBishop){
                        boardFields[i][j] = new Bishop(new Point(j, i), isWhite);
                    }else if(isRook){
                        boardFields[i][j] = new Rook(new Point(j, i), isWhite);
                    }else if(isKing){
                        boardFields[i][j] = new King(new Point(j, i), isWhite);
                    }else if(isPawn){

                        boardFields[i][j] = new Pawn(new Point(j, i), isWhite);
                    }else if(isQueen){
                        boardFields[i][j] = new Queen(new Point(j, i), isWhite);
                    }else if(isKnight){
                        boardFields[i][j] = new Knight(new Point(j, i), isWhite);
                    }else if(isEmpty){
                        boardFields[i][j] = new Figure(new Point(j, i), "EMPTY");
                    }


                }

            }

            bufferedReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("File with board does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * function to move figures
     * @param from position from wants move figure
     * @param to destinatin
     * @return 1 - figure is beaten up,
     *         0 - moved without beaten up
     *         -1 - can't move to destination point
     */

    public int moveFigure(Point from, Point to){
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();

        int toX = to.getPositionX();
        int toY = to.getPositionY();

       boolean canMove = canMove(from, to);

        if(canMove){
            boolean figureIsBeatenUp = boardFields[toY][toX].getTypeOfFigure() != "EMPTY";

            boardFields[toY][toX] = boardFields[fromY][fromX];
            boardFields[fromY][fromX] = new Figure(from, "EMPTY");
            return figureIsBeatenUp ? 1 : 0;
        }

        return -1;
    }

    /**
     * Function calculate that figures can be moved from to another position
     * @param from position from figures would be move
     * @param to position figure destination
     * @return true if can move
     */

    public boolean canMove(Point from, Point to) {
        return getAvailableMovements(from).contains(to);
    }

    public List<Figure> getAllWhiteFigures(){
        return getAllFiguresByColor(true);
    }

    public List<Figure> getAllBlackFigures(){
        return getAllFiguresByColor(false);
    }

    private List<Figure> getAllFiguresByColor(boolean isWhite){
        List<Figure> figures = new LinkedList<>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean isTheSameColor = boardFields[j][i].isWhite() == isWhite;
                boolean notEmpty = boardFields[j][i].getTypeOfFigure() != "EMPTY";
                if(isTheSameColor && notEmpty)
                    figures.add(boardFields[j][i]);
            }
        }

        return figures;
    }

    /**
     * Function calculate (with exclusion points) available point
     * where figure can move
     *
     * @param point - calculate movements for arg point
     * @return list of available movements
     */

    public List<Point> getAvailableMovements(Point point) {

        int positionX = point.getPositionX();
        int positionY = point.getPositionY();

        Figure figure = boardFields[positionY][positionX];
        boolean figureIsWhite = figure.isWhite();
        boolean figureIsKing = figure.getTypeOfFigure() == "KING";
        boolean figureIsKnight = figure.getTypeOfFigure() == "KNIGHT";
        boolean figureIsPawn = figure.getTypeOfFigure() == "PAWN";

        List<Point> pseudoMovements = figure.getAvailableMovements();

        List<Point> allFiguresInPseudoMovements = getAllFiguresFromPointList(pseudoMovements);
        List<Point> movementsWithAllyFigures = pseudoMovements;

        if(!(figureIsKing || figureIsKnight)){
            movementsWithAllyFigures = exclusiveUnAvailablePoint(figure, pseudoMovements, allFiguresInPseudoMovements);
        }

        List<Point> availableMovements = exclusiveAllAllyFigures(figureIsWhite, movementsWithAllyFigures);

        if(figureIsPawn){
            availableMovements = exclusiveBeatenUp(figure, availableMovements);
        }

        return availableMovements;
    }

    private List<Point> exclusiveBeatenUp(Figure figure, List<Point> availableMovements) {
        List<Point> resultPoints = availableMovements;
        List<Point> pawnBeatenUpMask = new LinkedList<>(Arrays.asList(
                new Point(-1, 1),
                new Point(1, 1)
        ));

        boolean figureIsBlack = !figure.isWhite();

        if(figureIsBlack){
            for (Point p :pawnBeatenUpMask
                 ) {
                p.setPositionY(p.getPositionY()*-1);
            }
        }

        for(Point mask : pawnBeatenUpMask){
            int positionX = mask.getPositionX() + figure.getPosition().getPositionX();
            int positionY = mask.getPositionY() + figure.getPosition().getPositionY();

            boolean positionHigherThanZero = positionX >= 0 && positionX <= 7 && positionY >= 0 && positionY <=7;
            boolean maskPointIsEmpty = positionHigherThanZero && boardFields[positionY][positionX].getTypeOfFigure() == "EMPTY";
            boolean listContainsPointMask = positionHigherThanZero && resultPoints.contains(new Point(positionX, positionY));

            if(maskPointIsEmpty && listContainsPointMask){
                resultPoints.remove(new Point(positionX, positionY));
            }

        }
        return resultPoints;
    }

    private List<Point> exclusiveAllAllyFigures(boolean figureIsWhite, List<Point> movementsWithAllyFigures) {

        for(Iterator<Point> it = movementsWithAllyFigures.iterator(); it.hasNext();){
            Point p = (Point) it.next();

            int pX = p.getPositionX();
            int pY = p.getPositionY();

            Figure checkingFigure = boardFields[pY][pX];
            boolean isNotEmpty = checkingFigure.getTypeOfFigure() != "EMPTY";
            boolean isTheSameColor = checkingFigure.isWhite() == figureIsWhite;

            if(isNotEmpty && isTheSameColor){
                it.remove();
            }


        }

        return movementsWithAllyFigures;
    }

    private List<Point> exclusiveUnAvailablePoint(Figure figure, List<Point> pseudoMovements, List<Point> allFiguresInPseudoMovements) {
        PointComparator pointComparator = new PointComparator();

        for (Iterator<Point> it = pseudoMovements.listIterator(); it.hasNext(); ) {
            Point p = (Point) it.next();
            int directionFromFigureToPoint = getDirection(figure.getPosition(), p);

            for (Point pointsFigures : allFiguresInPseudoMovements) {
                int directionFromFigureToFigure = getDirection(figure.getPosition(), pointsFigures);

                boolean pointsAreOnTheSameDirection = directionFromFigureToFigure == directionFromFigureToPoint;


                if (pointsAreOnTheSameDirection) {

                    boolean pointIsFromHigherHalf = directionFromFigureToPoint >= 1 && directionFromFigureToFigure <= 4;
                    boolean pointIsFromLowerHalf = directionFromFigureToFigure >= 5 && directionFromFigureToFigure <= 8;

                    boolean pointIsLowerThanFigure = pointComparator.compare(p, pointsFigures) < 0;
                    boolean pointIsHigherThanFigure = pointComparator.compare(p, pointsFigures) > 0;

                    if (pointIsFromHigherHalf && pointIsLowerThanFigure) {
                        it.remove();
                        break;
                    } else if ( pointIsFromLowerHalf && pointIsHigherThanFigure) {
                        it.remove();
                        break;
                    }

                }
            }
        }

        return pseudoMovements;
    }

    private List<Point> getAllFiguresFromPointList(List<Point> pseudoMovements) {
        List<Point> figuresFromPseudoMovements = new LinkedList<>();

        for (Point p : pseudoMovements) {
            int pointX = p.getPositionX();
            int pointY = p.getPositionY();

            boolean figureStandsOnPoint = boardFields[pointY][pointX].getTypeOfFigure() != "EMPTY";

            if (figureStandsOnPoint) {
                figuresFromPseudoMovements.add(p);
            }
        }

        return figuresFromPseudoMovements;
    }

    /**
     * Function returns int which menas direction
     *
     * @param from - coordition from where figures moves
     * @param destination - cordination to where figures moves
     * @return 1 - up left,
     * 2 - up center
     * 3 - up right
     * 4 - left center
     * 5 - right center
     * 6 - bottom left
     * 7 - bottom center
     * 8 - bottom right
     * 0 - undefinded direction
     *
     */

    public int getDirection(Point from, Point destination){
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();

        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();

        //up
        if(fromY > destinationY){

            //left
            if(fromX > destinationX){
                return 1;
            }

            //right
            if(fromX < destinationX){
                return 3;
            }

            //center
            return 2;
        }

        //center

        if(fromY == destinationY){

            //left
            if(fromX > destinationX){
                return 4;
            }

            //right
            return 5;

        }

        //bottom
        if(fromY < destinationY){

            //left
            if(fromX > destinationX){
                return 6;
            }

            //right
            if(fromX < destinationX){
                return 8;
            }

            //center
            return 7;

        }




        return 0;
    }

    /*
    Displays methods
     */

    public void display(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean isRook = boardFields[i][j].getTypeOfFigure() == "ROOK";
                boolean isKnight = boardFields[i][j].getTypeOfFigure() == "KNIGHT";
                boolean isQueen = boardFields[i][j].getTypeOfFigure() == "QUEEN";
                boolean isKing = boardFields[i][j].getTypeOfFigure() == "KING";
                boolean isPawn = boardFields[i][j].getTypeOfFigure() == "PAWN";
                boolean isEmpty = boardFields[i][j].getTypeOfFigure() == "EMPTY";
                boolean isBishop = boardFields[i][j].getTypeOfFigure() == "BISHOP";

                if(isRook){
                    System.out.print("R");
                }else if(isKing){
                    System.out.print("K");
                }else if(isQueen){
                    System.out.print("Q");
                }else if(isBishop){
                    System.out.print("B");
                }else if(isPawn){
                    System.out.print("P");
                }else if(isKnight){
                    System.out.print("N");
                }else if(isEmpty) {
                    System.out.print("X");
                }
            }
            System.out.println();
        }

    }


    public void displayAllFigures(){

        System.out.println("All figures: ");
        for(Figure[] figures : boardFields){
            for(Figure figure : figures){
                System.out.println(figure.toString());
            }
        }


    }

}
