//import java.time.Instant;
//
//public class TOTPGenerator extends HMACGenerator {
//    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
//    //In here a C is a unix time divided by 30 seconds (recommended time window).
//    private long timeCounter;
//    private int[] DIVISOR =
//            //for   {0,  1,     2,      3,      4,      5,      6,          7,          8} the divisor in modulo function will be:
//                    {1, 10,     100,    1000,   10000,  100000, 1000000,    10000000,   100000000};
//    private int digitNumber;
//    int TOTPCode;
//
//    public TOTPGenerator(){}
//
//    public TOTPGenerator(String key){
//        this.key = key;
//    }
//
//    public TOTPGenerator(String key, int digitNumber){
//        this.key = key;
//        this.digitNumber = digitNumber;
//    }
//
////  @override
//
//    public int getTOTPCode(){
//        return TOTPCode;
//    }
//
//    public void generateTOTPCode(){
//        setMessage();
//        generateHMACode();
//        dynamicTruncation();
//    }
//
//    //  In TOTP a message is a time counter only
//    private void setMessage(){
//        //The time counter is Epoch divided by time-step (in this case 30 seconds)
//        timeCounter = (Instant.now().getEpochSecond())/30L;
//        this.message = Long.toString(timeCounter);
//    }
//
//    //dynamicTruncation firstly get the low-order 4 bits out of the digestedMessage[length -1] byte.
//    private int dynamicTruncation() {
//        int offset;
//        int truncatedMessage = 0;
//        offset = digestedMessage[digestedMessage.length - 1] & 15;
//        //in binary 0x7f is 0111 111, so we purposely drop the most significant bit (MSB) to be sure the result will be un-signed
//        if(offset>=0 && offset<=(digestedMessage.length-4)) {
//            truncatedMessage = (digestedMessage[offset] & 0x7f) << 24
//                    | (digestedMessage[offset + 1] & 0xff) << 16
//                    | (digestedMessage[offset + 2] & 0xff) << 8
//                    | (digestedMessage[offset + 3] & 0xff);
//        }
//
//        TOTPCode = truncatedMessage % DIVISOR[digitNumber];
//
//        return TOTPCode;
//    }
//
//
//
//
//
//
//
//
//}
