char buff[100];
byte i;

void setup(){
 for(i=0;i<100;i++){ //initialise
  buff[i]='\0';
 } 
 
 Serial.begin(9200); //begin of communication
}

void loop(){
  Serial.println(buff);
}
