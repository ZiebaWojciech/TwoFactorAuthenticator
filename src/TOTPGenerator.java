import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;

public class TOTPGenerator extends HMACGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).

    public byte[] creatingTimeCounter(){
        long timeCounter = (Instant.now().getEpochSecond())/30L;
        ByteBuffer keyArray = ByteBuffer.allocate(8);
        keyArray.clear();
        keyArray.putLong(timeCounter);

        byte[] result = keyArray.array();
        return result;
    }





}
