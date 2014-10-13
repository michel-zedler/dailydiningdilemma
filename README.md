# dailydiningdilemma

DDD v2

## Requirements and steps for building the app

### Requirements:
- [nodejs](http://nodejs.org/)
- git
- gulp
- XCode for ios, android sdk for android
- Facebook developer account for facebook login

### General setup

0. install nodejs
1. install git
2. in git bash: npm install -g gulp 
3. install cordova (you may need sudo for this): npm install -g cordova ionic
4. cd ddd-app
5. cordova plugin add org.apache.cordova.device
6. cordova plugin add org.apache.cordova.console
7. cordova plugin add com.ionic.keyboard
8. npm install
9. gulp profile-mock or gulp profile-production 

### Steps for running the app within an ios emulator (mac-os only)
10. ionic platform add ios
11. ionic build ios
12. ionic emulate ios

### Steps for running the app within an android emulator
10. ionic platform add android
11. ionic build android
12. ionic emulate android

## App Development
In order to use the app in a browser you need a running webserver like [mongoose](http://cesanta.com/mongoose.shtml).
After you created the index.html (see gulp profile-mock or gulp-profile-production) you can access the app via:
http://127.0.0.1:8080/dailydiningdilemma/ddd-app/www/
It is very important to use localhost otherwise the login will not work in production mode (origin of callback for oauth must be localhost)

