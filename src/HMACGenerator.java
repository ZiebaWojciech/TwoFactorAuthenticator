
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HMACGenerator {
    //blockLength is a lenth of block used to hash the message. According to standards it shall be not shorter than 64 bytes.
    //ipad is an inner padding string. It may be any constant string of length not longer than stated block length.
    //opad is an outer padding string. It may be any constant string of length not longer than stated block length.
    //If ipad or opad is shorter than block length it must be repeated until whole block is filled.

    //blockLength, ipad and opad at his stage will be constant and not to be changed. TODO it may be changed in the future
    private byte blockLength = 64; //64 is a recommended length of block
    private byte ipad = 0x36; //The bytes used as ipad and opad are random and may be changes TODO check this info
    private byte opad = 0x5c;

    private String key ; //TODO make sure that key is always assigned OR put nullpointer exeption over subsequent code
    private String message;
    private byte[] finalMessage;
    private byte[] digestedMessage;

    private byte[] preDigest = new byte[0];


    public void setKey(String key){
        this.key = key;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getKey(){
        return key;
    }

    public byte getBlockLength(){ return blockLength;}

    public byte[] getDigestedMessage(){
        return digestedMessage;
    }

    public HMACGenerator(){
    }

    public HMACGenerator(String key, String message){
        this.key = key;
        this.message = message;

    }



    //TODO check whether the msg is shorter than blockLength. If not then hash the message first
    public byte[] shortening(){

        try{
            byte[] messageInBytes = message.getBytes();
            MessageDigest md  = MessageDigest.getInstance("MD5");
            md.update(messageInBytes);
            preDigest = md.digest();
            System.out.println("Preliminary digest (when the message is longer that block length: " + Arrays.toString(preDigest));

            md.reset();

            return preDigest;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return new byte[0];
    }

    private byte[] keyIpad = new byte[blockLength];
    private byte[] keyOpad = new byte[blockLength];

    //padAndHash() is firstly padding ipad and opad in arrays of block length and then XOR the key with opad and ipad
    public void padAndHash() {
        byte[] keyInBytes = key.getBytes();
        for (int i = 0; i < blockLength; i++) {
            if (i < keyInBytes.length) {
                keyIpad[i] = keyInBytes[i];
                keyOpad[i] = keyInBytes[i];
            } else {
                keyIpad[i] = 0;
                keyOpad[i] = 0;
            }

            keyIpad[i] ^= ipad;
            keyOpad[i] ^= opad;
        }
    }


    public byte[] digesting(){
//    if(preDigest.length == 0){
//        finalMessage =  messageInBytes;
//    }
//    else{
//        finalMessage = preDigest;
//    }
        try{
            byte[] messageInBytes = message.getBytes();
            MessageDigest md  = MessageDigest.getInstance("MD5");
            md.update(keyIpad);
            md.update(messageInBytes);
            byte[] innerDigest = md.digest();


            md.reset();

            md.update(keyOpad);
            md.update(innerDigest);
            digestedMessage = md.digest();

            return digestedMessage;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return new byte[0];
    }





//    public HMACGenerator() {
//        keyIpad = new byte[blockLength];
//    }
//        System.out.println(hex);
}
