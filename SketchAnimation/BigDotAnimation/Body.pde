color defaultLineColor = color(11,255,167, 40);
color defaultFillColor = color(11,255,167, 3);

color viewingLineColor = color(255,51,0,60);
color viewingFillColor = color(255,51,0,1);


//color defaultLineColor = color(30, 60);
//color defaultFillColor = color(30, 3);

class Body {

  float centerX;
  float centerY;

  int headRadius = floor(width/30);

  color lineColor;
  color fillColor;

  int bRandom = floor(width/600);
  
  boolean flagViewing;
  
  void drawBody() {
    
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

  void randomVertex(float _x, float _y) {
    vertex(_x+random(-bRandom,bRandom), _y+random(-bRandom,bRandom));
  }
}

