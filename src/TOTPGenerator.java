import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;

public class TOTPGenerator extends HMACGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).
    private long timeCounter;

    public TOTPGenerator(String key){
        this.key = key;
    }

    public byte getOffset() {
        return offset;
    }

    public byte[] creatingTimeCounter(){
        timeCounter = (Instant.now().getEpochSecond())/30L;
        ByteBuffer keyArray = ByteBuffer.allocate(8);
        keyArray.clear();
        keyArray.putLong(timeCounter);

        messageToDigest = keyArray.array();
        return messageToDigest;
    }
    //dynamicTruncation firstly get the low-order 4 bits out of the digestedMessage[length -1] byte.
    byte offset;
    public byte dynamicTruncation(){
       byte offsetByte = digestedMessage[digestedMessage.length-1];
       //Loop for extracting the 4 low-order bites.
        byte offsetBites = 0;
        for(byte i = 1; i<=8; i *= 2){
            offsetBites += offsetByte & i;
        }
        System.out.println("4 bits: " + offsetBites);

       return offset;
    }








}
