float lnth = 800;

PVector loc;
PVector dir;

PFont f;

int initialn=1;
int n;

void setup() {
  size(800,800);
  background(255);
  stroke(0);
  f = createFont("Arial",20,true); 
  textFont(f);
  fill(0);
  textAlign(CENTER);
  text("This program draws a Koch curve. Left click to increase n and re-draw.", width/2, 50);
  text("Current value of n:", width/2, 100);
  text(initialn, width/2, 150);
  
  fill(255);
  dir = new PVector(1,0);
  loc = new PVector(0, height/2);
  n = initialn;
  beginShape();
  vertex(loc.x,loc.y);
  Koch(initialn);
  endShape();
}

void draw() {
}

void mousePressed()
{
  if(mouseButton == LEFT) {
    if(initialn<8) initialn++;
    else initialn=1;
    stroke(255);
    rect(0, 110, width, height-110);
    fill(0);
    text(initialn, width/2, 150);
    
    stroke(0);
    fill(255);
    dir = new PVector(1,0);
    loc = new PVector(0, height/2);
    n = initialn;
    beginShape();
    vertex(loc.x,loc.y);
    Koch(initialn);
    endShape();
  }
}

void Koch(int n)
{
  if(n>1) Koch(n-1);
  else fd();
  l60();
  if(n>1) Koch(n-1);
  else fd();
  r120();
  if(n>1) Koch(n-1);
  else fd();
  l60();
  if(n>1) Koch(n-1);
  else fd();
}

PVector rot(PVector direction, float angle)
{
  PVector outputVector;
  angle = radians(angle);
  float[][] rotMatrix = new float[2][2];
  rotMatrix[0][0] = cos(angle);
  rotMatrix[0][1] = sin(angle);
  rotMatrix[1][0] = -sin(angle);
  rotMatrix[1][1] = cos(angle);
  
  float A = direction.x*rotMatrix[0][0] + direction.y*rotMatrix[0][1];
  float B = direction.x*rotMatrix[1][0] + direction.y*rotMatrix[1][1];
  outputVector = new PVector(A,B);
  return outputVector;  
}

void l60()
{
  dir = rot(dir, 60);
}

void r120()
{
  dir = rot(dir, -120);
}

void fd()
{
  dir.normalize();
  dir.mult(lnth/pow(3,initialn));
  loc.add(dir); 
  vertex(loc.x,loc.y); 
}
