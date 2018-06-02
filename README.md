# A RESTful web-service written in Scala & Play Framework 2.6.15
 
The service allow users to place new car adverts, get, modify and delete existing.

# Run

Type the following at the prompt and it will run the project:

```
sbt run
```

Once we start our application we need go to `localhost:9000`  in browser.
`Evolution` should ask to init `DB`. Just press `Apply this script now!`

# Testing

This project don't have tests yet. 

So all `GET` tests were done from browser:

```
http://localhost:9000/?sortBy=title

http://localhost:9000/adverts/cars/1
```

All `POST` through `curl`:

```
curl -X POST -H "Content-Type: application/json" -d '{"title":"Ford", "fuel": "gasoline", "price": 5030, "isNew":true}' localhost:9000/adverts/cars

curl -X POST -H "Content-Type: application/json" -d '{"title":"Ford", "fuel": "gasoline", "price": 5030, "isNew":false, "mileage": 1000, "firstReg": "2010-10-01" }' localhost:9000/adverts/cars
``` 

All `PUT` through `curl`:

```
 curl -X PUT -H "Content-Type: application/json" -d '{"title":"AUDI"}' localhost:9000/adverts/cars/1
 curl -X PUT -H "Content-Type: application/json" -d '{"title":"ROLLS-ROYCE", "firstReg":"2015-01-01"}' localhost:9000/adverts/cars/2
```

and `DELETE`:

```
curl -X DELETE -H "Content-Type: application/json" localhost:9000/adverts/cars/4
```

Good luck! ;)

If you have any questions - just contact me!