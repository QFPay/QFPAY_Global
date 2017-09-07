import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TestMain {
    public static void main(String args[]){
        String appcode="123456";
        String key="123456";
        String mchid="eqqmYMn0Zj6pncw5ZDxjgMqbzV";

         //如果是国内钱台，pay_type和海外版本不同.
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
}
