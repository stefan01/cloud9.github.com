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

public class BigDotAnimation extends PApplet {





String[] names = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Bruce", "Jenny"};
HashMap playersHashMap;

String viewingPlayer = "Steve";

int bgColor = color(14,40,59);

int demoMode;


Person zoominPerson;

public void setup(){
  size(800, 600, OPENGL);
//  background(30);
  background(14,40,59);
  demoMode = 0;
  
  playersHashMap = new HashMap();
  
//  // initiate Player Info
//  for(int i=0; i<names.length; i++){
//    color randomColor = color(random(255),random(255),random(255));
//    Person person = new Person(names[i], randomColor);
//    playersHashMap.put( names[i], person);
//  }
  
  int randomColor = color(random(255),random(255),random(255));
  zoominPerson = new Person(names[0], randomColor);
  
  smooth();
}

public void draw(){

  noStroke();
  fill(255,5);
//  fill(30,5);
  rect(0,0,width,height);
 
//  Iterator i = playersHashMap.entrySet().iterator();  // Get an iterator
//
//  while (i.hasNext()) {
//    
//    Map.Entry entry = (Map.Entry)i.next();
//    Person person = (Person)entry.getValue();
//    person.flagViewing = false;
//    
//    if(demoMode==0)
//      person.draw();
//      
//    else if(demoMode==1)
//    {
//      if(person.name.equals(viewingPlayer))
//        person.flagViewing = true;
//      person.draw();
//    }
//    
//    
//  }

  zoominPerson.centerX = width/2+random(-20,20);  
  zoominPerson.centerY = height/2+random(-20,20);
  zoominPerson.headRadius = floor( width/3+random(-5,5) );
  zoominPerson.drawBody();
  
  
}

public void keyPressed() {
  
  if (key == 's')
    saveFrame(); 
  
  else if(key == '0')
    demoMode = 0;
    
  else if(key == '1')
    demoMode = 1;
  
    
    
}
int defaultLineColor = color(11,255,167, 40);
int defaultFillColor = color(11,255,167, 3);

int viewingLineColor = color(255,51,0,60);
int viewingFillColor = color(255,51,0,1);


//color defaultLineColor = color(30, 60);
//color defaultFillColor = color(30, 3);

class Body {

  float centerX;
  float centerY;

  int headRadius = floor(width/30);

  int lineColor;
  int fillColor;

  int bRandom = floor(width/600);
  
  boolean flagViewing;
  
  public void drawBody() {
    
    float headCenterX = centerX+random(-bRandom,bRandom);
    float headCenterY = centerY+random(-bRandom,bRandom);

    // head
    noStroke();
    if(!flagViewing)
      fill(defaultFillColor);
    else
      fill(viewingFillColor);
    ellipse( headCenterX, headCenterY, headRadius, headRadius);

    noFill();
    strokeWeight(5);
    if(!flagViewing)
      stroke(defaultLineColor);
    else
      stroke(viewingLineColor);
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
    
    flagViewing = false;
    
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
    PApplet.main(new String[] { "--bgcolor=#ffffff", "BigDotAnimation" });
  }
}
