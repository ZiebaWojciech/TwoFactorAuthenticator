import java.time.Instant;

public class TOTPGenerator extends HMACGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).
    private long timeCounter;
    private int digitNumber = 6;

    public TOTPGenerator(){}

    public TOTPGenerator(String key){
        this.key = key;
    }

//   @override
    public void setMessage(){
        timeCounter = (Instant.now().getEpochSecond())/30L;
        message = Long.toString(timeCounter);
    }

    //dynamicTruncation firstly get the low-order 4 bits out of the digestedMessage[length -1] byte.
    public int dynamicTruncation() {
        int offset;
        int truncatedMessage = 0;
        offset = digestedMessage[digestedMessage.length - 1] & 15;
        //in binary 0x7f is 0111 111, so we purposely drop the most significant bit MSB to be sure the result will be un-signed
        if(offset>=0 && offset<=(digestedMessage.length-4)) {
            truncatedMessage = (digestedMessage[offset] & 0x7f) << 24
                    | (digestedMessage[offset + 1] & 0xff) << 16
                    | (digestedMessage[offset + 2] & 0xff) << 8
                    | (digestedMessage[offset + 3] & 0xff);
        }

        int OTPassword = truncatedMessage % 1000000;
        System.out.println("n-digit OTP:" + OTPassword);
        return OTPassword;
    }








}
