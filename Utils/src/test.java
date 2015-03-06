
public class test {
	
	
	  private String md5decode(String str)
	    {
	        MD5 md5 = new MD5();
	        return md5.decrypt(str, "jcms2008");
	    }
}
