package com.project;
import java.util.Scanner;
import java.util.Random;

interface Board{
    public void fill(int row,int column,int id);
    public boolean isValidMove(int row,int column);
    public boolean isMoveOccupied(int row,int column);

}
interface Game{
    public void start(Player p1,Player p2);
    //  public void undo();
    public boolean isGameOver(Player p1,Player p2);

}
class SquareBoard implements Board{
    public int[][] table;
    private int size;
    SquareBoard(int size){
        this.table=new int[size][size];
        this.size=size;
    }
    public void fill(int row,int column,int id){
        this.table[row][column]=id;
    }
    public boolean isValidMove(int row,int column){
        if((row>=0 && row < this.size) && (column >= 0 && column < this.size)){
            return true;
        }
        return false;
    }
    public boolean isMoveOccupied(int row,int column){
        if(this.table[row][column]!=0)
            return true;
        return false;
    }
    public int getVal(int row,int column){
        return this.table[row][column];
    }

}

class SquareTicTicToe implements Game{
    private SquareBoard board ;
    private int total_occupied=0;
    private int mul;
    private int size;
    SquareTicTicToe(int size,int mul){
        board=new SquareBoard(size);
        this.mul=mul;
        this.size=size;
    }
    public int[] getUserInput(Player p){
        int [] input =new int[2];
        int row,column;
        Random r=new Random();
        Scanner in =new Scanner(System.in);
        if(p.iscomputer==1){
            row= r.nextInt(this.size);
            column=r.nextInt(this.size);
            while(board.isMoveOccupied(row,column)){
                System.out.println("Your move is filled,Enter unfilled position");
                row= r.nextInt(this.size);
                column=r.nextInt(this.size);
            }
        }
        else{
            row= in.nextInt();
            column=in.nextInt();
            while(!board.isValidMove(row,column)){
                System.out.println("Your move is out of board,Enter again");
                row= in.nextInt();
                column=in.nextInt();
            }
            while(board.isMoveOccupied(row,column)){
                System.out.println("Your move is filled,Enter unfilled position");
                row= in.nextInt();
                column=in.nextInt();
            }
        }
        input[0]=row;
        input[1]=column;
        return input;
    }
    public void move(int[] input,int id){
        board.fill(input[0],input[1],id);
    }
    public void start(Player p1,Player p2){
        int turn=0;
        int [] input;
        while(!isGameOver(p1,p2)){
            System.out.println("Player " + (turn+1) + " move");
            if(turn==0) {
                input = getUserInput(p1);
                move(input,p1.getUserId());
            }
            else{
                input = getUserInput(p2);
                move(input,p2.getUserId());
            }
            total_occupied++;
            turn = 1-turn ;
        }
    }
    int checkBaseMatrix(int size,int row,int column,int[][] table){
        int cnt=0,val;
        for(int i=0;i<size;i++){
            cnt=0;val=board.getVal(row+i,column);
            for(int j=0;j<size;j++) {
                if(board.getVal(row+i,column+j)==val){
                    cnt++;                                              //checking for minimum size .i.e 3*3,4*4....
                }
            }
            if(val!=0 && cnt==size){
                return val;
            }
        }
        for(int i=0;i<size;i++){
            cnt=0;val=board.table[row][column+i];
            for(int j=0;j<size;j++) {
                if(board.table[row+j][column+i]==val){
                    cnt++;
                }
            }
            if(val!=0 && cnt==size){
                return val;
            }
        }
        cnt=0;val=board.table[row][column];
        for(int i=0;i<size;i++){
            if(board.table[i][i]==val) cnt++;
        }
        if(val!=0 && cnt==size) return val;

        cnt=0;val=board.table[row+size-1][column+size-1];

        for(int i=size-1;i>=0;i--){
            if(board.table[i][i]==val) cnt++;
        }
        if(val!=0 && cnt==size) return val;

        return 0;
    }
    public int checkrec(int size,int row,int column){
        if(size/mul==1){
            return checkBaseMatrix(size,row,column,board.table);
        }
        int [][] res=new int[mul][mul];
        int trow=0,tcol=0,i=0,j=0;
        while(row+trow<size){
            j=0;
            while(column+tcol<size){
                res[i][j]=checkrec(size/mul,row+trow,column+tcol);
                tcol+=mul;
                j++;
            }
            trow+=mul;
            i++;
        }
        return checkBaseMatrix(mul,0,0,res);
    }
    public boolean isGameOver(Player p1,Player p2){
        int result=checkrec(this.size,0,0);
        if(result==1) {
            p1.iswinner = 1;
            return true;
        }
        if(result==2) {
            p2.iswinner = 1;
            return true;
        }
        if(total_occupied==size*size) return true;
        return false;
    }

}
class HexagonalTicTacToe implements Game{

    HexagonalTicTacToe(int size){

    }
    public void start(Player p1,Player p2){

    }

    public boolean isGameOver(Player p1,Player p2){
        return true;
    }

}
class Player{
    private static int id=1;
    private int userid;
    public int iscomputer;
    public int iswinner=0;
    Player(){
        this.userid=id;
        id=id+1;
    }
    public int getUserId(){
        return this.userid;
    }
    public void setisComputer(int val){
        this.iscomputer=val;
    }
}
public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("press 1 : Human Human");
        System.out.println("press 2 : Human Computer");
        int input;
        input=in.nextInt();
        Player p1=new Player();
        Player p2=new Player();
        if(input==1){
            p1.setisComputer(0);
            p2.setisComputer(0);
        }
        if(input==2){
            p1.setisComputer(0);
            p2.setisComputer(1);
        }
        System.out.println("press 1 : square TicTacToe");
        System.out.println("press 2 : Hexagonal TicTacToe");

        input = in.nextInt();

        int size,mul=0;
        System.out.println("enter the size of board.If board is  M * M ,enter the value of M");
        size = in.nextInt();
        if(size%3==0) mul=3;
        if(size%4==0) mul=4;
        if(input==1){
            SquareTicTicToe game = new SquareTicTicToe(size,mul);
            game.start(p1,p2);
        }
        if(input==2){
            HexagonalTicTacToe game =new HexagonalTicTacToe(size);
        }

        if(p1.iswinner==1){
            System.out.println("Player 1 is a winner");
        }
        else if(p2.iswinner==1){
            System.out.println("Player 2 is a winner");
        }
        else{
            System.out.println("Match Drawn");
        }
    }
}
