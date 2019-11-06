/*
  // March 2014 - TMRh20 - Updated along with High Speed RF24 Library fork
  // Parts derived from examples by J. Coliz <maniacbug@ymail.com>
*/
/**
 * Example for efficient call-response using ack-payloads 
 *
 * This example continues to make use of all the normal functionality of the radios including
 * the auto-ack and auto-retry features, but allows ack-payloads to be written optionally as well.
 * This allows very fast call-response communication, with the responding radio never having to 
 * switch out of Primary Receiver mode to send back a payload, but having the option to if wanting
 * to initiate communication instead of respond to a commmunication.
 */
 


#include <SPI.h>
#include "nRF24L01.h"
#include "RF24.h"
#include "printf.h"

// Hardware configuration: Set up nRF24L01 radio on SPI bus plus pins 7 & 8 
RF24 radio(7,8);

// Topology
const uint64_t pipes[2] = { 0xABCDABCD71LL, 0x544d52687CLL };              // Radio pipe addresses for the 2 nodes to communicate.

// Role management: Set up role.  This sketch uses the same software for all the nodes
// in this system.  Doing so greatly simplifies testing.  

typedef enum { role_ping_out = 1, role_pong_back } role_e;                 // The various roles supported by this sketch
const char* role_friendly_name[] = { "invalid", "Ping out", "Pong back"};  // The debug-friendly names of those roles
role_e role = role_ping_out;                                              // The role of the current running sketch

// A single byte to keep track of the data being sent back and forth
int counter = 1;
char txdata[16] = "FVdFfNvyVfoeW3v";

const byte buffSize = 33;
char inputBuffer[buffSize];
const char startMarker = '<';
const char endMarker = '>';
byte bytesRecvd = 0;
boolean readInProgress = false;
boolean newDataFromPC = false;

void setup(){

  Serial.begin(115200);
  printf_begin();
  //Serial.print(F("\n\rRF24/examples/pingpair_ack/\n\rROLE: "));
  Serial.println(role_friendly_name[role]);

  // Setup and configure rf radio

  radio.begin();
  //radio.setChannel(110);
  radio.setDataRate(RF24_2MBPS);
  radio.setAutoAck(1);                    // Ensure autoACK is enabled
  radio.enableAckPayload();               // Allow optional ack payloads
  radio.setRetries(15,15);                 // Smallest time between retries, max no. of retries
  radio.setPayloadSize(32);                // Here we are sending 1-byte payloads to test the call-response speed
  radio.openWritingPipe(pipes[0]);        // Both radios listen on the same pipes by default, and switch when writing
  //radio.openReadingPipe(1,pipes[1]);
  radio.startListening();                 // Start listening
  radio.printDetails();                   // Dump the configuration of the rf unit for debugging
  Serial.println("<Arduino is ready>");
}

void loop(void) {

    if (role == role_ping_out)
    {
      //sendData();
      getDataFromPC();
      sendToRecive();
    }
}
//####################################################################################################

void getDataFromPC() {

  if(Serial.available() > 0) 
  {

    char x = Serial.read();

    if (x == endMarker) 
    {
      readInProgress = false;
      newDataFromPC = true;
      inputBuffer[bytesRecvd] = 0;
    }
    
    if(readInProgress) 
    {
      inputBuffer[bytesRecvd] = x;
      bytesRecvd ++;
      if (bytesRecvd == buffSize) 
      {
        bytesRecvd = buffSize - 1;
      }
    }

    if (x == startMarker) 
    { 
      bytesRecvd = 0; 
      readInProgress = true;
    }
  }
}

//####################################################################################################

void sendToRecive() {

  if (newDataFromPC) 
  {
    sendData();  
  }
}

//####################################################################################################

void sendData(void){
  
    radio.stopListening();                                  // First, stop listening so we can talk.
        
    //printf("Now sending %d as payload. ",counter);
    int gotByte;  
    //unsigned long time = micros();                          // Take the time, and send it.  This will block until complete   
                                                            //Called when STANDBY-I mode is engaged (User is finished sending)
    if (!radio.writeFast( &inputBuffer, sizeof(inputBuffer) ))
    {
      //Serial.println("<failed>");
      //delay(1000);      
    }
    else
    {
      if(!radio.available())
      { 
        Serial.println("<Blank Payload Received>");
        //delay(1); 
      }
      else
      {
        while(radio.available())
        {
          //unsigned long tim = micros();
          radio.read( &gotByte,sizeof(gotByte) );
          //delay(1);
          newDataFromPC = false;
          Serial.println("<ReceiveACK>"); 
          //printf("Got response %d, round-trip delay: %lu microseconds\n\r",gotByte,tim-time);
          //counter++;
        }
      }
    }
    //delay(250);
}
