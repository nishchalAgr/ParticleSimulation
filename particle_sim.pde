Grid current_grid;
int c_x = 150;
int c_y = 5;
String[] types = {"DIRT", "WATER"};
int current_type_index = 0;
void setup(){
  size(900,900);
  current_grid = new Grid(300, 300, 900, 900);
}

void draw(){
  noCursor();
  background(0, 0, 0);
  fill(255, 255, 255);
  textSize(16);
  text(types[current_type_index], 0, 0);
  current_grid.updateGrid();
  current_grid.drawGrid();
  current_grid.drawCursor((int)mouseX, (int)mouseY);
}

void keyPressed(){
  
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
