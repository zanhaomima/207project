/**
 * Code for Arduino to read the message send from Java
 * @author Jifeng Zheng
 */
#include <Servo.h> 
#include <LiquidCrystal.h>

Servo myservo; 

//  initialize the library by associating any needed LCD interface pin
//  with the arduino pin number it is connected to
//  The circuit:
//  * LCD RS pin to digital pin 12
//  * LCD Enable pin to digital pin 11
//  * LCD D4 pin to digital pin 5
//  * LCD D5 pin to digital pin 4
//  * LCD D6 pin to digital pin 3
//  * LCD D7 pin to digital pin 2
//  * LCD R/W pin to ground
//  * LCD VSS pin to ground
//  * LCD VCC pin to 5V
//  * 10K resistor:
//  * ends to +5V and ground
//  * wiper to LCD VO pin (pin 3)
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
int pos = 100;   // variable to store the servo position 
int passpos=10; //turing angle when starting pass

void setup() 
{ 
  Serial.begin(9600);
  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  // attaches the servo on pin 9 to the servo object 
  myservo.attach(9); 
} 

void loop() 
{ 
  //read the String from the USB port
  String timePass=Serial.readString();
  //Getting the time from the string
  int howlong=atoi(timePass.c_str());
  //Out put to the Serial.println
  Serial.println(howlong);
  //if the time is bigger than 0, we needs to output the message srceen and let the Servo work
  if(howlong>0){
    //point to the 0,0
    lcd.setCursor(0,0);
    //show the "Passing Time is" to he 0,0
    lcd.print ("Passing Time is");
    //point to the 0,1
    lcd.setCursor(0, 1);
    //display the time of ns to the 0,1
    lcd.print(howlong);
    lcd.print ("ns");
    //let the servo true to pass the iphone screen
    myservo.write(passpos);
    //wait for the time
    delay(howlong);
    //let the servo release to pass the iphone screen
    myservo.write(pos);
  }
  else{
    //point to the 0,0
    lcd.setCursor(0,0);
    //show the "Calculating...   " to he 0,0
    lcd.print ("Calculating...   ");
    //point to the 0,1
    lcd.setCursor(0, 1);
    //showing how many secends the program starting now
    lcd.print(millis() / 1000);
    //outprint the s and space
    lcd.print ("s              ");
    //let the servo release to pass the iphone screen
    myservo.write(pos);
  }
} 
