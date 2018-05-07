import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

public class Main {

    public static void main(String[] args) {
//        HMACGenerator HMACode = new HMACGenerator("S", "s");
//        HMACode.padAndHash();
//        HMACode.shortening();
//        HMACode.digesting();
//
//        System.out.println("Message length: " + HMACode.getMessage().getBytes().length);
//        System.out.println("Key: " + HMACode.getKey());
//        System.out.println("Message: " + HMACode.getMessage());
//        System.out.println("Key XORd with opad + previous digest - > hashed: " + Arrays.toString(HMACode.getDigestedMessage()));
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.getDigestedMessage()));
//        System.out.println("Byte hashed msg:" + Arrays.toString(HMACode.getDigestedMessage()));

//The TOTP module test:
        TOTPGenerator TOTPCode = new TOTPGenerator("tajnyklucz");

        TOTPCode.padAndHash();
        TOTPCode.digesting();

//        System.out.println("Message length: " + TOTPCode.getMessage().getBytes().length);
        System.out.println("Key: " + TOTPCode.getKey());
        System.out.println("Message: " + TOTPCode.getMessage());
        System.out.println("Key XORd with opad + previous digest - > hashed: " + Arrays.toString(TOTPCode.getDigestedMessage()));
//        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(TOTPCode.getDigestedMessage()));
        System.out.println("Byte hashed msg:" + Arrays.toString(TOTPCode.getDigestedMessage()));
        System.out.println(Instant.now().getEpochSecond()/30);



    }
}
