package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.SearchResult;
import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;

@WebServlet("/client/QueryServlet")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		String pageNum = request.getParameter("pagenum");
		BusinessService service = new BusinessServiceImpl();
		try {
			if(pageNum==null){
				pageNum = new String("1");
			}
			SearchResult searchResult = service.search(keyword, Integer.parseInt(pageNum), 3);
			request.setAttribute("result", searchResult);
			request.getRequestDispatcher("/client/search.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
