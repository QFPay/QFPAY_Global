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
