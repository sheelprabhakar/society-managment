package com.c4c.housing.core.service.impl;

import jakarta.persistence.AttributeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;


@Component
public class EntityAttributeEncryptor implements AttributeConverter<String, String>
{
    private static final Logger logger = LogManager.getLogger(EntityAttributeEncryptor.class);
    private static final String AES = "AES";
    @Value("${security.db.encryption.secret-key:security.db.encryption.secret-key}")
    private String SECRET = "secret-key-98765432";

    private final Key key;
    private final Cipher cipher;

    public EntityAttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            logger.error("Entity attribute encryption exception", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            logger.error("Entity attribute encryption exception", e);
            throw new IllegalStateException(e);
        }
    }
}
