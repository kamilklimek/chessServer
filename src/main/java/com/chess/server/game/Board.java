package com.chess.server.game;

import com.chess.server.api.Castle;
import com.chess.server.figures.Figure;
import com.chess.server.figures.Point;
import com.chess.server.comparators.PointComparator;
import com.chess.server.figures.*;

import java.io.*;
import java.util.*;

public class Board {

    private Figure[][]boardFields;
    private String nameFile;
    private ChessAvailableMovements chessAvailableMovements;
    private ChessCheckMate chessCheckMate;
    protected Point[] castledRook;


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
        chessCheckMate = new ChessCheckMate(boardFields);
        chessAvailableMovements = new ChessAvailableMovements(boardFields);
        initGameFromFile(nameFile);
        castledRook = new Point[2];
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
     * @param isTurnPlayersOne
     * @return 1 - figure is beaten up,
     *         0 - moved without beaten up
     *         -1 - can't move to destination point
     *         2 - castling
     *         3 - pawn arrive end of board
     */


    public int moveFigure(Point from, Point to, boolean isTurnPlayersOne){

        System.out.println("Wejscie numer jeden dla: "+from+", do"+to);
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();

        int toX = to.getPositionX();
        int toY = to.getPositionY();

        boolean canMove = canMove(from, to);

        if(canMove){
            recalculatePseudoMovements();
            Figure figureToMove = boardFields[fromY][fromX];

            boolean itIsNotFigureTheSameColor = isTurnPlayersOne == figureToMove.isWhite();

            if(itIsNotFigureTheSameColor){
                return -1;
            }

            boolean figureIsBeatenUp = boardFields[toY][toX].getTypeOfFigure() != "EMPTY";



            boolean isCastling = figureToMove.getTypeOfFigure() == "KING";

            if(isCastling && Math.abs(from.getPositionX() - to.getPositionX()) == 2 && from.getPositionY() == from.getPositionY()){
                int substract = from.getPositionX() - to.getPositionX();

                System.out.println("Start ");
                if(substract > 0 ){
                    move(new Point(0, from.getPositionY()), new Point(3, from.getPositionY()));
                    castledRook[0] = new Point(0, from.getPositionY());
                    castledRook[1] = new Point(3, from.getPositionY());
                }else{
                    move(new Point(7, from.getPositionY()), new Point(5, from.getPositionY()));
                    castledRook[0] = new Point(7, from.getPositionY());
                    castledRook[1] = new Point(5, from.getPositionY());
                }

                boardFields[toY][toX] = boardFields[fromY][fromX];
                boardFields[toY][toX].setPosition(to);
                boardFields[fromY][fromX] = new Figure(from, "EMPTY");

                System.out.println("NO I KONIEC MOVE FIGURE, CASTLE ROBIE");
                return 2;

            }

            boolean isPawn = figureToMove.getTypeOfFigure() == "PAWN";
            boolean pawnArrived = false;
            if(isPawn){
                boolean whitePawnArrived = figureToMove.isWhite() && figureToMove.getPosition().getPositionY() == 7;
                boolean whiteBlackArrived = figureToMove.isWhite() && figureToMove.getPosition().getPositionY() == 0;

                if(whiteBlackArrived || whitePawnArrived){
                    pawnArrived = true;
                }


            }

            System.out.println("Jestem na pozycji wykonania ruchu");
            boardFields[toY][toX] = boardFields[fromY][fromX];
            boardFields[toY][toX].setPosition(to);
            boardFields[fromY][fromX] = new Figure(from, "EMPTY");



            return figureIsBeatenUp ? 1 : pawnArrived ? 3 : 0;
        }

        return -1;
    }

    private void recalculatePseudoMovements() {

        for (Figure[] figures:boardFields
             ) {
            for(Figure figure:figures){
                figure.calculateAllAvailableMovements();
            }

        }

    }

    private void move(Point from, Point to) {
        int toX = to.getPositionX();
        int toY = to.getPositionY();

        int fromY = from.getPositionY();
        int fromX = from.getPositionX();

        boardFields[toY][toX] = boardFields[fromY][fromX];
        boardFields[toY][toX].setPosition(to);
        boardFields[fromY][fromX] = new Figure(from, "EMPTY");

    }

