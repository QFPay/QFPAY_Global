import hashlib

unicode_to_utf8 = lambda s: s.encode('UTF-8')
def make_sign(data, key):
    keys = data.keys()
    keys=sorted(keys,reverse=False)
    p = ""
    for k in keys:
        v = data[k]
        temp=str(k)+"="+str(v)+"&"
        p=p+temp
    p=p[:-1]+key
    print(p)
    s = hashlib.md5(unicode_to_utf8(p)).hexdigest()
    print ("key:    "+str(s).upper())
    return s.upper()
