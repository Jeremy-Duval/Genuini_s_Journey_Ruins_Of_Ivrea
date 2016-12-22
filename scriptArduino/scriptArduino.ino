#define MAX_SIZE 100
char buff[100];
byte i;

void setup(){
 for(i=0;i<MAX_SIZE;i++){ //initialise the string at end of line character
  buff[i]='\0';
 } 
 
 Serial.begin(9600); //begin of communication
}

void loop(){
   if (Serial.available() > 0){
    i = 0;
    while((Serial.available() > 0)&&(i<MAX_SIZE)){ //while there is character avalaible and it lower than the size of the string
      
      buff[i] = Serial.read();
      i++;
    }
    buff[i]='\0'; //end by the end of line character
    Serial.print(buff);
    Serial.println(); //ligne break
  }
}
