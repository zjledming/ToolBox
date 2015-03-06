import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class GXGH {

	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		
		String temp ="@GenFormField(htmlType=EnumGenHtmlType.htText,maxLength=2222,orderNumber=1)"+"\n"+
		"@GenGridField(orderNumber=1,dataFormat=\"text\")"+"\n"+
		"@GenQueryField(elementType=EnumGenHtmlType.htText,orderNumber=1,sqlOperator=EnumGenSqlOperator.like,visibleFlag=EnumGenVisible.visible)"+"\n"+
		"@GenPropInfo(propLabel=\"1111\")"+"\n"+
		"private java.lang.String 1111;"+"\n";
		
		
		String xml = 
		"<property name=\"1111\" type=\"java.lang.String\">"+"\n"+
			"<column name=\"3333\" length=\"2222\" not-null=\"false\">"+"\n"+
				"<comment></comment>"+"\n"+
			"</column>"+"\n"+
		"</property>"+"\n";
		
		
		String[] arr = {"gm","100","yl","10","dsrxb","10","szdw","100","lxdz","200","lxdh","10","yb","10","frzz","100","dz","200","frdb","100","zw","100","zxfr1","100","zxfr2","100","nd","10","yd","10","rd","10","fzgzjg","100","fzqm","20","fnd","10","fyd","10","frd","10","xzzfjg","100","xzqm","20","xnd","10","xyd","10","xrd","10"};
		
		for (int i = 0; i < arr.length; i=i+2) {
			//System.out.println(arr[i]+"||"+arr[i+1]);
			
			
			System.out.println(temp.replaceAll("1111", arr[i]).replaceAll("2222", arr[i+1]));
			
			//System.out.println(xml.replaceAll("1111", arr[i].toLowerCase()).replaceAll("3333", arr[i].toUpperCase()).replaceAll("2222", arr[i+1]));
			
		}

	}
	
	
	public static void main(String[] args) throws IOException {
		File input = new File("F:/workspace/tweb/WebRoot/jsp/app/gxgh/hdxzgl/xzzfajgl/zlgztz/addZlgztz.jsp");
		Document doc = Jsoup.parse(input, "UTF-8");
		
		String temp = "";
		
		Elements elements =  doc.select("input[type=text]");
		for (Element element : elements) {
			String name =element.attr("name");
			temp += "\""+name+"\",";
			//System.out.println(name);
		}
		
		Elements links = doc.getElementsByTag("textarea");
		for (Element link : links) {
			String name =link.attr("name");
			temp += "\""+name+"\",";
			//System.out.println(name);
		}
		
		links = doc.getElementsByTag("select");
		for (Element link : links) {
			String name =link.attr("name");
			temp += "\""+name+"\",";
			//System.out.println(name);
		}
		
		System.out.println(temp);
		
		
//		Element content =doc.getElementById("content");
//		Elements links = content.getElementsByTag("a");
//		for (Element link : links) {
//		String linkHref =link.attr("href");
//		String linkText =link.text();
//		System.out.println(linkHref);
//		}
	}

}
