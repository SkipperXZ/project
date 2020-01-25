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

boolean waitForData = true;

byte Inmsgid = 0;
byte Waitid = 0;
const byte numChars = 255;
char receivedChars[numChars];   // an array to store the received data
char previousReceivedChars[numChars] = "";
boolean newData = false;

void setup() {
  Serial.begin(115200);                   // initialize serial
  while (!Serial);

  LoRa.enableCrc();
  LoRa.setSyncWord(0xF3);
  LoRa.setSpreadingFactor(7);
  LoRa.setSignalBandwidth(125E3);
  LoRa.setCodingRate4(8);
  
  Serial.println("LoRa Duplex");
  
  LoRa.setPins(csPin, resetPin, irqPin);// set CS, reset, IRQ pin

  if (!LoRa.begin(433E6)) {            
    Serial.println("LoRa init failed. Check your connections.");
    while (true);                      
  }

  Serial.println("LoRa init succeeded.");

  LoRa.onReceive(onReceive);
  LoRa_rxMode();
}

//#================================================================================



void loop() {
  if (!waitForData) {
    sendACK();
    waitForData = true;
  }
}

//#================================================================================

void LoRa_rxMode(){
  LoRa.disableInvertIQ();               // normal mode
  LoRa.receive();                       // set receive mode
}

void LoRa_txMode(){
  LoRa.idle();                          // set standby mode
  LoRa.enableInvertIQ();                // active invert I and Q signals
}
//#================================================================================

void sendACK() {
  
    LoRa_txMode();
    LoRa.beginPacket();
    LoRa.print("<ACK_");
    LoRa.print(">");
    LoRa.endPacket();
    LoRa_rxMode();

    //Serial.println("<Send_ACK>");
   
}

//#================================================================================



void onReceive(int packetSize) {

  static byte ndx = 0;
  char endMarker = '>';
  char rc;
  
  if (packetSize == 0) return;          // if there's no packet, return

  //Serial.println("<Receiving.....>");

  Inmsgid = LoRa.read();
  if (Inmsgid != Waitid) {return;}
  
  while (LoRa.available() > 0 && newData == false) {
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
            newData = true;
        }
    }

    //Serial.println("<checkMsgID.....>");
   
    showNewData();
  
}

//#================================================================================

void showNewData() {
    if (newData == true) {
        Serial.print("<");
        Serial.print(receivedChars);
        Serial.println(">");

        Waitid++;
        newData = false;
        waitForData = false;
    }
}

//#================================================================================
