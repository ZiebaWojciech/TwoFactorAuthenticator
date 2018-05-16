package com.github.ziebawojciech.tfa;
import org.apache.commons.codec.binary.Base32;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;


public class Main {

    public static void main(String[] args) {
//        HMACGenerator HMACode = new HMACGenerator();
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.generateHMACode("bardzodlugiklubardzodlugikluczbardzodlugikluczcz", "śćąc")));
//
//        TOTPGenerator TOTPCode = new TOTPGenerator();
//        TOTPCode.setDigitNumber(6);
//        System.out.println("TOTP digit code: " + TOTPCode.generateTOTPCode("bardzodlugiklubardzodlugikluczbardzodlugikluczcz"));
        Base32 base32Key = new Base32();

        System.out.println(base32Key.encodeAsString("test".getBytes()));
    }
}
