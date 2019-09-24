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

const byte numChars = 240;
char receivedChars[numChars];   // an array to store the received data
boolean newData = false;

void setup() {
  Serial.begin(115200);                   // initialize serial
  while (!Serial);

  LoRa.setSyncWord(0xF3);
  
  Serial.println("LoRa Duplex");

  // override the default CS, reset, and IRQ pins (optional)
  LoRa.setPins(csPin, resetPin, irqPin);// set CS, reset, IRQ pin

  if (!LoRa.begin(433E6)) {             // initialize ratio at 915 MHz
    Serial.println("LoRa init failed. Check your connections.");
    while (true);                       // if failed, do nothing
  }

  Serial.println("LoRa init succeeded.");
}

//#================================================================================



void loop() {
  if (!waitForData) {
    sendACK();
    waitForData = true;
    Serial.println("Send");
  }
  else{
    onReceive(LoRa.parsePacket());
    Serial.print("wait");
  }
}

//#================================================================================


void sendACK() {
  
    // send packet
    LoRa.beginPacket();
    LoRa.print("<ACK>");
    LoRa.endPacket();
   
}

//#================================================================================



void onReceive(int packetSize) {

  static byte ndx = 0;
  char endMarker = '>';
  char rc;
  
  if (packetSize == 0) return;          // if there's no packet, return

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

    showNewData();
}

void showNewData() {
    if (newData == true) {
        Serial.print("<");
        Serial.print(receivedChars);
        Serial.println(">");
        newData = false;
        waitForData = false;
        delay(1);
    }
}


//#================================================================================
