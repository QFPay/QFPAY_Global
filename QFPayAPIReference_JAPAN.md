QFPay API in Japanese Business

**Index**

-   1\. Business Process

-   1.1 Merchant registration

-   1.2 Payment Process

    -   1.2.1 Barcode payment

    -   1.2.2 Query transaction

    -   1.2.3 Cancel transaction

    -   1.2.4 Response code description

-   1.3 Reconciliation

-   2\. API

-   2.1 Scan barcode interface

-   2.2 Dynamic QR code pay interface

-   2.3 Refund interface

-   2.4 Query interface

-   2.5 Close interface

-   2.6 Download reconciliation interface

-   2.7 Parameter Description

-   3\. Developer Guide

-   3.1 Environment Description

-   3.2 Signature Algorithm

-   3.3 Request Description

-   3.4 Verifying the Signature

-   3.5 Notification Callback

-   3.6 Sample code

1. Business Process
===================

1.1 Merchant registration 
--------------------------

Merchants have to register to the QFPay services.

After registration, merchants will receive their app-code, key for
integration, and the unique merchant ID (mchid) for each store.

Example:

AppCode: *\<32\_character\_MD5\_string\_qfpay\>*

Key: *\<32\_character\_MD5\_string\_qfpay\>*

1.2 Payment Process
-------------------

Now, we provide an example about how merchants can use the API to do a
barcode payment.

