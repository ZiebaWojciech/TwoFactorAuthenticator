
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMACGenerator {
    //BLOCK_LENGTH is a length of block used to hash the message. According to standards it shall be not shorter than 64 bytes.
    //IPAD is an inner padding string. It may be any constant string of length not longer than stated block length.
    //OPAD is an outer padding string. It may be any constant string of length not longer than stated block length.
    //If IPAD or OPAD is shorter than block length it must be repeated until whole block is filled.

    //BLOCK_LENGTH, IPAD and OPAD at his stage will be constant and not to be changed.
    private byte BLOCK_LENGTH = 64; //64 is a recommended length of block
    private byte IPAD = 0x36;
    private byte OPAD = 0x5c;
    private byte[] keyIpad = new byte[BLOCK_LENGTH];
    private byte[] keyOpad = new byte[BLOCK_LENGTH];

    protected String key ; //TODO make sure that key is always assigned OR put nullpointer exeption over subsequent code
    protected String message;
    protected MessageDigest msgDigestBuffor;

    protected MessageDigest digestingInstanceInit(){
        try {
            msgDigestBuffor = MessageDigest.getInstance("SHA1");
            return msgDigestBuffor;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


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

    public byte[] getDigestedMessage(){
        return digestedMessage;
    }

    public HMACGenerator(){
    }

    public HMACGenerator(String key, String message){
        this.key = key;
        this.message = message;

    }

    public void generateHMACode(){
        digestingInstanceInit();
        padAndHash();
        shortenMessageOverBlockLength(msgDigestBuffor);
        digestMessage(msgDigestBuffor, messageToDigest);
    }
    /*padAndHash() is firstly padding IPAD and OPAD in arrays of BLOCK_LENGTH length and then XOR the key with OPAD and IPAD*/
    private void padAndHash() {
        byte[] keyInBytes = key.getBytes();
        for (int i = 0; i < BLOCK_LENGTH; i++) {
            if (i < keyInBytes.length) {
                keyIpad[i] = keyInBytes[i];
                keyOpad[i] = keyInBytes[i];
            } else {
                keyIpad[i] = 0;
                keyOpad[i] = 0;
            }

            keyIpad[i] ^= IPAD;
            keyOpad[i] ^= OPAD;
        }
    }

    private byte[] shortenMessageOverBlockLength(MessageDigest md) {
        byte[] messageArray = message.getBytes();
        if (messageArray.length > BLOCK_LENGTH) {
                md.reset();
                md.update(messageArray);
                messageToDigest = md.digest();
                System.out.println("The message was preliminarily digested as the message was longer than assumed blocked length.");

                return messageToDigest;
        }
        else {
        messageToDigest = messageArray;

        return messageToDigest;
        }
    }

        private byte[] digestMessage(MessageDigest md, byte[] messageToDigest){
            md.reset();
            md.update(keyIpad);
            md.update(messageToDigest);
            byte[] innerDigest = md.digest();

            md.reset();

            md.update(keyOpad);
            md.update(innerDigest);
            digestedMessage = md.digest();

            return digestedMessage;
    }
}
