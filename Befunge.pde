import peasy.*;

 PeasyCam cam;
 int dim = 3;
 Cubie[][][] cube = new Cubie[dim][dim][dim];

void setup() {
  size(600, 600, P3D);
  textMode(SHAPE);
  
  cam = new PeasyCam(this, 400);
  float len = 50;
  for(int i = 0; i < dim; i++) {
    for(int j = 0; j < dim; j++) {
      for(int k = 0; k < dim; k++) {
        float offset = (dim-1) * len * 0.5;
        float x = i*len - offset;
        float y = j*len - offset;
        float z = k*len - offset;
        cube[i][j][k] = new Cubie(x, y, z, len);
      }
    }
  }
  
  Interpreter interp = new Interpreter();
  interp.loadFile("program.bf3");
  try {
    interp.run();
  }catch(InterruptedException e) {}
  
}

void draw() {
  background(200);
 
  for(int i = 0; i < dim; i++) {
    for(int j = 0; j < dim; j++) {
      for(int k = 0; k < dim; k++) {
        cube[i][j][k].show();
      }
    }
  }
  
}
