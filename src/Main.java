import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.time.Instant;


public class Main {

    public static void main(String[] args) {
        HMACGenerator HMACode = new HMACGenerator();

//        System.out.println("Key: " + HMACode.getKey());
//        System.out.println("Message: " + HMACode.getMessage());
//        HMACode.generateHMACode();
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.generateHMACode("sekret", "wiadomość")));
        TOTPGenerator TOTPCode = new TOTPGenerator();
        TOTPCode.setDigitNumber(6);
        System.out.println("TOTP digit code: " + TOTPCode.generateTOTPCode("sekret"));


    }
}
