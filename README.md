# card-verify
There are two endpoints as per the given requirements.

 1- Check the card detail/status.
      **_GET /card-scheme/verify/{cardNumber}_**
      
    If cardNumber is in invalid format, service will send the response with 400 Bad request status code
    If cardNumber is not valid or found then service will send the response with 404 status code
    If card is valid then server will respond with below response
    
    {
    "success" : true ,
    "payload" : {
    "scheme" : "visa" ,
    "type" : "debit" ,
    "bank" : "UBS"
    }
    }
   
   2- Check number of hits stats against card number
         **_GET /card-scheme/stats?start=1&limit=3_**
         
       **Important points to remember**
               "start" params mean the page number in Spring DATA JPA
               "limit" params mean the page SIZE in Spring DATA JPA    
                     
     If start and limit request params are not given then server will respond with bad request status code
     Valid response for this service is given below
       
     {
     "success" : true ,
     "start" : 1 ,
     "limit" : 3 ,
     "size" : 133 ,
     "payload" : {
     "545451" : "5" ,
     "234233" : "3" ,
     "111111" : "1"
     }
     }
