/**
*By Jeremy Duval
*Since 22/12/2016
*last update : 23/12/2016
*/
#define MAX_SIZE 100
const byte del = 9; //del's pin
char buff[100];
byte i;

void setup(){
 init_string();
 pinMode(del, OUTPUT);//del is an output pin (electricity : Arduino to Pin)
 digitalWrite(del, LOW);//init the del at out
 Serial.begin(9600); //begin of communication
}

void loop(){
   if (Serial.available() > 0){
    /*while((Serial.available() > 0)&&(i<MAX_SIZE)){ //while there is character avalaible and it lower than the size of the string
      delay(5);//wait 5ms : necessary for a good read and avoid bug
      buff[i] = Serial.read();
      i++;
    }
    delay(5);//security
    buff[i]='\0'; //end by the end of line character
    */
    Serial.readBytes(buff, MAX_SIZE);//read each byte in the buffer under a number of MAX_SIZE and save it in buff
    //Serial.print(buff);
    action();
    init_string();
    
  }
}

void init_string(){
 for(i=0;i<MAX_SIZE;i++){ //initialise the string at end of line character
  buff[i]='\0';
 }  
}

void action(){
  if((strcmp(buff,"on")==0)||(strcmp(buff,"ON")==0)){
      digitalWrite(del, HIGH);
    }
  if((strcmp(buff,"off")==0)||(strcmp(buff,"OFF")==0)){
    digitalWrite(del, LOW);
  }
}
