#include <Grove_LED_Bar.h>
#include <Wire.h>
#include <string.h>
#include "rgb_lcd.h"

// Define the pin to which the angle sensor is connected.

//const int potentiometer = A0;
const int pinButton = 4; //D4 pin

Grove_LED_Bar bar(9, 8, 0);  // Clock pin, Data pin, Orientation
rgb_lcd lcd;

const int colorR = 255;
const int colorG = 0;
const int colorB = 0;

String sentence[] = {"Be Happy !", "Smile my gamer", "I love you"};
unsigned long previousMillis = 0;
unsigned long previousMillis2 = 0;
boolean changeSentence = false;
unsigned int i = 0;


void clearLign(int column, int lign) {
  lcd.setCursor(column, lign);
  lcd.print("                ");
}

enum State : uint8_t {
  None,
  Menu,
  Game,
  Death,
  Gravity,
  Book,
  Exit
} previousState = State::None, currentState = State::None;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bar.begin();
  bar.setBits(0x0);
  lcd.begin(16, 2);

  // Configure the angle sensor's pin for input.
  //  pinMode(potentiometer, INPUT);

   // Configure the button's pin for input signals.
    pinMode(pinButton, INPUT);
}


boolean stateButton = false;
void loop() {
    unsigned long currentMillis = millis() / 1000;
 /*   unsigned long currentMillis2 = millis() / 1000;
      
  
  if ((unsigned long)(currentMillis2 - previousMillis2) >= 1 && digitalRead(pinButton)) { 
       stateButton = !stateButton;
       previousMillis2 = currentMillis2;
    }
    
    Serial.write(stateButton);
    delay(100);  */

  
  String rec;

  // put your main code here, to run repeatedly:

  if (Serial.available() > 0) {

    rec = Serial.readStringUntil('\n');

    //parser avec les deux strings separes par ;
    int commaIndex = rec.indexOf(';');
    String firstValue = rec.substring(0, commaIndex);
    String secondValue = rec.substring(commaIndex + 1);

    if (firstValue == "menu") {
      previousState = currentState;
      currentState = State::Menu;
    } else if (firstValue == "game") {
      previousState = currentState;
      currentState = State::Game;
    } else if (firstValue == "death") {
      previousState = currentState;
      currentState = State::Death;
    } else if (firstValue == "exit") {
      previousState = currentState;
      currentState = State::Exit;
    }else if (firstValue == "gravity") {
      previousState = currentState;
      currentState = State::Gravity;
    }else if (firstValue == "book") {
      previousState = currentState;
      currentState = State::Book;
    }

    int life = secondValue.toInt();

    uint8_t myarray[] = {
      0b10000,
      0b10000,
      0b00111,
      0b01000,
      0b00110,
      0b00001,
      0b01110
    };
    lcd.createChar(1, myarray);
    
    switch (currentState) {
      case State::Menu:
        lcd.setRGB(255, 255, 255);
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.print("Genuini\001 Journey");
        lcd.setCursor(0, 1);
        lcd.print("Main menu");
        bar.setBits(0x3ff);
        changeSentence = false;
        break;
      case State::Game:
        clearLign(0, 0);
        lcd.setCursor(0, 0);
        lcd.print("Life: ");
        lcd.setCursor(6, 0);
        lcd.print(life);
        bar.setLevel(life / 10);
        changeSentence = true;  //changeSentence permet de bloquer le changement de texte sur la phrase en cours
        break;
      case State::Death:
        lcd.clear();
        lcd.setRGB(colorR, colorG, colorB);
        changeSentence = false;
        break;
      case State::Exit:
        lcd.clear();
        lcd.setRGB(0, 0, 0);
        changeSentence = false;  
        bar.setBits(0x0);
        break;
      case State::Gravity:
        clearLign(0, 1);
        lcd.setCursor(0, 1);
        lcd.print("Press G"); 
        break;
      case State::Book:
        clearLign(0, 1);
        lcd.setCursor(0, 1);
        lcd.print("Open Book"); 
        break;      
      default:
        bar.setBits(0x0);
        break;
    }
  }
  if (changeSentence == true) {
    if ((unsigned long)(currentMillis - previousMillis) >= 10) { //Toutes les 10 secondes, change le texte a afficher a l'écran grace a l'indice i
      if (i < (sizeof(sentence) / sizeof(String)) - 1) {
        i = i + 1;
      } else {
        i = 0;
      }
      previousMillis = currentMillis;
      clearLign(0, 1);
      lcd.setCursor(0, 1);
      lcd.print(sentence[i]);
    }
  }




}


