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
role_e role = role_pong_back;                                              // The role of the current running sketch

// A single byte to keep track of the data being sent back and forth
byte counter = 1;
char rxdata[32];
bool newData = false;
int gotByte = 109;
byte IncomeID = 48;
byte REid = 0;
String temp = "";

void setup(){

  Serial.begin(115200);
  printf_begin();
  //Serial.print(F("\n\rRF24/examples/pingpair_ack/\n\rROLE: "));
  Serial.println(role_friendly_name[role]);

  // Setup and configure rf radio

  radio.begin();
  //radio.setChannel(110);
  radio.setCRCLength(RF24_CRC_8);
  radio.setDataRate(RF24_250KBPS);
  //radio.setAutoAck(1);                    // Ensure autoACK is enabled
  radio.enableAckPayload();               // Allow optional ack payloads
  radio.setRetries(15,15);                 // Smallest time between retries, max no. of retries
  //radio.setPayloadSize(32);                // Here we are sending 1-byte payloads to test the call-response speed
  //radio.openWritingPipe(pipes[1]);        // Both radios listen on the same pipes by default, and switch when writing
  radio.openReadingPipe(1,pipes[0]);
  radio.startListening();                 // Start listening
  radio.printDetails();                   // Dump the configuration of the rf unit for debugging
  Serial.println("<Arduino is ready>");
}

void loop(void) {

  // Pong back role.  Receive each packet, dump it out, and send it back

  if ( role == role_pong_back ) 
  {                                       // Dump the payloads until we've gotten everything
    while( radio.available())
    {
      radio.read( &rxdata, sizeof(rxdata) );
      radio.writeAckPayload(1,&gotByte, sizeof(gotByte));
      REid = rxdata[0];
      if(REid == IncomeID)
      {
        temp = String(rxdata);
        temp.remove(0,1);
        //Serial.print(temp);
        updateIncomeID();
        Serial.print("<");
        Serial.print(temp);
        Serial.println(">");
      }          
    }
  }
}

void updateIncomeID(void){

  if(IncomeID < 48 || IncomeID >57)
  {
    IncomeID = 48;
  }
  else
  {
    IncomeID = IncomeID+1;
  }
}