    public List<Point> addCastling(Figure[][] anotherBoard, Figure king){
        System.out.println("Generuje castling");
        //Lista na ruchy krola
        List<Point> availableMovements = new LinkedList<Point>();

        //jezeli krol jest szachowany nie mozna wykonac roszady
        if(chessCheckMate.checkIsMate(anotherBoard, king.isWhite())) return availableMovements;


        //figura nie jest krolem
        if(king.getTypeOfFigure() != "KING") return availableMovements;

        //krol sie ruszyl
        if(king instanceof King){
            boolean moved = ((King) king).isMoved();
            if(moved) return availableMovements;
        }

        //wieza sie ruszyla
        boolean kingIsWhite = king.isWhite();

        //znajdz wieze tego samego koloru
        List<Figure> allyFigures = Board.getAllFiguresByColor(anotherBoard, kingIsWhite);
        List<Figure> allyRooks = new LinkedList<Figure>();

        for (Figure figure:allyFigures
                ) {
            boolean isRook = figure.getTypeOfFigure() == "ROOK";
            boolean isNotMoved = figure.isMoved();
            if(isRook && isNotMoved)
                allyRooks.add(figure.copy());
        }


        //jezeli wieze sie ruszyly to nie mozna wykonac roszady
        if(allyRooks.size()==0) return availableMovements;

        //czy sa puste pola miedzy wiezami i nie sa szachowane

        boolean shortCastling, longCastling, notCheck = false;
        if (kingIsWhite) {

            Point kingPosition = new Point(3, 0);
            shortCastling = anotherBoard[0][0].getTypeOfFigure()=="ROOK" && anotherBoard[0][1].getTypeOfFigure() == "EMPTY" && anotherBoard[0][2].getTypeOfFigure() == "EMPTY";
            longCastling = anotherBoard[0][7].getTypeOfFigure()=="ROOK" && anotherBoard[0][4].getTypeOfFigure() == "EMPTY" && anotherBoard[0][5].getTypeOfFigure() == "EMPTY" && anotherBoard[0][6].getTypeOfFigure() == "EMPTY";

            if (shortCastling) {

                notCheck =canMove(kingPosition, new Point(1, 0)) && canMove(kingPosition, new Point(2, 0));

                if (notCheck)
                    availableMovements.add(new Point(1, 0));

            }

            if (longCastling) {

                notCheck = canMove(kingPosition, new Point(4, 0)) && canMove(kingPosition, new Point(5, 0)) && canMove( kingPosition, new Point(6, 0));

                if (notCheck)
                    availableMovements.add(new Point(6, 0));
            }


        } else {

            Point kingPosition = new Point(4, 7);
            shortCastling = anotherBoard[7][7].getTypeOfFigure()=="ROOK" && anotherBoard[7][5].getTypeOfFigure() == "EMPTY" && anotherBoard[7][6].getTypeOfFigure() == "EMPTY";
            longCastling =anotherBoard[7][0].getTypeOfFigure()=="ROOK" &&  anotherBoard[7][1].getTypeOfFigure() == "EMPTY" && anotherBoard[7][2].getTypeOfFigure() == "EMPTY" && anotherBoard[7][3].getTypeOfFigure() == "EMPTY";

            if (shortCastling) {

                notCheck = wontBeCheck(kingPosition, new Point(5, 7)) && wontBeCheck(kingPosition, new Point(6, 7));

                if (notCheck)
                    availableMovements.add(new Point(6, 7));

            }

            if (longCastling) {
                notCheck = wontBeCheck(kingPosition, new Point(1, 7)) && wontBeCheck(kingPosition, new Point(2, 7)) && wontBeCheck(kingPosition, new Point(3, 7));
                if (notCheck)
                    availableMovements.add(new Point(2, 7));
            }

        }
        return  availableMovements;

    }


    /**
     * Function calculate that figures can be moved from to another position
     * Function checking mate before movement
     * @param from position from figures would be move
     * @param to position figure destination
     * @return true if can move
     */

    public boolean canMove(Point from, Point to) {
        return getAvailableMovements(from).contains(to) && wontBeCheck(from, to);
    }

