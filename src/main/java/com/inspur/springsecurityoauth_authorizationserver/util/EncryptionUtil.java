package com.inspur.springsecurityoauth_authorizationserver.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 加密工具类
 */
public class EncryptionUtil {
    private static final Log log = LogFactory.getLog(EncryptionUtil.class);
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * BASE64解密,入参出参为String
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64String(String key) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return new String(decoder.decodeBuffer(key));
    }


    /**
     * BASE64加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptBASE64String(String data) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.getBytes());
    }

    /**
     * MD5加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5String(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * SHA-1加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA1(String data) {
        return DigestUtils.sha1Hex(data);
    }

    /**
     * SHA-256加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA256(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String sha256_mac(String message,String key){
        String outPut= null;
        try{
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec(key.getBytes(),"HmacSHA256"));
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            outPut = byteArrayToHexString(bytes);
        }catch (Exception e){
            System.out.println("Error HmacSHA256========"+e.getMessage());
        }
        return outPut;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                sb.append('0');
            sb.append(stmp);
        }
        return sb.toString().toLowerCase();
    }

    /**
     * SHA-384加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA384(String data) {
        return DigestUtils.sha384Hex(data);
    }

    /**
     * SHA-512加密,入参出参为String
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptSHA512(String data) {
        return DigestUtils.sha512Hex(data);
    }
}
