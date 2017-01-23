#include <Grove_LED_Bar.h>
Grove_LED_Bar bar(9, 8, 0);  // Clock pin, Data pin, Orientation


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bar.begin();

}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){
    int level= Serial.read()/10;
    bar.setLevel(level);
  }
}
