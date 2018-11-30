package com.cn.android_testtwo;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class pass {

        // 推荐的RSA加密模式，否则容易被重放攻击
        private static final String RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING = "RSA/ECB/OAEPWithSHA256AndMGF1Padding";
        private static final String RSA = "RSA";

        /**
         * 生成秘钥,公钥和私钥
         *
         * @return Base64编码的公钥和私钥 secretKeys[]{rsaPublicKey,rsaPrivateKey}
         */
        public static String[] generateSecretKey() {
            String[] secretKeys = null;
            try {
                // 构建密钥对生成器
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
                // 初始化秘钥长度 2048位（512位已被破解，至少用1024及以上）
                keyPairGenerator.initialize(2048);

                // 生成密钥对，包含公钥私钥
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                // 公钥对象，RSAPublicKey为PublicKey子接口
                RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
                // 公钥对象，RSAPrivateKey为PrivateKey子接口
                RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

                // 公钥
                byte[] publicKeyBytes = rsaPublicKey.getEncoded();
                // 私钥
                byte[] privateKeyBytes = rsaPrivateKey.getEncoded();

                secretKeys = new String[2];
                // 对公私钥Base64转码，加解密时需要把转码后的公私钥进行Base64还原
                secretKeys[0] = Base64.encodeToString(publicKeyBytes, Base64.DEFAULT);
                secretKeys[1] = Base64.encodeToString(privateKeyBytes, Base64.DEFAULT);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return secretKeys;
        }

        /**
         * 公钥加密
         *
         * @param plaintext    明文
         * @param rsaPublicKey 公钥
         * @return Base64编码的密文
         */
        public static String encryptByPublicKey(String plaintext, String rsaPublicKey) {
            try {
                // Base64还原公钥
                byte[] publicKeyBytes = Base64.decode(rsaPublicKey, Base64.DEFAULT);
                // X509编码秘钥规范
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                // 秘钥工厂
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                // 还原公钥对象，PublicKey为RSAPublicKey父接口
                PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

                // Cipher对象，指定算法模式
                Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING);
                // 初始化，指定为加密模式
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                // 执行加密
                byte[] result = cipher.doFinal(plaintext.getBytes("UTF-8"));
                return Base64.encodeToString(result, Base64.DEFAULT); // 对密文Base64转码，解密时需要把转码后的密文进行Base64还原
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 公钥解密
         *
         * @param ciphertext   密文
         * @param rsaPublicKey 公钥
         * @return Base64编码的密文
         */
        public static String decryptByPublicKey(String ciphertext, String rsaPublicKey) {
            try {
                // Base64还原公钥
                byte[] publicKeyBytes = Base64.decode(rsaPublicKey, Base64.DEFAULT);
                // X509秘钥编码规范
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                // 秘钥工厂
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                // 还原公钥对象，PublicKey为RSAPublicKey父接口
                PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

                // Cipher对象，指定算法模式
                Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING);
                // 初始化，指定为解密模式
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
                // Base64还原密文
                byte[] cipherBytes = Base64.decode(ciphertext, Base64.DEFAULT);
                // 执行解密
                byte[] result = cipher.doFinal(cipherBytes);
                return new String(result, "UTF-8");
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 私钥加密
         *
         * @param plaintext     明文
         * @param rsaPrivateKey 私钥
         * @return Base64编码的密文
         */
        public static String encryptByPrivateKey(String plaintext, String rsaPrivateKey) {
            try {
                // Base64还原私钥
                byte[] privateKeyBytes = Base64.decode(rsaPrivateKey, Base64.DEFAULT);
                // PKCS8秘钥编码规范
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                // 秘钥工厂
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                // 还原公钥对象，PrivateKey为RSAPrivateKey父接口
                PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

                // Cipher对象，指定算法模式
                Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING);
                // 初始化，指定为加密模式
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                // 执行加密
                byte[] result = cipher.doFinal(plaintext.getBytes("UTF-8"));
                return Base64.encodeToString(result, Base64.DEFAULT);// 对密文Base64转码，解密时需要把转码后的密文进行Base64还原
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 私钥解密
         *
         * @param ciphertext    密文
         * @param rsaPrivateKey 秘钥
         * @return 明文
         */
        public static String decryptByPrivateKey(String ciphertext, String rsaPrivateKey) {

            try {
                // Base64还原私钥
                byte[] privateKeyBytes = Base64.decode(rsaPrivateKey, Base64.DEFAULT);
                // PKCS8秘钥编码规范
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                // 秘钥工厂
                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
                // 还原公钥对象，PrivateKey为RSAPrivateKey父接口
                PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

                // Cipher对象，指定算法模式
                Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_WITH_SHA256_AND_MGF1_PADDING);
                // 初始化，指定为解密模式
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                // Base64还原密文
                byte[] cipherBytes = Base64.decode(ciphertext, Base64.DEFAULT);
                // 执行解密
                byte[] result = cipher.doFinal(cipherBytes);
                return new String(result, "UTF-8");
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
