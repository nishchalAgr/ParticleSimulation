import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class particle_sim extends PApplet {

Grid current_grid;
int c_x = 150;
int c_y = 5;
String[] types = {"DIRT", "WATER"};
int current_type_index = 0;
public void setup(){
  
  current_grid = new Grid(300, 300, 900, 900);
}

public void draw(){
  noCursor();
  background(0, 0, 0);
  fill(255, 255, 255);
  textSize(16);
  text(types[current_type_index], 0, 0);
  current_grid.updateGrid();
  current_grid.drawGrid();
  current_grid.drawCursor((int)mouseX, (int)mouseY);
}

public void keyPressed(){
  
  if(key == CODED){
    
    if(keyCode == SHIFT){
      
      float[] arr = current_grid.getBlockSize();
      current_grid.changeBlock((int)(mouseX/arr[0]), (int)(mouseY/arr[1]), types[current_type_index]);
    }
  }
  
  if(key == 97){//a
      current_type_index--;
      if(current_type_index < 0) current_type_index = types.length - 1;
  }
  
  if(key == 100){//d
      current_type_index++;
      if(current_type_index >= types.length) current_type_index = 0;
  }
}
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
  
  public int isEmpty(){
    return is_empty;
  }
  
  public void updatePos(){}
  
  public void drawBlock(){
  
    if (is_empty == 1) return;
    noStroke();
    fill(this.r, this.g, this.b);
    rect(this.x_pos * this.size_x, this.y_pos * this.size_y, this.size_x, this.size_y);
  }
  
  public void setPos(int x, int y){
    
    this.x_pos = x;
    this.y_pos = y;
  }
  
  public int[] getPos(){
    int[] pos = {this.x_pos, this.y_pos};
    return pos;
  }
}
class Dirt extends Block{
  
  Dirt(float size_x, float size_y, int init_x, int init_y, Grid grid){
    
    super(size_x, size_y, init_x, init_y, grid);
    this.r = 124;
    this.g = 94;
    this.b = 66;
    this.type = "DIRT";
  }
  
  public void updatePos(){
    if(this.grid.isBlockHere(this.x_pos, this.y_pos + 1) == -1) {return;}
    if(this.grid.isBlockHere(this.x_pos, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos, this.y_pos + 1); return;}
    else if(this.grid.isBlockHere(this.x_pos + 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos + 1, this.y_pos + 1); return;}
    else if(this.grid.isBlockHere(this.x_pos - 1, this.y_pos + 1) == 0) {this.grid.moveBlock(this, this.x_pos - 1, this.y_pos + 1); return;}
  } 
}
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
  
  public void drawCursor(int x, int y){
    stroke(255, 255, 255);
    noFill();
    rect(x, y, this.block_size_x, this.block_size_y);
  }
  
  public int isBlockHere(int x, int y){
  
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return -1;
    
    if (this.grid[x][y].isEmpty() == 1) return 0;
    
    return 1;
  }
  
  public void updateGrid(){
  
    for(int x = num_blocks_x - 1; x >= 0; x--) {
      
      for(int y = num_blocks_y - 1; y >= 0; y--) {
    
        this.grid[x][y].updatePos();
      }
    }
  }
  
  public void moveBlock(Block block, int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;

    int[] pos = block.getPos();
    
    this.grid[x][y] = block;
    block.setPos(x, y);
    deleteBlock(pos[0], pos[1]);
  }
  
  public void changeBlock(int x, int y, String type){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    if(type.equals("DIRT"))
      this.grid[x][y] = new Dirt(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);
    
    else if(type.equals("WATER"))
      this.grid[x][y] = new Water(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);

    this.grid[x][y].setPos(x, y);
  }
  
  public void changeBlock(int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    this.grid[x][y] = new Block(this.block_size_x, this.block_size_y, (int)(x * this.block_size_x), (int)(y * this.block_size_y), this);
    this.grid[x][y].setPos(x, y);
  }
  
  public void deleteBlock(int x, int y){
    
    if (x < 0 || x >= num_blocks_x || y < 0 || y >= num_blocks_y) return;
    
    this.grid[x][y] = new Block();
  }
  
  public void drawGrid(){
  
    for (int x = 0; x < this.num_blocks_x; x++) {
    
      for (int y = 0; y < this.num_blocks_y; y++) {
      
        this.grid[x][y].drawBlock();
      }
    }
  }
  
  public float[] getBlockSize(){
    
    float[] arr = {this.block_size_x, this.block_size_y};
    
    return arr;
  }
}
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
  
  public void updatePos(){
    
    if(is_random <= 5) go_left = PApplet.parseInt(random(2));
    
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
  public void settings() {  size(900,900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "particle_sim" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
