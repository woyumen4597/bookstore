package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Page;
import service.impl.BusinessServiceImpl;
/**
 * 优化之前的搜索服务，对数据库压力较大，且查询方式限制
 * @author jrc
 * @see web.client.QueryServlet
 */
@Deprecated
@WebServlet("/client/BookSearchServlet")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		BusinessServiceImpl service = new BusinessServiceImpl();
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum = request.getParameter("pagenum");
		System.out.println(pagenum);
		Page page = service.getSearchPageData(pagenum,keyword);
		if(page.getList().isEmpty()){
			request.setAttribute("message", "没有找到相关书籍!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}else{
			request.setAttribute("page", page);
			request.getRequestDispatcher("/client/body.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