    public boolean wontBeCheck(Point from, Point to) {
        int fromX = from.getPositionX();
        int fromY = from.getPositionY();
        boolean isWhite = boardFields[fromY][fromX].isWhite();

        int toX = to.getPositionX();
        int toY = to.getPositionY();


        Figure[][] newBoard = copyOriginalBoard();
        newBoard[toY][toX] = boardFields[fromY][fromX].copy();
        newBoard[toY][toX].setPosition(to);
        newBoard[fromY][fromX] = new Figure(from, "EMPTY");

        boolean isMate = chessCheckMate.checkIsMate(newBoard, isWhite);

        return !isMate;
    }



    public List<Figure> getAllWhiteFigures(){
        return getAllFiguresByColor(true);
    }

    public List<Figure> getAllBlackFigures(){
        return getAllFiguresByColor(false);
    }

    public List<Figure> getAllFiguresByColor(boolean isWhite){
        return Board.getAllFiguresByColor(boardFields, isWhite);
    }

    public static List<Figure> getAllFiguresByColor(Figure[][] board, boolean isWhite){
        List<Figure> figures = new LinkedList<Figure>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean isTheSameColor = board[j][i].isWhite() == isWhite;
                boolean notEmpty = board[j][i].getTypeOfFigure() != "EMPTY";
                if(isTheSameColor && notEmpty)
                    figures.add(board[j][i]);
            }
        }

        return figures;
    }


    public List<Point> getAvailableMovements(Point point){
        List<Point> availableMovements = chessAvailableMovements.getAvailableMovements(point);
        System.out.println("Ruchy dla figury: "+point.toString());
        for (Point pointA:availableMovements
             ) {
            System.out.println(pointA);
        }
        System.out.println("======");

        Figure figure = boardFields[point.getPositionY()][point.getPositionX()];
        if(figure.getTypeOfFigure()=="KING"){
            List<Point> castling = addCastling(boardFields, figure);

            for (Point p:castling
                 ) {
                if(!availableMovements.contains(point)){
                    availableMovements.add(p);
                }
                
            }
        }


        return availableMovements;
    }

    public boolean checkMate(boolean isWhite){
        return chessCheckMate.checkIsMate(isWhite);
    }

    public boolean checkIsCheckMate(boolean isWhite){
        return chessCheckMate.checkIsCheckMate(isWhite);
    }

    public Figure[][] getBoardFields(){
        return copyOriginalBoard();
    }

    public Figure[][] copyOriginalBoard(){
        return Board.copyOriginalBoard(boardFields);
    }

    public static Figure[][] copyOriginalBoard(Figure[][] board){
        Figure[][] newBoard = new Figure[board.length][];
        for(int i=0;i<board.length;i++){
            newBoard[i] = Arrays.copyOf(board[i], board.length);
            for(int j=0;j<board[i].length;j++){
                newBoard[i][j] = board[i][j].copy();
            }
        }

        return newBoard;
    }

    /*
    Displays methods
     */

    public static void display(Figure[][] board){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                boolean isRook = board[i][j].getTypeOfFigure() == "ROOK";
                boolean isKnight = board[i][j].getTypeOfFigure() == "KNIGHT";
                boolean isQueen = board[i][j].getTypeOfFigure() == "QUEEN";
                boolean isKing = board[i][j].getTypeOfFigure() == "KING";
                boolean isPawn = board[i][j].getTypeOfFigure() == "PAWN";
                boolean isEmpty = board[i][j].getTypeOfFigure() == "EMPTY";
                boolean isBishop = board[i][j].getTypeOfFigure() == "BISHOP";

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

    public void display(){
        Board.display(boardFields);
    }

    public static void displayAllFigures(Figure[][] boardFields){
        System.out.println("All figures: ");
        for(Figure[] figures : boardFields){
            for(Figure figure : figures){
                System.out.println(figure.toString());
            }
        }
    }

    public void displayAllFigures() {
        Board.displayAllFigures(this.boardFields);
    }

    public String getBoardInString(){
        StringBuffer stringBuffer = new StringBuffer();
        for(Figure[] figures : boardFields){
            for(Figure figure : figures){
                stringBuffer.append(figure.toString());
            }
        }

        return stringBuffer.toString();
    }



}
