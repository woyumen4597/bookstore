package web.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Book;
import domain.Comment;
import domain.User;
import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;
import utils.IDUtils;

@WebServlet("/client/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService service = new BusinessServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		String book_id = request.getParameter("bookid");
		Book book = service.findBook(book_id);
		request.setAttribute("book", book);
		if(method.equalsIgnoreCase("getAll")){
			getAllComments(request,response,book);
		}else if(method.equalsIgnoreCase("comment")){
			Comment(request,response,book);
		}else if(method.equalsIgnoreCase("addComment")){
			addComment(request,response,book);
		}

	}

	private void addComment(HttpServletRequest request, HttpServletResponse response,Book book) {
		try {
			String commentString = request.getParameter("comment");
			String username = request.getParameter("username");
			Comment comment = new Comment();
			comment.setBook_id(book.getId());
			comment.setUser_name(username);
			comment.setComment(commentString);
			comment.setId(IDUtils.genItemId()+"");
			comment.setCreate_time(new Date());
			service.addComment(comment);
			request.setAttribute("message","评论成功!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "评论失败");
			try {
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private void Comment(HttpServletRequest request, HttpServletResponse response,Book book) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user == null){
				request.setAttribute("message", "对不起，请先登录");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			request.setAttribute("book", book);
			request.getRequestDispatcher("/client/comment.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void getAllComments(HttpServletRequest request, HttpServletResponse response,Book book) {
		List<Comment> comments = service.getCommentsById(book.getId());
		request.setAttribute("book", book);
		request.setAttribute("comments", comments);
		try {
			request.getRequestDispatcher("/client/showComments.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
