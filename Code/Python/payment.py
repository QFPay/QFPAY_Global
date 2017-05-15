def payment():
    url = URL + '/trade/v1/payment'
    data = {}
    # Test
    data['pay_type'] = '800207'
    data['out_trade_no']=random.randint(0,99999999)
    data['txdtm'] = GetNowTime()
    data['product_name'] = "香港測試："+str(data['out_trade_no'])
    data['txamt'] = '10'
    data['mchid'] = 'xQj7bVwVq38PvTwqlZ0jMGdMr6'
    data['sub_openid']='om3Tivln_Z8-ohW4lCiko50h3Svw'
    # data['auth_code'] = '130105832659498462'
    headers = {'X-QF-APPCODE': '', 'X-QF-SIGN': make_sign(data, '')}
    ret = requests.post(url, headers=headers, params=data)
    return ret.text
