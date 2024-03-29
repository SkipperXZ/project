/*
  LoRa Duplex communication

  Sends a message every half second, and polls continually
  for new incoming messages. Implements a one-byte addressing scheme,
  with 0xFF as the broadcast address.

  Uses readString() from Stream class to read payload. The Stream class'
  timeout may affect other functuons, like the radio's callback. For an

  created 28 April 2017
  by Tom Igoe
*/
#include <SPI.h>              // include libraries
#include <LoRa.h>

const int csPin = 8;          // LoRa radio chip select
const int resetPin = 4;       // LoRa radio reset
const int irqPin = 7;         // change for your board; must be a hardware interrupt pin

const byte buffSize = 240;
char inputBuffer[buffSize];
const char startMarker = '<';
const char endMarker = '>';
byte bytesRecvd = 0;
boolean readInProgress = false;
boolean newDataFromPC = false;
boolean waitForReply = false;
boolean newData = false;

const byte numChars = 10;
char receivedChars[numChars];

byte msgCount = 0;

long interval = 500;
long previousMillis = 0;

char messageFromPC[buffSize] = {0};
String message;

unsigned long curMillis;

//========================================================================================

void setup() {
  Serial.begin(250000);                   // initialize serial
  while (!Serial);

  LoRa.setSyncWord(0xF3);
  
  Serial.println("LoRa Duplex");

  // override the default CS, reset, and IRQ pins (optional)
  LoRa.setPins(csPin, resetPin, irqPin);// set CS, reset, IRQ pin

  if (!LoRa.begin(433E6)) {             // initialize ratio at 433 MHz
    Serial.println("LoRa init failed. Check your connections.");
    while (true);                       // if failed, do nothing
  }
  Serial.println("LoRa init succeeded.");
  Serial.println("<Arduino is ready>");
}


//========================================================================================


void loop() {

  if (!waitForReply) {
    getDataFromPC();
    sendToRecive();
    //Serial.println("Send");
    previousMillis = millis();
  }
  else{
    //Serial.println("<WaitACK....>");
  // parse for a packet, and call onReceive with the result:
  onReceive(LoRa.parsePacket());
  //Serial.print("wait");
  if(millis() - previousMillis >= interval) { 
    previousMillis = millis();
    reSend();
  }   
  }
}



//========================================================================================


//========================================================================================

void onReceive(int packetSize) {

  static byte ndx = 0;
  char rc;
  
  if (packetSize == 0) return;          // if there's no packet, return

  //Serial.println("<Receiving.....>");

  while (LoRa.available() > 0) {
        rc = (char)LoRa.read();

        if (rc != endMarker) {
            receivedChars[ndx] = rc;
            ndx++;
            if (ndx >= numChars) {
                ndx = numChars - 1;
            }
        }
        else {
            receivedChars[ndx] = '\0'; // terminate the string
            ndx = 0;
        }
    }

        Serial.print("<");
        Serial.print(receivedChars);
        Serial.println(">");
        
      
        waitForReply = false;
        delay(1);
}


//========================================================================================

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

//========================================================================================
 
void parseData() {
    
  char * strtokIndx; // this is used by strtok() as an index
    
  //strtokIndx = strtok(inputBuffer,",");      // get the first part - the string
  strcpy(messageFromPC, inputBuffer); // copy it to messageFromPC
  message = String(messageFromPC) + '>';

}

//========================================================================================

void sendToRecive() {

  if (newDataFromPC) {
    newDataFromPC = false;
    
    // send packet
    LoRa.beginPacket();
    LoRa.write(msgCount);
    LoRa.print(message);
    LoRa.endPacket();
    msgCount++;
    if (message == "EOF>")
    {
      msgCount = 0;
    }
    waitForReply = true;

    //Serial.println("<Sending....>");
  }
}

//========================================================================================

void reSend() {

    // send packet
    LoRa.beginPacket();
    LoRa.write(msgCount-1);
    LoRa.print(message);
    LoRa.endPacket();

    Serial.println("<Retransmission....>");
}
