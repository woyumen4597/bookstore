package web.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;

/**
 *
 * @author jrc
 * solr服务
 */
@WebServlet("/manager/SolrServlet")
public class SolrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			BusinessService service = new BusinessServiceImpl();
			service.importAllBooks();
			request.setAttribute("message", "导入成功");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "导入失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
