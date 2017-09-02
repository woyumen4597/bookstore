package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import domain.Order;
@WebServlet("/client/ClientListOrderServlet")
public class ClientListOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessServiceImpl service = new BusinessServiceImpl();
		String userid = request.getParameter("userid");
		String state = request.getParameter("state");
		List<Order> orders;
		if(state==null){
			orders = service.clientListOrder(userid);
		}else{
			orders = service.listOrder(state, userid);
		}
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/client/clientlistorder.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
