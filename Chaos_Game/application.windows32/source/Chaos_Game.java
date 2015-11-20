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

public class Chaos_Game extends PApplet {

float lnth = 100;

float side_length = 800;
float half_length = side_length/2;
float triangle_height = side_length*sin(PI/3);

PVector ver1 = new PVector (0,triangle_height);
PVector ver2 = new PVector (side_length, triangle_height);
PVector ver3 = new PVector (half_length, 0);

PVector plot = new PVector();

public void setup() {
  
  background(255);
  
  point(ver1.x,ver1.y);
  point(ver2.x,ver2.y);
  point(ver3.x,ver3.y);
  
  plot = new PVector (random(0,side_length),random(0,triangle_height));
}

public void draw() {
  int ranint = PApplet.parseInt(random(1,4));
  
  if (ranint == 1) {
    plot = PVector.add(plot,ver1);
  }
  
  else if (ranint == 2) {
    plot = PVector.add(plot,ver2);
  }
  
  else if (ranint == 3) {
    plot = PVector.add(plot,ver3);
  }
  plot.mult(0.5f);
  
  point(plot.x,plot.y);
}
  public void settings() {  size(850,850); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Chaos_Game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
