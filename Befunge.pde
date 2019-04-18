import g4p_controls.*;
import peasy.*;

PeasyCam cam;
Cubie[][][] cube;
Interpreter interp;
int[] dim;
boolean cameraLock = false;
int requestedFace = 0;
int timer;
PFont mono;
String output;
int buttonWidth;
boolean crawl = false;

void setup() {
  size(1280, 720, P3D);
  textMode(SHAPE);
  output = "";
  interp = new Interpreter("program.bf3", false);
  dim = interp.loadFile();
  cube = new Cubie[dim[0]][dim[1]][dim[2]];
  cam = new PeasyCam(this, 400);
  buttonWidth = 60;
  float len = 50;
  for(int i = 0; i < dim[0]; i++) {
    for(int j = 0; j < dim[1]; j++) {
      for(int k = 0; k < dim[2]; k++) {
        float offset =  len * 0.5;
        float x = i*len - offset*(dim[0]-1);
        float y = j*len - offset*(dim[1]-1);
        float z = k*len - offset*(dim[2]-1);
        
        cube[i][j][k] = new Cubie(x, y, z, len, interp.getChar(i, j, k));
      }
    }
  }
  
  // The font "andalemo.ttf" must be located in the 
  // current sketch's "data" directory to load successfully
  mono = createFont("VeraMono.ttf", 32);
  textFont(mono);
}

void draw() {
  background(127);
  if(cameraLock) {
    
  }
  
  for(int i = 0; i < dim[0]; i++) {
    for(int j = 0; j < dim[1]; j++) {
      for(int k = 0; k < dim[2]; k++) {
        cube[i][j][k].show(i == interp.x && j == interp.y && k == interp.z);
      }
    }
  }
  if (crawl && millis() - timer >= 20) {
    interp.step();
    timer = millis();
  }
  
  cam.beginHUD();
  strokeWeight(1);
  textSize(15);

  fill(0);
  rect(0, 0, width, height/5);
  fill(255);
  text(interp.stack.toString(), 10, 30);
  cam.endHUD();

  
  //cam.rotateX(rot[0]);
  //cam.rotateY(rot[1]);
  //cam.rotateZ(rot[2]);
  
}

void keyPressed() {
  cameraLock ^= true;
  try {
    println(key);
    switch(key) {
      // Run
      case '1':
        interp.run();
        break;
        
      // Crawl
      case '2':
        crawl = true;
        break;
      case '3':
        interp.step();
        break;
        
      case '4':
        interp.stop();
        break;
      case '5':
        interp.reset();
        break;
    }    
  }
  catch(InterruptedException e) {
  }
}
