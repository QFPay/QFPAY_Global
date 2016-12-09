# Table of Contents
## Introduction
This documentation is for QFPAY oversea integration。
The QFPay Request URL：https://osqt.qfpay.com                     
Note:This API has no sandbox enviroment, please be noted.
## 0.Request method      
* 1.Non OAuth 2.0 Interface 
* 2.OAuth 2.0 Interface    

## 1.Sub merchant signup/query (/mch/v1)
* 1./signup------------sign up 
* 2./uploadcert-------upload more information 
* 3./query-------------query user info
* 4./mcc---------------Merchant Category Codes

##  2.Payment(/trade/v1)
* 1./payment----------payment
* 2./close--------------close the order
* 3./refund------------refund the money
* 4./reversal-----------cancel/flushes
* 5./query------------query an order
* 6.Pre order result notification
* 7.Pre order notification

## 3.OAuth authorization(/oauth/v2)
* 1./authorize-------User authorization
* 2./access_token---Get/Refresh access_token

## 4.OAuth query(/user/v1)
* 1./baseinfo--------Get user information
* 2./tradelist--------Get order information

## 5.Settlement query：
* 1./settlement/v1/query

## 6. Appendix
* 1.Respcd List
