import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TestMain {
    public static void main(String args[]){
        String appcode="123456";
        String key="123456";
        String mchid="eqqmYMn0Zj6pncw5ZDxjgMqbzV";
        String username = "test@gmail.com";

         //如果是国内钱台，pay_type和海外版本不同.
        testPayment(appcode, key, mchid);
        //generateTestMchid(appcode,key,username);
    }

    private static void testPayment(String appcode, String key, String mchid) {
        String pay_type="800151";
        String out_trade_no=String.valueOf(Math.random()*100);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date=df.format(new Date());
        String txdtm=date;
        String txamt="10";
        //如果是国内钱台，产品名称对应的字段是goods_name，不是product_name.
        String product_name="Test Name";


        Map<String, String> unsortMap = new HashMap<>();
        unsortMap.put("mchid", mchid);
        unsortMap.put("pay_type", pay_type);
        unsortMap.put("out_trade_no", out_trade_no);
        unsortMap.put("txdtm", txdtm);
        unsortMap.put("txamt", txamt);
        unsortMap.put("product_name", product_name);
        unsortMap.put("valid_time", "300");

        String data=QFPayUtils.getDataString(unsortMap);
        System.out.println("Data:\n"+data+key);
        String md5Sum=QFPayUtils.getMd5Value(data+key);
        System.out.println("Md5 Value:\n"+md5Sum);

        //如果是国内钱台，网址是：https://openapi-test.qfpay.com.
        String url="https://osqt.qfpay.com/trade/v1";
        String resp= Requests.sendPostRequest(url+"/payment", data, appcode,key);
        System.out.println(resp);
    }

    private static String generateTestMchid(String appcode, String key, String username) {
        String product_name="Test Name";
        Map<String, String> unsortMap = new HashMap<>();
        unsortMap.put("mchid","8w5pdhDJkm");
        unsortMap.put("code","001nu49P0Vk1fa2I7c7P0L0Q8P0nu49A");
        unsortMap.put("username", username);
        unsortMap.put("idnumber", "12345678");
        unsortMap.put("name", product_name);
        unsortMap.put("country", "HK");
        unsortMap.put("city", "HK");
        unsortMap.put("address", product_name);
        unsortMap.put("shopname", product_name);
        unsortMap.put("bankaccount","12345678");
        unsortMap.put("bankuser",product_name);
        unsortMap.put("bankcountry","HK");
        unsortMap.put("bankcity","HK");
        unsortMap.put("bankaddr",product_name);
        unsortMap.put("bankname",product_name);
        unsortMap.put("bankcode","1234");
        unsortMap.put("bankswiftcode","456");
        unsortMap.put("mcc_id","6125485337528623306");
        unsortMap.put("legalperson","peter");
        unsortMap.put("channel_type","1");
        unsortMap.put("email","email@email.com");
        unsortMap.put("mobile","18000000000");
        unsortMap.put("licensenumber","123456");
        unsortMap.put("licenseactive_date","20181231");
        unsortMap.put("idcardfront","123");
        unsortMap.put("licensephoto","123");
        unsortMap.put("goodsphoto","123");
        unsortMap.put("shopphoto","123");
        String data=QFPayUtils.getDataString(unsortMap);
        System.out.println("Data:\n"+data+key);
        String md5Sum=QFPayUtils.getMd5Value(data+key);
        System.out.println("Md5 Value:\n"+md5Sum);

        //如果是国内钱台，网址是：https://openapi-test.qfpay.com.
        String url="https://osqt.qfpay.com/mch/v1/signup";
        return Requests.sendPostRequest(url, data, appcode,key);
    }
}
