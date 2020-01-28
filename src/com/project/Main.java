package com.project;
import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

class Board {
    int [][] board;
    int row;
    int column;
    Board(int size) {
        this.row = size;
        this.column = size;
        board = new int[size][size];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = 0;
            }
        }
    }
    boolean isValid(int row,int column,int size){
        if((row>=0 && row<size) && (column>=0 && column<size)){
            return true;
        }
        return false;
    }
    boolean isOccupied(int row,int column){
        if(board[row][column]==0) return false;
        return true;
    }
    void userInput(int row,int column,int value){
        board[row][column]=value;
    }
    void display(int size){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    void undo(int row,int column){
        board[row][column]=0;
    }
    int check1(int size,int row,int column,int [][] board){
        int cnt=0,val;
        for(int i=0;i<size;i++){
             cnt=0;val=board[row+i][column];
            for(int j=0;j<size;j++) {
                if(board[row+i][column+j]==val){
                    cnt++;                                              //checking for minimum size .i.e 3*3,4*4....
                }
            }
            if(val!=0 && cnt==size){
                return val;
            }
        }
        for(int i=0;i<size;i++){
             cnt=0;val=board[row][column+i];
            for(int j=0;j<size;j++) {
                if(board[row+j][column+i]==val){
                    cnt++;
                }
            }
            if(val!=0 && cnt==size){
                return val;
            }
        }
         cnt=0;val=board[row][column];
        for(int i=0;i<size;i++){
            if(board[i][i]==val) cnt++;
        }
        if(val!=0 && cnt==size) return val;

        cnt=0;val=board[row+size-1][column+size-1];

        for(int i=size-1;i>=0;i--){
            if(board[i][i]==val) cnt++;
        }
        if(val!=0 && cnt==size) return val;

        return 0;
    }
    int check(int size,int row,int column,int div){
        if(size/div==1){
            return check1(size,row,column,board);
        }
        else{
            int [][] temp=new int[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[i][j]=0;
                }
            }
            int inc=size/div;
            temp[0][0]=check(inc,row,column,div);
            temp[0][1]=check(inc,row,column+inc,div);
            temp[0][2]=check(inc,row,column+2*inc,div);
            temp[1][0]=check(inc,row+inc,column,div);
            temp[1][1]=check(inc,row+inc,column+inc,div);
            temp[1][2]=check(inc,row+inc,column+2*inc,div);
            temp[2][0]=check(inc,row,column,div);
            temp[2][1]=check(inc,row+2*inc,column+inc,div);
            temp[2][2]=check(inc,row+2*inc,column+2*inc,div);


            return check1(3,0,0,temp);
        }
    }

}
class Player{
    private static int id=1;
    int isComputer ;
    int user;
    int iswinner;
    Player(){
        this.user=id;
        id++;
        this.iswinner=0;
    }
    void setIsComputer(int computer){
        this.isComputer=computer;
    }
}

class TicTacToe{
    Scanner in=new Scanner(System.in);
    Random r=new Random();

    Board board;
    int total_filled=0;
    int size;
    int div;
    int turn=1;
    TicTacToe(int size){
        this.size=size;
        board=new Board(size);
        if(size%3==0) div=3;
        if(size%4==0) div=4;
    }
    boolean isOver(Player p1,Player p2){
        int result=board.check(size,0,0,div);
        if(result==1){
            p1.iswinner=1;
            return true;
        }
        if(result==2){
            p2.iswinner=1;
            return true;
        }
        return false;
    }
    void move(Player p){
        int row;
        int column;
        if(p.isComputer==1){
            row = r.nextInt(size);
            column = r.nextInt(size);
            while(!board.isOccupied(row,column)){
                row = r.nextInt(size);
                column = r.nextInt(size);
            }
        }
        else{
            row=in.nextInt();
            column=in.nextInt();
            while(!board.isValid(row,column,size)){
                System.out.println("Your input is out of board,enter again");
                row=in.nextInt();
                column=in.nextInt();
            }
            while(board.isOccupied(row,column)){
                System.out.println("Your input position is filled,enter again");
                row=in.nextInt();
                column=in.nextInt();
            }
        }
        board.userInput(row,column,p.user);

        total_filled++;
        board.display(size);
        if(turn==1) turn =2;
        else if(turn ==2) turn =1;
            System.out.println("press 9 to undo the move else press 10");
            int undo1 = in.nextInt();
            if(undo1==9){
                board.undo(row,column);
                if(turn==1) turn =2;
                else if(turn ==2) turn =1;
            }

    }
    void start(Player p1,Player p2){

        while(!isOver(p1,p2) && total_filled!=size*size){
            if(turn==1){
                System.out.println("Player 1 move");
                move(p1);

            }
            else{
                System.out.println("Player 2 move");
                move(p2);

            }
        }
    }
}
public class Main{
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        System.out.println("Press 1 : Game between humans");
        System.out.println("press 2 : Game between Human and computer");

        int type=in.nextInt();
        Player p1=new Player();
        Player p2=new Player();
        if(type==1){
            p1.setIsComputer(0);
            p2.setIsComputer(0);
        }
        if(type==2){
            p1.setIsComputer(0);
            p2.setIsComputer(1);
        }
        System.out.println("If you want to play M * M size ,enter value of M");
        int size=in.nextInt();
        TicTacToe game=new TicTacToe(size);
        game.start(p1,p2);

        if(p1.iswinner==1){
            System.out.println("Player 1 is a winner");
        }
        else if(p2.iswinner==1){
            System.out.println("Player 2 is a winner");
        }
        else{
            System.out.println("Match drawn");
        }
    }
}