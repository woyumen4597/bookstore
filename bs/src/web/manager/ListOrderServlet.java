package web.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Order;
import service.impl.BusinessServiceImpl;
@WebServlet("/manager/ListOrderServlet")
public class ListOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Order> orders = null;
		request.setCharacterEncoding("UTF-8");
		String state = request.getParameter("state");
		BusinessServiceImpl service = new BusinessServiceImpl();
		if(state==null){
			orders = service.listOrder();
		}else{
			orders = service.listOrder(state);//这里需要获得该用户所有订单消息，不用只看未发货的(state==false)，在后台会区分未发货和已发货，在前台要罗列在一起
		}
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/manager/listorder.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
