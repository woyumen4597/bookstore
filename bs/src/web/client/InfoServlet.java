package web.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Book;
import domain.BookDetail;
import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;

@WebServlet("/client/InfoServlet")
public class InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String book_id = request.getParameter("bookid");
		BusinessService service = new BusinessServiceImpl();
		Book book = service.findBook(book_id);
		BookDetail detail = service.findBookInfo(book_id);
		request.setAttribute("book", book);
		request.setAttribute("detail", detail);
		request.getRequestDispatcher("/client/showInfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
