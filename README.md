# dailydiningdilemma

DDD v2

## Requirements and steps for building the app

### Requirements:
- [nodejs](http://nodejs.org/)
- XCode for ios, android sdk for android
- Facebook developer account for facebook login

### Steps (for ios, substitute ios with android for android)
1. install cordova (you may need sudo for this): npm install -g cordova ionic
2. cordova plugin add org.apache.cordova.device
3. cordova plugin add org.apache.cordova.console
4. cordova plugin add com.ionic.keyboard
5. cd ddd-app
6. ionic platform add ios
7. ionic build ios
8. ionic emulate ios

