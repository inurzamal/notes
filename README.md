1. Import Certificate and Private Key
If you have a .pfx file (PKCS12 format) that contains both the private key and certificate, you can import it into a Java keystore like this:

keytool -importkeystore -srckeystore HLFUS.pfx -srcstoretype pkcs12 -destkeystore mykeystore.jks

You will be prompted to set a keystore password. Remember this password, as you will need it in the configuration.


If you have separate .crt and .key files, you can convert them to a PKCS12 keystore:
openssl pkcs12 -export -out keystore.pfx -inkey private.key -in certificate.crt
