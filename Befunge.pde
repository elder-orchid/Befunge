import peasy.*;

PeasyCam cam;
Cubie[][][] cube;
Interpreter interp;
int[] dim;
void setup() {
  size(600, 600, P3D);
  textMode(SHAPE);
  
  interp = new Interpreter("program.bf3", false);
  dim = interp.loadFile();
  println("dim: " + dim[0] + ":" + dim[1] + ":" + dim[2]);
  cube = new Cubie[dim[0]][dim[1]][dim[2]];
  
  cam = new PeasyCam(this, 400);
  float len = 50;
  for(int i = 0; i < dim[0]; i++) {
    for(int j = 0; j < dim[1]; j++) {
      for(int k = 0; k < dim[2]; k++) {
        float offset =  len * 0.5;
        float x = i*len - offset*(dim[0]-1);
        float y = j*len - offset*(dim[1]-1);
        float z = k*len - offset*(dim[2]-1);
        println(i + ":" + j + ":" + k);
        cube[i][j][k] = new Cubie(x, y, z, len, interp.getChar(i, j, k));
      }
    }
  }
  
  Interpreter interp = new Interpreter("program.bf3", false);
  interp.loadFile();
  try {
    interp.run();
  }catch(InterruptedException e) {}
  
}

void draw() {
  background(200);
 
  for(int i = 0; i < dim[0]; i++) {
    for(int j = 0; j < dim[1]; j++) {
      for(int k = 0; k < dim[2]; k++) {
        cube[i][j][k].show();
      }
    }
  }
  
}
