public class Block{

  String type;
  float size_x;
  float size_y;
  int x_pos;
  int y_pos;
  int is_empty;
  int r;
  int g;
  int b;
  
  Grid grid;
  
  Block(){
    
    this.is_empty = 1;
  }
  
  Block(float size_x, float size_y, int init_x, int init_y, Grid grid){
  
    this.size_x= size_x;
    this.size_y = size_y;
    this.x_pos = init_x;
    this.y_pos = init_y;
    this.grid = grid;
    this.is_empty = 0;
    this.r = 255;
    this.g = 255;
    this.b = 255;
    this.type = "BLOCK";
  }
  
  int isEmpty(){
    return is_empty;
  }
  
  void updatePos(){}
  
  void drawBlock(){
  
    if (is_empty == 1) return;
    noStroke();
    fill(this.r, this.g, this.b);
    rect(this.x_pos * this.size_x, this.y_pos * this.size_y, this.size_x, this.size_y);
  }
  
  void setPos(int x, int y){
    
    this.x_pos = x;
    this.y_pos = y;
  }
  
  int[] getPos(){
    int[] pos = {this.x_pos, this.y_pos};
    return pos;
  }
}
