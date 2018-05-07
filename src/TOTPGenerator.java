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

    public int getOffset() {
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
    int offset;
    public void dynamicTruncation() {
        //TODO assure that the offset will be between 0 and length -4
        offset = digestedMessage[digestedMessage.length - 1] & 15;
        System.out.println("Offset: " + offset);

        int binary = (digestedMessage[offset] & 0x7f) << 24 //in binary its 0111 111, so we purposely drop the most significant bit MSB to be sure the result will be un-signed
                | (digestedMessage[offset + 1] & 0xff) << 16
                | (digestedMessage[offset + 2] & 0xff) << 8
                | (digestedMessage[offset + 3] & 0xff);
//        int binary = (digestedMessage[offset] & 0xff ) << 8;

        for (int i = 0; i < 4; i++) {
            System.out.println("binary  " + Integer.toBinaryString(digestedMessage[offset+i]) + " int " + digestedMessage[offset+i] + " hex " + Integer.toHexString(digestedMessage[offset+i]));
        }
        System.out.println("Code after offset - binary " + Integer.toBinaryString(binary) + " int " + binary + " hex " + Integer.toHexString((binary)));
    }








}
