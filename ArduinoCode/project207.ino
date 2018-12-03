
#include <Servo.h> 
#include <LiquidCrystal.h>

Servo myservo; 
// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
int pos = 100;   // variable to store the servo position 
int passpos=10; //turing angle when starting pass

void setup() 
{ 
  Serial.begin(9600);
  lcd.begin(16, 2);
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object 
} 

void loop() 
{ 
  String timePass=Serial.readString();
  int howlong=atoi(timePass.c_str());
  Serial.println(howlong);
  if(howlong>0){
    lcd.setCursor(0,0);
    lcd.print ("Passing Time is");
    lcd.setCursor(0, 1);
    lcd.print(howlong);
    lcd.print ("ns");
    myservo.write(passpos);
    delay(howlong);
    myservo.write(pos);
  }
  else{
    lcd.setCursor(0,0);
    lcd.print ("Calculating...   ");
    lcd.setCursor(0, 1);
    lcd.print(millis() / 1000);
    lcd.print ("s              ");
    myservo.write(pos);
  }
} 
