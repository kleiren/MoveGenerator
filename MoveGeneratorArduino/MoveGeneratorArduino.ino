#include <Servo.h> 

//Settings
Servo servoList[55]; //Listado de servos

//----------------------
char serialReadString[100];//stores the recieved characters
int stringPosition=-1;//stores the current position of my serialReadString

void setup(){
  Serial.begin(115200);
}

void loop(){
   readSerialLine();//check for incoming data
}

//Method check for incoming charactersand stores them in serialReadString
//if a new line character is detected, the string serialReadString is closed
//and the method stringReceived is called
void readSerialLine(){
  while (Serial.available() > 0){ // As long as there's more to read, keep reading
      int inByte = Serial.read();// Read next byte
      stringPosition++;//increase the position in the string
      if(inByte=='\n'){//if it's my terminating character
         serialReadString[stringPosition] = 0;//set current position to Null to terminate the String
         //chose correct method
          switch (serialReadString[0]) {
            case '0':
              setConfig();
              break;
            case '2':
               setServoPosition();//call the method, to encode the recieved signal
               break;
            default:
               // nothing 
              break;
          }
          stringPosition=-1;//set the string position to -1
      }else{//if it's not a terminating character
          serialReadString[stringPosition] = inByte; // Save the character in a character array
      }
  }
}

void setConfig(){
      Serial.println("start config");
      int read_pos = 1;
      
      // --------- Atach each servo ------------
      char servoPin[10];//string to store Pin   
      while(serialReadString[read_pos]!=0){
        int i=0;
          while(serialReadString[read_pos]!=';'){
          servoPin[i]=serialReadString[read_pos];
          read_pos++;
          i++;
        }
        read_pos++;
        servoPin[i]=0;//terminate the String
        int pin =atoi(servoPin);//convert String to int
        if(pin<55 && pin>-1)
          servoList[pin].attach(pin);  // attach servo[pin] on the given Pin
      }
       Serial.println("finished config");
}

//Method is called after a new Command is recieved
void setServoPosition(){
      int read_pos=1;

      char servoPin[10];  
      int i=0;
      while(serialReadString[read_pos]!=';'){//run through all the characters after "s;s1,"  
        servoPin[i]=serialReadString[read_pos];//and store them in the new String
        read_pos++;//increase for new position
        i++;
      }      
      servoPin[i]=0;//terminate the String

      read_pos++;

      char servoPosition[10];//create a new String to store the characters of the value
      i = 0;
      while(serialReadString[read_pos]!=0){
        servoPosition[i]=serialReadString[read_pos];
        read_pos++;
        i++;
      }      
      servoPosition[i]=0;//terminate the String
      
      int receivedPin=atoi(servoPin);
      int recievedPosition=atoi(servoPosition);//convert String to int
      servoList[receivedPin].write(recievedPosition);//set the Servo
  }

