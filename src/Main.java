import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        HMACGenerator HMACode = new HMACGenerator("secretKey", "a massage");
//
//
//
//        System.out.println("Key: " + HMACode.getKey());
//        System.out.println("Message: " + HMACode.getMessage());
//        HMACode.generateHMACode();
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.getDigestedMessage()));
////
////The HMAC shorted test:
//        HMACGenerator HMACode2 = new HMACGenerator("secretKey", "a massage");
//        HMACode2.generateHMACode();
//
//
//        System.out.println("Key: " + HMACode2.getKey());
//        System.out.println("Message: " + HMACode2.getMessage());
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode2.getDigestedMessage()));
//
//
////The TOTP module test:
//        TOTPGenerator TOTPCode = new TOTPGenerator("ggg");
//
////        TOTPCode.creatingTimeCounter();
//        TOTPCode.setMessage();
//
////        System.out.println("Message length: " + TOTPCode.getMessage().getBytes().length);
////        System.out.println("Key: " + TOTPCode.getKey());
////        System.out.println("Message: " + TOTPCode.getMessage());
////        System.out.println("Hex digested message for TOTP: " + DatatypeConverter.printHexBinary(TOTPCode.getDigestedMessage()));
////        System.out.println("Byte hashed msg:" + Arrays.toString(TOTPCode.getDigestedMessage()));
//        System.out.println(Instant.now().getEpochSecond()/30L);
////        TOTPCode.dynamicTruncation();
//
        TOTPGenerator TOTPCode2 = new TOTPGenerator("ggg", 6);
        TOTPCode2.generateTOTPCode();
        System.out.println("n-digit OTP:" + TOTPCode2.getTOTPCode());
        System.out.println("Message length: " + TOTPCode2.getMessage().getBytes().length);
        System.out.println("Key: " + TOTPCode2.getKey());
        System.out.println("Message: " + TOTPCode2.getMessage());
        System.out.println("Hex digested message for TOTP: " + DatatypeConverter.printHexBinary(TOTPCode2.getDigestedMessage()));
        System.out.println("Byte hashed msg:" + Arrays.toString(TOTPCode2.getDigestedMessage()));


    }
}
