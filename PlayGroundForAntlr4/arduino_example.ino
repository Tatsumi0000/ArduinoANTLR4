int LED_PIN = 0;
int led1 = 1;
int led2 = 2;
int led3 = 3;
int led3 = 5;
String str = "Hello, World";
led3 = 4;
bool flag = true;
void setup()
{
  Serial.begin(9800);
  pinMode(led3, OUTPUT);
  if (flag == true){
  led1 = 2;
  }
}

void loop()
{
  Serial.println("Hello, World");
  delay(500);
  digitalWrite(led3, HIGH);
  delay(500);
  digitalWrite(led3, LOW);
}


