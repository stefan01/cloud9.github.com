

color defaultLineColor = color(30, 60);
color defaultFillColor = color(30, 3);

class Body {

  float centerX;
  float centerY;

  int headRadius = floor(width/30);

  color lineColor;
  color fillColor;

  int bRandom = floor(width/300);

  void drawBody() {
    
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

  void randomVertex(float _x, float _y) {
    vertex(_x+random(-bRandom,bRandom), _y+random(-bRandom,bRandom));
  }
}

