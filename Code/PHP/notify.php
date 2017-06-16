<?php
  file_put_contents("test.txt",date('Y-m-d H:i:s',time()).":\n",FILE_APPEND);
  $data=file_get_contents('php://input');
  file_put_contents("test.txt","Parse  Response Header \n", FILE_APPEND);
  foreach (getallheaders() as $name => $value) {
    file_put_contents("test.txt",$name.":".$value."\n", FILE_APPEND);
   }
  file_put_contents("test.txt",$data."\n", FILE_APPEND);
?>
