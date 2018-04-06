package com.chess.server.models;

import com.chess.server.models.figures.*;

import java.io.*;

public class Board {

    private Figure [][]boardFields;
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
    public void displayAllFigures(){

        System.out.println("All figures: ");
        for(Figure[] figures : boardFields){
            for(Figure figure : figures){
                System.out.println(figure.toString());
            }
        }


    }

    private void initGameFromFile(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            for(int i=0;i<8;i++){
                line = bufferedReader.readLine();

                for(int j=0;j<line.length();j++){
                    boolean isRook = line.charAt(j) == 'R';
                    boolean isKnight = line.charAt(j) == 'N';
                    boolean isQueen = line.charAt(j) == 'Q';
                    boolean isKing = line.charAt(j) == 'K';
                    boolean isPawn = line.charAt(j) == 'P';
                    boolean isEmpty = line.charAt(j) == 'X';
                    boolean isBishop = line.charAt(j) == 'B';

                    boolean isWhite = true;
                    if(i>4)
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

    public void moveFigure(Point from, Point to){
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();

        int toX = to.getPositionX();
        int toY = to.getPositionY();

        boardFields[toY][toX] = boardFields[fromY][fromX];
        boardFields[fromY][fromX] = new Figure(from, "EMPTY");


    }

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
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }
}
