import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Random;
class Piece{// every piece has 4 blocks
    
    int[] x = new int[4];//array to store the x coordinates of the blocks
    int[] y = new int[4];//array to store the y coordinates of the blocks
    char type; // type can be A/B/C/D/E/F/G, each type signifies a different kind of piece
}

public class Tetris implements KeyListener{
    public static String ANSI_RESET = "\u001B[0m";
    static final int startx=5,starty=17;//The coordinates where a new piece is spawned
    static char grid[][] = new char [10][20];//the tetris grid
    static Piece ActivePiece=new Piece();//ActivePiece refers to the piece that is currently floating, and has not reached the bottom yet
    static Piece PieceOnHold = newPiece(0);//this is the piece thats on hold
    //Hold is a storage space that can store 1 piece.
    //if the player encounters a piece that they dont wanna use, they can put it in hold, and then hold will be full
    
    static int score = 0;
    public static void clrscr()// Function to clear screen
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
    static void display(char[][]grid)
    {//function to display the grid,hold,and score
        clrscr();
        System.out.print("+");
        for(int i=0;i<10;i++){
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println();
        for(int j=19;j>=0;j--){
            System.out.print("|");
            for(int i=0;i<10;i++){
                if(grid[i][j]=='x'){
                 System.out.print(" ");
                } else {
                switch(ActivePiece.type)
                {
                    case 'A':
                    //cyan
                    String ANSI_CYAN = "\u001B[36m";
                    System.out.println(ANSI_CYAN + grid[i][j]+ ANSI_RESET);
                    break;
                    case 'B':
                    //yellow
                    String ANSI_YELLOW = "\u001B[33m";
                    System.out.println(ANSI_YELLOW + grid[i][j]+ ANSI_RESET);
                    break;
                    case 'C':
                    //purple
                    String ANSI_PURPLE = "\u001B[35m";
                    System.out.println(ANSI_PURPLE + grid[i][j]+ ANSI_RESET);
                    break;
                    case 'D':
                    //green
                    String ANSI_GREEN = "\u001B[32m";
                    System.out.println(ANSI_GREEN + grid[i][j]+ ANSI_RESET);     
                    break;
                    case 'E':
                    //red
                    String ANSI_RED = "\u001B[31m";
                    System.out.println(ANSI_RED + grid[i][j]+ ANSI_RESET); 
                    break;    
                    case 'F':
                    //orange
                    String ANSI_ORANGE = "\033[48:5:208m%s\033[m";
                    System.out.println(ANSI_ORANGE + grid[i][j]+ ANSI_RESET); 
                    break;
                    case 'G':
                    //blue
                    String ANSI_BLUE = "\u001B[34m";
                    System.out.println(ANSI_BLUE + grid[i][j]+ ANSI_RESET); 
                    break;  
                }
            }
        }
            System.out.print("|");
            if(j==19){
                System.out.print("      Hold : "+PieceOnHold.type);
            }
            if(j==10){
                System.out.print("      Score : "+score);          
            }
            System.out.println();
        }
        System.out.print("+");
        for(int i=0;i<10;i++){
            System.out.print("-");
        }
        System.out.println("+");
        System.out.println("Instructions :");
        System.out.println(" a : Move Left ");
        System.out.println(" s : Move Down Faster ");
        System.out.println(" d : Move Right ");
        System.out.println(" r : Rotate Piece Clockwise ");
        System.out.println(" h : Move Piece to Hold, and call the piece from Hold");
        System.out.println(" NOTE: Do not attempt to rotate a piece when it its close to edge... program will crash ");
    }
    static Piece newPiece(int type){//function to return a piece object depending of a certain type
        Piece obj = new Piece();
        int j=0;
        int i=0;
        switch(type){
            case 0:
                    obj.type='0';
                    for(i=0;i<4;i++){
                        obj.x[i]=0;
                        obj.y[i]=0;
                    }
                    break;
            case 1: // Line
                    
                    
                    obj.type='A';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    j--;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    j+=2;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    j++;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 2: // Square   BB
                    //          BB
                    //          
                    obj.type = 'B';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i++;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    i--;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 3: // T piece       C
                    //              CCC
                    //             
                    obj.type='C';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i--;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    i++;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    j--;
                    i++;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 4: //S piece   D
                    //          DD
                    //           D
                    obj.type='D';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i--;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    i++;
                    j-=2;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 5: // Z piece
                    obj.type='E';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i++;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    i--;
                    j-=2;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 6: //L piece
                    obj.type='F';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i++;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    i--;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    j++;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            case 7: //Backwards L piece
                    obj.type='G';
                    obj.x[0]=startx+i;
                    obj.y[0]=starty+j;
                    i--;
                    obj.x[1]=startx+i;
                    obj.y[1]=starty+j;
                    i++;
                    j++;
                    obj.x[2]=startx+i;
                    obj.y[2]=starty+j;
                    j++;
                    obj.x[3]=startx+i;
                    obj.y[3]=starty+j;
                    break;
            default:
        }
        return obj;
    }
    static Piece newPiece(char type)
    {//same function to return a piece object but with overloading
        Piece obj = new Piece();
        switch(type){
            case '0': obj = newPiece(0);
                    break;
            case 'A': obj = newPiece(1);
                    break;
            case 'B': obj = newPiece(2);
                    break;
            case 'C': obj = newPiece(3);
                    break;
            case 'D': obj = newPiece(4);
                    break;
            case 'E': obj = newPiece(5);
                    break;
            case 'F': obj = newPiece(6);
                    break;
            case 'G': obj = newPiece(7);
                    break;
            default:
        }
        
        
        return obj;
    }
    
    static void clearGrid(Piece obj, char [][] grid){//This doesnt actually clear the whole grid, just clears active piece
        for(int i=0;i<4;i++){                        // This method is used to temporarily suspend the active piece, while it moves\rotates etc
            grid[obj.x[i]][obj.y[i]]='x';            
        }    
    }
    static void updateGrid(Piece obj, char [][] grid){//Function to update the grid after every move
        for(int i=0;i<4;i++){
            grid[obj.x[i]][obj.y[i]]=obj.type;
        }
    }
    static boolean isBlockedRight(Piece obj,char[][]grid){//check whether the ActivePiece has free space on its right
        boolean a = false;
        char x;
        for(int i=0;i<4;i++){
            x=grid[obj.x[i]+1][obj.y[i]];
            if(x!='x'&&x!=obj.type){
                return true;
            }
        }
        
        
        return a;
    }
    static boolean isBlockedLeft(Piece obj,char[][]grid){//check for free space on the left of activepiece
        boolean a = false;
        char x;
        for(int i=0;i<4;i++){
            x=grid[obj.x[i]-1][obj.y[i]];
            if(x!='x'&&x!=obj.type){
                return true;
            }
        }
        
        
        return a;
    }
    static boolean isAtBottom(Piece obj,char [][] grid){//check whether the active piece has reached the bottom, or has landed on top of another piece
        boolean a=false;
        char x;
        for(int i=0;i<4;i++){
            if(obj.y[i]==0){
                return true;
            }
        }
        for(int i=0;i<4;i++){
            x=grid[obj.x[i]][obj.y[i]-1];
            if(x!='x'&&x!=obj.type){
                return true;
            }
        }
        return a;
    }
    
    static void moveDown(Piece obj, char [][] grid){//move active piece one step down
        if(isAtBottom(obj,grid)) return;
        clearGrid(obj,grid);
        for(int i=0;i<4;i++){
            obj.y[i]--;
        }
        updateGrid(obj,grid);
    }
    public static void moveLeft(Piece obj, char [][] grid){//move to the left
        if(isBlockedLeft(obj,grid)){return;}
        for(int i=0;i<4;i++){
            if(obj.x[i]==0) return;
        }
        clearGrid(obj,grid);
        for(int i=0;i<4;i++){
            obj.x[i]--;
        }
        updateGrid(obj,grid);
    }
    public static void moveRight(Piece obj, char [][] grid){//Function to move the active Piece one space to the right
        if(isBlockedRight(obj,grid)){return;}
        for(int i=0;i<4;i++){
            if(obj.x[i]==9) return;
        }
        clearGrid(obj,grid);
        for(int i=0;i<4;i++){
            obj.x[i]++;
        }
        updateGrid(obj,grid);
    }
    static void Hold(){//function to exchange the active piece with the hold piece
                        // if hold is empty, active piece is just a new random piece
        Random rand = new Random();
        clearGrid(ActivePiece,grid);
        if(PieceOnHold.type=='0'){
            PieceOnHold = newPiece(ActivePiece.type);
            ActivePiece = newPiece(rand.nextInt(8));
        }else{
            Piece temp = newPiece(ActivePiece.type);
            ActivePiece = newPiece(PieceOnHold.type);
            PieceOnHold = newPiece(temp.type);
        }
        
        
    }
    public static void Rotate(Piece obj, char [][] grid){//function to rotate activepiece clockwise
        int temp;
        int x,y;
        clearGrid(obj,grid);
        for(int i=1;i<4;i++){
            x=obj.x[i]-obj.x[0];
            y=obj.y[i]-obj.y[0];
            switch(x){
                case -2:switch(y){
                            case -2:x=-2;y=2;
                                    break;
                            case -1:x=-1;y=2;
                                    break;
                            case 0: x=0;y=2;
                                    break;
                            case 1: x=1;y=2;
                                    break;
                            case 2: x=2;y=2;
                                    break;
                            default:
                        }
                        break;
                case -1:switch(y){
                            case -2:x=-2;y=1;
                                    break;
                            case -1:x=-1;y=1;
                                    break;
                            case 0:x=0;y=1;
                                    break;
                            case 1:x=1;y=1;
                                    break;
                            case 2:x=2;y=1;
                                    break;
                            default:
                        }
                        break;
                
                case 0: switch(y){
                            case -2:x=-2;y=0;
                                    break;
                            case -1:x=-1;y=0;
                                    break;
                            case 0:x=0;y=0;
                                    break;
                            case 1:x=1;y=0;
                                    break;
                            case 2:x=2;y=0;
                                    break;
                            default:
                        }
                        break;
                        
                case 1: switch(y){
                            case -2:x=-2;y=-1;
                                    break;
                            case -1:x=-1;y=-1;
                                    break;
                            case 0:x=0;y=-1;
                                    break;
                            case 1:x=1;y=-1;
                                    break;
                            case 2:x=2;y=-1;
                                    break;
                            default:
                        }
                        break;
                case 2: switch(y){
                            case -2:x=-2;y=-2;
                                    break;
                            case -1:x=-1;y=-2;
                                    break;
                            case 0:x=0;y=-2;
                                    break;
                            case 1:x=1;y=-2;
                                    break;
                            case 2:x=2;y=-2;
                                    break;
                            default:
                        }
                        break;
                
                default:
            }
            obj.x[i]=obj.x[0]+x;
            obj.y[i]=obj.y[0]+y;
        }
        updateGrid(obj,grid);
    }
    public static void checkForCompletion(char[][] grid){//this function checks the grid to see if there are any completed rows
        int count=0;                // and it deletes the row if it is full
        for(int j=0;j<20;j++){
            boolean flag = true;
            for(int i=0;i<10;i++){
                if(grid[i][j]=='x'){
                    flag = false;
                }
            }
            if(flag){
                for(int k=j;k<20-1;k++){
                    for(int i=0;i<10;i++){
                        grid[i][k]=grid[i][k+1];
                    }
                }
                j--;
                count++;// count shows how many rows are removed simultaneously, to calculate scorebonus
            }
        }
        switch(count){
            case 0:
                    break;
            case 1: score+=1000;//only 1 row removed
                    break;
            case 2: score+=4000;//2 rows filled in the same move
                    break;
            case 3: score+=9000;// 3 rows filled in the same move
                    break;
            case 4: score+=16000;// 4 rows
            default:
        }
    }
    public static void Solidify(char[][]grid){//this function is called whenever active piece reaches bottom
        for(int i=0;i<10;i++){
            for(int j=0;j<20;j++){
                if(grid[i][j]!='x'){
                    grid[i][j]=Character.toLowerCase(grid[i][j]);
                }
            }
        }// so what this function does is it converts the characters to lower case, so that when 
        //when the active piece changes, the finishedpieces are retained
    }
    
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        Random rand = new Random();
        char ch;
        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new FlowLayout());
        JTextField field = new JTextField(20);
        field.addKeyListener(new Tetris());
        panel.add(field);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        for(int i=0;i<10;i++){
            for(int j=0;j<20;j++){
                grid[i][j]='x';
            }
        }
        int randint = rand.nextInt(8);
        ActivePiece = newPiece(randint);
        for(int i=0;i<4;i++){
            System.out.print(" "+ActivePiece.x[i]);
            System.out.print(ActivePiece.y[i]);
            
        }System.out.println();
        for(int i=0;i<4;i++){
            grid[ActivePiece.x[i]][ActivePiece.y[i]]=ActivePiece.type;
        }
        
