import java.time.Instant;

public class TOTPGenerator {
    //Time-Based One-time Password Generator is using HMAC(K,C) method where K is a secret shared between parts and C is a time-based counter.
    //In here a C is a unix time divided by 30 seconds (recommended time window).

    long timeCounter = (Instant.now().getEpochSecond())/30;

    HMACGenerator TOTPCode = new HMACGenerator(timeCounter, "lol");


}
