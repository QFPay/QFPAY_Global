# 0.Request method                      
1.Non OAuth 2.0 Interface              
+ 1．Based on interface documentation, Use GET Or POST to do the request.                             
+ 2．For any files upload interfaces,Content-Type needs to be set as multipart/form-data .                      
+ 3． If not specified, http header needs 2 following parameters:               
X-QF-APPCODE: Allocate to developer the appcode.      
X-QF-SIGN:Data signature, The signature based on the algorithm                                      
+ 4．Signature algorithm：                                          
  + 1.All non-empty parameters will be sorted in ascending order.
  + 2. Concatenate all the parameters using & to get the string
  + 3.Append the string with developer’s application key 
  + 4.Using md5 algorithm to do the hash calculation.
