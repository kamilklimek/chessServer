package com.chess.server.game;

import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private Figure[][]boardFields;
    private String nameFile;
    private ChessAvailableMovements chessAvailableMovements;
    private ChessCheckMate chessCheckMate;


    public Board() {
        nameFile = "boards/standard.game";
        init();
    }

    public Board(String fileName) {
        nameFile = fileName;
        init();
    }

    private void init(){
        boardFields = new Figure[8][8];
        chessAvailableMovements = new ChessAvailableMovements(boardFields);
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
        return chessAvailableMovements.getAvailableMovements(from).contains(to);
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
