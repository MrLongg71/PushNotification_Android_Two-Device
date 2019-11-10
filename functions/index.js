// "use-strict";
// const functions = require("firebase-functions");
// var admin = require("firebase-admin");
// admin.initializeApp(functions.config().firebase);

// exports.sendNotification = functions.database
//   .ref("/article/{articleId}")
//   .onWrite((change, context) => {

//     const author = change.after.child("author").val();
//     const title = change.after.child("title").val();
//     const iduser = change.after.child("iduser").val();
//     var token = 'fvI0xGm3N84:APA91bHCwyzqO4pgJSgTjyjRzz34AhyJ-x_qYP0KevtbLfz3kVr28LtsInTf5D3AYLpeZ2Db99nGczLCM0X0ZN4xhvhlSyBaiHkGi1SEOQz8r0ay6VxBqq57x9ZHa3NkiJVPLGSuisZN';
    
//     var payload = {
//           data: {
//             title: author,
//             author: title
//           },
//           token : token
//         };

//     admin.messaging().send(payload)
//   .then((response) => {
//     // Response is a message ID string.
//     console.log('Successfully sent message:', response);
//   })
//   .catch((error) => {
//     console.log('Error sending message:', error);
//   });




//     // const devicetoken = admin.database.ref(`/User/${iduser}/token`).once('value');
//     // return devicetoken.then(result =>{

//     //   var token = 'fvI0xGm3N84:APA91bHCwyzqO4pgJSgTjyjRzz34AhyJ-x_qYP0KevtbLfz3kVr28LtsInTf5D3AYLpeZ2Db99nGczLCM0X0ZN4xhvhlSyBaiHkGi1SEOQz8r0ay6VxBqq57x9ZHa3NkiJVPLGSuisZN';

//     //   console.log("token do : "+token);
    
//     //   var payload = {
//     //     data: {
//     //       title: author,
//     //       author: title
//     //     },
//     //     token : token
//     //   };
//     //   return admin
//     //     .messaging()
//     //     .sendToDevice(token,payload)
//     //     .then(function(response) {
//     //       return console.log("Successfully sent message:", response);
//     //     })
//     //     .catch(function(error) {
//     //       return console.log("Error sending message:", error);
//     //     });
//     // });
     


//     });

"use-strict";
const functions = require("firebase-functions");

var admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database
  .ref("/article/{articleId}")
  .onWrite((change, context) => {
      
    const author = change.after.child("author").val();
    const title = change.after.child("title").val();
    var iduser = change.after.child("iduser").val();

    console.log(iduser)

    var str1 = "Author is ";
    var str = str1.concat(author);
    console.log(str);
    // var tokenRE = 'fvI0xGm3N84:APA91bHCwyzqO4pgJSgTjyjRzz34AhyJ-x_qYP0KevtbLfz3kVr28LtsInTf5D3AYLpeZ2Db99nGczLCM0X0ZN4xhvhlSyBaiHkGi1SEOQz8r0ay6VxBqq57x9ZHa3NkiJVPLGSuisZN';

    return admin.database().ref().child("User").child(iduser).child("token").once('value')
    .then(function(snapshot) {
      // var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
      // send thu di
      var tokenRE = snapshot.val();

      var strtoken = "Token is ";
      var stre = strtoken.concat(tokenRE);
      console.log(stre);


      var topic = "android";
      var payload = {
        data: {
          title: author,
          author: title
        },
        token :  tokenRE
      };
      return admin
        .messaging()
        .send(payload)
        .then(function(response) {
          return console.log("Successfully sent message:", response);
        })
        .catch(function(error) {
          return console.log("Error sending message:", error);
        });




    });
    
  });
    
  
