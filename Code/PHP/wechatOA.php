<?php 
/*
AppId：Wechat official account appid
Secret：Wechat  official account appsecret
Redirect_uri：Payment directory
Code：Get the code for sub_openid
*/
				$AppId = '';
				$Secret = '';
				$Scope = 'snsapi_base';
				$Redirect_uri = urlencode('');
				$Code = $_GET['code'];
					$OpenId = "";	
if(empty($_GET['code'])){

			header('Location: '.'https://open.weixin.qq.com/connect/oauth2/authorize?appid='.$AppId.'&redirect_uri='.$Redirect_uri.'&response_type=code&scope='.$Scope.'&state=STATE#wechat_redirect');
				
}else{
      /*
      Copy the previous parameters.
      Get sub_openid for the next step.
      Explation: Do not confuse with openid and sub_openid.For QFPay,it is sub_openid.    
      */
			
			$url = 'https://api.weixin.qq.com/sns/oauth2/access_token?appid='.$AppId.'&secret='.$Secret.'&code='.$Code.'&grant_type=authorization_code';
			
			$ch = curl_init();
			curl_setopt($ch,CURLOPT_URL, $url); 
			curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,FALSE);
			curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,FALSE);
			curl_setopt($ch, CURLOPT_HEADER, FALSE);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
			$data = curl_exec($ch);
			curl_close($ch);
			if($data){
				curl_close($ch);
				$resulteRetun = json_decode($data);
				$OpenId =  $resulteRetun->openid;
				echo $OpenId;
			}
				
				
		
error_reporting(E_ERROR | E_PARSE );
date_default_timezone_set("Asia/Hong_Kong");

$url = 'https://osqt.qfpay.com';
$api_type = '/trade/v1/payment';
$mchid = ""; //錢台提供的 mchid
$app_code = ''; //錢台提供的 App Code
$app_key = ''; //錢台提供的 App Key
$now_time = date("Y-m-d H:i:s"); //獲取當前時間

//// 拼裝 Post 資料 ////
$fields_string = '';
$fields = array(
'mchid' => urlencode($mchid),
'out_trade_no' => urlencode(),
'pay_type' => urlencode(800207),
'sub_openid' => $OpenId,
'txamt' => urlencode(),
'txdtm' => $now_time 
);
ksort($fields); //字典排序 A-Z 升序台式
foreach($fields as $key=>$value) {
$fields_string .= $key.'='.$value.'&' ;
}
 echo $fields_string;
$fields_string = substr($fields_string , 0 , strlen($fields_string) - 1);
$sign = '';
$sign = strtoupper(md5($fields_string . $app_key));
$header = array();
$header[] = 'X-QF-APPCODE: ' . $app_code;
$header[] = 'X-QF-SIGN: ' . $sign;
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url . $api_type);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $fields_string);
$output = curl_exec($ch);
if(curl_exec($ch) === false)
{
echo 'Curl error: ' . curl_error($ch);
}
else
{
}
curl_close($ch);
header('Content-type:text/json');
$final_data = json_decode($output, true);
echo "<pre>";
var_dump($final_data);
echo "</pre>";
exit();
		
				
				
	
}		
?>
