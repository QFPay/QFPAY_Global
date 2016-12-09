# 2.OAuth 2.0 Interface
+ 1.Based on standard OAuth process, user will redirect to /oauth/v2/authorize interface, in addition with parameters like appid, scope and redirect_url.
+ 2.After user authorization, it will redirect user to redirect_uri, with code parameter.
+ 3.Use code to get access_token 
