import javax.xml.bind.DatatypeConverter;
import java.time.Instant;

public class TOTPGenerator extends HMACGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).
    private static final int[] DIVISOR =
            //for   {0,  1,     2,      3,      4,      5,      6,          7,          8} the divisor in modulo function will be:
                    {1, 10,     100,    1000,   10000,  100000, 1000000,    10000000,   100000000};
    private int digitNumber;

    public void setDigitNumber(int digitNumber){
        this.digitNumber = digitNumber;
    }


    public int generateTOTPCode(String key){
        return dynamicTruncation(generateHMACode(key, createTimeCounter()));
    }

    //  In TOTP a time counter act as a message in regular HMAC algorithm
    private String createTimeCounter(){
        //The time counter is Unix Epoch divided by time-step (in this case - 30 seconds) and act as a MESSAGE for HMAC generating algorithm
        String timeCounter = Long.toString((Instant.now().getEpochSecond())/30L);
        return timeCounter;
    }

    //dynamicTruncation firstly get the low-order 4 bits out of the digestedMessage[length -1] byte, which is an offset value.
    //a truncated message is a four byte out of the original message [offset, offset+3].
    private int dynamicTruncation(byte[] HMACodeForTimeCounter) {
        int truncatedMessage = 0;
        int offset = HMACodeForTimeCounter[HMACodeForTimeCounter.length - 1] & 15;
        //in binary 0x7f is 0111 111, so we purposely drop the most significant bit (MSB) to be sure the result will be un-signed
        if(offset>=0 && offset<=(HMACodeForTimeCounter.length-4)) {
            truncatedMessage =    (HMACodeForTimeCounter[offset] & 0x7f) << 24
                                | (HMACodeForTimeCounter[offset + 1] & 0xff) << 16
                                | (HMACodeForTimeCounter[offset + 2] & 0xff) << 8
                                | (HMACodeForTimeCounter[offset + 3] & 0xff);
        }
        return truncatedMessage % DIVISOR[digitNumber];
    }








}
