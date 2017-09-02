package web.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.impl.BusinessServiceImpl;
@WebServlet("/client/UpdateInfoServlet")
public class UpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try{
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String cellphone = request.getParameter("cellphone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			BusinessServiceImpl service = new BusinessServiceImpl();
			User user = service.findUser(id);
			user.setAddress(address);
			user.setCellphone(cellphone);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhone(phone);
			user.setUsername(username);
			user.setId(id);
			service.updateUser(user);

			request.setAttribute("message", "修改成功");
			request.getRequestDispatcher("/message.jsp").forward(request, response);//����Ҫ��ת����ҳ��������ʾ��ӭ�����������޸�

		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "注册失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
