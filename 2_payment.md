# 2.Payment(/trade/v1)
What is QFPay_Oversea API?

Merchant applies Wechat and Alipay from QFPay.QFPay person helps to do the backend configuration.
So Merchant can just change payment type to do various type of payment.
In Alipay,one merchant linkes to one PID(Partner Identification ID).One PID can only be Online or Offline.
In Wechat,one merchant linkes to mchid(Merchant ID).One mchid can be both Online or Offline.

![](https://github.com/linan0828/QFPAY_Oversea/blob/master/Contents/Pic/HowQFPayWorks.jpeg)

Level1：
It is visible to merchant.

Level2:
It is not visible to merchant,QFPay encapsulates the logic behind it.
So one pair of parameter can use be both used in Alipay and Wechat.

 This payment API only applicable for Chinese mainland E-wallet only!.
 
 This payment API can not be used in a mobile phone app development.（No SDK provided）
 
 One appcode can only correspond to one notify and one return URL, even through one merchant have multiple payment types.(For example ,one merchant has both Alipay and Wechat payment type,merchant can not set two notify and two return URL separately.)
 
 Normal development process：
 
 1.Apply Wechat and Alipay account from Qfpay.
 
2.Qfpay provides appcode,key,mchid to merchant.

3.Merchant use Qfpay parameters to do the development.

4.Based on merchant application to Qfpay, merchant can use Wechat or Alipay payment based on pay_type.
If the pay_type is not available, it will show filter_error(获取用户支付通道失败)

5.Give Qfpay callback and return URL for online payment.

6.If merchant uses Wechat official account payment, fill in
[公众号信息_钱方+微信公众号支付绑定申请表](https://github.com/linan0828/QFPAY_Oversea/tree/master/Contents/Wechat/OA)


Notes:
1. one Qfpay mchid can at most consist one mch_id from Wechat(online+offline) ,two PID from Alipay(online+offline)

 
 [Test link](http://bartforfun.oicp.io:8080/QFPayOverseaQiantai/)
 
 [Example PHP code](https://github.com/linan0828/QFPAY_Oversea/tree/master/Code/PHP)
 
 
 
+ 1. /payment---------------Open a payment

| Field Name    | Description   | Required  | Notes 	 | Example  |
| ------------- |:-------------:| ---------:| ----------:| --------:|
|Mchid  |The code return by signup interface|Y| |BvDtmKJA5mx7GpN0 |
| pay_type |Payment type|Y | | 800208|
| out_trade_no |Reference number|Y| |1470020842103 |
| txdtm |Payment time| Y | |2016-08-01 11:07:22|
| txamt |Payment amount,unit is the minimum unit of the currency.If 1 HKD, txamt is 100|Y| | 10|
| auth_code |Authentication code.| |It is only used for offline scene.When do the payment offline, it is must be passed in.The length is usually 18 bits long.The length may change in the future. | |
| sub_openid |Only for Wechat OA account payment| | | |
| product_name|product name/good name| | | |
|valid_time|Order valid time||At least 300s(5mins)||

Pay_type has the following parameters:

Note：800151,800152,800201,800207 are for online scene.800108,800208 are for offline scene.
![](https://github.com/linan0828/QFPAY_Oversea/blob/master/Contents/Pic/QFPayAPI.jpeg)


|Pay_type | Type of payment   |Comments |
| ------------- |:-------------:|:-------------:| 
|800151  |Alipay pre order|In a PC-based website|
|800152  |Alipay WAP|In a mobile-based browser website|
|800108  |Alipay swipe card|For bar code scanning gun in a store|
|800201  |Wechat pre order|In a PC-based website|
|800208  |Wechat swipe card|For bar code scanning gun in a store|
|800207  |Wechat H5|In Wechat browser|

Attention:Alipay and Wechat have some difference!!

i.Revoke Alipay order,even through the successfuly paid order,will revoke and refund.

ii,Close Wechat order will only
close the unfinished order.Successfuly paid order can not be closed,it will return a failure code.

iii.In most situation, development just need to implement the following interface:

iv.1145 problem means it is in pending state.Please wait another 5 seconds to do the query to confirm the transaction status.

1.payment

2.offline:reverse and refund

  online:close and refund
  
3.query

4.settlement

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
|Mchid  |Y||


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


Example Response：

Payment:
```javascript
{"pay_type": "800208", "out_trade_no": "57003579", "cardcd": "oKeGJuIhEWuWZQPdXFBkXdgr5nLc", "txdtm": "2017-09-06 14:56:43", "resperr": "", "txamt": "10", "respmsg": "ok", "sysdtm": "2017-09-06 14:56:43", "syssn": "201709060902990042326492", "txcurrcd": "HKD", "respcd": "0000", "code_url": ""}
```
Query_Payment: 
```javascript
{"respmsg": "", "resperr": "", "respcd": "0000", "data": [{"pay_type": "800208", "sysdtm": "2017-09-06 14:56:43", "order_type": "payment", "txcurrcd": "", "txdtm": "2017-09-06 14:56:43", "txamt": "10", "out_trade_no": "57003579", "syssn": "201709060902990042326492", "cancel": "0", "respcd": "0000", "errmsg": "\u4ea4\u6613\u6210\u529f"}], "page": 1, "page_size": 10}
```  
Reversal:
```javascript
{"resperr": "", "sysdtm": "2017-09-06 15:00:35", "orig_syssn": "201709060902990042326492", "respmsg": "ok", "out_trade_no": "57003579", "syssn": "201709060902990042326548", "respcd": "0000"}
``` 
Query_Reversal_Orig:   
```javascript
{"respmsg": "", "resperr": "", "respcd": "0000", "data": [{"pay_type": "800208", "sysdtm": "2017-09-06 14:56:43", "order_type": "payment", "txcurrcd": "", "txdtm": "2017-09-06 14:56:43", "txamt": "10", "out_trade_no": "57003579", "syssn": "201709060902990042326492", "cancel": "1", "respcd": "0000", "errmsg": "\u4ea4\u6613\u6210\u529f"}], "page": 1, "page_size": 10}
``` 
Query_Reversal_New:   
```javascript
{"respmsg": "", "resperr": "", "respcd": "0000", "data": [], "page": 1, "page_size": 10}
```  
Refund: 
```javascript
{"resperr": "", "txdtm": "2017-09-06 15:09:08", "txamt": "10", "respmsg": "ok", "out_trade_no": "98654216", "syssn": "201709060901990042326683", "respcd": "0000", "sysdtm": "2017-09-06 15:09:09", "orig_syssn": "201709060902990042326663"}
``` 
Query_Refund_Orig:   
```javascript
{"respmsg": "", "resperr": "", "respcd": "0000", "data": [{"pay_type": "800208", "sysdtm": "2017-09-06 15:07:47", "order_type": "payment", "txcurrcd": "", "txdtm": "2017-09-06 15:07:46", "txamt": "10", "out_trade_no": "98654216", "syssn": "201709060902990042326663", "cancel": "2", "respcd": "0000", "errmsg": "\u4ea4\u6613\u6210\u529f"}], "page": 1, "page_size": 10}
```
Query_Refund_New:   
```javascript
{"respmsg": "", "resperr": "", "respcd": "0000", "data": [{"pay_type": "800208", "sysdtm": "2017-09-06 15:09:09", "order_type": "refund", "txcurrcd": "", "txdtm": "2017-09-06 15:09:08", "txamt": "10", "out_trade_no": "98654216", "syssn": "201709060901990042326683", "cancel": "0", "respcd": "0000", "errmsg": "\u4ea4\u6613\u6210\u529f"}], "page": 1, "page_size": 10}
```          