        int delay = 1000;
        display(grid);
        while(true){
            while(!isAtBottom(ActivePiece,grid)){    
                try{
                    Thread.sleep(delay);   
                }catch(Exception e){
                    
                }
                moveDown(ActivePiece,grid);
                display(grid);
            }
            try{
                Thread.sleep(delay/3);   
            }catch(Exception e){
                    
            }
            checkForCompletion(grid);
            Solidify(grid);
            ActivePiece = newPiece(rand.nextInt(8));
            delay-=5;//decremening the delay, so that the game gets faster as you play
        }
    }
    public void keyTyped(KeyEvent e){//keyboard input
        char x = e.getKeyChar();
        switch(x){
            case 'a':
                    moveLeft(ActivePiece,grid);
                    display(grid);
                    break;
                    
            case 'd':
                    moveRight(ActivePiece,grid);
                    display(grid);
                    break;
            case 's':
                    moveDown(ActivePiece,grid);
                    score+=14;
                    display(grid);
                    break;
            case 'r':
                    Rotate(ActivePiece,grid);
                    display(grid);
                    break;
            case 'h':
                    Hold();
                    display(grid);
            default:
        }
    }
    public void keyPressed(KeyEvent e){
        
    }
    public void keyReleased(KeyEvent e){
        
    }
}
