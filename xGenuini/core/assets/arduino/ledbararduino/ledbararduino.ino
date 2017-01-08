#include <Grove_LED_Bar.h>

Grove_LED_Bar bar(9, 8, 0);  // Clock pin, Data pin, Orientation

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bar.begin();
  bar.setBits(0x0);
}

void loop() {
  int rec;
  
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){
    rec = Serial.read();

    if(rec=='e'){
      bar.setBits(0x3ff);
    }else{
      bar.setBits(0x0);
    }
  }

}
