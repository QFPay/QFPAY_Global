#   4. OAuth query(/user/v1)
+ 1./baseinfo--------Get user information
Use access_token to get user information
GET

|Field Name	|Description	|Required	|Notes|
|:---:|:---:|:---:|:---:|
Access_token	|Access_token|	Y	|Response|

```javascript
{
    "respcd": "0000",
    "data": {
        "nickname": "\u94b1\u53f0\u6d4b\u8bd5",
        "userid": "rVY8nPPzMYLJV"
    },
    "resperr": "",
    "respmsg": ""
}

```

+ 2 . /tradelist--------Get order information
GET

|Field Name|	Description|	Required	|Notes|
|:---:|:---:|:---:|:---:|
|Access_token	|access_token|	Y	|
|Start_time|	Start time	|Y|	
|End_time|	End time|	Y	|
|Page|	Page number|	Y	|
|Page_size	|Page size|	Y	|
```javascript

Response
{
    "respcd": "0000",
    "data": {
        "page": 1,
        "pagesize": 10,
        "tradelist": [{
            "pay_type": "800208",
            "sysdtm": "2016-07-20 14:47:50",
            "txcurrcd": "HKD",
            "txdtm": "2016-07-20 14:47:49",
            "txamt": "1",
            "syssn": "201607200901020011215925",
            "customer_id": ""
        }]
    },
    "resperr": "",
    "respmsg": ""
}
```







