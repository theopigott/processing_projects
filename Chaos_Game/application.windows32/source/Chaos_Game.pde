float lnth = 100;

float side_length = 800;
float half_length = side_length/2;
float triangle_height = side_length*sin(PI/3);

PVector ver1 = new PVector (0,triangle_height);
PVector ver2 = new PVector (side_length, triangle_height);
PVector ver3 = new PVector (half_length, 0);

PVector plot = new PVector();

void setup() {
  size(850,850);
  background(255);
  
  point(ver1.x,ver1.y);
  point(ver2.x,ver2.y);
  point(ver3.x,ver3.y);
  
  plot = new PVector (random(0,side_length),random(0,triangle_height));
}

void draw() {
  int ranint = int(random(1,4));
  
  if (ranint == 1) {
    plot = PVector.add(plot,ver1);
  }
  
  else if (ranint == 2) {
    plot = PVector.add(plot,ver2);
  }
  
  else if (ranint == 3) {
    plot = PVector.add(plot,ver3);
  }
  plot.mult(0.5);
  
  point(plot.x,plot.y);
}