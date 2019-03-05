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

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException

    */

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






   /*Return the string that represents the maze.

     It should look like the text file with some characters replaced.

    */
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



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
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

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
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
