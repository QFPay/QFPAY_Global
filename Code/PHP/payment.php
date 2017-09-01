<?php

  function GetRandStr($length){
  $str='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  $len=strlen($str)-1;
  $randstr='';
  for($i=0;$i<$length;$i++){
  $num=mt_rand(0,$len);
  $randstr .= $str[$num];
  }
  return $randstr;
  }
  
  
  
  
     $url = 'https://osqt.qfpay.com';
     $api_type = '/trade/v1/payment';
     $mchid = "eqqmYMn0Zj6pncw5ZDxjgMqbzV"; //錢方提供的mchid
     $app_code = '123456'; //錢方提供的App Code
     $app_key = '123456'; //錢方提供的App Key
     $now_time = date("Y-m-d H:i:s"); //獲取當前時間    
     
     $fields_string = '';
     $fields = array(
	  'mchid' => urlencode($mchid),
	  'out_trade_no' => urlencode(GetRandStr(20)),
	  'pay_type' => urlencode(800151),
	  'txamt' => urlencode(10),
	  'txdtm' => $now_time
    );
    ksort($fields); //字典排序A-Z升序方式
    print_r($fields);
    
    foreach($fields as $key=>$value) { 
	$fields_string .= $key.'='.$value.'&' ;
  }
  $fields_string = substr($fields_string , 0 , strlen($fields_string) - 1); //刪除最後一個 & 符號
  
  $sign = strtoupper(md5($fields_string . $app_key));
  
  //// 設置Header ////
  $header = array();
  $header[] = 'X-QF-APPCODE: ' . $app_code;
  $header[] = 'X-QF-SIGN: ' . $sign;
  
  //Post Data去錢方海外URL
  $ch = curl_init();
  curl_setopt($ch, CURLOPT_URL, $url . $api_type);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
  curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
  curl_setopt($ch, CURLOPT_POST, 1);
  curl_setopt($ch, CURLOPT_POSTFIELDS, $fields_string);
  $output = curl_exec($ch);
  curl_close($ch);    
 
  //打印結果信息 
  header('Content-type:text/json'); 
  $final_data = json_decode($output, true); 
  //print_r($final_data["out_trade_no"])
  header("Location:".$final_data["pay_url"]);
 
  ?>
  
