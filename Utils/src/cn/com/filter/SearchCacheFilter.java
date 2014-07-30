package cn.com.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title:��ѯ��������������
 * </p>
 * <p>
 * Description:ͨ����ѯ�б������ϸҳ�����ϸҳ�淵��ʱ���践�ص��м�¼��ѯ��������ҳ�Ĳ�ѯ�б�
 */
public class SearchCacheFilter implements Filter {
	@Override
	public void destroy() {

	}

	/**
	 * @throws IOException
	 *             , ServletException
	 * @Description ��¼��ѯ��������ҳ�Լ�����ҳ����ת
	 * @return ����ApplicantBean����
	 * @exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// request.setCharacterEncoding("gbk");
		Map<String, String[]> paramsMap = request.getParameterMap();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (paramsMap.get("searchCache") != null) {
			String params = (String) httpRequest.getSession().getAttribute(
					"searchCache");
			if (params == null || "null".equals(params)) {
				params = "";
			}
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(httpRequest.getRequestURL() + params);
		} else {
			String params = changeMapToString(paramsMap);
			httpRequest.getSession().setAttribute("searchCache", params);
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * @description ƴ�Ӳ���
	 * @param paramsMap
	 *            ͨ��request��ȡ����map
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	private String changeMapToString(Map<String, String[]> paramsMap)
			throws UnsupportedEncodingException {
		StringBuffer url = new StringBuffer();
		url.append("?");
		for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			url.append(key + "=" + encodeUrl(key, Arrays.toString(value)));
			url.append("&");
		}
		return url.toString();
	}

	/**
	 * @description �ַ�ת��
	 * @param value
	 *            ��Ҫת����ַ�
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	private String encodeUrl(String key, String value)
			throws UnsupportedEncodingException {
		String result = "";
		result = value.replaceAll("\\[", "").replaceAll("\\]", "");
		// result=java.net.URLDecoder.decode(result,"gbk");
		// result=new String(result.getBytes("ISO-8859-1"));
		return result;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
