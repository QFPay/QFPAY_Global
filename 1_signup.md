# 1.Sub merchant signup/query interface
+ 1./mch/v1/signup sign up interface                            
Sign up sub merchant interface, if succeed, return sub merchant id:mchid,after auditing,merchant can use the payment service. The sub merchant default PIN code is the last 6 digits of ID card number.                         
POST          

| Field Name    | Description   | Required  | Notes  | Example  |
| ------------- |:-------------:| ---------:| ------:| --------:|
| Username |Username| Y | |Some_user|
| Id number | Id number |Y | | |
|  |  |Y| |100000201608220123 |
| Name | Enterprise name |Y| | |
|  |  |Y| | XX Company|
|Country  | Country name |Y| | China|
|City  |  |Y| | |
| Address  |  |Y| | |
| Shopname |  |Y| | |
| Bankaccount |  |Y| | |
|bankuser  |  |Y| | |
|bankcountry  |  |Y| | |
|Bankcity  |  |Y| | |
| bankaddr |  |Y| | |
| Bankname |  |Y| | |
|Bankcode  |  |Y| | |
|bankswiftcode  |  |Y| | |
|Mcc_id  |  |Y| | |
| legalperson |  |Y| | |
|Channel_type  |  |Y| | |
| Email |  |Y| | |
|Mobile  |  |Y| | |
| licensenumber |  |Y| | |
| licenseactive_date |  |Y| | |
| idcardfront |  |Y| | |
| licensephoto |  |Y| | |
| Goodphoto |  |Y| | |
| Shopphoto |  |Y| | |
|  |  |Y| | |



