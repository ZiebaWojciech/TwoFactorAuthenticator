import com.sun.prism.shader.Solid_Color_AlphaTest_Loader;

import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

public class Main {

    public static void main(String[] args) {
        HMACGenerator HMACode = new HMACGenerator("hfhahibjsaldf", "12345678901234567890123456789012345678901234567890123456789012341");
        HMACode.padAndHash();
        HMACode.digesting();

        if(HMACode.getMessage().getBytes().length>HMACode.getBlockLength()){
            HMACode.shortening();
            HMACode.digesting();
        }
        else{
            HMACode.digesting();
        }
        System.out.println("Message length: " + HMACode.getMessage().getBytes().length);
        System.out.println("Key: " + HMACode.getKey());
        System.out.println("Message: " + HMACode.getMessage());
        System.out.println("Key XORd with opad + previous digest - > hashed: " + Arrays.toString(HMACode.getDigestedMessage()));
        System.out.println("Hex digested message: " + DatatypeConverter.printHexBinary(HMACode.getDigestedMessage()));
        System.out.println("Byte hashed msg:" + Arrays.toString(HMACode.getDigestedMessage()));
    }
}
