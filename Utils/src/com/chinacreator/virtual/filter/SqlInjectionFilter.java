package com.chinacreator.virtual.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SqlInjectionFilter implements Filter {
	private static List<String> regList = new ArrayList();

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Map paramsMap = request.getParameterMap();

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (checkSqlInjetionAndCss(paramsMap)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(httpRequest.getRequestURL().toString());
			System.out.println("===========处理后的url："
					+ httpRequest.getRequestURL().toString());
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		buildRegList();
	}

	public boolean checkSqlInjetionAndCss(Map<String, String[]> paramsMap) {
		Iterator iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			String value2 = Arrays.toString(value);
			if (checkValue(value2)) {
				return true;
			}
		}

		return false;
	}

	public boolean checkValue(String value) {
		value = value.replaceAll("\\s*", "");
		for (int i = 0; i < regList.size(); i++) {
			Pattern p = Pattern.compile((String) regList.get(i));
			Matcher m = p.matcher(value.toLowerCase());
			if (m.find()) {
				System.out.println("===========以下是非法字符===========");
				System.out.println(m.group());
				System.out.println("===========以上是非法字符===========");
				return true;
			}
		}
		return false;
	}

	public void buildRegList() {
		regList = new ArrayList();

		String reg1 = "(\\%27)|(\\')|(\\-\\-)|(\\%23)|(#)";
		regList.add(reg1);
		String reg2 = "\\w*((\\%27)|(\\'))(\\s)*((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))";
		regList.add(reg2);

		String reg3 = "((\\%27)|(\\'))(\\s)*union|((\\%27)|(\\'))(\\s)*select|((\\%27)|(\\'))(\\s)*insert|((\\%27)|(\\'))(\\s)*update|((\\%27)|(\\'))(\\s)*delete|((\\%27)|(\\'))(\\s)*drop";
		regList.add(reg3);

		String reg4 = "((\\%3D)|(=))[^\\n]*((\\%27)|(\\')|(\\-\\-)|(\\%3B)|(;))";
		regList.add(reg4);

		String reg5 = "(\\%3C|<)(([^\\%2F]|[^/]).*)(\\%3E|>).*(\\%3C|<)(\\%2F|/)\\2(\\%3E|>)";
		regList.add(reg5);

		String reg6 = "((\\%3C)|<((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\\n]+((\\%3E)|>))";
		regList.add(reg6);
	}

	public String codeToString(String str) {
		String strString = str;
		try {
			byte[] tempB = strString.getBytes("UTF-8");
			return new String(tempB);
		} catch (Exception e) {
		}
		return strString;
	}

	public String getBackUrl(HttpServletRequest request) throws Exception {
		String strBackUrl = "";
		try {
			strBackUrl = "http://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath()
					+ request.getServletPath() + "?"
					+ codeToString(request.getQueryString());
			strBackUrl = URLEncoder.encode(strBackUrl, "gbk");
		} catch (Exception e) {
			throw e;
		}
		return strBackUrl;
	}

	public static void main(String[] args) {
		SqlInjectionFilter s = new SqlInjectionFilter();
		s.buildRegList();

		System.out
				.println(s
						.checkValue("%B7%A2%B8%C4%CE%AF, frame_inj: */-->');></iframe></script></style></title></textarea><iframe src=http://www.dbappsecurity.com.cn>"));
	}
}