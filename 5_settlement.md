# 5. Settlement(/settlement)
+  1.Query the settlement(/query)

Field Name|	Description|	Required	|Notes|	Example|
|:--:|:--:|:--:|:--:|:--:|
|mchid|	The code return by signup interface.|	Y|	Sub-merchant ID|	BvDtmKJA5mx7GpN0|
|Date_start|	Query settlement start date|	Y|		|20160101|
|Date_end|	Query settlement end date|	Y	||20160130|	
|paymethod	Payment method||	N|	|2(Alipay),3(Wechat)|	

Response
```javascript
{
    "resperr": "",
    "respcd": "0000",
    "respmsg": "",
    "data": [
        {
            "chnl_mch_id": "1315135101",            // merchant ID allocated by channel
            "refund_amt": 0,                        //total refund amount
            "date_settlement": "2016-02-29",        //settlement time
            "paymethod": "微信",                    //payment type
            "date_end": "2016-02-29",               //transaction start time
            "date_start": "2016-02-23",             //transaction end time
            "currency": "HKD",                      //type of currency
            "pay_amt": 433280000,                   //payment amount
            "chnl_poundage_amt": 4309600,           //channel fee
            "pay_net_amt": 433280000,               //payment net amount
            "settlement_amt": 428970400             //settlement amount
        }
    ]
}
```
