import java.util.*;
import java.io.*;
public class Maze{

    private Move[] moves = new Move[4];
    private char[][]maze;
    private boolean animate;//false by default

    private class Move{
      int x;
      int y;
      public Move(int a, int b){
        x = a;
        y = b;
      }
    }

    public static void main(String[] args){
      try {
        Maze test = new Maze("data1.dat");
        System.out.println(test.toString());
      } catch (FileNotFoundException e){
        System.out.println("need valid filename");
      }
    }
    public Maze(String filename) throws FileNotFoundException{
      try{
        //COMPLETE CONSTRUCTOR
          File greebler = new File(filename);
          Scanner keebler = new Scanner(greebler);
          int a = 0;
          while (keebler.hasNextLine()){
            a++;
            keebler.nextLine();
          }
          keebler = new Scanner(greebler);
          String n = keebler.nextLine();
          int b = n.length();
          maze = new char[a][b];
          for (int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[0].length; j++){
              maze[i][j] = n.charAt(j);
            }
            if (i != maze.length - 1){
              n = keebler.nextLine();
            }
          }
          moves[0] = new Move(1, 0);
          moves[1] = new Move(-1, 0);
          moves[2] = new Move(0, 1);
          moves[3] = new Move(0, -1);
        } catch (FileNotFoundException e){
          System.out.println("file does not exist");
        }
    }


    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){

        animate = b;

    }


    public void clearTerminal(){

        //erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }
    public String toString(){
      String result = "";
      for (int i = 0; i < maze.length; i++){
        for (int j = 0; j < maze[i].length; j++){
          result += maze[i][j];
        }
        result += "\n";
      }
      return result;

    }

    public int solve(){
      int r = 0;
      int c = 0;
      for (int i = 0; i < maze.length; i++){
        for (int j = 0; j < maze[i].length; j++){
          if (maze[i][j] == 'S'){
            r = i;
            c = j;
            maze[i][j] = ' ';
          }
        }
      }
      return solve(r, c, 0);
    }

    private int solve(int r, int c, int result){ //you can add more parameters since this is private


        //automatic animation! You are welcome.
        if(animate){

            clearTerminal();
            System.out.println(this);

            wait(150);
        }

        //COMPLETE SOLVE
        if (maze[r][c] == 'E'){
          return result;
        } else {
          maze[r][c] = '@';
        }
        for (int i = 0; i < moves.length; i++){
          int rowI = moves[i].x;
          int colI = moves[i].y;
          if (maze[r + rowI][c + colI] == ' ' || maze[r + rowI][c + colI] == 'E'){
            int solvler = solve(r + rowI, c + colI, result + 1);
            if (solvler != -1){
              return solvler;
            }
          }
        }
        maze[r][c] = '.';
        return -1; //so it compiles
    }


}
