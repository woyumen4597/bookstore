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
@WebServlet("/client/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("getAll")){
			getAll(request, response);
		}else if(method.equalsIgnoreCase("listBookWithCategory")){
			listBookWithCategory(request, response);
		}else if(method.equalsIgnoreCase("sort")){
			sort(request,response);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @deprecated
	 * @see web.client.SortServlet
	 */
	@Deprecated
	private void sort(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		BusinessServiceImpl service = new BusinessServiceImpl();
		Page page = null;
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum = request.getParameter("pagenum");
		String category_id = request.getParameter("category_id");
		if(category_id==null||category_id.equals("")){//不存在分类
			page = service.getSortedBookPageData(pagenum);
		}else{
			page = service.getSortedBookPageData(pagenum,category_id);
		}
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/body.jsp").forward(request, response);
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessServiceImpl service = new BusinessServiceImpl();
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		String pagenum = request.getParameter("pagenum");
		Page page = service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/body.jsp").forward(request, response);
	}

	public void listBookWithCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BusinessServiceImpl service = new BusinessServiceImpl();
		String category_id = request.getParameter("category_id");
		List<Category> categories = service.getAllCategory();
		request.setAttribute("categories", categories);
		request.setAttribute("category_id", category_id);
		String pagenum = request.getParameter("pagenum");
		Page page = service.getBookPageData(pagenum, category_id);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/body.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
