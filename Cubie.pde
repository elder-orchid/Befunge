class Cubie{
  PVector pos;
  float len;
  char letter;
  
  Cubie(float x, float y , float z, float len_, char letter_) {
    pos = new PVector(x, y, z);
    len = len_;
    letter = letter_;
  }
  
  void show(boolean current) {
    
    stroke(0);
    strokeWeight(4);
    
    // Draw cubie
    pushMatrix();
    translate(pos.x, pos.y, pos.z);
    
    // Draw text
    pushMatrix();
    float[] rotations = cam.getRotations();
    rotateX(rotations[0]);
    rotateY(rotations[1]);
    rotateZ(rotations[2]);
    //println(rotations[0] + ":" + rotations[1] + ":" + rotations[2]);
    fill(255);
    textSize(40);
    text(letter, -len/4, +len/4,0);
    popMatrix();
    
    // Draw wireframe
    if(current) {
      fill(255,0,0,63);
    }
    else {
      noFill();
    }
    box(len);
    
    popMatrix();
  }
}
