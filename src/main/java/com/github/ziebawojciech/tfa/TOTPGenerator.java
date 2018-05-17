package com.github.ziebawojciech.tfa;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;

public class TOTPGenerator extends HMACGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).
    private static final int[] DIVISOR =
            //for   {0,  1,     2,      3,      4,      5,      6,          7,          8} the divisor in modulo function will be:
                    {1, 10,     100,    1000,   10000,  100000, 1000000,    10000000,   100000000};
    private int digitNumber;

    public void setDigitNumber(int digitNumber){
        this.digitNumber = digitNumber;
    }


    public String generateTOTPCode(String key){
        return dynamicTruncation(hashCodeForTOTP(key, createTimeCounter()));
    }

    //  In TOTP a time counter act as a message in regular HMAC algorithm
    private byte[] createTimeCounter(){
        //The time counter is Unix Epoch divided by time-step (in this case - 30 seconds) and act as a MESSAGE for HMAC generating algorithm
        byte[] timeCounter2 = new byte[8];
        byte[] timeCounter = BigInteger.valueOf(Instant.now().getEpochSecond()/30L).toByteArray();
        for(int i=0;i<8;i++){
            if(i<(8-timeCounter.length)){
                timeCounter2[i]=0x00;
            }
            else{
                timeCounter2[i]=timeCounter[i-(8-timeCounter.length)];
            }
        }
//        System.out.println(Arrays.toString(timeCounter2));
//        System.out.println("time counter as string " + Instant.now().getEpochSecond()/30L);
//        System.out.println("time counter as hex " + DatatypeConverter.printHexBinary(timeCounter2));
//        System.out.println("time counter as string rep " + Arrays.toString(Long.toString((59/30L)).getBytes()));
        return timeCounter2;
    }

    //dynamicTruncation firstly get the low-order 4 bits out of the digestedMessage[length -1] byte, which is an offset value.
    //a truncated message is a four byte out of the original message [offset, offset+3].
    private String dynamicTruncation(byte[] HMACodeForTimeCounter) {
        int truncatedMessage = 0;
        int offset = HMACodeForTimeCounter[HMACodeForTimeCounter.length - 1] & 15;
        //in binary 0x7f is 0111 111, so we purposely drop the most significant bit (MSB) to be sure the result will be un-signed
        if(offset>=0 && offset<=(HMACodeForTimeCounter.length-4)) {
            truncatedMessage =    (HMACodeForTimeCounter[offset] & 0x7f) << 24
                                | (HMACodeForTimeCounter[offset + 1] & 0xff) << 16
                                | (HMACodeForTimeCounter[offset + 2] & 0xff) << 8
                                | (HMACodeForTimeCounter[offset + 3] & 0xff);
        }
        String totpCode = Integer.toString(truncatedMessage % DIVISOR[digitNumber]);
        if(totpCode.length()<digitNumber) {
            for (int i = 0; i < (totpCode.length() - digitNumber); i++) {
                totpCode = ("0" + totpCode);
            }
        }
        return totpCode;
    }








}
