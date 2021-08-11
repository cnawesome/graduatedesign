package com.zkl.secondhand.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zkl.secondhand.Exception.UserException;
import com.zkl.secondhand.model.User;
import com.zkl.secondhand.service.UserService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//检验验证码
		//获取验证码
		String check_client=request.getParameter("checkcode");
		String check_session=(String) request.getSession().getAttribute("checkcode_session");
		if(!check_client.equalsIgnoreCase(check_session)) {
			//客户端提交的验证与服务器的不一样
			request.setAttribute("checkcode_err","验证码不一致");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
			
		}
		
		//1.�Ѳ���ת��Ϊbean
		User user =new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			System.out.println(user);
			
			//�������ݵ����Ը�ֵ
			user.setActiveCode(UUID.randomUUID().toString());
			user.setRole("普通用户");
			user.setRegistTime(new Date());
			System.out.println(user);
			//2.ע��
			UserService us=new UserService();
			us.regiser(user);
			
			//3.���ؽ��
			//3.1�ɹ���ײ��ע��ɹ�ҳ��
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
			
		}catch(UserException e) {
			e.printStackTrace();
			//3.2ʧ�ܣ��ص�ע��ҳ��
			request.setAttribute("register_err", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("model swiched fail!");
			e.printStackTrace();
		} 
		
	}

}
