class Cubie{
  PVector pos;
  float len;
  
  Cubie(float x, float y , float z, float len_) {
    pos = new PVector(x, y, z);
    len = len_;
  }
  
  void show() {
    // Draw cubie
    noFill();
    //fill(255,0,0,63);
    stroke(0);
    strokeWeight(8);
    pushMatrix();
    translate(pos.x, pos.y, pos.z);
    box(len);
    
    // Draw text
    float[] rotations = cam.getRotations();
    rotateX(rotations[0]);
    rotateY(rotations[1]);
    rotateZ(rotations[2]);
    fill(255);
    textSize(40);
    text("a", -len/4, +len/4,0);
    popMatrix();
  }
}