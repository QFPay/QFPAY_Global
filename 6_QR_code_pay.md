# Dynamic QR code pay
Merchant enter the price of product and call QFPAY's interface to generate a dynamic QR code, consumer swipe the QR code with Wechat or Alipay app to finish the payment. The dynamic QR code will expire in 30 minutes.

|Method|Path|Description|
|-------------|-------------|-------------|
|Post|/trade/v1/payment|start payment|

Request parameters:

|parameter|Required|Description|
|-------------|-------------| ---------|
|txamt|Y|amount of payment, measured in cent|
|txcurrcd|Y|currency code : HKD, RMB|
|pay_type|Y|payment type: wechat: 800201；alipay: 800101|
|out_trade_no|Y|merchant's order number, this number should unique, even do the refund, no longer than 128 byte|
|txdtm|Y|transaction time; format: YYYY-MM-MM HH:MM:SS|
|goods_name|Y|name of the product, no long than 20 byte, special character is not allowed|
|mchid|Y|sub merchant id, allocate by QFPAY(You can look up merchant ID in channel system)|
|limit_pay|N|the value of this parameter can only be set to no_credit, which means credit is not allowed|
|udid|N|UUID of device, no longer than 40 characters|

Response parameters:

|parameter|description|
|-------------|-------------|
|pay_type|payment type: wechat: 800201；alipay: 800101；|
|sysdtm|system time|
|txdtm|transaction time;format：YYYY-MM-MM HH:MM:SS|
|resperr|error message|
|txamt|amount of payment, measured in cent|
|respmsg|response message|
|out_trade_no|merchant's order number, this number should unique, even do the refund, no longer than 128 byte|
|syssn|order number in QFPAY system|
|qrcode|QR code url, merchant need to convert it to QR code image|
|respcd|0000 stands for order placed successfully 1143,1145 stand for transaction on going, merchant need to continue querying transaction. if other codes returned, transaction failed|

response example:

{"pay_type": "800201",
"sysdtm": "2018-01-12 17:38:50", "cardcd":
"", "txdtm": "2018-01-12 17:38:56",
"resperr": "\u4ea4\u6613\u6210\u529f", "txcurrcd":
"CNY", "txamt": "1", "respmsg":
"", "out_trade_no": "13014597457448787530052",
"syssn": "20180112000100020001659801", "qrcode":
"weixin://wxpay/bizpayurl?pr=hBvNxhc", "respcd":
"0000"} 

Asynchronous notification:

Asynchronous notification can be delayed because of external factors, please call order query interface if your system requires high real-time performance. Due to security reason, asynchronous notification only support port 80 and 443, customized port is not support.



# Swipe consumer's QR code
In this scenario, merchant can swipe consumer's Wechat/Alipay qrcode via scanning gun to get auth_code, cash will be deducted from consumer's Wechat/Alipay account 

|Method|Path|Description|
|-------------|-------------|-------------|
|Post|/trade/v1/payment|start payment|

Parameters


|Parameter|Required|Description|
|-------------|-------------| ---------|
|txamt|Y|amount of payment, measured in cent|
|txcurrcd|Y|currency code : HKD, RMB|
|pay_type|Y|Paytype  Wechat:800208；Alipay:800108;All-in-one swipe 800008；|
|out_trade_no|Y|merchant's order number, this number should unique, even do the refund, no longer than 128 byte|
|txdtm|Y|transaction time;format：YYYY-MM-MM HH:MM:SS|
|auth_code|Y|authentication code of Wechat or Alipay|
|goods_name|Y|product name|
|mchid|Y|sub merchant id, allocate by QFPAY(You can look up merchant ID in channel system)|
|udid|N|UUID of device, no longer than 40 characters|

Response parameters:

|Parameter|Description|
|-------------|-------------|
|pay_type|Paytype  Wechat:800208；Alipay:800108;|
|sysdtm|system time|
|txdtm|transaction time;format：YYYY-MM-MM HH:MM:SS|
|resperr|error message|
|txcurrcd|currency code : HKD, RMB|
|txamt|amount of payment, measured in cent|
|respmsg|response message|
|out_trade_no|merchant's order number, this number should unique, even do the refund, no longer than 128 byte|
|syssn|order number in QFPAY system|
|respcd|0000 stands for order placed successfully 1143,1145 stand for transaction on going, merchant need to continue querying transaction. if other codes returned, transaction failed|

Swipe consumer's QR code is trying to deduct cash from consumer's account directly, respcd parameter in response stands for the payment result, if this parameter is 1143 or 1145, we recommend merchant continuing calling query interface to track the payment result. if the order's status keeps returning 1143 or 1145 for more than 2 minutes, we recommend that merchant closing previous order and place the order again. 

response example:

{"pay_type": "800208",
"sysdtm": "2018-01-12 17:10:26", "cardcd":
"oo3Lss-DzPSygtHtAbfuXeQFCz18", "txdtm": "2018-01-12
17:10:32", "resperr": "\u4ea4\u6613\u6210\u529f",
"txcurrcd": "CNY", "txamt": "1",
"respmsg": "", "out_trade_no":
"1301459478787530052", "syssn":
"20180112000100020001659358", "respcd": "0000"}
Asynchronous notification:

Asynchronous notification can be delayed because of external factors, please call order query interface if your system requires high real-time performance. Due to security reason, asynchronous notification only support port 80 and 443, customized port is not support.