# 0.Request method   

 ##1. How to do signature                                  
  + 1.All non-empty parameters will be sorted in ascending order.
  + 2.Concatenate all the parameters using & to get the string
  + 3.Append the string with developer’s application key 
  + 4.Using md5 algorithm to do the hash calculation.
  
  Example:
  After step 3 and 4 it should like below:
  ```
  address=TST&bankaccount=168477081838&bankaddr=TST&bankcity=Hong Kong&bankcode=123&bankcountry=Hong Kong&bankname=HSBC&bankswiftcode=HSBCHKHHHKH&bankuser=Peter&channel_type=&city=Hong Kong&country=Hong Kong&email=peter@test.com&idnumber=172845391795&legalperson=Peter&licenseactive_date=20160810&licensenumber=123456&mcc_id=21002&mobile=123456&name=Peter &shopname=PeterShopR&username=peter@test.com001c49ce1d444994b419934286eb07b40f2
```
key is :3a7a3bba647fb6fbc28f6687d78212e2                 
You can also confirm your signature algorithm with above strings in [this page](http://md5jiami.51240.com/) 

## 2. Non OAuth 2.0 Interface              
 + 1. Based on interface documentation, Use GET Or POST to do the request.                             
 + 2．For any files upload interfaces,Content-Type needs to be set as multipart/form-data .                      
 + 3． If not specified, http header needs 2 following parameters:               
 X-QF-APPCODE: Allocate to developer the appcode.      
 X-QF-SIGN:Data signature, The signature based on the algorithm        

## 3. OAuth 2.0 Interface
 + 1.Based on standard OAuth process, user will redirect to /oauth/v2/authorize interface, in addition with parameters like appid, scope and redirect_url.
 + 2.After user authorization, it will redirect user to redirect_uri, with code parameter.
 + 3.Use code to get access_token 




 
