class Water extends Block{

  int go_left;
  int is_random = 0;
  
  Water(float size_x, float size_y, int init_x, int init_y, Grid grid){
    
    super(size_x, size_y, init_x, init_y, grid);
    this.r = 29;
    this.g = 162;
    this.b = 216;
    this.type = "WATER";
  }
  
  void updatePos(){
    
    if(is_random <= 5) go_left = int(random(2));
    
    if(this.grid.isBlockHere(this.x_pos, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos, this.y_pos + 1);}
    else if(this.grid.isBlockHere(this.x_pos + 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos + 1, this.y_pos + 1);}
    else if(this.grid.isBlockHere(this.x_pos - 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos - 1, this.y_pos + 1);} 

    else if (go_left == 0){
      if(this.grid.isBlockHere(this.x_pos + 1, this.y_pos) == 0) {this.grid.moveBlock(this, this.x_pos + 1, this.y_pos); is_random++;}
      else if(this.grid.isBlockHere(this.x_pos - 1, this.y_pos) == 0) {this.grid.moveBlock(this, this.x_pos - 1, this.y_pos); is_random++;}
    }
    
    else if (go_left == 1){
      if(this.grid.isBlockHere(this.x_pos - 1, this.y_pos) == 0) {this.grid.moveBlock(this, this.x_pos - 1, this.y_pos); is_random++;}
      else if(this.grid.isBlockHere(this.x_pos + 1, this.y_pos) == 0) {this.grid.moveBlock(this, this.x_pos + 1, this.y_pos); is_random++;}
    }
  } 
}
