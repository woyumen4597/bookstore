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
/**
 * @author jrc
 * 实现排序
 */
@WebServlet("/client/SortServlet")
public class SortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		String category_id = request.getParameter("category_id");
		String method = request.getParameter("method"); //升序，降序
		BusinessService service = new BusinessServiceImpl();
		String pageNum = request.getParameter("pagenum");
		try {
			if(pageNum==null){
				pageNum = new String("1");
			}
			SearchResult result = service.sort(key, category_id,method,Integer.parseInt(pageNum), 3);
			request.setAttribute("result", result);
			request.getRequestDispatcher("/client/sort.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "排序失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
