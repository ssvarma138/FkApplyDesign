package com.project;
import java.sql.SQLOutput;
import java.util.Scanner;
class TicTacToe{
    int [][] board ;
    int total_occupied ;
    TicTacToe(int size)
    {
        board=new int[size][size];                                     //let it be size 3*3  now.change later implementation
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                board[i][j]=0;
            }
        }
        total_occupied=0;
    }
    void putUserInput(int value,int row,int column)
    {
        board[row][column]=value;
        total_occupied=total_occupied+1;
    }
     boolean isGameOver(int size)
    {
        if( checkRow(size)||checkColumn(size)||checkDiagonal(size) ){
            return true;
        }
        return false;
    }
     boolean checkRow(int size)
    {
        for(int i=0;i<size;i++){
            if((board[i][0]!=0) && ((board[i][0]==board[i][1]) && (board[i][1]==board[i][2])))
            {
                return true;
            }
        }

        return false;
    }
     boolean checkColumn(int size)
    {
        for(int i=0;i<size;i++){
            if((board[0][i]!=0) && ((board[0][i]==board[1][i]) && (board[1][i]==board[2][i])))
            {
                return true;
            }
        }
        return false;
    }
     boolean checkDiagonal(int size)
    {
        if ( board[0][0]!=0 && (board[0][0] == board[1][1] && board[1][1] == board[2][2]) )
            return(true);
        if ( board[0][2]!=0 && (board[0][2] == board[1][1] && board[1][1] == board[2][0]) )
            return(true);

        return false;
    }
    boolean isValid(int size,int row,int column)
    {
        if((row>=0&&row<size) && (column>=0 && column<size))
        {
            return true;
        }
        return false;
    }
    boolean isOccupied(int row,int column){
        if(board[row][column]!=0) return true;

        return false;
    }

}
public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the size of Board you want to play");
        Scanner in=new Scanner(System.in);
        int size = in.nextInt();
        TicTacToe obj=new TicTacToe(size);
        int move=1;      //let default move be user1
        int row=0,column=0;
//        for(int i=0;i<size;i++)
//        {
//            for(int j=0;j<size;j++){
//                System.out.print(obj.board[i][j]);
//            }
//            System.out.println();
//        }
////
        while(obj.isGameOver(size)==false&& obj.total_occupied!=size*size)
        {

            if(move==1)
            {
                System.out.println("user 1 : Give Your position ");
                row=in.nextInt();
                column=in.nextInt();
                while(!((row>=0&&row<size) && (column>=0 && column<size))){
                    System.out.println("Your position is out of board,Give a position which is inside the board");
                    row=in.nextInt();
                    column=in.nextInt();
                }
                while(obj.board[row][column]!=0){
                    System.out.println("Position is occupied,enter a unoccupied position");
                    row=in.nextInt();
                    column=in.nextInt();
                }
                obj.putUserInput(1,row,column);
                move=2;
            }
            else if(move==2)
            {
                System.out.println("user 2 : Give Your Position");
                row = in.nextInt();
                column = in.nextInt();
                while(obj.isValid(size,row,column)==false){
                    System.out.println("Your position is out of board,Give a position which is inside the board");
                    row=in.nextInt();
                    column=in.nextInt();
                }
                while(obj.isOccupied(row,column)){
                    System.out.println("Your position is occupied");
                    row=in.nextInt();
                    column=in.nextInt();
                }
                obj.putUserInput(2, row, column);
                move=1;
            }

        }
        if(obj.isGameOver(size)==false && obj.total_occupied==size*size){
            System.out.println("Match Drawn");
        }
        else {
            if(move==1)
            {
                System.out.println("User 2 is a Winner!!");
            }
            else
            {
                System.out.println("User 1 is a Winner!!");
            }
        }
    }
}
