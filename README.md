# dailydiningdilemma

DDD v2

## Requirements and steps for building the app

### Requirements:
- [nodejs](http://nodejs.org/)
- gulp, install nodejs first: npm install gulp -g (sudo for mac and linux users)
- XCode for ios, android sdk for android
- Facebook developer account for facebook login

### Steps for running the app within an emulator (for ios, substitute ios with android for android)
1. install cordova (you may need sudo for this): npm install -g cordova ionic
2. cd ddd-app
3. cordova plugin add org.apache.cordova.device
4. cordova plugin add org.apache.cordova.console
5. cordova plugin add com.ionic.keyboard
6. gulp profile-mock or gulp profile-production 
7. ionic platform add ios
8. ionic build ios
9. ionic emulate ios

## App Development
In order to use the app in a browser you need a running webserver like [mongoose](http://cesanta.com/mongoose.shtml).
After you created the index.html (see gulp profile-mock or gulp-profile-production) you can access the app via:
http://127.0.0.1:8080/dailydiningdilemma/ddd-app/www/
It is very important to use localhost otherwise the login will not work in production mode (origin of callback for oauth must be localhost)

