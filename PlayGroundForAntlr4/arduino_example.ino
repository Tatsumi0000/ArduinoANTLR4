int LED_PIN = 0;
int debug_led = 100;
int led1 = 1;
int led2 = 2;
int led3 = 3;
int led3 = 5;
String str = "Hello, World";
led3 = 3;
bool flag = true;
void setup()
{
  Serial.begin(9800);
  pinMode(led3, OUTPUT);
//   if (flag != false){
if (led3 <= 4){
  led1 = 5;
  led1 = 3;
  led1 = 2;
  }
     if(led3 == 3) {
  led1 = 0;
  led1 = 100;
  }
    if(true) {
  led1 = 0;
  led1 = 100;
  }
  if(!flag){
  }
}

void loop()
{
digitalWrite(led3, HIGH);
}


