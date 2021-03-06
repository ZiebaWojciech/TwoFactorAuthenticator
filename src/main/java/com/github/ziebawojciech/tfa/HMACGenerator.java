package com.github.ziebawojciech.tfa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.*;


public class HMACGenerator {
    //BLOCK_LENGTH is a length of block used to hash the message. According to standards it shall be not shorter than 64 bytes.
    //IPAD is an inner padding string. It may be any constant string of length not longer than stated block length.
    //OPAD is an outer padding string. It may be any constant string of length not longer than stated block length.
    //If IPAD or OPAD is shorter than block length it must be repeated until whole block is filled.

    //BLOCK_LENGTH, IPAD and OPAD at his stage will be constant and not to be changed.
    private static final byte BLOCK_LENGTH = 64; //64 is a recommended length of block/
    // OPAD and IPAD are fixed values, recommended as below (as hamming value is 4 and it is considered optimal)
    private static final byte IPAD = 0x36;
    private static final byte OPAD = 0x5c;

    public byte[] generateHMACode(String key, String message){
          return digestMessage( padAndHashIPAD(keyToBase32(key)),
                                padAndHashOPAD(keyToBase32(key)),
                                initiateDigestingInstance(),
                                shortenMessageLongerThanBlockLength(initiateDigestingInstance(),
                                                                    encodeMessageAsByteArray(message)));
    }

    public byte[] hashCodeForTOTP(String key, byte[] timeCounter){
        return digestMessage(   padAndHashIPAD(keyToBase32(key)),
                                padAndHashOPAD(keyToBase32(key)),
                                initiateDigestingInstance(),
                                timeCounter);
    }

    //Encode a key into a base32 byteArray
    private byte[] keyToBase32(String key){
    Base32 keyArray = new Base32();
    return keyArray.decode(key);

    }


    /*padAndHashIPAD() is firstly padding IPAD in arrays of BLOCK_LENGTH length and then XOR the key with IPAD*/
    private byte[] padAndHashIPAD(byte[] key) {
        byte[] keyIPAD = new byte[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            if (i < key.length) {
                keyIPAD[i] = key[i];
            } else {
                keyIPAD[i] = 0;
            }
            keyIPAD[i] ^= IPAD;
        }
        return keyIPAD;
    }

    /*padAndHashOPAD() is firstly padding OPAD in array of BLOCK_LENGTH length and then XOR the key with OPAD*/
    private byte[] padAndHashOPAD(byte[] key) {
        byte[] keyOPAD = new byte[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            if (i < key.length) {
                keyOPAD[i] = key[i];
            } else {
                keyOPAD[i] = 0;
            }
            keyOPAD[i] ^= OPAD;
        }
        return keyOPAD;
    }


    private MessageDigest initiateDigestingInstance(){
        try {
            MessageDigest msgDigestBuffer = MessageDigest.getInstance("SHA-1");
            return msgDigestBuffer;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] encodeMessageAsByteArray(String message){
        return message.getBytes();
    }

    private byte[] shortenMessageLongerThanBlockLength(MessageDigest msgDigestBuffer, byte[] message) {
        byte [] messageToDigest;
        if (message.length > BLOCK_LENGTH) {
                msgDigestBuffer.reset();
                msgDigestBuffer.update(message);
                messageToDigest = msgDigestBuffer.digest();
                System.out.println("The message was preliminarily digested as the message was longer than assumed blocked length.");
        }
        else {
        messageToDigest = message;
        }
        return messageToDigest;
    }

        private byte[] digestMessage(byte[] keyIPAD, byte[] keyOPAD, MessageDigest msgDigestBuffer, byte[] messageToDigest){
            msgDigestBuffer.reset();
            msgDigestBuffer.update(keyIPAD);
            msgDigestBuffer.update(messageToDigest);
            byte[] digestedMessage = msgDigestBuffer.digest();

            msgDigestBuffer.reset();

            msgDigestBuffer.update(keyOPAD);
            msgDigestBuffer.update(digestedMessage);
            digestedMessage = msgDigestBuffer.digest();

            return digestedMessage;
    }
}
