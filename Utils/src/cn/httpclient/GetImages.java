package cn.httpclient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetImages {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetImages getImages = new GetImages();
		// getImages.getImage();
	}

	public static void getImage(String filePath, String fileName, String url_)
			throws MalformedURLException {
		URL url;
		FileOutputStream outfile = null;
		InputStream infile = null;
		URLConnection con = null;
		byte[] buffer = null;

		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.mkdirs();
			}
			url = new URL(url_);
			outfile = new FileOutputStream(
					filePath + File.separator + fileName, true);
			con = url.openConnection();
			infile = con.getInputStream();
			buffer = new byte[1024];
			while (infile.read(buffer, 0, 1024) != -1) {
				outfile.write(buffer);
			}
			// infile.read(buffer, 0, fileLenght);
			// outfile.write(buffer, 0, fileLenght);
			// System.out.println("image save ok");
			infile.close();
			outfile.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}