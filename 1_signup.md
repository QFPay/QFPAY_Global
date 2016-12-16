# 1.Sub merchant signup/query interface
+ 1./mch/v1/signup sign up interface                            
Sign up sub merchant interface, if succeed, return sub merchant id:mchid,after auditing,merchant can use the payment service. The sub merchant default PIN code is the last 6 digits of ID card number.                         
POST          

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
| Username |Username| Y | |Some_user|
| Id number | Id number |Y | |100000201608220123 |
| Name | Enterprise name |Y| | XX Company|
|Country  | Country name |Y| | China|
|City  |City name  |Y| |Beijing |
| Address  |Specific address  |Y| | |
| Shopname |  |Y| | XX District,XX Street|
| Bankaccount |  |Y| | XX Shop|
|bankuser  | Account holder Name |Y| | 1234567890|
|bankcountry  | Bank of deposit country name |Y| |China |
|Bankcity  |Bank of deposit city name  |Y| |Beijing |
| bankaddr | Bank of deposit address |Y| |XX district XX branch |
| Bankname |Bank Name  |Y| |BOC |
|Bankcode  |Bank Code  |Y| |123 |
|bankswiftcode  | Swift Code |Y| |456 |
|Mcc_id  | Merchant category code |Y| By invoke mcc list interface to get|6125485337528623306 |
| legalperson | Legal person name |Y| |Peter |
|Channel_type  |Channel_type ID  |Y| | |
| Email |Email address  |Y| |mail@exmaple.com |
|Mobile  | Mobile phone number |Y| | 14412345678|
| licensenumber |License number  |Y| |1234567890 |
| licenseactive_date | Active data of license |Y| | |
| idcardfront |Idcard front photo  |Y| | |
| licensephoto | Business Registration license photo |Y| File| |
| Goodphoto | Interior shop photo |Y|File | |
| Shopphoto | Exterior shop photo |Y|File  | |
|  Credit_front| Company registration license photo |Y|File   | |
| shoper | Staff member photo |Y| | |
| Other_1 |  |Y| File| |
| Other2 |  |Y|File | |

Response：
```javascript
{
    "respcd": "0000",
    "respmsg": "",
    "mchid": "BvDtmKJA5mx7GpN0"  // 子商户 id
}
 ```     
+ 2./uploadcert-------upload more information                    
Upload sign up interface for file not upload yet.                          
POST        

| Field Name    | Description   | Required  | Notes 	 | 
| ------------- |:-------------:| ---------:| ----------:|
|mchid  |The code return by signup interface.| Y | |
| idcardfront |Idcard front photo|Y  |File |
|  licensephoto|Business Registration license photo|  | |File|
| Goodphoto |Interior shop photo|  | File|
| Shopphoto |Exterior shop photo|  |File |
|Credit_front  |Company registration license photo|  |File |
| shoper |Staff member photo|  |File |
|Other_1  |Others|  |File |
| Other2 |Others|  | File|
Response：
```javascript
{
   "respcd": "0000",
    "respmsg": ""
}
 ```     
 
+ 3./query-------------query user info 

GET

| Field Name    | Description   | Required  | Notes 	 | 
| ------------- |:-------------:| ---------:| ----------:|
|mchid  |The code return by signup interface.| Y|By invoke mcc list interface to get|
Response：
```javascript
{
    "respcd": "0000",
    "respmsg": "",
    "data": {
        "username": "",       // username	
        "name": "",           // Enterprise name
        "email": "",          // email address
        "mobile": "",         // mobile phone number
        "idnumber": "",       // ID Card number
        "legalperson": "",    // Legal person name
        "shopname": "",       // Shop name
        "country": "",        // Country name
        "city": "",           // City name
        "address": "",        // address
        "businessaddr": "",   // business address
        "bankaccount": "",    // bank account number
        "bankuser": "",       // Account holder name
        "bankcountry": "",    // Bank of deposit name
        "bankcity": "",       // Bank of deposit city
        "bankaddr": "",       // Bank of deposit address
        "bankname": "",       // Bank name
        "bankswiftcode": "",  // Bank Swift code
        "state": 1            // user state
    }
}
 ``` 
 
 + 4./mcc mcc list interface

This interface get be visited by HTTP GET method, no other field or signature needed.
Response:
```javascript
{
"respcd": "0000",
    "respmsg": "",
    "data": {
        "mcc_list": [{
            "mcc_id": 6128181289566687125,
            "mcc_name": "XXX"
        }
```
