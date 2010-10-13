import javax.media.opengl.*;
import processing.opengl.*;


String[] names = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Bruce", "Jenny"};
HashMap playersHashMap;

String viewingPlayer = "Steve";

color bgColor = color(14,40,59);

int demoMode;


Person zoominPerson;

void setup(){
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
  
  color randomColor = color(random(255),random(255),random(255));
  zoominPerson = new Person(names[0], randomColor);
  
  smooth();
}

void draw(){

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

void keyPressed() {
  
  if (key == 's')
    saveFrame(); 
  
  else if(key == '0')
    demoMode = 0;
    
  else if(key == '1')
    demoMode = 1;
  
    
    
}
