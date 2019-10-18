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

  LoRa.enableCrc();
  LoRa.setSyncWord(0xF3);
  
  Serial.println("LoRa Duplex");

  LoRa.setPins(csPin, resetPin, irqPin);// set CS, reset, IRQ pin

  if (!LoRa.begin(433E6)) {           
    Serial.println("LoRa init failed. Check your connections.");
    while (true);                       
  }
  Serial.println("LoRa init succeeded.");
  Serial.println("<Arduino is ready>");

  LoRa.onReceive(onReceive);
  LoRa_rxMode();
}


//========================================================================================


void loop() {
  
  getDataFromPC();
  sendToRecive();
 
  if(millis() - previousMillis >= interval) { 
    previousMillis = millis();
    reSend();
  }   
}

//========================================================================================

void LoRa_rxMode(){
  LoRa.enableInvertIQ();                // active invert I and Q signals
  LoRa.receive();                       // set receive mode
}

void LoRa_txMode(){
  LoRa.idle();                          // set standby mode
  LoRa.disableInvertIQ();               // normal mode
}

//========================================================================================

void onReceive(int packetSize) {

  static byte ndx = 0;
  char rc;
  
  if (packetSize == 0) return;          // if there's no packet, return


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
            receivedChars[ndx] = '\0'; 
            ndx = 0;
        }
    }

        Serial.print("<");
        Serial.print(receivedChars);
        Serial.println(">");
        
}


//========================================================================================

void getDataFromPC() {

    
  if(Serial.available() > 0) {

    char x = Serial.read();

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
    
  char * strtokIndx;
    
  strcpy(messageFromPC, inputBuffer); 
  message = String(messageFromPC) + '>';

}

//========================================================================================

void sendToRecive() {

  if (newDataFromPC) {
    newDataFromPC = false;

    LoRa_txMode();
    LoRa.beginPacket();
    LoRa.write(msgCount);
    LoRa.print(message);
    LoRa.endPacket();
    LoRa_rxMode();
     
    msgCount++;
    if (message == "EOF>")
    {
      msgCount = 0;
    }
    previousMillis = millis();
  }
}

//========================================================================================

void reSend() {

    LoRa_txMode();
    LoRa.beginPacket();
    LoRa.write(msgCount-1);
    LoRa.print(message);
    LoRa.endPacket();
    LoRa_rxMode();

    Serial.println("<Retransmission....>");
}
