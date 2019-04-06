# card-verify


Card Verify is a card verification microservice which contains two endpoints.

  - `GET /card-scheme/verify/{cardNumber}`
  - `GET /card-scheme/stats?start=1&limit=3`

# Features!

  - Check the card detail/status
  - Check number of hits stats against card number

### /card-scheme/verify/{cardNumber}

  - If given card number is in invalid format, service will send the response with `400` Bad request status code.
  - If given card number is not found then service will send the response with `404` status code.
  - If card is valid then server will respond with below response.
```javascript
{
    "success" : true ,
    "payload" : {
      "scheme" : "visa" ,
      "type" : "debit" ,
      "bank" : "UBS"
    }
}
```
### /card-scheme/stats?start=1&limit=3
Important points to note:
  - `start` params mean the page number in Spring DATA JPA.
  - `limit` params mean the page SIZE in Spring DATA JPA.
  - If `start` and `limit` request params are not given then server will respond with bad request status code i.e. `400`.
  - Valid response for this service is given below
```javascript
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
```
