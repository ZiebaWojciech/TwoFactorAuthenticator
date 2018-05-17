package com.github.ziebawojciech.tfa;
import org.apache.commons.codec.binary.Base32;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.*;


public class Main {

    public static void main(String[] args) {
//        HMACGenerator HMACode = new HMACGenerator();
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.generateHMACode("bardzodlugikluczklua", "śćąc")));
//
        TOTPGenerator TOTPCode = new TOTPGenerator();
        TOTPCode.setDigitNumber(6);
        System.out.println("TOTP digit code: " + TOTPCode.generateTOTPCode("JJBFGV2ZGNCFARKIKBFTGUC2"));

        Timer t = new Timer();
        t.schedule(new TimerTask() {
        @Override
        public void run() {
            TOTPGenerator TOTPCode = new TOTPGenerator();
            TOTPCode.setDigitNumber(6);
            System.out.println("TOTP digit code at time " + TOTPCode.generateTOTPCode("aleocochodziztymwszystkim"));
        }
    }, 0, 30000);
//        int digitNumber = 6;
//        String totpCode = "test";
//        int codeLength = totpCode.length();
//        if(totpCode.length()<digitNumber) {
//            for (int i = 0; i < (digitNumber - codeLength); i++) {
//                totpCode = ("0" + totpCode);
//            }
//        }
//        System.out.println(totpCode);
    }

}

