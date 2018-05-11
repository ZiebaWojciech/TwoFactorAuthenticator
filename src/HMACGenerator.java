
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMACGenerator {
    //BLOCK_LENGTH is a length of block used to hash the message. According to standards it shall be not shorter than 64 bytes.
    //IPAD is an inner padding string. It may be any constant string of length not longer than stated block length.
    //OPAD is an outer padding string. It may be any constant string of length not longer than stated block length.
    //If IPAD or OPAD is shorter than block length it must be repeated until whole block is filled.

    //BLOCK_LENGTH, IPAD and OPAD at his stage will be constant and not to be changed.
    private static final byte BLOCK_LENGTH = 64; //64 is a recommended length of block/
    // OPAD and IPAD are fixed values, recommended as below (as hamming value is 4 and it is considered optimal)
    private static final byte IPAD = 0x36;
    private static final byte OPAD = 0x5c;


    protected String key ; //TODO make sure that key is always assigned OR put nullpointer exeption over subsequent code
    protected String message;
//    protected MessageDigest msgDigestBuffer;
//
//    protected byte[] messageToDigest;
//    protected byte[] digestedMessage;


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

//    public byte[] getDigestedMessage(){
//        return digestedMessage;
//    }

    public HMACGenerator(){//TODO only no-args and set/get to set values?
    }

    public HMACGenerator(String key, String message){
        this.key = key;
        this.message = message;

    }

    public byte[] generateHMACode(){
//        initiateDigestingInstance();
//        padAndHashIPAD();
//        padAndHashOPAD();
//        shortenMessageLongerThanBlockLength(initiateDigestingInstance());
          byte[] result = digestMessage(padAndHashIPAD(), padAndHashOPAD(), initiateDigestingInstance(), shortenMessageLongerThanBlockLength(initiateDigestingInstance()));
          return result;
    }

    /*padAndHashIPAD() is firstly padding IPAD in arrays of BLOCK_LENGTH length and then XOR the key with IPAD*/
    private byte[] padAndHashIPAD() {
        byte[] keyIPAD = new byte[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            if (i < key.getBytes().length) {
                keyIPAD[i] = key.getBytes()[i];
            } else {
                keyIPAD[i] = 0;
            }
            keyIPAD[i] ^= IPAD;
        }
        return keyIPAD;
    }

    /*padAndHashOPAD() is firstly padding OPAD in array of BLOCK_LENGTH length and then XOR the key with OPAD*/
    private byte[] padAndHashOPAD() {
        byte[] keyOPAD = new byte[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            if (i < key.getBytes().length) {
                keyOPAD[i] = key.getBytes()[i];
            } else {
                keyOPAD[i] = 0;
            }
            keyOPAD[i] ^= OPAD;
        }
        return keyOPAD;
    }


    private MessageDigest initiateDigestingInstance(){
        try {
            MessageDigest msgDigestBuffer = MessageDigest.getInstance("SHA1");
            return msgDigestBuffer;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] shortenMessageLongerThanBlockLength(MessageDigest msgDigestBuffer) {
        byte [] messageToDigest;
        if (message.getBytes().length > BLOCK_LENGTH) {
                msgDigestBuffer.reset();
                msgDigestBuffer.update(message.getBytes());
                messageToDigest = msgDigestBuffer.digest();
                System.out.println("The message was preliminarily digested as the message was longer than assumed blocked length.");
        }
        else {
        messageToDigest = message.getBytes();
        }
        return messageToDigest;
    }

        private byte[] digestMessage(byte[] keyIPAD, byte[] keyOPAD, MessageDigest msgDigestBuffer, byte[] messageToDigest){
            msgDigestBuffer.reset();
            msgDigestBuffer.update(keyIPAD);
            msgDigestBuffer.update(messageToDigest);
            byte[] digestedMessage = msgDigestBuffer.digest();

            msgDigestBuffer.reset();

            msgDigestBuffer.update(keyOPAD);
            msgDigestBuffer.update(digestedMessage);
            digestedMessage = msgDigestBuffer.digest();

            return digestedMessage;
    }
}
