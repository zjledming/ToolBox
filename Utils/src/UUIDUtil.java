

import java.util.UUID;

public class UUIDUtil {
	 /** 
     * 获得�?��UUID 
     * @return String UUID 
     */ 
    public static String createUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉�?”符�?
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
}
