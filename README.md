# Table of Contents
## Introduction
This documentation is for QFPAY oversea integration.

To invoke the payment interface for example ,the URL is :https://osqt.qfpay.com/trade/v1/payment

Note:This API has no sandbox enviroment, please be noted.
## Glossary
* appcode：Developer only identification ID.
* key: Developer key， invoke other interfaces except SDK,include refund，reverse，get OAuth and access token，

## [0.Request method](https://github.com/linan0828/QFPAY_Oversea/blob/master/0_Request_method.md)     
* 1.Non OAuth 2.0 Interface 
* 2.OAuth 2.0 Interface    

## [1.Sub merchant signup/query (/mch/v1)](https://github.com/linan0828/QFPAY_Oversea/blob/master/1_signup.md)
* 1./signup------------sign up 
* 2./uploadcert-------upload more information 
* 3./query-------------query user info
* 4./mcc---------------Merchant Category Codes

##  [2.Payment(/trade/v1)](https://github.com/linan0828/QFPAY_Oversea/blob/master/2_payment.md)
* 1./payment----------payment
* 2./close--------------close the order
* 3./refund------------refund the money
* 4./reversal-----------cancel/flushes
* 5./query------------query an order
* 6.Pre order result notification
* 7.Pre order notification

## [3.OAuth authorization(/oauth/v2)](https://github.com/linan0828/QFPAY_Oversea/blob/master/3_OAuth authorization.md)
* 1./authorize-------User authorization
* 2./access_token---Get/Refresh access_token

## [4.OAuth query(/user/v1)](https://github.com/linan0828/QFPAY_Oversea/blob/master/4_Oauth_query.md)
* 1./baseinfo--------Get user information
* 2./tradelist--------Get order information

## [5.Settlement query]((https://github.com/linan0828/QFPAY_Oversea/blob/master/5_settlement.md)
* 1./settlement/v1/query

## [6. Appendix](https://github.com/linan0828/QFPAY_Oversea/blob/master/appendix.md)
* 1.Respcd List
* 2.Example code
* 3.Common questions
