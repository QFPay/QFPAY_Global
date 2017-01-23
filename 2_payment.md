# 2.Payment(/trade/v1)
 This payment API only applicable for Chinese mainland E-wallet.
 
 This payment API can not be used in a mobile phone app development.
 
 One appcode can only correspond to one notify and one return URL, even through one merchant have multiple payment types.(For example ,one merchant has both Alipay and Wechat payment type,merchant can not set two notify and two return URL separately.)
 
+ 1. /payment---------------Open a payment

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|Mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
| pay_type |Payment type|Y | | 800208|
| out_trade_no |Reference number| Y| |1470020842103 |
| txdtm |Payment time| Y | |2016-08-01 11:07:22|
| txamt |Payment amount,unit is the minimum unit of the currency.| 1470020842103| | 10|
| udid |Device unique id| | | |
| auth_code |Authentication code| | | |
| openid || | | |
| product_name|product name/good name| | | |
| lnglat |Latitude,longtitude,format is "12.34 56.78",latitude in the front,demlit by one space| | | |

Pay_type has the following parameters:

|Pay_type | Type of payment   |Comments |
| ------------- |:-------------:|:-------------:| 
|800151  |Alipay pre order|Alipay Online Payment|
| 800108 |Alipay swipe card|Alipay Offline Payment|
|800201  |Wechat pre order|Wechat Online Payment|
| 800207 |Wechat H5|Wechat offical account(H5) Payment|
|800208  |Wechat swipe card|Wechat Offline Payment|

Attention:Alipay and Wechat have some difference!!

i.Revoke Alipay order,even through the successfuly paid order,will revoke and refund.

ii,Close Wechat order will only
close the unfinished order.Successfuly paid order can not be closed,it will return a failure code.

iii.For 800207(Wechat H5),it will need both wechant merchant account and wechat official account, and the registered company of merchant account and offical account should be the same.Right now, it is not temporarily not available.   

Response:
```javascript
{"sysdtm": "2016-12-16 15:53:18", "resperr": "", "respmsg": "OK", "out_trade_no": "XXX", "syssn": "XXX", "respcd": "0000", "pay_url": "XXX"}
```

+ 2.  /close--------------close the order(Only applicable for Online payment)

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
|syssn|transaction serial number|||201607280901020011216135|
|out_trade_no|external order number|||1470020842103|
| txdtm |transaction time| | | |
| txamt |transaction amount| | | 10|
| udid|| | | |


+ 3.  /refund ------------refund the money
 
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

+ 4. /reversal-----------Only applicable for offline situation.
When the payment failed, it will close the order.
When the payment is successful,then refund the payment and close the order.

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

+ 5. /query------------query an order


|Field Name|Required|Description|Example
| ------------- |:-------------:| ---------:|---------:|
| mchid |Y| It returns from signup interface|BvDtmKJA5mx7GpN0
| syssn ||Transaction serial number,it supports batch query,use "," to delimit mutliple syssn |201607280901020011216135,201607280901020011216136
| out_trade_no ||out_trade_number,it supports batch query,use "," to delimit mutliple out_trade_number|1470020842103,1470020842104 
| pay_type || Payment type,it supports batch query,use "," to delimit mutliple pay_type|800201,800208
| respcd || |0000
| start_time | |Start time YYYY-MM-DD HH:mm:ss ,begin from this month.|2016-08-01 11:00:00
| end_time ||End time YYYY-MM-DD HH:mm:ss,end with this month |2016-08-21 11:00:00
| page||Page default 1 |1
|page_size||Default page size：10|20

Note:
If use start_time and end_time for query, syssn and out_trade_no can be ignored.

Response:
```javascript
{
    "respcd": "0000",
    "respmsg": "", 
    "page": 1,
    "page_size": 10,
    "data": [{
        "pay_type": "800201",
        "sysdtm": "2016-07-26 17:02:01",
        "order_type": "payment",
        "txcurrcd": "HKD", 
        "txdtm": "2016-07-26 17:02:01",
        "txamt": "1",
        "out_trade_no": "1469523721486",
        "syssn": "201607260901020011216001",
        "cancel": "2",
        "respcd": "1142",
        "errmsg": ""
    }]
}
```


+ 6. /Pre order notification

For pre order ,when server get payment result, the result will be sent through pre-configured callback(notify) address（Note:The callback address is set by QFPay technical support）,and set signature in HTTP Header X-QF-SIGN,including the following data:

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|Mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
|syssn  |original transaction number|Y|
| pay_type |Payment type|Y | | 800208|
| out_trade_no |Reference number| Y| |1470020842103 |
| txdtm |Payment time| | | |
| txamt |Payment amount,unit is the minimum unit of the currency.| | | 10|
| udid |Device unique id| | | |
|respcd|Respond code|||0000|
|respmesg|Respond message||||

+  7.Online Alipay payment redirect parameters

For Alipay online payment, after the transaction, it will redirect to the return_url set by QFPay(Note:Merchant needs to tell QFPay technical person with return_url information),with the GET method and the following parameters:

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|Mchid  |The code return by signup interface| |If not fill in |BvDtmKJA5mx7GpN0 |
|syssn  |original transaction number|Y|
| pay_type |Payment type|Y | | 800208|
| out_trade_no |Reference number| Y| |1470020842103 |
| txdtm |Payment time| | | |
| txamt |Payment amount,unit is the minimum unit of the currency.| | | 10|
| udid |Device unique id| | | |
|respcd|Respond code|||0000|
|respmesg|Respond message||||

