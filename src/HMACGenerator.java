
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMACGenerator {
    //BLOCK_LENGTH is a length of block used to hash the message. According to standards it shall be not shorter than 64 bytes.
    //ipad is an inner padding string. It may be any constant string of length not longer than stated block length.
    //opad is an outer padding string. It may be any constant string of length not longer than stated block length.
    //If ipad or opad is shorter than block length it must be repeated until whole block is filled.

    //BLOCK_LENGTH, ipad and opad at his stage will be constant and not to be changed. TODO it may be changed in the future
    private byte BLOCK_LENGTH = 64; //64 is a recommended length of block
    private byte ipad = 0x36; //The bytes used as ipad and opad are random and may be changes TODO check this info
    private byte opad = 0x5c;

    protected String key ; //TODO make sure that key is always assigned OR put nullpointer exeption over subsequent code
    protected String message;
    protected byte[] messageToDigest;
    protected byte[] digestedMessage;


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

    public byte getBlockLength(){ return BLOCK_LENGTH;}

    public byte[] getDigestedMessage(){
        return digestedMessage;
    }

    public HMACGenerator(){
    }

    public HMACGenerator(String key, String message){
        this.key = key;
        this.message = message;

    }

    public byte[] shortening() {
        byte[] messageArray = message.getBytes();
        if (messageArray.length > BLOCK_LENGTH) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); //TODO assure that instance in shortening and digesting is the same thing
                md.reset();
                md.update(messageArray);
                messageToDigest = md.digest();
                System.out.println("The message was preliminarily digested as the message was longer than assumed blocked length.");

                md.reset();

                return messageToDigest;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        else {
        messageToDigest = messageArray;
        return messageToDigest;
        }
        return new byte[0];
    }

    private byte[] keyIpad = new byte[BLOCK_LENGTH];
    private byte[] keyOpad = new byte[BLOCK_LENGTH];

    /*padAndHash() is firstly padding ipad and opad in arrays of block length and then XOR the key with opad and ipad*/
    public void padAndHash() {
        byte[] keyInBytes = key.getBytes();
        for (int i = 0; i < BLOCK_LENGTH; i++) {
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

//TODO check if proteced will be enough?
    public byte[] digesting(){

//    if(preDigest.length == 0){
//        byte[] messageInBytes = message.getBytes();
//        messageToDigest =  messageInBytes;
//    }
//    else{
//        messageToDigest = preDigest;
//    }
        try{
            MessageDigest md  = MessageDigest.getInstance("MD5");
            md.update(keyIpad);
            md.update(messageToDigest);
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
}
