<?php
  file_put_contents("test.txt",date('Y-m-d H:i:s',time()).":\n",FILE_APPEND);
  $data=file_get_contents('php://input');
  file_put_contents("test.txt","Parse  Response Header \n", FILE_APPEND);
  foreach (getallheaders() as $name => $value) {
    file_put_contents("test.txt",$name.":".$value."\n", FILE_APPEND);
   }
  file_put_contents("test.txt",$data."\n", FILE_APPEND);
  
  
  $arr = json_decode($data,true, 512, JSON_BIGINT_AS_STRING);
  $app_key = ''; //錢台提供的 App Key
  $fields_string = '';
  ksort($arr); //字典排序 A-Z 升序台式
  foreach($arr as $key=>$value) {
      $fields_string .= $key.'='.$value.'&' ;
  }
  
  $fields_string = substr($fields_string , 0 , strlen($fields_string) - 1);
  $sign = '';
  $sign = strtoupper(md5($fields_string . $app_key));
  
//qf_sign means the md5 value from response header.Note:product_name is not in notify url.
  if($sign != $qf_sign){
      echo '签名错误';
  }else{
      //业务处理
      echo 'success';
  }
  
?>
