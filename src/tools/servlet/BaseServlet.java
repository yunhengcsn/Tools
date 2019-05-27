package tools.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BaseServlet:帮助子类调用方法及转发重定向
 *
 * @author csn
 * */
public class BaseServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//处理乱码
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		//获取参数
		//识别用户想请求的方法
		String methodName = req.getParameter("method");
		
		if(methodName == null || methodName.trim().isEmpty()) {
			throw new RuntimeException("请传递method参数！");
		}
		//使用反射进行调用
		Class c = this.getClass();
		Method m ;
		try {
			m = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("所调用的方法不存在!");
		}
		//得到调用方法返回的字符串
		//完成转发或重定向
		try {
			String res = (String)m.invoke(this,req,resp);
			if(res == null || res.trim().isEmpty()) {
				return;
			}
			//res中没有":"则表示转发
			//res中有":"则冒号前为f表示转发，r表示重定向，冒号后为路径
			if(!res.contains(":")) {
				req.getRequestDispatcher(res).forward(req, resp);
			}
			else {
				String[] ss = res.split(":");
				String option = ss[0];
				String path = ss[1];
				if(option.equalsIgnoreCase("f")) {//转发
					req.getRequestDispatcher(path).forward(req, resp);
				} else if(option.equalsIgnoreCase("r")) {//重定向
					resp.sendRedirect(req.getServletPath() + path);
				} else {//其他
					throw new RuntimeException("不支持指定操作！");
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("调用方法时出现异常!");
		}
	}
	
}
