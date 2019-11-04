// プリプロセッサの命令
// C言語の命令といえば命令
// C++のANTLR4でやるとエラーがでないけど，CのANTLR$でやるとエラーが出る

// #define LED_PIN 13
//
// void setup() {
//   Serial.begin(9800);
//   digital.write(LED_PIN, OUTPUT);
// }
//
// void loop()
// {
//   Serial.println("Hello, World");
//   delay(1000);
// }

// プリプロセッサの命令
// C言語の命令といえば命令
// C++のANTLR4でやるとエラーがでないけど，CのANTLR$でやるとエラーが出る

#define LED_PIN 13

int LED_PIN = 0;
int led1 = 1;
int led2 = 2;
int led3 = 3;
int led3 = 5;

void setup()
{
  Serial.begin(9800);
  digital.write(LED_PIN, OUTPUT);
}

void loop()
{
  Serial.println("Hello, World");
  delay(1000);
}


