# team-project-newteam
team-project-newteam created by GitHub Classroom
### 

## Use Case:

Online Banking system. 
* Design and implement supporting APIs for a banking system including a Database of your choice to persist Customer data.

        - register for new user
        - Adding new accounts - Checking, Savings
        - Deposit money
        - Closing existing accounts
        - Transfer between accounts - one time or recurring
        - Set up recurring or one-time Bill payment for external payees
        - View and search Transactions - for credits/debits/checks/fees - up to  last 18 months
        - (Admin only) - Add transactions - such as manual refunds on fees

## Deploy 
   - Deploy API to **AWS in an Auto Scaled EC2 Cluster** with Load Balancer
   
### Deploy API and Database to Docker Containers in AWS     
   
   1. Create a online-banking app docker image: 
   docker build -t online-banking-app .
   
   2. Create a online-banking db docker image:
   ```
   FROM mysql:5.7
   Add a database
   ENV MYSQL_DATABASE onlinebanking
   COPY V1__20190307_Init_Tables_Data.sql /docker-entrypoint-initdb.d/
   docker build -t online-banking-db .
   ```
   2. Push docker images to AWS ECR:
   ```
   docker tag online-banking-app:latest 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-app:latest
   docker push 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-app:latest
  
   docker tag online-banking-db:latest 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-db:latest
   docker push 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-db:latest
   ```
   3. Use docker compose to start up docker containers:
   ```
   version: '3'
   
   services:
     mysql-docker-container:
       image: 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-db
       environment:
         - MYSQL_ROOT_PASSWORD=welcome1
         - MYSQL_DATABASE=onlinebanking
         - MYSQL_USER=root
         - MYSQL_PASSWORD=welcome1
       volumes:
         - /data/mysql
     online-banking:
       image: 120231263760.dkr.ecr.us-east-1.amazonaws.com/online-banking-app
       depends_on:
         - mysql-docker-container
       ports:
         - 8080:8080
       volumes:
         - /data/online-banking
   
   docker-compose up 
   ```   
 ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/UseCase%20Diagram0.png)


## Diagram:
    
   - Architecture
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/Architecture.png)
   - Register and add account
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/AccoutApi.png)
   - Transfer between accounts - one time
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/transfer%20account.jpg)
   - Transfer between accounts - recurring
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/transfer_account_reccuring.jpg)
   - View and search  Transactions
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/transaction.jpg)
## APIs:
>Register new user and add account
- **/api/addUser**
- **/api/addCheckingAcc**
- **/api/addSavingsAcc**
>Deposit money
- **api/deposit/**
>One time transfer
- **/onetime/transfer/start** 
>Recurring transfer
- **/recurring/transfer/start**  
- **/recurring/transfer/shutdown** 
>View and search transaction
- **/api/primarytransaction/searchresult**
- **/api/savingstransaction/searchresult**
>Admin refund
- **/admin/refund** 
>Delete account
- **/api/deleteCheckingAcc**
- **/api/deleteSavingsAcc**

# Using postman to test APIs

* Register new user.
    * URL: Register new user: http://localhost:8080/api/addUser
    * Method: POST
    * Params:
    ```json
        {"username" : "test111",
        "password" : "123456",
        "firstName" : "yiyo",
        "lastName" : "chen",
        "Email" : "yiyo_1234@sjsu.edu",
        "phone" : "1234445678"
        }  
    ```
 
*   Add Primary Account and Savings Account. 
     * URL: 
       * Add Primary Accounts: http://localhost:8080/api/addPrimaryAcc/{id}
       * Add Savings Accounts: http://localhost:8080/api/addCheckingAcc/{id}
       * Method: PUT
        * Params:
               * Required: User_id
               
*   Deposit money to saving account or primary account 
     * URL: 
       * Add Primary Accounts: http://localhost:8080/api/deposit/{accountType}/{amount}/{receiver}"
       * Method: PUT
        * Params:
               * Required: AccountType (savings or primary),Amount,Receiver (username)       
* Transfer between accounts - one time
     * URL: http://localhost:8080/onetime/transferbetweenaccounts
     * Method: POST
     * Params Example: transfer $10 from checking account to saving account one time
       ```json
       {
	    "transferFrom":"Primary",
	    "transferTo":"Savings",
		"amount":"10"
       }
       ``` 
* Transfer between accounts - recurring
     * URL: http://localhost:8080/recurring/transferbetweenaccounts
     * Method: POST
     * Params Example: transfer $1 from checking account to saving account every 2 sec
	```json
	{
	"transferFrom":"Primary",
	"transferTo":"Savings",
	"amount":"1",
	"cron":"*/2 * * * * ?"
	}
        
* Shutting down recurring transfer between accounts
	* URL: http://localhost:8080/recurring/transferbetweenaccounts/shutdown
	* Method: GET
* One time transfer
     * Prerequisite : get cookie with logged in status from browser and put it on Postman
     * URL:
       * http://localhost:8080/onetime/transfer/start   
     * Method: POST
     * Params:
       ```json
       {
       	"recipientName":"ad",
       	"accountType":"Primary",
       	"amount":"1"
       }
       ``` 
* Recurring transfer
     * URL:  http://localhost:8080/recurring/transfer/start   
     * Prerequisite : get cookie with logged in status from browser and put it on Postman 
     * Method: POST
     * Params: 
     ```json
     {
     	"recipientName":"ad",
     	"accountType":"Primary",
     	"amount":"1",
     	"cron":"*/2 * * * * ?"
     }
     ```
* Shutting down recurring transfer between accounts
	* URL: http://localhost:8080/recurring/transfer/shutdown
	* Method: GET

* View and search  Transactions
    * URL: 
    	* View all transactions in checking accounts: http://localhost:8080/api/user/primary/transaction
		* View all transactions in saving accounts: http://localhost:8080/api/user/savings/transaction
		* Search transactions in checking accounts: http://localhost:8080/api/primarytransaction/searchresult
		* Search transactions in saving accounts: http://localhost:8080/api/savingstransaction/searchresult
    * Prerequisite: get cookie with logged in status from browser and put it on Postman
    * Method: GET
    * Params:
        * Required: username
        * Optional: date/type/status/amount
                     
* Refund money
    * URL: 
        * http://localhost:8080/admin/refund
    * Prerequisite: get cookie with logged in status from browser and put it on Postman
    * Method: POST
    * Params:
    ```json
    {
        "amount": "6",
        "accountType": "Primary",
        "targetUserName":"kobe73er"
    }
    ```
* Delete account
    * URL: 
        * http://localhost:8080/api/deleteSavingsAcc/{userId}/{accountId}
	* http://localhost:8080/api/deleteCheckingAcc/{userId}/{accountId}
    * Method: Delete
