# 2.Payment(/trade/v1)

+ 1. /payment---------------Open a payment

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|Mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
| pay_type |Payment type|Y | | 800208|
| out_trade_no |Reference number| Y| |1470020842103 |
| txdtm |Payment time| | | |
| txamt |Payment amount,unit is the minimum unit of the currency.| 1470020842103| | 10|
| udid |Device unique id| | | |
| auth_code |Authentication code| | | |
| openid || | | |
| lnglat |Latitude,longtitude,format is "12.34 56.78",latitude in the front,demlit by one space| | | |

Pay_type has the following parameters:

|Pay_type | Type of payment   |
| ------------- |:-------------:| 
|800151  |Alipay pre order|
| 800108 |Alipay swipe card|
|800201  |Wechat pre order|
| 800207 |Wechat H5|
|800208  |Wechat swipe card|

Attention:Alipay and Wechat have some difference!!

i.Revoke Alipay order,even through the successfuly paid order,will revoke and refund.

Ii,Close Wechat order will only
close the unfinished order.Successfuly paid order can not be closed,it will return a failure code.

Response:
```javascript
{"sysdtm": "2016-12-16 15:53:18", "resperr": "", "respmsg": "OK", "out_trade_no": "XXX", "syssn": "XXX", "respcd": "0000", "pay_url": "XXX"}
```

+ 2.  /close--------------close the order(Only applicable for Wechat)

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
|syssn|transaction serial number|||201607280901020011216135|
|out_trade_no|external order number|||1470020842103|
| txdtm |transaction time| | | |
| txamt |transaction amount| | | 10|
| udid|| | | |


+ 3.  /refund------------refund the money
 
 | Field Name     | Required  | Description|  
| ------------- |:-------------:| ---------:|
|syssn  |Y|original transaction number|
|out_trade_no  |Y|refund number,not the original transaction number.|
| txamt |Y|amount|
| txdtm |Y|request side time|
|udid  |N|device only id|
|Mchid  ||The code return by signup interface.|

Response:
```javascript
{
    "orig_syssn": "201607280901020011216135",
    "respmsg": "",
    "txdtm": "2016-07-28 14:13:50",
    "txamt": "1",
    "out_trade_no": "1469686430937",
    "sysdtm": "2016-07-28 14:13:51",
    "syssn": "201607280901020011216137",
}
```

+ 4. /reversal-----------cancel/flushes(Only applicable for Alipay)

When the payment failed, it will close the order.
When the payment is successful,then refund the payment will close the order.

| Field Name    |Required  | Description    | 
| ------------- |:-------------:| ---------:|
| mchid |Y| The code return by signup interface.|
|syssn|Y||transaction serial number|
|out_trade_no|Y||external order number|
|txdtm|Y||transaction time|
|txamt|Y||transaction amount|
|udid||Device only id|
Response:
```javascript
{
    "orig_syssn": "201607280901020011216135",
    "respmsg": "",
    "txdtm": "2016-07-28 14:13:50",
    "txamt": "1",
    "out_trade_no": "1469686430937",
    "sysdtm": "2016-07-28 14:13:51",
    "syssn": "201607280901020011216137",
}
```
