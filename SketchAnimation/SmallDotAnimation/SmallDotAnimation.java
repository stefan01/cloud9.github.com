import processing.core.*; 
import processing.xml.*; 

import javax.media.opengl.*; 
import processing.opengl.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class SmallDotAnimation extends PApplet {





String[] names = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Bruce", "Jenny"};
HashMap playersHashMap;

int bgColor = color(14,40,59);

public void setup(){
  size(800, 600, OPENGL);
  background(30);

  
  playersHashMap = new HashMap();
  
  // initiate Player Info
  for(int i=0; i<names.length; i++){
    int randomColor = color(random(255),random(255),random(255));
    Person person = new Person(names[i], randomColor);
    playersHashMap.put( names[i], person);
  }
  
  smooth();
}

public void draw(){
  
  noStroke();
  fill(255,5);
  rect(0,0,width,height);
   
  Iterator i = playersHashMap.entrySet().iterator();  // Get an iterator

  while (i.hasNext()) {
    
    Map.Entry entry = (Map.Entry)i.next();
    Person person = (Person)entry.getValue();
    
    person.draw();
  }
  
}

public void keyPressed() {
  if (key == 's') {
    saveFrame(); 
  }
}


int defaultLineColor = color(30, 60);
int defaultFillColor = color(30, 3);

class Body {

  float centerX;
  float centerY;

  int headRadius = floor(width/30);

  int lineColor;
  int fillColor;

  int bRandom = floor(width/300);

  public void drawBody() {
    
    float headCenterX = centerX+random(-bRandom,bRandom);
    float headCenterY = centerY+random(-bRandom,bRandom);

    // head
    noStroke();
    fill(defaultFillColor);
    ellipse( headCenterX, headCenterY, headRadius, headRadius);

    noFill();
    strokeWeight(2);
    stroke(defaultLineColor);
    float angleStart = PI*random(1);
    float angleStop = angleStart + 2*PI*random(1);
    arc( headCenterX, headCenterY, headRadius, headRadius, angleStart, angleStop);


    
  }

  public void randomVertex(float _x, float _y) {
    vertex(_x+random(-bRandom,bRandom), _y+random(-bRandom,bRandom));
  }
}

class Person extends Body{
  
  String name;
  
  float x;
  float y;
  
  float moveSpeedX;
  float moveSpeedY;
  
  int moveCounter;
  int moveCycle;
  
  Body body;
  
  Person(String _name, int _color){
    lineColor = color(red(_color), green(_color), blue(_color), 200);
    fillColor = color(red(_color), green(_color), blue(_color), 100);
    name = _name;
    centerX = width/2+random(-width/2+100, width/2-100);
    centerY = height/2+random(-height/2+100, height/2-100);
    
    moveSpeedX = 0;
    moveSpeedY = 0;
    
    moveCounter = -1;
  }
  
  public void simulateMove(){
    
    if(moveCounter==-1){
      if(random(100)<0.1f){ // start moving
        moveCounter++;
        moveSpeedX = random(-5,5);
        moveSpeedY = random(-5,5);
        
        moveCycle = floor( random(30, 70) );
      }
    }
    else if(moveCounter<moveCycle){
      moveCounter++;
      centerX += moveSpeedX*0.2f;
      centerY += moveSpeedY*0.2f;
    }
    else if(moveCounter>=moveCycle){
      moveCounter=-1;
      moveSpeedX=0;
      moveSpeedY=0;
      moveCycle=0;
    }
    
  }
 
  
  public void draw(){
    simulateMove();

    drawBody();
  }
  
  
  
}


  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#ffffff", "SmallDotAnimation" });
  }
}
