package web.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.impl.BusinessServiceImpl;
@WebServlet("/client/UpdateInfoServletUI")
public class UpdateInfoServletUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		BusinessServiceImpl service = new BusinessServiceImpl();
		User user = service.findUser(userid);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/client/updateinfo.jsp").forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
