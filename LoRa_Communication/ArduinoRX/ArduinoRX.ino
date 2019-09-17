#include <SPI.h>
#include <LoRa.h>

const byte numChars = 240;
char receivedChars[numChars];   // an array to store the received data
boolean newData = false;

void setup() {
  Serial.begin(115200);
  while (!Serial);
  Serial.println("LoRa Receiver");
  LoRa.setPins(8,4,7);
  if (!LoRa.begin(433E6)) {
    Serial.println("Starting LoRa failed!");
    while (1);
  }
  LoRa.setSyncWord(0xF3);
}
void loop() {
  
  recvWithEndMarker();
  showNewData();
}

void recvWithEndMarker() {
    static byte ndx = 0;
    char endMarker = '>';
    char rc;
    int packetSize = LoRa.parsePacket();

    if (packetSize){
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
    }
}

void showNewData() {
    if (newData == true) {
        //Serial.println(receivedChars);
        Serial.print("<");
        Serial.print(receivedChars);
        Serial.println(">");
        newData = false;
    }
}
