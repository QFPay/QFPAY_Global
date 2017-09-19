using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;

namespace EQFPayment
{
    public class Payment
    {
        /// <summary>
        /// 用于电子支付付款
        /// </summary>
        /// <param name="paySource">付款方式： wechat:微信， 其他：支付宝</param>
        /// <param name="tradeNo">交易单号</param>
        /// <param name="authCode">顾客手机上的付款码</param>
        /// <param name="amount">付款金额</param>
        /// <returns></returns>
        public string Pay(string paySource, string tradeNo, string authCode, decimal amount)
        {
            String appcode = "123456";
            String key = "123456";
            String mchid = "eqqmYMn0Zj6pncw5ZDxjgMqbzV";

            //如果是国内钱台，pay_type和海外版本不同.

            String pay_type = "800108"; // Alipay:800108, Wechat:800208

            if (string.Compare(paySource, "wechat", 0) == 0)
            {
                pay_type = "800208";
            }

            String txdtm = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

            string txamt = ((int)(amount * 100)).ToString();

            //如果是国内钱台，产品名称对应的字段是goods_name，不是product_name.
            String product_name = "Test Name";

            var dict = new Dictionary<string, string>();
            dict.Add("mchid", mchid);
            dict.Add("pay_type", pay_type);
            dict.Add("out_trade_no", tradeNo);
            dict.Add("txdtm", txdtm);
            dict.Add("txamt", txamt);
            dict.Add("auth_code", authCode);
            dict.Add("product_name", product_name);

            String data = GetDataString(dict);

            String md5Sum = ToMD5String(data + key);

            //如果是国内钱台，网址是：https://openapi-test.qfpay.com.
            String url = "https://osqt.qfpay.com/trade/v1";

            String resp = SendPostRequest(url + "/payment", data, appcode, key);

            return resp;

        }

        /// <summary>
        /// 用于电子支付退款
        /// </summary>
        /// <param name="syssn">原交易单中所返回的 syssn</param>
        /// <param name="orgtradeno">原交易单号</param>
        /// <param name="amount">退款金额</param>
        /// <returns></returns>
        public string Refund(string syssn, string orgtradeno, decimal amount)
        {
            String appcode = "123456";
            String key = "123456";
            String mchid = "eqqmYMn0Zj6pncw5ZDxjgMqbzV";

            String txdtm = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            string txamt = ((int)(amount * 100)).ToString();

            var dict = new Dictionary<string, string>();
            dict.Add("mchid", mchid);
            dict.Add("out_trade_no", orgtradeno);
            dict.Add("syssn", syssn);
            dict.Add("txdtm", txdtm);
            dict.Add("txamt", txamt);

            String data = GetDataString(dict);

            String md5Sum = ToMD5String(data + key);

            //如果是国内钱台，网址是：https://openapi-test.qfpay.com.
            String url = "https://osqt.qfpay.com/trade/v1";

            String resp = SendPostRequest(url + "/refund", data, appcode, key);

            return resp;
        }


        public string Query()
        {
            String appcode = "123456";
            String key = "123456";
            String mchid = "eqqmYMn0Zj6pncw5ZDxjgMqbzV";

            var dict = new Dictionary<string, string>();
            dict.Add("mchid", mchid);

            String data = GetDataString(dict);

            String md5Sum = ToMD5String(data + key);

            //如果是国内钱台，网址是：https://openapi-test.qfpay.com.
            String url = "https://osqt.qfpay.com/trade/v1";

            String resp = SendPostRequest(url + "/query", data, appcode, key);

            return resp;
        }

        /// <summary>
        /// 将字典中的键值对输出为 Key1=Value1&Key2=Value2 的格式
        /// </summary>
        /// <param name="dict"></param>
        /// <returns></returns>
        public static String GetDataString(Dictionary<string, string> dict)
        {
            if (dict == null)
            {
                return "";
            }

            var sb = new StringBuilder();

            foreach (var key in dict.Keys.OrderBy(p => p))
            {
                sb.AppendFormat("{0}={1}&", key, (dict[key]));
            }

            return sb.ToString().TrimEnd('&');
        }

        public static string ToMD5String(string sourceString)
        {
            var md5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
            byte[] result = md5.ComputeHash(Encoding.UTF8.GetBytes(sourceString));

            var sb = new StringBuilder();

            for (int i = 0; i < result.Length; i++)
            {
                sb.Append(result[i].ToString("x2"));
            }
            return sb.ToString().ToUpper();
        }


        public static String SendPostRequest(String inputURL, String data, String appcode, String key)
        {
            WebClient client = new WebClient();

            client.Headers.Add("Accept-Charset", "utf-8");
            client.Headers.Add("X-QF-APPCODE", appcode);
            client.Headers.Add("X-QF-SIGN", ToMD5String(data + key));

            var responseBytes = client.UploadString(inputURL, "POST", data);

            string responseString = GetUnescapeString((responseBytes));
            return responseString;
        }


        public static string GetUnescapeString(string str)
        {
            return Regex.Unescape(Regex.Unescape(str ?? ""));
        }



    }
}
