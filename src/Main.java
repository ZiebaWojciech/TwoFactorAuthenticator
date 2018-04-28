import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

public class Main {

    public static void main(String[] args) {
        HMACGenerator HMACode = new HMACGenerator("Jefe", "what do ya want for nothing?");
        HMACode.padAndHash();
        HMACode.digesting();

//        if(HMACode.getMessage().getBytes().length>HMACode.getBlockLength()){
//            HMACode.shortening();
//            HMACode.digesting();
//        }
//        else{
//            HMACode.digesting();
//        }

        //System.out.println("Key XORd with ipad + msg - > hashed: " + Arrays.toString(innerDigest));
        System.out.println("Key: " + HMACode.getKey());
        System.out.println("Message: " + HMACode.getMessage());
        System.out.println("Key XORd with opad + previous digest - > hashed: " + Arrays.toString(HMACode.getDigestedMessage()));
        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.getDigestedMessage()));
    }
}
