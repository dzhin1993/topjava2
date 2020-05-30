
## curl samples (application deployed in application context `topjava`).

### for logged user:

#### get All meals
`curl -s http://localhost:8080/topjava/rest/meals`

#### get meal 100002
`curl -s http://localhost:8080/topjava/rest/meals/100002`

#### delete meal 100002
`curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100002`

#### create meal
`curl -s -X POST -d '{"dateTime":"2020-06-01T18:00:00", "description":"new", "calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals`


#### update lunch 100005
`curl -s -X PUT -d '{"id": 100003, "dateTime":"2020-06-01T18:00:00", "description":"update", "calories":300}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100003`


