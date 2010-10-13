class Person extends Body{
  
  String name;
  
  float x;
  float y;
  
  float moveSpeedX;
  float moveSpeedY;
  
  int moveCounter;
  int moveCycle;
  
  Body body;
  
  Person(String _name, color _color){
    lineColor = color(red(_color), green(_color), blue(_color), 200);
    fillColor = color(red(_color), green(_color), blue(_color), 100);
    name = _name;
    centerX = width/2+random(-width/2+100, width/2-100);
    centerY = height/2+random(-height/2+100, height/2-100);
    
    moveSpeedX = 0;
    moveSpeedY = 0;
    
    flagViewing = false;
    
    moveCounter = -1;
  }
  
  void simulateMove(){
    
    if(moveCounter==-1){
      if(random(100)<0.1){ // start moving
        moveCounter++;
        moveSpeedX = random(-5,5);
        moveSpeedY = random(-5,5);
        
        moveCycle = floor( random(30, 70) );
      }
    }
    else if(moveCounter<moveCycle){
      moveCounter++;
      centerX += moveSpeedX*0.2;
      centerY += moveSpeedY*0.2;
    }
    else if(moveCounter>=moveCycle){
      moveCounter=-1;
      moveSpeedX=0;
      moveSpeedY=0;
      moveCycle=0;
    }
    
  }
  
  void draw(){
    simulateMove();
    drawBody();
  }
 
  
  
}


