

import java.util.UUID;

public class UUIDUtil {
	 /** 
     * è·å¾—ä¸?¸ªUUID 
     * @return String UUID 
     */ 
    public static String createUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //å»æ‰â€?â€ç¬¦å?
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
}
