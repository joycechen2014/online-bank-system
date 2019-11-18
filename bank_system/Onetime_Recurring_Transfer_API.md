# Three APIS
- **/recurring/transfer/start** 启动定时转账任务  
- **/onetime/transfer/start** 一次性转账
- **/recurring/transfer/shutdown** 关闭定时转账任务
# Using ostman to test API


## 定时转账API测试
- test data
```json
{
	"recipientName":"ad",
	"accountType":"Primary",
	"amount":"1",
	"cron":"*/2 * * * * ?"
}
```
- test method
`POST`

- test url `localhost:8080/recurring/transfer/start`

##一次性转账API测试
- test data
```json
{
	"recipientName":"ad",
	"accountType":"Primary",
	"amount":"1"
}
```
-  test method 
`POST`
- test url `localhost:8080/recurring/transfer/start`

# Swagger
`http://localhost:8080/swagger-ui.html#/`