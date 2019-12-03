# team-project-newteam
team-project-newteam created by GitHub Classroom
### 

## Use Case:

Online Banking system. 
* Design and implement supporting APIs for a banking system including a Database of your choice to persist Customer data.

        - register for new user
        - Adding new accounts - Checking, Savings
        - Closing existing accounts
        - Transfer between accounts - one time or recurring
        - Set up recurring or one-time Bill payment for external payees
        - View and search Transactions - for credits/debits/checks/fees - up to  last 18 months
        - (Admin only) - Add transactions - such as manual refunds on fees
        - Deploy API to **AWS in an Auto Scaled EC2 Cluster** with Load Balancer
        - Web or mobile UI
        
 ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/UseCase%20Diagram0.png)


## Diagram:
    
   - Architecture
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/Architecture.png)
   - Register and add account
    ![image](https://github.com/gopinathsjsu/team-project-newteam/blob/master/img/AccoutApi.png)

## APIs:
* View and search Transactions
    * URL: 
        * Checking Accounts: localhost:8080/api/primarytransaction/searchresult
        * Saving Accounts: localhost:8080/api/savingstransaction/searchresult
    * Before Use: get cookie with logged in status from browser and put it on Postman
    * Method: GET
    * Params:
        * Required: username
        * Optional: date/type/status/amount
        
* Register new user and add Primary Account and Savings Account.
    * URL: 
        * Register new user: http://localhost:8080/account/addUser

    Method: POST
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
               * Required: AccountType (savings or primary)
               * Required: Amount
               * Required: Receiver (username)              