Note: There are limitations to the payment amount (depending on bank).
Please refer to
[[kf.qq.com/touch/sappfaq/151210NZzmuY151210ZRj2y2.html?platform=15&ADTAG=veda.weixinpay.wenti]{.underline}](http://kf.qq.com/touch/sappfaq/151210NZzmuY151210ZRj2y2.html?platform=15&ADTAG=veda.weixinpay.wenti)

### 1.2.1 Barcode payment

After receiving the code, key and mchid, merchants are ready to use the
QFPay services. By using code scanner, Merchant can scan consumer\'s
Wechat/Alipay QR code to get the auth\_code, cash will be deducted from
consumer\'s Wechat/Alipay account. The whole process is shown as the
following flow chart

Process:

Step 1, using code scanner to scan consumer\'s Wechat/Alipay QR code,
send the transaction request to QFPay system. (The detail of the barcode
payment interface can be found at API 2.1)

1a, if get response code as 0000 from the QFPay system, then the
transaction is successful.

1c, if get response code which is not 0000, 1143 or 1145, then the
transaction is failed.

1b, if get response code as 1143, 1145 from the QFPay system, then the
transaction is still in processing.

![C:\\Users\\16N008-5Zhang\\Desktop\\Integration\\flow.PNG](media/image1.png){width="5.052083333333333in"
height="2.6180555555555554in"}

### 1.2.2 Query transaction

Step 2, call the query interface to QFPay system and check the status of
the transaction again.

(The detail of the query interface can be found at API 2.4)

2a, if get response code as 0000 from the QFPay system, then the
transaction is successful.

2c, if get response code which is not 0000, 1143 or 1145, then the
transaction is failed.

2b, if get response code as 1143, 1145 from the QFPay system, then the
transaction is still in processing. Go back to Step 2.

![C:\\Users\\16N008-5Zhang\\Desktop\\Integration\\flow.PNG](media/image1.png){width="5.052083333333333in"
height="1.9131944444444444in"}

### 1.2.3 Cancel transaction

After calling query interface for more than 2 minutes, if the response
code is still 1143 or 1145, please cancel the transaction and place the
order again.

(The detail of the cancel interface can be found at API 2.5)

![C:\\Users\\16N008-5Zhang\\Desktop\\Integration\\flow.PNG](media/image1.png){width="5.052083333333333in"
height="0.4340277777777778in"}

### 1.2.4 Response code description

+-----------------------------------+-----------------------------------+
| Response code                     | Description                       |
+-----------------------------------+-----------------------------------+
| 0000                              | Operation successful              |
+-----------------------------------+-----------------------------------+
| 1147                              | Wechat card failed \| brushed     |
|                                   | Hong Kong wallet, does not        |
|                                   | support or password input error   |
+-----------------------------------+-----------------------------------+
| 2007                              | The consumer closed the           |
|                                   | transaction \| the consumer needs |
|                                   | to enter the password, but the    |
|                                   | consumer closes the password      |
|                                   | input box, causing the order to   |
|                                   | close.                            |
+-----------------------------------+-----------------------------------+
| 2006                              | QR code is illegal;               |
|                                   |                                   |
|                                   | Alipay QR code was scanned by     |
|                                   | wechat;                           |
|                                   |                                   |
|                                   | QR code is invalid                |
+-----------------------------------+-----------------------------------+
| 1142                              | The order has been closed \| the  |
|                                   | merchant opened an order, then    |
|                                   | timed out and the order closed.   |
+-----------------------------------+-----------------------------------+
| 1144                              | Wechat pay QR code was scanned by |
|                                   | Alipay                            |
|                                   |                                   |
|                                   | Bank card binded is invalid.      |
+-----------------------------------+-----------------------------------+
| 2004                              | Insufficient account balance      |
+-----------------------------------+-----------------------------------+
| 2005                              | QR code is invalid \| payment is  |
|                                   | made within one minute of the     |
|                                   | consumer opening the payment APP  |
+-----------------------------------+-----------------------------------+
| 1142                              | Order has been closed.            |
+-----------------------------------+-----------------------------------+
| 1145                              | Waiting for payment.              |
+-----------------------------------+-----------------------------------+
| 1296                              | Channel error (configuration      |
|                                   | error or Wechat is suspended)     |
+-----------------------------------+-----------------------------------+
| 1297                              | Please try other payment types \| |
|                                   | Configuration error, scaner side  |
|                                   | and payment side are the same     |
|                                   | Wechat user.                      |
+-----------------------------------+-----------------------------------+
| 1143                              | \"The order does not exist\" ,    |
|                                   | The refund channel request was    |
|                                   | not passed, resulting in no       |
|                                   | refund                            |
+-----------------------------------+-----------------------------------+

2. API
======

2.1 Barcode Payment
-------------------

### 2.1.1 Interface Description

In this scenario, merchant can scan consumer\'s Wechat/Alipay QR code
via scanning gun to get auth\_code, cash will be deducted from
consumer\'s Wechat/Alipay account

### 2.1.2 Process Flow

![C:\\Users\\16N008-5Zhang\\Desktop\\Integration\\flow.PNG](media/image1.png){width="5.052083333333333in"
height="4.546500437445319in"}

### 2.1.3 Request Information

  ----------------- ----------------------------------------------------------
  **Method**        HTTP Post
  **Path**          /trade/v1/payment
  **Description**   Merchants scan the QR code in the consumer's smartphone.
  ----------------- ----------------------------------------------------------

### 2.1.4 Request Parameters

+-------------+-------------+-------------+-------------+-------------+
| **Parameter | **Required* | **Definitio | **Descripti | **Example** |
| **          | *           | n**         | on**        |             |
+-------------+-------------+-------------+-------------+-------------+
| txamt       | Y           | **Int**     | **Amount of | **1**       |
|             |             |             | payment: ** |             |
|             |             |             |             |             |
|             |             |             | It is       |             |
|             |             |             | measured in |             |
|             |             |             | the minimum |             |
|             |             |             | price unit  |             |
|             |             |             | of the      |             |
|             |             |             | transaction |             |
|             |             |             | currency.   |             |
|             |             |             |             |             |
|             |             |             | (Maximum    |             |
|             |             |             | 10,000 USD) |             |
+-------------+-------------+-------------+-------------+-------------+
| txcurrcd    | Y           | **String**  | **Currency  | **JPY**     |
|             |             |             | code:** JPY |             |
+-------------+-------------+-------------+-------------+-------------+
| pay\_type   | Y           | **String**  | **Payment   | **800208**  |
|             |             |             | type:**     |             |
|             |             |             |             |             |
|             |             |             | Wechat:8002 |             |
|             |             |             | 08；Alipay:8 |            |
|             |             |             | 00108;      |             |
|             |             |             |             |             |
|             |             |             | All-in-one  |             |
|             |             |             | swipe       |             |
|             |             |             | 800008；    |             |
+-------------+-------------+-------------+-------------+-------------+
| [[out\_trad | Y           | **String**  | **Merchant\ | **146968638 |
| e\_no]{.und |             |             | 's          | 9649**      |
| erline}](#o |             | No longer   | order       |             |
| ut_trade_no |             | than 128    | number:**   |             |
| )           |             | byte        |             |             |
|             |             |             | The         |             |
|             |             |             | merchant's  |             |
|             |             |             | order       |             |
|             |             |             | number has  |             |
|             |             |             | to be       |             |
|             |             |             | unique      |             |
+-------------+-------------+-------------+-------------+-------------+
| txdtm       | Y           | **String ** | **Transacti | **2016-07-2 |
|             |             |             | on          | 8           |
|             |             |             | time :**    | 14:13:10**  |
|             |             |             |             |             |
|             |             |             | format:     |             |
|             |             |             | YYYY-MM-DD  |             |
|             |             |             | HH:MM:SS    |             |
+-------------+-------------+-------------+-------------+-------------+
| auth\_code  | Y           | **String(12 | **Authentic | **120061098 |
|             |             | 8)**        | ation       | 828009406** |
|             |             |             | code:**     |             |
|             |             |             |             |             |
|             |             |             | The QR code |             |
|             |             |             | shown from  |             |
|             |             |             | the user's  |             |
|             |             |             | Wechat or   |             |
|             |             |             | Alipay      |             |
+-------------+-------------+-------------+-------------+-------------+
| goods\_name | Y           | **String**  | **Name of   |             |
|             |             |             | the         |             |
|             |             | No long     | product:**  |             |
|             |             | than 20     |             |             |
|             |             | byte        | special     |             |
|             |             |             | character   |             |
|             |             |             | is not      |             |
|             |             |             | allowed     |             |
+-------------+-------------+-------------+-------------+-------------+
| mchid       | Y           | **String**  | **Merchant  | **BvDtmKJA5 |
|             |             |             | id:**       | mx7GpN0**   |
|             |             |             |             |             |
|             |             |             | allocate by |             |
|             |             |             | QFPAY(You   |             |
|             |             |             | can look up |             |
|             |             |             | merchant ID |             |
|             |             |             | in Agent    |             |
|             |             |             | Management  |             |
|             |             |             | System)     |             |
+-------------+-------------+-------------+-------------+-------------+
| udid        | N           |             | UUID of     |             |
|             |             |             | device, no  |             |
|             |             |             | longer than |             |
|             |             |             | 40          |             |
|             |             |             | characters  |             |
+-------------+-------------+-------------+-------------+-------------+

### 2.1.5 Request Example

+-----------------------------------------------------------------------+
| {\'pay\_type\': \'800201\',                                           |
|                                                                       |
| \'out\_trade\_no\': 755004603759,                                     |
|                                                                       |
| \'goods\_name\':                                                      |
| \'\\xe9\\x92\\xb1\\xe6\\x96\\xb9\\xe6\\xb5\\x8b\\xe8\\xaf\\x95\',     |
|                                                                       |
| \'limit\_pay\': \'no\_credit\',                                       |
|                                                                       |
| \'udid\': \'1880105\',                                                |
|                                                                       |
| \'txcurrcd\': \'JPY\',                                                |
|                                                                       |
| \'txdtm\': \'2018-07-20 04:11:11\',                                   |
|                                                                       |
| \'mchid\': \'8w5pdhDJkm\', \'                                         |
|                                                                       |
| txamt\': 1}                                                           |
+-----------------------------------------------------------------------+

### 2.1.6 Response Parameters

+-----------------+-----------------+-----------------+-----------------+
| **Parameter**   | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| pay\_type       | **String**      | **Payment       | **800201**      |
|                 |                 | type**:         |                 |
|                 |                 |                 |                 |
|                 |                 | wechat:         |                 |
|                 |                 | 800201；alipay: |                 |
|                 |                 | 800101；        |                 |
+-----------------+-----------------+-----------------+-----------------+
| sysdtm          | **String**      | **System time** | **2016-07-28    |
|                 |                 |                 | 14:13:10**      |
+-----------------+-----------------+-----------------+-----------------+
| txdtm           | **String**      | **Transaction   | **2016-07-28    |
|                 |                 | time :**        | 14:13:09**      |
|                 |                 |                 |                 |
|                 |                 | format:         |                 |
|                 |                 | YYYY-MM-DD      |                 |
|                 |                 | HH:MM:SS        |                 |
+-----------------+-----------------+-----------------+-----------------+
| resperr         | **String**      | **Information   | **success**     |
|                 |                 | Description**   |                 |
+-----------------+-----------------+-----------------+-----------------+
| txcurrcd        | **String**      | **Currency      | **JPY**         |
|                 |                 | code:** JPY     |                 |
+-----------------+-----------------+-----------------+-----------------+
| txamt           | **Int**         | **Amount of     | **10000**       |
|                 |                 | payment: **     |                 |
|                 |                 |                 |                 |
|                 |                 | It is measured  |                 |
|                 |                 | in the minimum  |                 |
|                 |                 | price unit of   |                 |
|                 |                 | the transaction |                 |
|                 |                 | currency.       |                 |
+-----------------+-----------------+-----------------+-----------------+
| respmsg         | **String**      | **Debug         | **success**     |
|                 |                 | information**   |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[out\_trade\_n | **String**      | **Merchant\'s   | **1470020842103 |
| o]{.underline}] |                 | order number**  | **              |
| (#out_trade_no) | No longer than  |                 |                 |
|                 | 128 byte        |                 |                 |
+-----------------+-----------------+-----------------+-----------------+
| syssn           | **String**      | **Order         | **2016072809010 |
|                 |                 | number** in     | 20011216135**   |
|                 |                 | QFPAY system    |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[respcd]{.unde | **String**      | **Response      | **0000**        |
| rline}](#respon |                 | code:**         |                 |
| se-code-descrip |                 |                 |                 |
| tion)           |                 | 0000 stands for |                 |
|                 |                 | order placed    |                 |
|                 |                 | successfully    |                 |
|                 |                 |                 |                 |
|                 |                 | 1143,1145 stand |                 |
|                 |                 | for transaction |                 |
|                 |                 | in processing,  |                 |
|                 |                 | merchant need   |                 |
|                 |                 | to continue     |                 |
|                 |                 | querying        |                 |
|                 |                 | transaction. if |                 |
|                 |                 | other codes     |                 |
|                 |                 | returned,       |                 |
|                 |                 | transaction     |                 |
|                 |                 | failed          |                 |
+-----------------+-----------------+-----------------+-----------------+
| mchid           | **String**      | **Merchant      | **BvDtmKJA5mx7G |
|                 |                 | id:**           | pN0**           |
|                 |                 |                 |                 |
|                 |                 | allocate by     |                 |
|                 |                 | QFPAY(You can   |                 |
|                 |                 | look up         |                 |
|                 |                 | merchant ID in  |                 |
|                 |                 | channel system) |                 |
+-----------------+-----------------+-----------------+-----------------+

### 2.1.7 Response Example

+--------------------------------------------------+
| {\"pay\_type\": \"800208\",                      |
|                                                  |
| \"sysdtm\": \"2018-01-12 17:10:26\",             |
|                                                  |
| \"**cardcd**\":\"oo3Lss-DzPSygtHtAbfuXeQFCz18\", |
|                                                  |
| \"txdtm\": \"2018-01-12 17:10:32\",              |
|                                                  |
| \"resperr\": \"\\u4ea4\\u6613\\u6210\\u529f\",   |
|                                                  |
| \"txcurrcd\": \"CNY\",                           |
|                                                  |
| \"txamt\": \"1\",                                |
|                                                  |
| \"respmsg\": \"\",                               |
|                                                  |
| \"out\_trade\_no\":\"1301459478787530052\",      |
|                                                  |
| \"syssn\":\"20180112000100020001659358\",        |
|                                                  |
| \"respcd\": \"0000\"}                            |
+--------------------------------------------------+

2.2 Merchant QR Code Payment
----------------------------

### 2.2.1 Interface Description

Merchant enter the price of product and call QFPay's interface to
generate a dynamic QR code, consumer scan the QR code by using Wechat or
Alipay app to finish the payment. The dynamic QR code will expire in 30
minutes.

### 2.2.2 Request Information

  ----------------- --------------------------------------------
  **Method**        HTTP Post
  **Path**          /trade/v1/payment
  **Description**   Consumers scan the QR code from merchants.
  ----------------- --------------------------------------------

### 2.2.3 Request Parameters

+-------------+-------------+-------------+-------------+-------------+
| **Parameter | **Required* | **Definitio | **Descripti | **Example** |
| **          | *           | n**         | on**        |             |
+-------------+-------------+-------------+-------------+-------------+
| txamt       | Y           | **Int**     | **Amount of | **10000**   |
|             |             |             | payment: ** |             |
|             |             |             |             |             |
|             |             |             | It is       |             |
|             |             |             | measured in |             |
|             |             |             | the minimum |             |
|             |             |             | price unit  |             |
|             |             |             | of the      |             |
|             |             |             | transaction |             |
|             |             |             | currency.   |             |
|             |             |             |             |             |
|             |             |             | (Max 10,000 |             |
|             |             |             | USD)        |             |
+-------------+-------------+-------------+-------------+-------------+
| txcurrcd    | Y           | **String**  | **Currency  | **JPY**     |
|             |             |             | code:** JPY |             |
|             |             |             |             | **CNY**     |
+-------------+-------------+-------------+-------------+-------------+
| pay\_type   | Y           | **String**  | **Payment   | **800201**  |
|             |             |             | type:**     |             |
|             |             |             |             |             |
|             |             |             | wechat:     |             |
|             |             |             | 800201；alip |            |
|             |             |             | ay:         |             |
|             |             |             | 800101；    |             |
+-------------+-------------+-------------+-------------+-------------+
| [[out\_trad | Y           | **String**  | **Merchant\ | **147002084 |
| e\_no]{.und |             |             | 's          | 2103**      |
| erline}](#o |             | No longer   | order       |             |
| ut_trade_no |             | than 128    | number**    |             |
| )           |             | byte        |             |             |
|             |             |             | The         |             |
|             |             |             | merchant's  |             |
|             |             |             | order       |             |
|             |             |             | number has  |             |
|             |             |             | to be       |             |
|             |             |             | unique      |             |
+-------------+-------------+-------------+-------------+-------------+
| txdtm       | Y           | **String**  | **Transacti | **2016-08-0 |
|             |             |             | on          | 1           |
|             |             |             | time:**     | 11:07:22**  |
|             |             |             |             |             |
|             |             |             | format:     |             |
|             |             |             | YYYY-MM-DD  |             |
|             |             |             | HH:MM:SS    |             |
+-------------+-------------+-------------+-------------+-------------+
| [[goods\_na | Y           | **String**  | **Name of   | **Cosmetics |
| me]{.underl |             |             | the         | **          |
| ine}](#good |             |             | product**   |             |
| s_name)     |             |             |             |             |
+-------------+-------------+-------------+-------------+-------------+
| mchid       | Y           | **String**  | **Merchant  | **BvDtmKJA5 |
|             |             |             | id:**       | mx7GpN0**   |
|             |             |             |             |             |
|             |             |             | allocate by |             |
|             |             |             | QFPAY(You   |             |
|             |             |             | can look up |             |
|             |             |             | merchant ID |             |
|             |             |             | in channel  |             |
|             |             |             | system)     |             |
+-------------+-------------+-------------+-------------+-------------+
| limit\_pay  | N           |             | The value   |             |
|             |             |             | of this     |             |
|             |             |             | parameter   |             |
|             |             |             | can only be |             |
|             |             |             | set to      |             |
|             |             |             | ***no\_cred |             |
|             |             |             | it***,      |             |
|             |             |             | which means |             |
|             |             |             | credit is   |             |
|             |             |             | not allowed |             |
+-------------+-------------+-------------+-------------+-------------+
| udid        | N           |             | UUID of     |             |
|             |             |             | device, no  |             |
|             |             |             | longer than |             |
|             |             |             | 40          |             |
|             |             |             | characters  |             |
+-------------+-------------+-------------+-------------+-------------+

### 2.2.4 Request Example

+-----------------------------------------------------------------------+
| {\'pay\_type\': \'800208\',                                           |
|                                                                       |
| \'out\_trade\_no\': 725526999001L,                                    |
|                                                                       |
| \'goods\_name\':                                                      |
| \'\\xe9\\x92\\xb1\\xe6\\x96\\xb9\\xe6\\xb5\\x8b\\xe8\\xaf\\x95\',     |
|                                                                       |
| \'limit\_pay\': \'no\_credit\',                                       |
|                                                                       |
| \'udid\': \'1880105\',                                                |
|                                                                       |
| \'txcurrcd\': \'JPY\',                                                |
|                                                                       |
| **\'auth\_code\': \'134676275354204254\', **                          |
|                                                                       |
| \'txdtm\': \'2018-07-20 04:27:18\',                                   |
|                                                                       |
| \'mchid\': \'8w5pdhDJkm\',                                            |
|                                                                       |
| \'txamt\': 1}                                                         |
+-----------------------------------------------------------------------+

### 2.2.5 Response Parameters

+-----------------+-----------------+-----------------+-----------------+
| **Parameter**   | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| pay\_type       | **String**      | **Payment       | **800201**      |
|                 |                 | type**:         |                 |
|                 |                 |                 |                 |
|                 |                 | wechat:         |                 |
|                 |                 | 800201；alipay: |                 |
|                 |                 | 800101；        |                 |
+-----------------+-----------------+-----------------+-----------------+
| sysdtm          | **String**      | **System time** | **2016-07-28    |
|                 |                 |                 | 14:13:10**      |
+-----------------+-----------------+-----------------+-----------------+
| txdtm           | **String**      | **Transaction   | **2016-08-01    |
|                 |                 | time :**        | 11:07:22**      |
|                 |                 |                 |                 |
|                 |                 | format:         |                 |
|                 |                 | YYYY-MM-DD      |                 |
|                 |                 | HH:MM:SS        |                 |
+-----------------+-----------------+-----------------+-----------------+
| resperr         | **String**      | **Information   |                 |
|                 |                 | Description**   |                 |
+-----------------+-----------------+-----------------+-----------------+
| txamt           | **String**      | **Amount of     | **10000**       |
|                 |                 | payment: **     |                 |
|                 |                 |                 |                 |
|                 |                 | It is measured  |                 |
|                 |                 | in the minimum  |                 |
|                 |                 | price unit of   |                 |
|                 |                 | the transaction |                 |
|                 |                 | currency.       |                 |
+-----------------+-----------------+-----------------+-----------------+
| respmsg         | **String**      | **Debug         |                 |
|                 |                 | information**   |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[out\_trade\_n | **String**      | **Merchant\'s   | **1470020842103 |
| o]{.underline}] |                 | order number**  | **              |
| (#out_trade_no) | No longer than  |                 |                 |
|                 | 128 byte        |                 |                 |
+-----------------+-----------------+-----------------+-----------------+
| syssn           | **String**      | **Order         | **2016072809010 |
|                 |                 | number** in     | 20011216135**   |
|                 |                 | QFPAY system    |                 |
+-----------------+-----------------+-----------------+-----------------+
| qrcode          | **String**      | **QR code       | **weixin://wxpa |
|                 |                 | url:**          | y/bizpayurl?pr= |
|                 |                 |                 | xxxxxxx**       |
|                 |                 | merchant need   |                 |
|                 |                 | to convert it   |                 |
|                 |                 | to QR code      |                 |
|                 |                 | image           |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[respcd]{.unde | **String**      | **Response      | **1143**        |
| rline}](#respon |                 | code:**         |                 |
| se-code-descrip |                 |                 |                 |
| tion)           |                 | 0000 stands for |                 |
|                 |                 | order placed    |                 |
|                 |                 | successfully    |                 |
|                 |                 |                 |                 |
|                 |                 | 1143,1145 stand |                 |
|                 |                 | for transaction |                 |
|                 |                 | on going,       |                 |
|                 |                 | merchant need   |                 |
|                 |                 | to continue     |                 |
|                 |                 | querying        |                 |
|                 |                 | transaction. if |                 |
|                 |                 | other codes     |                 |
|                 |                 | returned,       |                 |
|                 |                 | transaction     |                 |
|                 |                 | failed          |                 |
+-----------------+-----------------+-----------------+-----------------+

### 2.2.6 Response Example

+------------------------------------------------------+
| {\"pay\_type\": \"800201\",                          |
|                                                      |
| \"sysdtm\": \"2018-01-12 17:38:50\",                 |
|                                                      |
| \"cardcd\": \"\",                                    |
|                                                      |
| \"txdtm\": \"2018-01-12 17:38:56\",                  |
|                                                      |
| \"resperr\": \"\\u4ea4\\u6613\\u6210\\u529f\",       |
|                                                      |
| \"txcurrcd\": \"JPY\",                               |
|                                                      |
| \"txamt\": \"1\",                                    |
|                                                      |
| \"respmsg\": \"\",                                   |
|                                                      |
| \"out\_trade\_no\": \"13014597457448787530052\",     |
|                                                      |
| \"syssn\": \"20180112000100020001659801\",           |
|                                                      |
| \"qrcode\": \"weixin://wxpay/bizpayurl?pr=hBvNxhc\", |
|                                                      |
| \"respcd\": \"0000\"}                                |
+------------------------------------------------------+

### 2.2.7 Notification callback

Merchants receive the notification callback when the payment is
successfully made

Note: The notification callback may be delayed due to external factors.
Therefore, please use query interface for the scene with real-time
processing requirements. For security reasons, notification callback
only supports ports 80 and 443, and custom port assignments are not
supported. For more detail, please see Developer Guide 3.5.

2.3 Refund interface
--------------------

### 2.3.1 Interface Description

The merchant and partner can use this interface to refund an existing
payment. A refund can be partial and a transaction can have multiple
refunds as long as the total refund amount is no greater than the
original transaction amount.

### 2.3.2 Request Information

  ----------------- ------------------------------------------------------------
  **Method**        HTTP Post
  **Path**          /trade/v1/refund
  **Description**   The Payer will be refunded with the request refund amount.
  ----------------- ------------------------------------------------------------

### 2.3.3 Request Parameters

+-------------+-------------+-------------+-------------+-------------+
| **Parameter | **Required* | **Definitio | **Descripti | **Example** |
| **          | *           | n**         | on**        |             |
+-------------+-------------+-------------+-------------+-------------+
| txamt       | Y           | **Int**     | **Amount of | **10000**   |
|             |             |             | payment: ** |             |
|             |             |             |             |             |
|             |             |             | It is       |             |
|             |             |             | measured in |             |
|             |             |             | the minimum |             |
|             |             |             | price unit  |             |
|             |             |             | of the      |             |
|             |             |             | transaction |             |
|             |             |             | currency.   |             |
+-------------+-------------+-------------+-------------+-------------+
| syssn       | Y           | **String**  | **Order     | **201607280 |
|             |             |             | number** in | 90102001121 |
|             |             |             | QFPAY       | 6135**      |
|             |             |             | system.     |             |
|             |             |             |             |             |
|             |             |             | It supports |             |
|             |             |             | batch       |             |
|             |             |             | query, use  |             |
|             |             |             | \",\" to    |             |
|             |             |             | delimit     |             |
|             |             |             | multiple    |             |
|             |             |             | syssn       |             |
|             |             |             | numbers     |             |
+-------------+-------------+-------------+-------------+-------------+
| [[out\_trad | Y           | **String**  | **Merchant\ | **147002084 |
| e\_no]{.und |             |             | 's          | 2103**      |
| erline}](#o |             | No longer   | order       |             |
| ut_trade_no |             | than 128    | number**    |             |
| )           |             | byte        |             |             |
+-------------+-------------+-------------+-------------+-------------+
| txdtm       | Y           | **String**  | **Transacti | **2016-08-0 |
|             |             |             | on          | 1           |
|             |             |             | time:**     | 11:07:22**  |
|             |             |             |             |             |
|             |             |             | format:     |             |
|             |             |             | YYYY-MM-DD  |             |
|             |             |             | HH:MM:SS    |             |
+-------------+-------------+-------------+-------------+-------------+
| mchid       | Y           | **String**  | **Merchant  | **BvDtmKJA5 |
|             |             |             | id:**       | mx7GpN0**   |
|             |             |             |             |             |
|             |             |             | allocate by |             |
|             |             |             | QFPAY(You   |             |
|             |             |             | can look up |             |
|             |             |             | merchant ID |             |
|             |             |             | in channel  |             |
|             |             |             | system)     |             |
+-------------+-------------+-------------+-------------+-------------+
| udid        | N           |             | UUID of     |             |
|             |             |             | device, no  |             |
|             |             |             | longer than |             |
|             |             |             | 40          |             |
|             |             |             | characters  |             |
+-------------+-------------+-------------+-------------+-------------+

### 2.3.4 Request Example

+--------------------------------------------+
| {\'txdtm\': \'2018-07-20 05:10:04\',       |
|                                            |
| \'mchid\': \'8w5pdhDJkm\',                 |
|                                            |
| \'txamt\': 1,                              |
|                                            |
| \'out\_trade\_no\': 428870566675L,         |
|                                            |
| \'syssn\': \'20180112000100020001661542\'} |
|                                            |
| }                                          |
+--------------------------------------------+

### 2.3.5 Response Parameters

+-----------------+-----------------+-----------------+-----------------+
| **Parameter**   | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| syssn           | **String**      | **Refund order  | **2016072809010 |
|                 |                 | number** in     | 20011216135**   |
|                 |                 | QFPAY system    |                 |
+-----------------+-----------------+-----------------+-----------------+
| orig\_syssn     | **String**      | **Original      | **2018011200010 |
|                 |                 | order number in | 0020001659358** |
|                 |                 | QFPAY system**  |                 |
+-----------------+-----------------+-----------------+-----------------+
| sysdtm          | **String**      | **System time** | **2016-07-28    |
|                 |                 |                 | 14:13:10**      |
+-----------------+-----------------+-----------------+-----------------+
| txdtm           | **String**      | **Transaction   | **2016-08-01    |
|                 |                 | time :**        | 11:07:22**      |
|                 |                 |                 |                 |
|                 |                 | format:         |                 |
|                 |                 | YYYY-MM-DD      |                 |
|                 |                 | HH:MM:SS        |                 |
+-----------------+-----------------+-----------------+-----------------+
| txamt           | **String**      | **Amount of     | **10000**       |
|                 |                 | payment: **     |                 |
|                 |                 |                 |                 |
|                 |                 | It is measured  |                 |
|                 |                 | in the minimum  |                 |
|                 |                 | price unit of   |                 |
|                 |                 | the transaction |                 |
|                 |                 | currency.       |                 |
+-----------------+-----------------+-----------------+-----------------+

### 2.3.6 Response Example

+-----------------------------------------------------+
| {\"orig\_syssn\": \"20180112000100020001659358\",   |
|                                                     |
| \"sysdtm\": \"2018-01-12 19:03:23\",                |
|                                                     |
| \"cardcd\": \"\",                                   |
|                                                     |
| \"txdtm\": \"2018-01-12 19:03:29\",                 |
|                                                     |
| \"resperr\": \"\\u4ea4\\u6613\\u6210\\u529f\",      |
|                                                     |
| \"txcurrcd\": \"JPY\",                              |
|                                                     |
| \"txamt\": \"1\",                                   |
|                                                     |
| \"respmsg\": \"\",                                  |
|                                                     |
| \"out\_trade\_no\": \"13014597435743348787530052\", |
|                                                     |
| \"syssn\": \"20180112000100020001661542\",          |
|                                                     |
| \"respcd\": \"0000\"}                               |
+-----------------------------------------------------+

2.4 Query interface 
--------------------

### 2.4.1 Interface Description

When the transaction is in processed, call the query interface to
acquire the status of transaction. Using the start-time and end-time to
check the status of transactions in multiple months. Or, using QFPay
order number(syssn) to check the status of specific transaction.

### 2.4.2 Request Information

  ----------------- ---------------------
  **Method**        HTTP Post
  **Path**          /trade/v1/query
  **Description**   Query a transaction
  ----------------- ---------------------

### 2.4.3 Request Parameters

+-------------+-------------+-------------+-------------+-------------+
| **Parameter | **Required* | **Definitio | **Descripti | **Example** |
| **          | *           | n**         | on**        |             |
+-------------+-------------+-------------+-------------+-------------+
| mchid       | Y           | **String**  | **Merchant  | **BvDtmKJA5 |
|             |             |             | id:**       | mx7GpN0**   |
|             |             |             |             |             |
|             |             |             | allocate by |             |
|             |             |             | QFPAY(You   |             |
|             |             |             | can look up |             |
|             |             |             | merchant ID |             |
|             |             |             | in channel  |             |
|             |             |             | system)     |             |
+-------------+-------------+-------------+-------------+-------------+
| syssn       | N           | **String**  | **Order     | **201607280 |
|             |             |             | number** in | 90102001121 |
|             |             |             | QFPAY       | 6135**      |
|             |             |             | system.     |             |
|             |             |             |             |             |
|             |             |             | It supports |             |
|             |             |             | batch       |             |
|             |             |             | query, use  |             |
|             |             |             | \",\" to    |             |
|             |             |             | delimit     |             |
|             |             |             | multiple    |             |
|             |             |             | syssn       |             |
|             |             |             | numbers     |             |
+-------------+-------------+-------------+-------------+-------------+
| [[out\_trad | N           | **String**  | **Merchant\ | **147002084 |
| e\_no]{.und |             |             | 's          | 2103**      |
| erline}](#o |             | No longer   | order       |             |
| ut_trade_no |             | than 128    | number**    |             |
| )           |             | byte        |             |             |
+-------------+-------------+-------------+-------------+-------------+
| pay\_type   | N           | **String**  | **Payment   | **800201**  |
|             |             |             | type**:     |             |
|             |             |             |             |             |
|             |             |             | wechat:     |             |
|             |             |             | 800201；alip |            |
|             |             |             | ay:         |             |
|             |             |             | 800101；    |             |
+-------------+-------------+-------------+-------------+-------------+
| [[respcd]{. | N           | **String**  | **Response  | **0000**    |
| underline}] |             |             | code:**     |             |
| (#response- |             |             |             |             |
| code-descri |             |             | 0000 stands |             |
| ption)      |             |             | for order   |             |
|             |             |             | placed      |             |
|             |             |             | successfull |             |
|             |             |             | y           |             |
|             |             |             |             |             |
|             |             |             | 1143,1145   |             |
|             |             |             | stand for   |             |
|             |             |             | transaction |             |
|             |             |             | on going,   |             |
|             |             |             | merchant    |             |
|             |             |             | need to     |             |
|             |             |             | continue    |             |
|             |             |             | querying    |             |
|             |             |             | transaction |             |
|             |             |             | .           |             |
|             |             |             | if other    |             |
|             |             |             | codes       |             |
|             |             |             | returned,   |             |
|             |             |             | transaction |             |
|             |             |             | failed      |             |
+-------------+-------------+-------------+-------------+-------------+
| start\_time | N           | **String**  | **Start     | **2016-08-0 |
|             |             |             | time:**     | 1           |
|             |             |             |             | 11:00:00**  |
|             |             |             | Default :   |             |
|             |             |             | starting at |             |
|             |             |             | this month  |             |
|             |             |             |             |             |
|             |             |             | format：YYYY |            |
|             |             |             | -MM-DD      |             |
|             |             |             | HH:MM:SS    |             |
|             |             |             |             |             |
|             |             |             | (in Local   |             |
|             |             |             | Time)       |             |
+-------------+-------------+-------------+-------------+-------------+
| end\_time   | N           | **String**  | **End       | **2016-08-2 |
|             |             |             | time:**     | 1           |
|             |             |             |             | 11:00:00**  |
|             |             |             | Default :   |             |
|             |             |             | end at this |             |
|             |             |             | month       |             |
|             |             |             |             |             |
|             |             |             | format：YYYY |            |
|             |             |             | -MM-DD      |             |
|             |             |             | HH:MM:SS    |             |
|             |             |             |             |             |
|             |             |             | (in Local   |             |
|             |             |             | Time)       |             |
+-------------+-------------+-------------+-------------+-------------+
| page        | N           | **Int**     | **Number of | **1**       |
|             |             |             | pages:**    |             |
|             |             |             |             |             |
|             |             |             | default:1   |             |
+-------------+-------------+-------------+-------------+-------------+
| page\_size  | N           | **Int**     | **Number of | **10**      |
|             |             |             | orders      |             |
|             |             |             | displayed   |             |
|             |             |             | on each     |             |
|             |             |             | page**      |             |
|             |             |             |             |             |
|             |             |             | Default:10， |            |
|             |             |             |             |             |
|             |             |             | max         |             |
|             |             |             | pagesize is |             |
|             |             |             | 100         |             |
+-------------+-------------+-------------+-------------+-------------+

### 2.4.4 Request Example

+-----------------------------+
| {\'pay\_type\': \'800201\', |
|                             |
| \'page\_size\': 10,         |
|                             |
| \'mchid\': \'9GGGDCRjYa\',  |
|                             |
| \'Page\': 2}                |
+-----------------------------+

### 2.4.5 Response Parameters

+-----------------+-----------------+-----------------+-----------------+
| **Parameters**  | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| page            | **Int**         | **Number of     | **1**           |
|                 |                 | pages**         |                 |
+-----------------+-----------------+-----------------+-----------------+
| resperr         | **String**      | **Information   |                 |
|                 |                 | Description**   |                 |
+-----------------+-----------------+-----------------+-----------------+
| page\_size      | **Int**         | **Number of     | **10**          |
|                 |                 | orders          |                 |
|                 |                 | displayed on    |                 |
|                 |                 | each page**     |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[respcd]{.unde | **String**      | **Response      | **0000**        |
| rline}](#respon |                 | code:**         |                 |
| se-code-descrip |                 |                 |                 |
| tion)           |                 | 0000 stands for |                 |
|                 |                 | order placed    |                 |
|                 |                 | successfully    |                 |
|                 |                 |                 |                 |
|                 |                 | 1143,1145 stand |                 |
|                 |                 | for transaction |                 |
|                 |                 | on going,       |                 |
|                 |                 | merchant need   |                 |
|                 |                 | to continue     |                 |
|                 |                 | querying        |                 |
|                 |                 | transaction. if |                 |
|                 |                 | other codes     |                 |
|                 |                 | returned,       |                 |
|                 |                 | transaction     |                 |
|                 |                 | failed          |                 |
+-----------------+-----------------+-----------------+-----------------+
| data            | **List**        | **Trade data**  |                 |
|                 |                 |                 |                 |
|                 |                 | type:list       |                 |
+-----------------+-----------------+-----------------+-----------------+

**Description of parameter \<data\>：**

+-----------------+-----------------+-----------------+-----------------+
| **Parameters**  | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| syssn           | **String**      | **Order         | **2016072809010 |
|                 |                 | number** in     | 20011216135**   |
|                 |                 | QFPAY system    |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[out\_trade\_n | **String**      | **Merchant\'s   | **1470020842103 |
| o]{.underline}] |                 | order number**  | **              |
| (#out_trade_no) | No longer than  |                 |                 |
|                 | 128 byte        |                 |                 |
+-----------------+-----------------+-----------------+-----------------+
| pay\_type       | **String**      | **Payment       | **800201**      |
|                 |                 | type**:         |                 |
|                 |                 |                 |                 |
|                 |                 | wechat:         |                 |
|                 |                 | 800201；alipay: |                 |
|                 |                 | 800101；        |                 |
+-----------------+-----------------+-----------------+-----------------+
| order\_type     | **String**      | **Order type:** | **payment**     |
|                 |                 |                 |                 |
|                 |                 | Payed           |                 |
|                 |                 | order：payment； |                |
|                 |                 |                 |                 |
|                 |                 |                 |                 |
|                 |                 | Refunded        |                 |
|                 |                 | order：refund； |                 |
|                 |                 |                 |                 |
|                 |                 | Closed          |                 |
|                 |                 | order：close；  |                 |
+-----------------+-----------------+-----------------+-----------------+
| txdtm           | **String**      | **Transaction   | **2016-08-01    |
|                 |                 | time:**         | 11:07:22**      |
|                 |                 |                 |                 |
|                 |                 | format：YYYY-MM- |                |
|                 |                 | DD              |                 |
|                 |                 | HH:MM:SS        |                 |
+-----------------+-----------------+-----------------+-----------------+
| txamt           | **Int**         | **Amount of     | **10000**       |
|                 |                 | payment: **     |                 |
|                 |                 |                 |                 |
|                 |                 | It is measured  |                 |
|                 |                 | in the minimum  |                 |
|                 |                 | price unit of   |                 |
|                 |                 | the transaction |                 |
|                 |                 | currency.       |                 |
+-----------------+-----------------+-----------------+-----------------+
| sysdtm          | **String**      | **System time** | **2016-07-28    |
|                 |                 |                 | 11:01:39**      |
+-----------------+-----------------+-----------------+-----------------+
| cancel          | **Int**         | **Cancel/refund | **2**           |
|                 |                 | :**             |                 |
|                 |                 |                 |                 |
|                 |                 | Normal trade:0  |                 |
|                 |                 |                 |                 |
|                 |                 | Cancel:2        |                 |
|                 |                 |                 |                 |
|                 |                 | refund:3        |                 |
+-----------------+-----------------+-----------------+-----------------+
| [[respcd]{.unde | **String**      | **Response      |                 |
| rline}](#respon |                 | code:**         |                 |
| se-code-descrip |                 |                 |                 |
| tion)           |                 | 0000 stands for |                 |
|                 |                 | order placed    |                 |
|                 |                 | successfully    |                 |
|                 |                 |                 |                 |
|                 |                 | 1143,1145 stand |                 |
|                 |                 | for transaction |                 |
|                 |                 | on going,       |                 |
|                 |                 | merchant need   |                 |
|                 |                 | to continue     |                 |
|                 |                 | querying        |                 |
|                 |                 | transaction. if |                 |
|                 |                 | other codes     |                 |
|                 |                 | returned,       |                 |
|                 |                 | transaction     |                 |
|                 |                 | failed          |                 |
+-----------------+-----------------+-----------------+-----------------+
| errmsg          | **String**      | **Message of    |                 |
|                 |                 | payment         |                 |
|                 |                 | result**        |                 |
+-----------------+-----------------+-----------------+-----------------+

The parameter respcd=0000 inside the data\_list stands for the
transaction is complete.

The respcd=0000 outside of the data\_list stands for the order has been
placed.

### 2.4.6 Response Example

+-----------------------------------------------------------------------+
| {\'resperr\': u\'\\u8bf7\\u6c42\\u6210\\u529f\',                      |
|                                                                       |
| \'page\_size\': 1,                                                    |
|                                                                       |
| \'respmsg\': \'\',                                                    |
|                                                                       |
| \'respcd\': \'0000\',                                                 |
|                                                                       |
| \'data\': \[{\'pay\_type\': \'800201\', \'sysdtm\': \'2018-07-24      |
| 12:30:57\', \'paydtm\': \'\', \'txcurrcd\': \'CNY\', \'txdtm\':       |
| \'2018-07-24 13:31:03\', \'txamt\': \'1\', \'chnlsn\': \'\',          |
| \'out\_trade\_no\': \'923500563158\', \'syssn\':                      |
| \'20180724000200020076586895\', \'cancel\': \'0\', \'respcd\':        |
| \'1143\', \'errmsg\': \'\\u8ba2                                       |
|                                                                       |
| \\u5355\\u8fd8\\u672a\\u652f\\u4ed8\\uff0c\\u6216\\u8005\\u6b63\\u572 |
| 8\\u8f93\\u5165\\u5bc6\\u                                             |
|                                                                       |
| 7801\\u4e2d\', \'order\_type\': \'payment\'}\], \'page\': 1}          |
+-----------------------------------------------------------------------+

2.5 Cancel interface 
---------------------

### 2.5.1 Interface Description

1\. What's kind of order can be closed?

If the payment is unsuccessful, the order can always be canceled. If the
cancel interface is called while the payment is successful made, which
actually stands for calling the WeChat revoke API, and the payment for
this order will be returned.

2\. Scenarios of canceling the transaction

Cancel interface is only applicable at the time,which is mainly used to
avoid double payments.\
In order to avoid causing various unknown problems, please do not use
the cancel interface as a refund interface.

### 2.5.2 Request Information

  ----------------- ---------------------
  **Method**        HTTP Post
  **Path**          /trade/v1/close
  **Description**   Close a transaction
  ----------------- ---------------------

### 2.5.3 Request Parameters

+-------------+-------------+-------------+-------------+-------------+
| **Parameter | **Required* | **Definitio | **Descripti | **Example** |
| **          | *           | n**         | on**        |             |
+-------------+-------------+-------------+-------------+-------------+
| mchid       | Y           | **String**  | **Merchant  | **BvDtmKJA5 |
|             |             |             | id:**       | mx7GpN0**   |
|             |             |             |             |             |
|             |             |             | allocate by |             |
|             |             |             | QFPAY(You   |             |
|             |             |             | can look up |             |
|             |             |             | merchant ID |             |
|             |             |             | in channel  |             |
|             |             |             | system)     |             |
+-------------+-------------+-------------+-------------+-------------+
| txamt       | Y           | **Int**     | **Amount of | **10000**   |
|             |             |             | payment: ** |             |
|             |             |             |             |             |
|             |             |             | It is       |             |
|             |             |             | measured in |             |
|             |             |             | the minimum |             |
|             |             |             | price unit  |             |
|             |             |             | of the      |             |
|             |             |             | transaction |             |
|             |             |             | currency.   |             |
+-------------+-------------+-------------+-------------+-------------+
| txdtm       | Y           | **String**  | **Transacti | **2016-08-0 |
|             |             |             | on          | 1           |
|             |             |             | time :**    | 11:07:22**  |
|             |             |             |             |             |
|             |             |             | format:     |             |
|             |             |             | YYYY-MM-DD  |             |
|             |             |             | HH:MM:SS    |             |
+-------------+-------------+-------------+-------------+-------------+
| syssn       | N           | **String**  | **Order     | **201607280 |
|             |             |             | number** in | 90102001121 |
|             |             |             | QFPAY       | 6137**      |
|             |             |             | system.     |             |
+-------------+-------------+-------------+-------------+-------------+
| [[out\_trad | N           | **String**  | **Merchant\ | **147002084 |
| e\_no]{.und |             |             | 's          | 2103**      |
| erline}](#o |             | No longer   | order       |             |
| ut_trade_no |             | than 128    | number**    |             |
| )           |             | byte        |             |             |
+-------------+-------------+-------------+-------------+-------------+
| udid        | N           | **String**  | UUID of     |             |
|             |             |             | device, no  |             |
|             |             |             | longer than |             |
|             |             |             | 40          |             |
|             |             |             | characters  |             |
+-------------+-------------+-------------+-------------+-------------+

### 2.5.4 Request Example

+--------------------------------------------+
| {\'txdtm\': \'2018-07-24 15:20:09\',       |
|                                            |
| \'syssn\': \'20180724000200020076667504\', |
|                                            |
| \'mchid\': \'9GGGDCRjYa\',                 |
|                                            |
| \'txamt\': 1}                              |
+--------------------------------------------+

### 2.5.5 Response Parameters

+-----------------+-----------------+-----------------+-----------------+
| **Parameters**  | **Definition**  | **Description** | **Example**     |
+-----------------+-----------------+-----------------+-----------------+
| syssn           | **String**      | **Order number  | **2016072809010 |
|                 |                 | of the          | 20011216137**   |
|                 |                 | cancellation ** |                 |
+-----------------+-----------------+-----------------+-----------------+
| orig\_syssn     | **String**      | **Original      | **2016072809010 |
|                 |                 | Order number**  | 20011216135**   |
|                 |                 | of this         |                 |
|                 |                 | transaction in  |                 |
|                 |                 | QFPAY system    |                 |
+-----------------+-----------------+-----------------+-----------------+
| txamt           | **Int**         | **Amount of     | **10000**       |
|                 |                 | payment: **     |                 |
|                 |                 |                 |                 |
|                 |                 | It is measured  |                 |
|                 |                 | in the minimum  |                 |
|                 |                 | price unit of   |                 |
|                 |                 | the transaction |                 |
|                 |                 | currency.       |                 |
+-----------------+-----------------+-----------------+-----------------+
| txdtm           | **String**      | **Request       | **2016-07-26    |
|                 |                 | Transaction     | 17:02:01**      |
|                 |                 | time :**        |                 |
|                 |                 |                 |                 |
|                 |                 | format:         |                 |
|                 |                 | YYYY-MM-DD      |                 |
|                 |                 | HH:MM:SS        |                 |
+-----------------+-----------------+-----------------+-----------------+
| sysdtm          | **String**      | **System        | **2016-07-20    |
|                 |                 | Transaction     | 14:47:50**      |
|                 |                 | time**          |                 |
+-----------------+-----------------+-----------------+-----------------+

### 2.5.6 Response Example

+---------------------------------------------------+
| {\"orig\_syssn\": \"20180112000100020001659801\", |
|                                                   |
| \"sysdtm\": \"2018-01-12 19:07:02\",              |
|                                                   |
| \"cardcd\": \"\",                                 |
|                                                   |
| \"txdtm\": \"2018-01-12 19:07:09\",               |
|                                                   |
| \"resperr\": \"\\u4ea4\\u6613\\u6210\\u529f\",    |
|                                                   |
| \"txcurrcd\": \"CNY\",                            |
|                                                   |
| \"txamt\": \"1\",                                 |
|                                                   |
| \"respmsg\": \"\",                                |
|                                                   |
| \"syssn\": \"20180112000100020001661611\",        |
|                                                   |
| \"respcd\": \"0000\"}                             |
+---------------------------------------------------+

Note: Cancel transaction is non-generic supported, there may be cases
that do not support canceling the transaction. If the cancel interface
response return code is \"1297\" or the returned content has no syssn
field (note: not orig\_syssn), the transaction does not support
canceling the order. If the user Successfully paid, the refund interface
can be called for a refund.

2.6 Parameter Description
-------------------------

### 2.6.1 out\_trade\_no

The merchant's order number is custom generated by the merchant. It only
supports the combination of alphanumeric characters, such as
alphanumeric, underline \_, vertical bar\|, and asterisk \*. Do not use
special characters such as Chinese characters or full-width characters.
The merchant order number is required to be unique (it is recommended to
be generated based on the current system time plus a random sequence).
To re-initiate a payment, use the original order number to avoid double
payment; the order number that has been paid or has been called, and the
cancelled order number cannot be reused for a new payment.

**Notice: If not using, please not input this parameter to request
parameters as the form of none. **

### 2.6.2 goods\_name

no longer than 20 characters, special characters are not allowed. Note:
special characters include full-width characters and symbols (★☆★\$ & ¤
§ \| °゜ ¨ ± · × ÷ ˇ ˉ ˊ ˋ ˙ Γ Δ Θ Ξ Π Σ Υ Φ Ψ Ω α β γ δ ε ζ η θ ι κ λ μ
ν ξ π ρ σ τ υ φ ψ ω Ё Б Г Д Е Ж З ИЙ К Л Ф У Ц Ч Ш Щ Ъ Ы Э Ю Я а б в г д
ж з и й к л ф ц ч ш щ ъ ы ю я
ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ - ― ‖ ‥ ... ‰ ′ ″ ※
℃ ℅ ℉ № ℡)

3. Developer Guide
==================

3.1 Environment Description
---------------------------

QFPay provides two environment for using our services：

### 3.1.1 Test Environment：

**Request Url: https://openapi-test.qfpay.com**

1.  During test, the money will not be operated. Please use little
    amount.

2.  Mchid can be found in the channel system, which will be provided
    when formally applying the app-code and key.

3.  Merchants under the same channel system can use the same app-code
    and key.

### 3.1.2 Formal Environment：

**Request Url: https://openapi.qfpay.com**

Please use your formal information including the mchid, code and key.

3.2 Signature Algorithm
-----------------------

When there is no special statement, the signature in all request
interfaces uses the format below:

**How to make the interface parameter signature：**

**Step 1：** Ascending order all parameters by parameter name。

Parameter list ：abc=value1 bcd=value2 bad=value3

The ascending result：abc=value1 bad=value3 bcd=value2

**Step 2：**Connect all parameters with & to get the string to be
signed.

abc=value1&bad=value3&bcd=value2

**Step 3：**Splice the string to be signed with the developer\'s Key.

abc=value1&bad=value3&bcd=value2**Key**

**Step 4：**Apply MD5 for the spliced string.

MD5(abc=value1&bad=value3&bcd=value2**Key**)

**Step 5：**Use signature to request the interface.

Store the signed results into the X-QF-SIGN in the HTTP head.

Python Sample：

![](media/image2.png){width="6.5in" height="2.0833333333333335in"}

3.3 Request Description
-----------------------

When requesting API interface, parameters have to be set up as follows
in the HTTP head.

  -------------- -------------- ------------------------------------------------------------------------------------------------------------------------------------------------
  **Name**       **Required**   **Description**
  X-QF-APPCODE   Y              The app\_code assigned to the developer is the unique identifier of the developer. This parameter requires to be directly assigned from QFPay.
  X-QF-SIGN      Y              Signature generated according to the signature algorithm
  -------------- -------------- ------------------------------------------------------------------------------------------------------------------------------------------------

3.4 Verifying the Signature
---------------------------

Description: after successfully requesting the interface, a JSON format
buffer will be received and to be verified.

The response HTTP head is set up as follows'

+-----------------------------------+-----------------------------------+
| **Name**                          | **Description**                   |
+-----------------------------------+-----------------------------------+
| X-QF-SIGN                         | **Step 1:** Get the signature fro |
|                                   | m X-QF-SIGN in HTTP head. {#step- |
|                                   | 1-get-the-signature-from-x-qf-sig |
|                                   | n-in-http-head.}                  |
|                                   | --------------------------------- |
|                                   | -------------------------         |
|                                   |                                   |
|                                   | **Step 2**: Connect the body of   |
|                                   | the HTTP response with Key to get |
|                                   | the string to be verified.        |
|                                   |                                   |
|                                   | **Step 3**: Apply MD5 for the     |
|                                   | spliced string. (SIGN = MD5(body  |
|                                   | + key))                           |
|                                   |                                   |
|                                   | **Step 4**: Compare the computing |
|                                   | result with the signature from    |
|                                   | X-QF-SIGN to verify whether they  |
|                                   | are identical.                    |
+-----------------------------------+-----------------------------------+

### 3.4.1 Head Signature sample

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  {\'Server\': \'nginx\', \'Date\': \'Fri, 12 Jan 2018 11:31:18 GMT\', \'Content-Type\': \'application/json; charset=UTF-8\', \'Content-Length\': \'302\', \'Connection\': \'keep-alive\', \'X-QF-SIGN\': \'1E2CE7C2A7F8F581C354A857182B7A31\', \'X-Powered-By\': \'QF/1.1\'}
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### 3.4.2 Public response parameters:

+-----------------------------------+-----------------------------------+
| ### **Parameters** {#parameters}  | ### **Description** {#description |
|                                   | }                                 |
+-----------------------------------+-----------------------------------+
| [[respcd]{.underline}](#response- | ### When the respcd outside of da |
| code-description)                 | ta buffer is 0000, it stands for  |
|                                   | the order is successfully generat |
|                                   | ed. {#when-the-respcd-outside-of- |
|                                   | data-buffer-is-0000-it-stands-for |
|                                   | -the-order-is-successfully-genera |
|                                   | ted.}                             |
+-----------------------------------+-----------------------------------+
| ### Resperr {#resperr}            | ### Response information {#respon |
|                                   | se-information}                   |
+-----------------------------------+-----------------------------------+
| ### pay\_type {#pay_type}         | ### Payment types {#payment-types |
|                                   | }                                 |
+-----------------------------------+-----------------------------------+
| ### respmsg {#respmsg}            | ### Debug information {#debug-inf |
|                                   | ormation}                         |
+-----------------------------------+-----------------------------------+

### 3.4.3 Response Signature sample

+-----------------------------------------------------------------------+
| MD5 data：{\"pay\_type\": \"800108\", \"sysdtm\": \"2018-01-12        |
| 19:31:16\", \"cardcd\": \"2088802362210279\", \"txdtm\": \"2018-01-12 |
| 19:31:22\", \"resperr\": \"\\u4ea4\\u6613\\u6210\\u529f\",            |
| \"txcurrcd\": \"CNY\", \"txamt\": \"1\", \"respmsg\": \"\",           |
| \"out\_trade\_no\": \"130145934788787530052\", \"syssn\":             |
| \"20180112000100020001662134\", \"respcd\":                           |
| \"0000\"}615ED178BA524459976CE40FAB78000F                             |
|                                                                       |
| MD5 results：1E2CE7C2A7F8F581C354A857182B7A31                         |
+-----------------------------------------------------------------------+

3.5 Notification Callback 
--------------------------

### **3.5.1 Note** 

The notification callback address has to be provided to the technical
support of QFPay through channel system. Each notification callback
address can only be configured with one set of code and key, which can
be modified.

If the order is successfully paid, the result of the notification
callback will be returned. However, the notification callback may be
delayed due to external factors. Therefore, please use query interface
for the scene with real-time processing requirements. It is recommended
that the notification callback can be used together with the query
interface. For security reasons, notification callback only supports
ports 80 and 443, and custom port assignments are not supported.

### 3.5.2 Notification Callback Rules

1.  The address of the notification callback has to be provided to the
    technical support of QFPay by setting up in the channel system.

2.  After the transaction succeeds, the QFPay will POST the notification
    data (JSON format) to the configured notification callback address.

3.  After receiving the notification callback and verifying the
    signature, the developer needs to return the SUCCESS string to the
    QFPay, indicating that it has been processed.

4.  If the developer returns other data to the QFPay, or if there is no
    return, then the QFpay will send the notification callback again
    with a certain strategy. The time interval is configured as 1m, 5m,
    10m, 60m, 2h, 6h, 15h. Once the SUCCESS string is sent back to
    QFPay, the follow-up callback notices will not be continued.

### 3.5.3 Verifying the signature in Callback:

**Step 1**: Get the signature from X-QF-SIGN in HTTP head.

**Step 2**: Connect the body of the HTTP response with Key to get the
string to be verified.

**Step 3**: Apply MD5 for the spliced string. (SIGN = MD5(body + key))

**Step 4**: Compare the computing result with the signature from
X-QF-SIGN to verify whether they are identical. If they are identical,
the verification is successful and return SUCCESS string to QFPay.

### 3.5.4 Sample Code

import hashlib\
unicode\_to\_utf8 = lambda s: s.encode(\'utf-8\') if isinstance(s,
unicode) else s\
def make\_resp\_sign(data, key):\
unsign\_str = unicode\_to\_utf8(data) + unicode\_to\_utf8(key)\
s = hashlib.md5(unsign\_str).hexdigest()\
return s.upper()

### 3.5.5 Callback Response Parameter

+-----------------------------------+-----------------------------------+
| ### **Parameter** {#parameter}    | ### **Description** {#description |
|                                   | -1}                               |
+-----------------------------------+-----------------------------------+
| ### notify\_type {#notify_type}   | ### Notification types; {#notific |
|                                   | ation-types}                      |
|                                   |                                   |
|                                   | ### payment； refund： close： {#pay |
|                                   | ment-refund-close}                |
+-----------------------------------+-----------------------------------+
| ### syssn {#syssn}                | **Order number** in QFPAY system  |
+-----------------------------------+-----------------------------------+
| ### [[out\_trade\_no]{.underline} | **Merchant\'s order number:**     |
| ](#out_trade_no) {#out_trade_no-1 |                                   |
| }                                 | The merchant's order number has   |
|                                   | to be unique.                     |
| **String**                        |                                   |
|                                   |                                   |
| No longer than 128 byte           |                                   |
+-----------------------------------+-----------------------------------+
| ### pay\_type {#pay_type-1}       | **Payment type:**                 |
|                                   |                                   |
|                                   | Wechat:800208；Alipay:800108;     |
|                                   |                                   |
|                                   | All-in-one swipe 800008；         |
+-----------------------------------+-----------------------------------+
| ### txdtm {#txdtm}                | **Transaction time:**             |
|                                   |                                   |
|                                   | format: YYYY-MM-DD HH:MM:SS       |
+-----------------------------------+-----------------------------------+
| ### txamt {#txamt}                | **Amount of payment: **           |
|                                   |                                   |
|                                   | It is measured in the minimum     |
|                                   | price unit of the transaction     |
|                                   | currency.                         |
|                                   |                                   |
|                                   | (Maximum 10,000 USD)              |
+-----------------------------------+-----------------------------------+
| [[respcd]{.underline}](#response- | **Response code:**                |
| code-description)                 |                                   |
|                                   | 0000 stands for order placed      |
|                                   | successfully                      |
|                                   |                                   |
|                                   | 1143,1145 stand for transaction   |
|                                   | in processing, merchant           |
+-----------------------------------+-----------------------------------+
| ### sysdtm {#sysdtm}              | **System time**                   |
+-----------------------------------+-----------------------------------+
| ### paydtm {#paydtm}              | ### **Time** when the user made t |
|                                   | he payment. {#time-when-the-user- |
|                                   | made-the-payment.}                |
+-----------------------------------+-----------------------------------+
| ### cancel {#cancel}              | ### **Marks** for refunded or clo |
|                                   | sed {#marks-for-refunded-or-close |
|                                   | d}                                |
|                                   |                                   |
|                                   | ### Processing：0；Closed：2；Refunde |
|                                   | d：3； {#processing0closed2refunded |
|                                   | 3}                                |
+-----------------------------------+-----------------------------------+
| ### cardcd {#cardcd}              | ### **OpenID** from Wechat or Ali |
|                                   | pay users {#openid-from-wechat-or |
|                                   | -alipay-users}                    |
+-----------------------------------+-----------------------------------+
| ### [[goods\_name]{.underline}](# | **Name of the product:**          |
| goods_name) {#goods_name-1}       |                                   |
|                                   | special character is not allowed  |
+-----------------------------------+-----------------------------------+
| ### status {#status}              | ### **Status of Transaction**：1：T |
|                                   | he transaction is successful； {#s |
|                                   | tatus-of-transaction1the-transact |
|                                   | ion-is-successful}                |
+-----------------------------------+-----------------------------------+
| ### txcurrcd {#txcurrcd}          | **Currency code:** JPY            |
+-----------------------------------+-----------------------------------+
| ### mchid {#mchid}                | **Merchant id:**                  |
|                                   |                                   |
|                                   | allocate by QFPAY(You can look up |
|                                   | merchant ID in channel system)    |
+-----------------------------------+-----------------------------------+

### 3.5.6 Response Callback sample

+-------------------------------------------------------+
| {\"status\": \"1\",                                   |
|                                                       |
| \"sysdtm\": \"2017-12-28 13:41:47\",                  |
|                                                       |
| \"paydtm\": \"2017-12-28 13:42:20\",                  |
|                                                       |
| \"goods\_name\": \"\", \"txcurrcd\": \"CNY\",         |
|                                                       |
| \"mchid\": \"XXxxxx\", \"cancel\": \"0\",             |
|                                                       |
| \"pay\_type\": \"800207\",                            |
|                                                       |
| \"cardcd\": \"oo3Lss1m9-eHSEyY2OGKzxFaRflY\",         |
|                                                       |
| \"txdtm\": \"2017-12-28 13:41:47\",                   |
|                                                       |
| \"txamt\": \"200\",                                   |
|                                                       |
| \"out\_trade\_no\": \"BO201712280117290001\",         |
|                                                       |
| \"syssn\": \"20171228000300020044178249\",            |
|                                                       |
| \"respcd\": \"0000\",                                 |
|                                                       |
| \"goods\_info\": \"\", \"notify\_type\": \"payment\"} |
+-------------------------------------------------------+

3.6 Python Sample code
----------------------

\#!/usr/bin/env python

\#encoding=utf8

import urllib, urllib2, hashlib

import json

import argparse

import random

import qrcode

from datetime import datetime

from PIL import ImageFile

import hashlib

parser = argparse.ArgumentParser(description=\"Simulate Payment Process
for QFPay and CIL\")

parser.add\_argument(\'\--txamt\', default=1, help=\'Payment Amount\')

parser.add\_argument(\'\--type\', default=\'wechat\', help=\'Payment
Type\')

parser.add\_argument(\'\--cur\', default=\'CNY\', help=\'Payment
Currency\')

parser.add\_argument(\'\--authcode\', help=\'Auth Code\')

parser.add\_argument(\'\--syssn\', help=\'Syssn Code\')

parser.add\_argument(\'\--tradeno\', help=\'Out Trade No\')

parser.add\_argument(\'\--query\', action=\'store\_true\',
help=\'Perform Query\')

parser.add\_argument(\'\--refund\', action=\'store\_true\',
help=\'Perform Refund\')

parser.add\_argument(\'\--txdtm\', help=\'Transaction Time\')

parser.add\_argument(\'\--starttime\', default=None)

parser.add\_argument(\'\--endtime\', default=None)

parser.add\_argument(\'\--close\', action=\'store\_true\')

parser.add\_argument(\'\--reconcile\', action=\'store\_true\')

parser.add\_argument(\'\--tradedate\', default=None)

unicode\_to\_utf8 = lambda s: s.encode(\'utf-8\') if isinstance(s,
unicode) else s

def make\_req\_sign(data, key):

keys = data.keys()

keys.sort()

p = \[\]

for k in keys:

k = unicode\_to\_utf8(k)

v = unicode\_to\_utf8(data\[k\])

p.append(\'%s=%s\'%(k,v))

unsign\_str = \'&\'.join(p) + unicode\_to\_utf8(key)

s = hashlib.md5(unsign\_str).hexdigest()

return s.upper()

def main():

pay\_type = {

\'wechat\': \'800201\', \#dynamic QR code generation

\'alipay\': \'800101\',

\'wechat\_app\': \'800208\', \#scan from user\'s app

\'alipay\_app\': \'800108\', \#scan from user\'s app

\'general\': \'800008\',

}

d = datetime.now()

\# mchid = None

args = parser.parse\_args()

txamt = args.txamt

txcurrcd = args.cur

pay\_type = pay\_type\[args.type\]

out\_trade\_no = random.randint(10\*\*11, 10\*\*12)

txdtm = d.strftime(\"%Y-%m-%d %H:%M:%S\")

\# goods\_name = \'商品名１：缶コーヒー\'

goods\_name = \'test\'

limit\_pay = \'no\_credit\'

 udid = \'xxxxxxxx\'

auth\_code = args.authcode

start\_time = args.starttime

end\_time = args.endtime

mchid = \'xxxxxxxxx\'

app\_code = \'xxxxxxxxxxxxxxxxxxxxxxxxxxx\'

key = \'xxxxxxxxxxxxxxxxxxxxxxxxxxx\'

is\_pay = True

url = \'https://openapi.qfpay.com/trade/v1/payment\'

data ={\'txamt\': txamt, \'txcurrcd\': txcurrcd, \'pay\_type\':
pay\_type, \'out\_trade\_no\': out\_trade\_no, \'txdtm\': txdtm,
\'goods\_name\':goods\_name,\'limit\_pay\':limit\_pay,\'udid\':udid}

if mchid: data\[\'mchid\'\] = mchid

if args.type in \[\'wechat\_app\', \'alipay\_app\', \'general\'\]:

data\[\'auth\_code\'\] = auth\_code

if args.query:

is\_pay = False

url = \'https://openapi.qfpay.com/trade/v1/query\'

data = { \'pay\_type\': pay\_type, \'mchid\': mchid, \'page\_size\': 1,
\'Page\': 2}

if start\_time: data\[\'start\_time\'\] = start\_time

if end\_time: data\[\'end\_time\'\] = end\_time

elif args.refund:

is\_pay = False

url = \'https://openapi.qfpay.com/trade/v1/refund\'

data = { \'syssn\': args.syssn, \'out\_trade\_no\': args.tradeno,
\'pay\_type\': pay\_type,\'mchid\': mchid, \'txamt\': txamt, \'txdtm\':
args.txdtm}

elif args.close:

is\_pay = False

url = \'https://openapi.qfpay.com/trade/v1/close\'

data = {\'txamt\': txamt, \'txdtm\': args.txdtm, \'mchid\': mchid }

if args.syssn is not None: data\[\'syssn\'\] = syssn

if args.tradeno is not None: data\[\'out\_trade\_no\'\] = args.tradeno

elif args.reconcile:

url = \'https://openapi.qfpay.com/download/v1/trade\_bill\'

data = { \'trade\_date\': args.tradedate }

print(data)

req = urllib2.Request(url, urllib.urlencode(data))

req.add\_header(\'X-QF-APPCODE\', app\_code)

req.add\_header(\'X-QF-SIGN\', make\_req\_sign(data, key))

resp = urllib2.urlopen(req)

output = json.loads(resp.read())

print resp.info()

print output

print output\[\'respcd\'\]

print output\[\'resperr\'\].encode(\"utf-8\")

if output\[\'respcd\'\] == \'0000\' and args.type in \[\'wechat\',
\'alipay\'\] and is\_pay:

img = qrcode.make(output\[\'qrcode\'\])

img.save(\'qrcode.png\')

if \_\_name\_\_ == \'\_\_main\_\_\':

main()
