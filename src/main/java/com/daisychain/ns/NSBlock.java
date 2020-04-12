package com.daisychain.ns;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NSBlock {
	private String id;
	private BigInteger index;
	private String public_key;
	private String previous_hash;
	private Date timestamp;
	private String nonce;
	private String symmetricKeyEncrypted;
	private String symmetricKeyAlgo;
	private String hash_text;
	private String user_data_base64;
	private String blockchain_id;
	private String external_id;
	private String hash;
	private String signed_hash;
	

	@Override
	public String toString() {
		return hash_text;
	}

	public String sha256hash() {
		
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(this.hash_text.getBytes());
			BigInteger bint = new BigInteger(1, digest);
			hash = bint.toString(16);
			while (hash.length() < 32) {
				hash = "0" + hash;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return hash;
	}
}
