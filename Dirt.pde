class Dirt extends Block{
  
  Dirt(float size_x, float size_y, int init_x, int init_y, Grid grid){
    
    super(size_x, size_y, init_x, init_y, grid);
    this.r = 124;
    this.g = 94;
    this.b = 66;
    this.type = "DIRT";
  }
  
  void updatePos(){
    if(this.grid.isBlockHere(this.x_pos, this.y_pos + 1) == -1) {return;}
    if(this.grid.isBlockHere(this.x_pos, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos, this.y_pos + 1); return;}
    else if(this.grid.isBlockHere(this.x_pos + 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos + 1, this.y_pos + 1); return;}
    else if(this.grid.isBlockHere(this.x_pos - 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos - 1, this.y_pos + 1); return;}
  } 
}
