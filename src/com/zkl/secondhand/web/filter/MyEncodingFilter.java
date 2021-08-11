package com.zkl.secondhand.web.filter;


import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

//���������������
@WebFilter("/*")
public class MyEncodingFilter implements Filter{
	//����OverrideûӰ��
	public void destroy() {}
	public void init(FilterConfig config) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 解决响应乱码
		HttpServletResponse mResponse=(HttpServletResponse) response;
		mResponse.setHeader("contene_type", "text/html;charset=utf-8");
		//1.����ת��
		HttpServletRequest hsr = (HttpServletRequest) request;
		
		//2.����request
		MyRequest myreuqest = new MyRequest(hsr);
		
		// ������Ӧ����
		response.setContentType("text/html;charset=utf-8");
		
		//3.����
		chain.doFilter(myreuqest, response);
	}
}

class MyRequest extends HttpServletRequestWrapper{

	HttpServletRequest request;
	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String[] values = getParameterMap().get(name);
		if(values != null){
			return values[0];
		}
		return null;
	}
	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		return getParameterValues(name);
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		
		String method = request.getMethod();
		if(method.equalsIgnoreCase("post")){//post����
			try {
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equalsIgnoreCase("get")){//�����tomcat8�汾���ϵľͲ���д�����
			// TODO Auto-generated method stub
			Map<String, String[]>  map =  request.getParameterMap();
			//������Ȼ������UTF-8
			/*for(Entry<String, String[]> entry : map.entrySet()){
				String[] values = entry.getValue();
				for(int i=0;i<values.length;i++){
					try {
						values[i] = new String(values[i].getBytes("ISO-8859-1"),"utf-8");
						System.out.println(values[i]);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
			
			
			return map;
		}
		
		return super.getParameterMap();
		
	}
}