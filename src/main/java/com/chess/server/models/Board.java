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

    private void initGameFromFile(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            for(int i=0;i<8;i++){
                line = bufferedReader.readLine();

                for(int j=0;j<line.length();j++){
                    boolean isRook = line.charAt(j) == 'R';
                    boolean isKnigt = line.charAt(j) == 'N';
                    boolean isQueen = line.charAt(j) == 'Q';
                    boolean isKing = line.charAt(j) == 'K';
                    boolean isPawn = line.charAt(j) == 'P';
                    boolean isEmpty = line.charAt(j) == 'X';
                    boolean isBishop = line.charAt(j) == 'B';

                    if(isBishop){
                        boardFields[i][j] = new Bishop(new Point(j, i));
                    }else if(isRook){
                        boardFields[i][j] = new Rook(new Point(j, i));
                    }else if(isKing){
                        boardFields[i][j] = new King(new Point(j, i));
                    }else if(isPawn){
                        boardFields[i][j] = new Pawn(new Point(j, i));
                    }else if(isQueen){
                        boardFields[i][j] = new Queen(new Point(j, i));
                    }else if(isKnigt){
                        boardFields[i][j] = new Knight(new Point(j, i));
                    }else if(isEmpty){
                        boardFields[i][j] = new Figure(new Point(j, i), "EMPTY");
                    }


                }

            }

            bufferedReader.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void display(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean isRook = boardFields[i][j].getTypeOfFigure() == "ROOK";
                boolean isKnigt = boardFields[i][j].getTypeOfFigure() == "KNIGHT";
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
                }else if(isKnigt){
                    System.out.print("N");
                }else if(isEmpty) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }
}
