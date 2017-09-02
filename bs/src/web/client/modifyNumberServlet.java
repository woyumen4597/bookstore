package web.client;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cart;
import domain.CartItem;
@WebServlet("/client/modifyNumberServlet")
public class modifyNumberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if(method==null){
			String number = request.getParameter("number");
			String bookid = request.getParameter("bookid");
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			Map<String,CartItem> map = cart.getMap();
			CartItem citem = map.get(bookid);
			citem.setQuantity(Integer.parseInt(number));
			request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);
		}else{
			delete(request,response);
		}
	}
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		String bookid = request.getParameter("bookid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Map<String,CartItem> map = cart.getMap();
		map.remove(bookid);
		if(map.isEmpty()){
			cart = null;
		}
		request.getSession().setAttribute("cart", cart);
		request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
