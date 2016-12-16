#  3.OAuth authorization(/oauth/v2).
+ 1. authorize--------User authorization

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

+ 2.Get access_token(oauth/v2/access_token)

Request type: POST

For data security purpose, please make sure call this interface from the server side,the access_token will be used in the later interface invocation.
Call parameters:

