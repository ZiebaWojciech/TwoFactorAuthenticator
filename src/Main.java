import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//Define to fixed strings opad and ipad of lenght blockLength = 64
        // eg. Ipad = the byte 0x36 repeated blockLength times
        // and opad = 0x5c repeated blockLength times.
        byte blockLength = 64;
        byte ipad = 0x36; //The bytes used as ipad and opad are random and may be changes TODO check this info
        byte opad = 0x5c;

        String key = "Wojtek";
        byte[] keyInBytes = key.getBytes();

        String message = "Marcin to stary pierdziel";
        byte[] messageInBytes = message.getBytes();
        System.out.println("Message in array is: " + Arrays.toString(messageInBytes));

        //TODO check whether the msg is shorter than blockLength. If not then hash the message first
        /**Padding (is it a proper name for filling a string with zeros?) the key  TODO check if it's proper naming
         Both keyIpad and keyOpad are the arrays of padded keys that will be XORd with the ipad and opad respectively */
        byte[] keyIpad = new byte[blockLength];
        byte[] keyOpad = new byte[blockLength];
        for(int i=0;i<blockLength;i++){
            if(i<keyInBytes.length){
                keyIpad[i]=keyInBytes[i];
                keyOpad[i]=keyInBytes[i];
            }
            else {
                keyIpad[i] = 0;
                keyOpad[i] = 0;
            }

            keyIpad[i] ^= ipad;
            keyOpad[i] ^= opad;
        }
        System.out.println("Key XORD with Ipad: " + Arrays.toString(keyIpad));
        System.out.println("Key XORD with Opad: " + Arrays.toString(keyOpad));

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        //byte[] innerDigest = md.digest(keyIpad);
        md.update(keyIpad);
        md.update(messageInBytes);
        byte[] innerDigest = md.digest();
        System.out.println("Key XORd with ipad + msg - > hashed: " + Arrays.toString(innerDigest));

        md.reset();

        md.update(keyOpad);
        md.update(innerDigest);
        byte[] outerDigest = md.digest();
        System.out.println("Key XORd with opad + previous digest - > hashed: " + Arrays.toString(outerDigest));

        String hex = DatatypeConverter.printHexBinary(outerDigest);
        System.out.println(hex);
    }
}
