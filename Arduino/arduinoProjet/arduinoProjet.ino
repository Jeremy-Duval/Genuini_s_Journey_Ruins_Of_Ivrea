#include <Grove_LED_Bar.h>
#include <Wire.h>
#include <string.h>
#include "rgb_lcd.h"

Grove_LED_Bar bar(9, 8, 0);  // Clock pin, Data pin, Orientation
rgb_lcd lcd;

const int colorR = 255;
const int colorG = 0;
const int colorB = 0;

String sentence[] = {"Be Happy !","Smile my gamer","I love you"};
unsigned long previousMillis=0;
boolean changeSentence = false;
int i = 0;
  
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bar.begin();
  bar.setBits(0x0);
  lcd.begin(16, 2);
}

void loop() {
  String rec;
  
  unsigned long currentMillis = millis()/1000;
  
  // put your main code here, to run repeatedly:
    
  if(Serial.available()>0){
   
    rec = Serial.readString();

    //parser avec les deux strings separes par ;
    int commaIndex = rec.indexOf(';');
    String firstValue = rec.substring(0, commaIndex);
    String secondValue = rec.substring(commaIndex+1);

    int life = secondValue.toInt(); 

      if(firstValue=="menu"){
                //lcd.setRGB(colorR, colorG, colorB);
               lcd.clear();
               lcd.print("Genuini's Journey");
               lcd.setCursor(0, 1);
               lcd.print("Main menu");
               bar.setBits(0x3ff);
      }else if(firstValue=="game"){
               lcd.clear();   //clear au premier passage
               lcd.print("Life: ");
               lcd.setCursor(6,0);
               lcd.print(life);
               bar.setLevel(life/10);  
               Serial.print(life);     
               changeSentence = !changeSentence;  //changeSentence permet de bloquer le changement de texte sur la phrase en cours
      }else{
               bar.setBits(0x0);
      }
}
if(changeSentence == true){
      lcd.setCursor(0, 1);
      lcd.print(sentence[i]); 
   }  

   
if ((unsigned long)(currentMillis - previousMillis) >= 10){  //Toutes les 10 secondes, change le texte a afficher a l'écran grace a l'indice i
    lcd.clear();
    lcd.print("Life: ");
    if(i < (sizeof(sentence)/sizeof(String))-1){
      i = i+1;
    }else{
      i = 0;
    }
    previousMillis = currentMillis;
  }
  
}
