#  3.OAuth authorization(/oauth/v2).
+ 1. /authorize--------User authorization

Request type: GET
In client side, open the interface page, it will prompt user authorization, if the authorization is succeed, it will call back to redirect_url.

|Field|Required|Description|
| ------------- |:-------------:| ---------:|
|client_id  |Y|APPCODE provided by QFPay |
|redirect_uri  |Y|Authorize for call back address  |
| scope |Y|Apply for scope access,like user_baseinfo |
| response_type |Y|Constant value code |
|state|N||User-defined value,it will return the call-back address.|

After user authentication, with the following parameters：

|Field|Description|
| ------------- |:-------------:|
|code  |For get access_token’s code|
|state  |If pass this parameter, it will return this parameter.|

+ 2. /access_token(Get access_token)

Request type: POST

For data security purpose, please make sure call this interface from the server side,the access_token will be used in the later interface invocation.

Call parameters:

|Field|Required|Description|
| ------------- |:-------------:|:-------------:|
|client_id|Y|APPCODE allocated by QFPay|
|client_secret|Y|KEY allocated by QFPay|
|grant_type|Y|"Request type| fill in authorization_code|
|code|Y|Get code value by invoking authorize|

Return result:

|Field|Description|
| ------------- |:-------------:|
|access_token|For latter interface invocation.|
|expires_in|Expired time|
|refresh_token|Use this token to refresh the session.|
|userid|QFPay user id|

Example:
```javascript
{
  "access_token": "dda83e2e-951d-4983-b03f-0d82008d2044",
  "expires_in": 7200,
  "refresh_token": "13dbd85f-1f85-48a6-9394-2371a09a3fa2",
  "token_type": "Bearer",
  "userid": 1000
}
'''
