public class Grid{

  Block grid[][];
  
  float block_size_x;
  float block_size_y;
  
  int num_blocks_x;
  int num_blocks_y;
  
  Grid(int num_blocks_x, int num_blocks_y, int x_window_size, int y_window_size){
    
    this.num_blocks_x = num_blocks_x;
    this.num_blocks_y = num_blocks_y;
    
    this.block_size_x = x_window_size / num_blocks_x;
    this.block_size_y = y_window_size / num_blocks_y;

    this.grid = new Block[num_blocks_x][num_blocks_y];
    
    for (int x = 0; x < num_blocks_x; x++) {
    
      for (int y = 0; y < num_blocks_y; y++) {
      
        this.grid[x][y] = new Block();
      }
    }
  }
  
  void drawCursor(int x, int y){
    stroke(255, 255, 255);
    noFill();
    rect(x, y, this.block_size_x, this.block_size_y);
  }
  
  int isBlockHere(int x, int y){
  
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return -1;
    
    if (this.grid[x][y].isEmpty() == 1) return 0;
    
    return 1;
  }
  
  void updateGrid(){
  
    for(int x = num_blocks_x - 1; x >= 0; x--) {
      
      for(int y = num_blocks_y - 1; y >= 0; y--) {
    
        this.grid[x][y].updatePos();
      }
    }
  }
  
  void moveBlock(Block block, int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;

    int[] pos = block.getPos();
    
    this.grid[x][y] = block;
    block.setPos(x, y);
    deleteBlock(pos[0], pos[1]);
  }
  
  void changeBlock(int x, int y, String type){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    if(type.equals("DIRT"))
      this.grid[x][y] = new Dirt(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);
    
    else if(type.equals("WATER"))
      this.grid[x][y] = new Water(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);

    this.grid[x][y].setPos(x, y);
  }
  
  void changeBlock(int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    this.grid[x][y] = new Block(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);
    this.grid[x][y].setPos(x, y);
  }
  
  void deleteBlock(int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    this.grid[x][y] = new Block();
  }
  
  void drawGrid(){
  
    for (int x = 0; x < this.num_blocks_x; x++) {
    
      for (int y = 0; y < this.num_blocks_y; y++) {
      
        this.grid[x][y].drawBlock();
      }
    }
  }
  
  float[] getBlockSize(){
    
    float[] arr = {this.block_size_x, this.block_size_y};
    
    return arr;
  }
}
