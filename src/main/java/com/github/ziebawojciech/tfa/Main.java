package com.github.ziebawojciech.tfa;
import javax.xml.bind.DatatypeConverter;



public class Main {

    public static void main(String[] args) {
        HMACGenerator HMACode = new HMACGenerator();
        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.generateHMACode("sekret", "wiadomość")));

        TOTPGenerator TOTPCode = new TOTPGenerator();
        TOTPCode.setDigitNumber(6);
        System.out.println("TOTP digit code: " + TOTPCode.generateTOTPCode("sekretkey"));


    }
}
