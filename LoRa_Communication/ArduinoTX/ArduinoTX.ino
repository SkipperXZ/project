#include <LoRa.h>

const byte buffSize = 240;
char inputBuffer[buffSize];
const char startMarker = '<';
const char endMarker = '>';
byte bytesRecvd = 0;
boolean readInProgress = false;
boolean newDataFromPC = false;

char messageFromPC[buffSize] = {0};
String message;
int newFlashInterval = 0;
float servoFraction = 0.0; // fraction of servo range to move

unsigned long curMillis;

unsigned long prevReplyToPCmillis = 0;
unsigned long replyToPCinterval = 1000;

//=============

void setup() {
  Serial.begin(115200);

  while (!Serial);
  Serial.println("LoRa Sender");
  LoRa.setPins(8,4,7);
  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  LoRa.setSyncWord(0xF3);  
    // tell the PC we are ready
  Serial.println("<Arduino is ready>");
}

//=============

void loop() {
  curMillis = millis();
  getDataFromPC();
  sendToRecive();
}

//=============

void getDataFromPC() {

    // receive data from PC and save it into inputBuffer
    
  if(Serial.available() > 0) {

    char x = Serial.read();
      // the order of these IF clauses is significant
      
    if (x == endMarker) {
      readInProgress = false;
      newDataFromPC = true;
      inputBuffer[bytesRecvd] = 0;
      parseData();
    }
    
    if(readInProgress) {
      inputBuffer[bytesRecvd] = x;
      bytesRecvd ++;
      if (bytesRecvd == buffSize) {
        bytesRecvd = buffSize - 1;
      }
    }

    if (x == startMarker) { 
      bytesRecvd = 0; 
      readInProgress = true;
    }
  }
}

//=============
 
void parseData() {
    
  char * strtokIndx; // this is used by strtok() as an index
    
  //strtokIndx = strtok(inputBuffer,",");      // get the first part - the string
  strcpy(messageFromPC, inputBuffer); // copy it to messageFromPC
  message = String(messageFromPC) + '>';

}

//=============

void sendToRecive() {

  if (newDataFromPC) {
    newDataFromPC = false;
    
    // send packet
    LoRa.beginPacket();
    LoRa.print(message);
    LoRa.endPacket();

    delay(10);

    Serial.print("<Send!! ");
    Serial.print(" Time ");
    Serial.print(curMillis >> 9); // divide by 512 is approx = half-seconds
    Serial.println(">");
  }
}
