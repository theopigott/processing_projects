import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Naughts_Crosses_AI extends PApplet {

float leftMargin = 100;
float rightMargin = 100;
float topMargin = 100;
float bottomMargin = 100;
float boxWidth = 200;
PFont f;

int turn = 1; // 1 is cross, 2 is naught
int[][] Board = new int[3][3];
boolean gameFinished = false;
boolean gameAI = true;
int AILevel = 2;

public void setup() {
  size(800,800);
  background(255);
  f = loadFont("Arial-Black-48.vlw");
  resetBoard();  
}

public void draw() {
}

public void mouseClicked() {
  if (mouseButton == RIGHT) {
    resetBoard();
    if (gameAI == true) {
      if (turn == 2) {
        AIGo();
      }
    }
  }
  else if (gameFinished == false) {
  if (mouseX>leftMargin && mouseX<width-rightMargin && mouseY>topMargin && mouseY<height-bottomMargin) {
    int x = Mouse2X();
    int y = Mouse2Y();
    if (Board[x][y]==0) {
      if (turn == 1) {
        drawCross(x, y, 60, 10);
        Board[x][y] = 1;
        turn = 2;
      }
      else {
        drawNaught(x, y, 60, 10);
        Board[x][y] = 2;
        turn = 1;
      }
    }
    if (gameAI == true) {
      if (turn == 2) {
        if (checkBoard() == 0) AIGo();
      }
    }
  }
  }
  if (mouseX>width-rightMargin) {
      if (mouseX>(width-rightMargin/2)-20 && mouseX<(width-rightMargin/2)+20 && mouseY>(topMargin+boxWidth/2)-20 && mouseY<(topMargin+boxWidth/2)+20) {
        if (AILevel != 3) AILevel ++;
      }
      if (mouseX>width-rightMargin/2-10 && mouseX<width-rightMargin/2+10 && mouseY>topMargin+5*boxWidth/2-10 && mouseY<topMargin+5*boxWidth/2+10) {
        if (AILevel != 1) AILevel --;
      }
    updateBottom();
  }
  if (mouseY>height-bottomMargin) {
    if (gameAI == false) gameAI = true;
    else gameAI = false;
  }
  updateTop();
  updateBottom();
}

public void resetBoard() {
  for(int i=0; i<3; i++) {
    for(int j=0; j<3; j++) {
      Board[i][j] = 0;
    }
  }
  gameFinished = false;
  background(255);
  
  // Draw Board
  strokeWeight(2);
  stroke(0);
  line(leftMargin+boxWidth, topMargin, leftMargin+boxWidth, topMargin+(3*boxWidth));
  line(leftMargin+(2*boxWidth), topMargin, leftMargin+(2*boxWidth), topMargin+(3*boxWidth));
  line(leftMargin, topMargin+boxWidth, leftMargin+(3*boxWidth), topMargin+boxWidth);
  line(leftMargin, topMargin+(2*boxWidth), leftMargin+(3*boxWidth), topMargin+(2*boxWidth));
  updateTop();
  updateBottom();
}

public void AIGo()
{
  int SqNo = computer();
  if(SqNo == 0) return;
  int x = 0;
  int y = 0;
  if(SqNo == 1) {
    x = 0;
    y = 2;
  }
  if(SqNo == 2) {
    x = 1;
    y = 2;
  }
  if(SqNo == 3) {
    x = 2;
    y = 2;
  }
  if(SqNo == 4) {
    x = 0;
    y = 1;
  }
  if(SqNo == 5) {
    x = 1;
    y = 1;
  }
  if(SqNo == 6) {
    x = 2;
    y = 1;
  }
  if(SqNo == 7) {
    x = 0;
    y = 0;
  }
  if(SqNo == 8) {
    x = 1;
    y = 0;
  }
  if(SqNo == 9) {
    x = 2;
    y = 0;
  }
  if (turn == 2) {
  drawNaught(x, y, 60, 10);
  Board[x][y] = 2;
  turn = 1;
  }
}

public int computer() 
{
    if (AILevel == 1) {
      int[] freeSquares = new int[9];
      int count = 0;
      for(int a=0; a<3; a++){ // loop through checking if all squares are occupied, if they are it's a draw
          for(int b=0; b<3; b++){
            if(Board[a][b]==0) {
              if (a == 0) {
                if (b == 0) freeSquares[count] = 7;
                if (b == 1) freeSquares[count] = 4;
                if (b == 2) freeSquares[count] = 1;
              }
              if (a == 1) {
                if (b == 0) freeSquares[count] = 8;
                if (b == 1) freeSquares[count] = 5;
                if (b == 2) freeSquares[count] = 2;
              }
              if (a == 2) {
                if (b == 0) freeSquares[count] = 9;
                if (b == 1) freeSquares[count] = 6;
                if (b == 2) freeSquares[count] = 3;
              }
              count++;
            }
          }
      }
      if(count == 0) return 0;
      
      int ranSquare = PApplet.parseInt(random(0,count));
      return freeSquares[ranSquare];
    }
    
    if (AILevel == 2) {
        //WIN IF POSSIBLE-------------------------------------------
        //1st
        if(Board[0][0]==2 && Board[0][1]==2 && Board[0][2]==0) return 1;
        if(Board[0][0]==2 && Board[0][1]==0 && Board[0][2]==2) return 4;
        if(Board[0][0]==0 && Board[0][1]==2 && Board[0][2]==2) return 7;
        //2nd
        if(Board[1][0]==2 && Board[1][1]==2 && Board[1][2]==0) return 2;
        if(Board[1][0]==2 && Board[1][1]==0 && Board[1][2]==2) return 5;
        if(Board[1][0]==0 && Board[1][1]==2 && Board[1][2]==2) return 8;
        //3rd
        if(Board[2][0]==2 && Board[2][1]==2 && Board[2][2]==0) return 3;
        if(Board[2][0]==2 && Board[2][1]==0 && Board[2][2]==2) return 6;
        if(Board[2][0]==0 && Board[2][1]==2 && Board[2][2]==2) return 9;
    
        //rows
        //1st
        if(Board[0][0]==2 && Board[1][0]==2 && Board[2][0]==0) return 9;
        if(Board[0][0]==2 && Board[1][0]==0 && Board[2][0]==2) return 8;
        if(Board[0][0]==0 && Board[1][0]==2 && Board[2][0]==2) return 7;
        //2nd
        if(Board[0][1]==2 && Board[1][1]==2 && Board[2][1]==0) return 6;
        if(Board[0][1]==2 && Board[1][1]==0 && Board[2][1]==2) return 5;
        if(Board[0][1]==0 && Board[1][1]==2 && Board[2][1]==2) return 4;
        //3rd
        if(Board[0][2]==2 && Board[1][2]==2 && Board[2][2]==0) return 3;
        if(Board[0][2]==2 && Board[1][2]==0 && Board[2][2]==2) return 2;
        if(Board[0][2]==0 && Board[1][2]==2 && Board[2][2]==2) return 1;
    
        //diagonals
        //1st
        if(Board[0][0]==2 && Board[1][1]==2 && Board[2][2]==0) return 3;
        if(Board[0][0]==2 && Board[1][1]==0 && Board[2][2]==2) return 5;
        if(Board[0][0]==0 && Board[1][1]==2 && Board[2][2]==2) return 7;
        //2nd
        if(Board[0][2]==2 && Board[1][1]==2 && Board[2][0]==0) return 9;
        if(Board[0][2]==2 && Board[1][1]==0 && Board[2][0]==2) return 5;
        if(Board[0][2]==0 && Board[1][1]==2 && Board[2][0]==2) return 1;
    
        //OTHERWISE BLOCK-------------------------------------------
        //columns
        //1st
        if(Board[0][0]==1 && Board[0][1]==1 && Board[0][2]==0) return 1;
        if(Board[0][0]==1 && Board[0][1]==0 && Board[0][2]==1) return 4;
        if(Board[0][0]==0 && Board[0][1]==1 && Board[0][2]==1) return 7;
        //2nd
        if(Board[1][0]==1 && Board[1][1]==1 && Board[1][2]==0) return 2;
        if(Board[1][0]==1 && Board[1][1]==0 && Board[1][2]==1) return 5;
        if(Board[1][0]==0 && Board[1][1]==1 && Board[1][2]==1) return 8;
        //3rd
        if(Board[2][0]==1 && Board[2][1]==1 && Board[2][2]==0) return 3;
        if(Board[2][0]==1 && Board[2][1]==0 && Board[2][2]==1) return 6;
        if(Board[2][0]==0 && Board[2][1]==1 && Board[2][2]==1) return 9;
    
        //rows
        //1st
        if(Board[0][0]==1 && Board[1][0]==1 && Board[2][0]==0) return 9;
        if(Board[0][0]==1 && Board[1][0]==0 && Board[2][0]==1) return 8;
        if(Board[0][0]==0 && Board[1][0]==1 && Board[2][0]==1) return 7;
        //2nd
        if(Board[0][1]==1 && Board[1][1]==1 && Board[2][1]==0) return 6;
        if(Board[0][1]==1 && Board[1][1]==0 && Board[2][1]==1) return 5;
        if(Board[0][1]==0 && Board[1][1]==1 && Board[2][1]==1) return 4;
        //3rd
        if(Board[0][2]==1 && Board[1][2]==1 && Board[2][2]==0) return 3;
        if(Board[0][2]==1 && Board[1][2]==0 && Board[2][2]==1) return 2;
        if(Board[0][2]==0 && Board[1][2]==1 && Board[2][2]==1) return 1;
    
        //diagonals
        //1st
        if(Board[0][0]==1 && Board[1][1]==1 && Board[2][2]==0) return 3;
        if(Board[0][0]==1 && Board[1][1]==0 && Board[2][2]==1) return 5;
        if(Board[0][0]==0 && Board[1][1]==1 && Board[2][2]==1) return 7;
        //2nd
        if(Board[0][2]==1 && Board[1][1]==1 && Board[2][0]==0) return 9;
        if(Board[0][2]==1 && Board[1][1]==0 && Board[2][0]==1) return 5;
        if(Board[0][2]==0 && Board[1][1]==1 && Board[2][0]==1) return 1;
        
        int[] freeSquares = new int[9];
        int count = 0;
        for(int a=0; a<3; a++){ // loop through checking if all squares are occupied, if they are it's a draw
            for(int b=0; b<3; b++){
              if(Board[a][b]==0) {
                if (a == 0) {
                  if (b == 0) freeSquares[count] = 7;
                  if (b == 1) freeSquares[count] = 4;
                  if (b == 2) freeSquares[count] = 1;
                }
                if (a == 1) {
                  if (b == 0) freeSquares[count] = 8;
                  if (b == 1) freeSquares[count] = 5;
                  if (b == 2) freeSquares[count] = 2;
                }
                if (a == 2) {
                  if (b == 0) freeSquares[count] = 9;
                  if (b == 1) freeSquares[count] = 6;
                  if (b == 2) freeSquares[count] = 3;
                }
                count++;
              }
            }
        }
        if(count == 0) return 0;
        
        int ranSquare = PApplet.parseInt(random(0,count));
        return freeSquares[ranSquare];
    }
    
    if (AILevel == 3) {
    //WIN IF POSSIBLE-------------------------------------------
    //1st
    if(Board[0][0]==2 && Board[0][1]==2 && Board[0][2]==0) return 1;
    if(Board[0][0]==2 && Board[0][1]==0 && Board[0][2]==2) return 4;
    if(Board[0][0]==0 && Board[0][1]==2 && Board[0][2]==2) return 7;
    //2nd
    if(Board[1][0]==2 && Board[1][1]==2 && Board[1][2]==0) return 2;
    if(Board[1][0]==2 && Board[1][1]==0 && Board[1][2]==2) return 5;
    if(Board[1][0]==0 && Board[1][1]==2 && Board[1][2]==2) return 8;
    //3rd
    if(Board[2][0]==2 && Board[2][1]==2 && Board[2][2]==0) return 3;
    if(Board[2][0]==2 && Board[2][1]==0 && Board[2][2]==2) return 6;
    if(Board[2][0]==0 && Board[2][1]==2 && Board[2][2]==2) return 9;

    //rows
    //1st
    if(Board[0][0]==2 && Board[1][0]==2 && Board[2][0]==0) return 9;
    if(Board[0][0]==2 && Board[1][0]==0 && Board[2][0]==2) return 8;
    if(Board[0][0]==0 && Board[1][0]==2 && Board[2][0]==2) return 7;
    //2nd
    if(Board[0][1]==2 && Board[1][1]==2 && Board[2][1]==0) return 6;
    if(Board[0][1]==2 && Board[1][1]==0 && Board[2][1]==2) return 5;
    if(Board[0][1]==0 && Board[1][1]==2 && Board[2][1]==2) return 4;
    //3rd
    if(Board[0][2]==2 && Board[1][2]==2 && Board[2][2]==0) return 3;
    if(Board[0][2]==2 && Board[1][2]==0 && Board[2][2]==2) return 2;
    if(Board[0][2]==0 && Board[1][2]==2 && Board[2][2]==2) return 1;

    //diagonals
    //1st
    if(Board[0][0]==2 && Board[1][1]==2 && Board[2][2]==0) return 3;
    if(Board[0][0]==2 && Board[1][1]==0 && Board[2][2]==2) return 5;
    if(Board[0][0]==0 && Board[1][1]==2 && Board[2][2]==2) return 7;
    //2nd
    if(Board[0][2]==2 && Board[1][1]==2 && Board[2][0]==0) return 9;
    if(Board[0][2]==2 && Board[1][1]==0 && Board[2][0]==2) return 5;
    if(Board[0][2]==0 && Board[1][1]==2 && Board[2][0]==2) return 1;

    //OTHERWISE BLOCK-------------------------------------------
    //columns
    //1st
    if(Board[0][0]==1 && Board[0][1]==1 && Board[0][2]==0) return 1;
    if(Board[0][0]==1 && Board[0][1]==0 && Board[0][2]==1) return 4;
    if(Board[0][0]==0 && Board[0][1]==1 && Board[0][2]==1) return 7;
    //2nd
    if(Board[1][0]==1 && Board[1][1]==1 && Board[1][2]==0) return 2;
    if(Board[1][0]==1 && Board[1][1]==0 && Board[1][2]==1) return 5;
    if(Board[1][0]==0 && Board[1][1]==1 && Board[1][2]==1) return 8;
    //3rd
    if(Board[2][0]==1 && Board[2][1]==1 && Board[2][2]==0) return 3;
    if(Board[2][0]==1 && Board[2][1]==0 && Board[2][2]==1) return 6;
    if(Board[2][0]==0 && Board[2][1]==1 && Board[2][2]==1) return 9;

    //rows
    //1st
    if(Board[0][0]==1 && Board[1][0]==1 && Board[2][0]==0) return 9;
    if(Board[0][0]==1 && Board[1][0]==0 && Board[2][0]==1) return 8;
    if(Board[0][0]==0 && Board[1][0]==1 && Board[2][0]==1) return 7;
    //2nd
    if(Board[0][1]==1 && Board[1][1]==1 && Board[2][1]==0) return 6;
    if(Board[0][1]==1 && Board[1][1]==0 && Board[2][1]==1) return 5;
    if(Board[0][1]==0 && Board[1][1]==1 && Board[2][1]==1) return 4;
    //3rd
    if(Board[0][2]==1 && Board[1][2]==1 && Board[2][2]==0) return 3;
    if(Board[0][2]==1 && Board[1][2]==0 && Board[2][2]==1) return 2;
    if(Board[0][2]==0 && Board[1][2]==1 && Board[2][2]==1) return 1;

    //diagonals
    //1st
    if(Board[0][0]==1 && Board[1][1]==1 && Board[2][2]==0) return 3;
    if(Board[0][0]==1 && Board[1][1]==0 && Board[2][2]==1) return 5;
    if(Board[0][0]==0 && Board[1][1]==1 && Board[2][2]==1) return 7;
    //2nd
    if(Board[0][2]==1 && Board[1][1]==1 && Board[2][0]==0) return 9;
    if(Board[0][2]==1 && Board[1][1]==0 && Board[2][0]==1) return 5;
    if(Board[0][2]==0 && Board[1][1]==1 && Board[2][0]==1) return 1;

    //CREATE a FORK IF POSSIBLE

    //BLOCK OPPONENT'S FORK IF POSSIBLE
    // diagonals
    if(Board[0][2]==1 && Board[1][1]==2 && Board[2][0]==1 && Board[0][0]==0 && Board[1][0]==0 && Board[0][1]==0) return 8;
    if(Board[0][2]==1 && Board[1][1]==2 && Board[2][0]==1 && Board[2][1]==0 && Board[1][2]==0 && Board[2][2]==0) return 2;
    if(Board[0][0]==1 && Board[1][1]==2 && Board[2][2]==1 && Board[0][1]==0 && Board[0][2]==0 && Board[1][2]==0) return 4;
    if(Board[0][0]==1 && Board[1][1]==2 && Board[2][2]==1 && Board[1][0]==0 && Board[2][0]==0 && Board[2][1]==0) return 6;

    //OTHERWISE GO IN CENTRE IF EMPTY
    if(Board[1][1]==0) return 5;

    //OTHERWISE GO IN A CORNER IF EMPTY
    if(Board[0][0]==0) return 7;
    if(Board[2][0]==0) return 9;
    if(Board[0][2]==0) return 1;
    if(Board[2][2]==0) return 3;

    //OTHERWISE GO IN AN EDGE
    if(Board[1][0]==0) return 8;
    if(Board[0][1]==0) return 4;
    if(Board[2][1]==0) return 6;
    if(Board[1][2]==0) return 2;
    }
    return 0;
}

public void drawCross(float x, float y, float r, float thickness) {
  float cx = leftMargin + (x*boxWidth) + boxWidth/2;
  float cy = topMargin + (y*boxWidth) + boxWidth/2;
  strokeWeight(thickness);
  stroke(0, 0, 255);
  line(cx-r, cy-r, cx+r, cy+r);
  line(cx+r, cy-r, cx-r, cy+r);
}

public void drawNaught(float x, float y, float r, float thickness) {
  float cx = leftMargin + (x*boxWidth) + boxWidth/2;
  float cy = topMargin + (y*boxWidth) + boxWidth/2;
  strokeWeight(thickness);
  noFill();
  stroke(255, 0, 0);
  ellipse(cx, cy, 2*r, 2*r);
}

public int Mouse2X() {
  int X;
  X = PApplet.parseInt((mouseX - leftMargin)/boxWidth);
  return X;
}

public int Mouse2Y() {
  int Y;
  Y = PApplet.parseInt((mouseY - topMargin)/boxWidth);
  return Y;
}

public int checkBoard() { // 0=no winner 1=crosses 2=naughts 3=draw
  stroke(0);
  strokeWeight(15);
  //checking crosses
  //columns
  if(Board[0][0]==1 && Board[0][1]==1 && Board[0][2]==1) {
    line(leftMargin+boxWidth/2, topMargin, leftMargin+boxWidth/2, topMargin+3*boxWidth);
    return 1;
  }
  if(Board[1][0]==1 && Board[1][1]==1 && Board[1][2]==1) {
    line(leftMargin+3*boxWidth/2, topMargin, leftMargin+3*boxWidth/2, topMargin+3*boxWidth);
    return 1;
  }
  if(Board[2][0]==1 && Board[2][1]==1 && Board[2][2]==1) {
    line(leftMargin+5*boxWidth/2, topMargin, leftMargin+5*boxWidth/2, topMargin+3*boxWidth);
    return 1;
  }
  //rows
  if(Board[0][0]==1 && Board[1][0]==1 && Board[2][0]==1) {
    line(leftMargin, topMargin+boxWidth/2, leftMargin+boxWidth*3, topMargin+boxWidth/2);
    return 1;
  }
  if(Board[0][1]==1 && Board[1][1]==1 && Board[2][1]==1) {
    line(leftMargin, topMargin+3*boxWidth/2, leftMargin+boxWidth*3, topMargin+3*boxWidth/2);
    return 1;
  }
  if(Board[0][2]==1 && Board[1][2]==1 && Board[2][2]==1) {
    line(leftMargin, topMargin+5*boxWidth/2, leftMargin+boxWidth*3, topMargin+5*boxWidth/2);
    return 1;
  }
  //diagonals
  if(Board[0][0]==1 && Board[1][1]==1 && Board[2][2]==1) {
    line(leftMargin, topMargin, leftMargin+boxWidth*3, topMargin+boxWidth*3);
    return 1;
  }
  if(Board[0][2]==1 && Board[1][1]==1 && Board[2][0]==1) {
    line(leftMargin+boxWidth*3, topMargin, leftMargin, topMargin+boxWidth*3);
    return 1;
  }

  //checking naughts
  //columns
  if(Board[0][0]==2 && Board[0][1]==2 && Board[0][2]==2) {
    line(leftMargin+boxWidth/2, topMargin, leftMargin+boxWidth/2, topMargin+3*boxWidth);
    return 2;
  }
  if(Board[1][0]==2 && Board[1][1]==2 && Board[1][2]==2) {
    line(leftMargin+3*boxWidth/2, topMargin, leftMargin+3*boxWidth/2, topMargin+3*boxWidth);
    return 2;
  }
  if(Board[2][0]==2 && Board[2][1]==2 && Board[2][2]==2) {
    line(leftMargin+5*boxWidth/2, topMargin, leftMargin+5*boxWidth/2, topMargin+3*boxWidth);
    return 2;
  }
  //rows
  if(Board[0][0]==2 && Board[1][0]==2 && Board[2][0]==2) {
    line(leftMargin, topMargin+boxWidth/2, leftMargin+boxWidth*3, topMargin+boxWidth/2);
    return 2;
  }
  if(Board[0][1]==2 && Board[1][1]==2 && Board[2][1]==2) {
    line(leftMargin, topMargin+3*boxWidth/2, leftMargin+boxWidth*3, topMargin+3*boxWidth/2);
    return 2;
  }
  if(Board[0][2]==2 && Board[1][2]==2 && Board[2][2]==2) {
    line(leftMargin, topMargin+5*boxWidth/2, leftMargin+boxWidth*3, topMargin+5*boxWidth/2);
    return 2;
  }
  //diagonals
  if(Board[0][0]==2 && Board[1][1]==2 && Board[2][2]==2) {
    line(leftMargin, topMargin, leftMargin+boxWidth*3, topMargin+boxWidth*3);
    return 2;
  }
  if(Board[0][2]==2 && Board[1][1]==2 && Board[2][0]==2) {
    line(leftMargin+boxWidth*3, topMargin, leftMargin, topMargin+boxWidth*3);
    return 2;
  }

  int count = 0;
  for(int a=0; a<3; a++){ // loop through checking if all squares are occupied, if they are it's a draw
      for(int b=0; b<3; b++){
      if(Board[a][b]!=0) count++;
      }
  }
  if(count == 9) return 3;
  else return 0;
}

public void updateBottom() {
  stroke(255);
  fill(255);
  rect(0, height-bottomMargin, width, height);
  stroke(0);
  fill(0);
  textFont(f, 30);
  textAlign(CENTER);
  if(gameAI == false) text("Game mode: Player vs. Player\nClick here to change", width/2, height-2*bottomMargin/3);
  else {
    text("Game mode: Player vs. AI\nClick here to change", 2*width/5, height-2*bottomMargin/3); 
    textAlign(LEFT); 
    text("Level: " + AILevel, 3*width/4, height-bottomMargin/2);
    text("+", width-rightMargin/2, topMargin+boxWidth/2);
    text("-", width-rightMargin/2, topMargin+5*boxWidth/2);
  }
}

public void updateTop() {
  //Clear top
  stroke(255);
  fill(255);
  rect(0, 0, width, topMargin);
  
  //Show status
  textFont(f, 30);
  textAlign(CENTER);
  fill(0);
  int gameStatus = checkBoard();
  if (gameStatus == 0) {
    if (turn == 1) text("It's crosses' turn!\n", width/2, topMargin/3);
    else if (turn == 2) text("It's naughts' turn!", width/2, topMargin/3);
  }
  else if (gameStatus == 1) {
    text("Crosses wins!", width/2, topMargin/2);
    textFont(f,24);
    text("Right click to reset!", width/2, topMargin/2 +30);
    gameFinished = true;
  }
  else if (gameStatus == 2) {
    text("Naughts wins!", width/2, topMargin/2);
    textFont(f,24);
    text("Right click to reset!", width/2, topMargin/2 +30);
    gameFinished = true;
  }
  else if (gameStatus == 3) {
    text("It's a draw!", width/2, topMargin/2);
    textFont(f,24);
    text("Right click to reset!", width/2, topMargin/2 +30);
    gameFinished = true;
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Naughts_Crosses_AI" });
  }
}
