import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Flip_Game extends PApplet {

// Options
int cols = 6; //Default 6
int rows = 6; //Default 6
int square_size = 50; //Default 50
//Colours are grayscale in the range 0-255
int colour_1 = 255; //Default 255
int colour_2 = 0; //Default 0
int line_colour = 117; //Default 117
int difficulty = 3; //Default 3 (doesn't have big effect)

int[][] gridArray = new int[cols][rows];
PFont f;

public void setup() {
  
  surface.setSize(rows*square_size, cols*square_size);
  f = createFont("Arial",10,true); 
  background(255);
  textFont(f);
  fill(0);
  textAlign(CENTER);
  text("Welcome to Flip Game.\nRight click to draw board or\nreset to new random board.\nThe aim is to make the board\nall the same colour.\nLeft click on a square to flip it \nand the squares surrounding it!", width/2, height/8);
}

public void draw(){
}

public void mousePressed() {
  if(mouseButton == LEFT) {
  flip_colours(mouseX, mouseY);
  draw_board();
  check_complete();
  }
  else if(mouseButton == RIGHT) {randomize_soluble_board();
  draw_board();
  }
}
  
public void flip_colours(int tempMouseX, int tempMouseY) {
  int i=tempMouseX/square_size;
  int j=tempMouseY/square_size;
  //Swap middle colour  
  change_colour(i, j);
  //Swap left colour if appropriate
  if(i-1>=0) change_colour(i-1, j);
  //Swap right colour if appropriate
  if(i+1<rows) change_colour(i+1, j);
  //Swap upper colour if appropriate
  if(j-1>=0) change_colour(i, j-1);
  //Swap lower colour if appropriate
  if(j+1<cols) change_colour(i, j+1);
}

public void change_colour(int _i, int _j) {
  if(gridArray[_i][_j]==0) gridArray[_i][_j]=1;
  else gridArray[_i][_j]=0;
}
  
public void draw_board() {
  stroke(line_colour);
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      if(gridArray[i][j]==0) fill(colour_1);
      else fill(colour_2);
      rect(i*square_size,j*square_size, square_size, square_size);
    }
  }
}

public void check_complete() {
  int count_max = rows*cols;
  int zero_count = 0;
  int one_count = 0;
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      if(gridArray[i][j]==0) zero_count++;
      else one_count++;
    }
  }
  if((zero_count==count_max) || (one_count==count_max)){
    background(255);
    f = createFont("Arial",10,true); 
    textFont(f);
    fill(0);
    textAlign(CENTER);
    text("Well done, you won! \n Right click to play again.", width/2, height/2);
  }
}

public void randomize_board() {
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
    gridArray[i][j] = PApplet.parseInt(random(2));
    }
  }
}

public void randomize_soluble_board() {
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
    gridArray[i][j] = 0;
    }
  }
  for (int c = 0; c<rows; c++){
    flip_colours(PApplet.parseInt(random(rows*square_size)),PApplet.parseInt(random(cols*square_size)));
  }
}  
  public void settings() {  size(300, 300); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Flip_Game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
