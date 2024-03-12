package product.management.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AesUtil {
	
	private final String PK = "AES_256_PRIVATE_KEY_SECRETKEY_UD";

	public String aes256Encode(String str) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(PK.getBytes("UTF-8"), "AES");
			IvParameterSpec iV = new IvParameterSpec(PK.substring(0, 16).getBytes());
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			c.init(Cipher.ENCRYPT_MODE, secretKeySpec, iV);
			
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));

			return Hex.encodeHexString(encrypted);
		}catch(Exception e) {
			log.info("AES Encode EXception "+e);
			return "";
		}
		
	}
	
	public String aes256Decode(String str) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(PK.getBytes("UTF-8"), "AES");
			IvParameterSpec iV = new IvParameterSpec(PK.substring(0, 16).getBytes());
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			c.init(Cipher.DECRYPT_MODE, secretKeySpec, iV);
			
			byte[] decodedBytes = Hex.decodeHex(str.toCharArray());

	        return new String(c.doFinal(decodedBytes), "UTF-8");
		}catch(Exception e) {
			log.info("AES Decode EXception "+e);
			return "";
		}
	}
}
