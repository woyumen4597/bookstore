package web.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;

import domain.Book;
import domain.BookDetail;
import domain.Category;
import domain.Page;
import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;
import utils.FtpUtil;
import utils.IDUtils;
@WebServlet("/manager/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if (method.equalsIgnoreCase("addUI")) {
			addUI(request, response);
		}
		if (method.equalsIgnoreCase("add")) {
			add(request, response);
		}
		if(method.equalsIgnoreCase("list")){
			list(request, response);
		}
		if(method.equalsIgnoreCase("modify")){
			modify(request, response);
		}
		if(method.equalsIgnoreCase("modifyUI")){
			modifyUI(request, response);
		}
	}


	private void modifyUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String bookId = request.getParameter("bookid");
		BusinessServiceImpl service = new BusinessServiceImpl();
		Book book = service.findBook(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/manager/modify.jsp").forward(request,response);

	}


	private void modify(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		try {
			String stock = request.getParameter("stock");
			String bookid = request.getParameter("bookid");
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.modifyBook(bookid,stock);
			request.setAttribute("message", "修改库存成功");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "修改库存失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}


	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pagenum = request.getParameter("pagenum");
		BusinessService service = new BusinessServiceImpl();
		Page page = service.getBookPageData(pagenum);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manager/listbook.jsp").forward(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			Book book = doupLoad(request);
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.addBook(book);
			request.setAttribute("message", "添加成功");
			//添加solr逻辑
			try {
				service.importBook(book.getId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "添加失败");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private Book doupLoad(HttpServletRequest request) {
		//把上传的图片保存到images目录中，并把request中的请求参数封装到Book中
		Book book = new Book();
		BookDetail detail = new BookDetail();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					if(item.getFieldName().equals("desc")||item.getFieldName().equals("publisher")||
							item.getFieldName().equals("publish_time")){
						BeanUtils.setProperty(detail, item.getFieldName(), item.getString("UTF-8"));
					}
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					BeanUtils.setProperty(book, name, value);
				}else{
					String filename = item.getName();
					String ext = filename.substring(filename.lastIndexOf("."));
					InputStream in = item.getInputStream();
					String imageName = IDUtils.genImageName();
					//把图片上传到ftp服务器（图片服务器）
					//需要把ftp的参数配置到配置文件中
					//文件在服务器的存放路径，应该使用日期分隔的目录结构
					DateTime dateTime = new DateTime();
					String filePath = dateTime.toString("/yyyy/MM/dd");
					Properties props = getProps();
					String IMAGE_BASE_URL = props.getProperty("IMAGE_BASE_URL");
					try {
						FtpUtil.uploadFile(filePath, imageName + ext, in);
					} catch (Exception e) {
						e.printStackTrace();
					}
					item.delete();
					book.setImage(IMAGE_BASE_URL + filePath + "/" + imageName + ext);
					System.out.println(book.getImage());
				}
			}
			detail.setId(book.getId());
			book.setDetail(detail);
			return book;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String makeFileName(String filename){
		String ext = filename.substring(filename.lastIndexOf(".") + 1);//lastIndexOf("\\.")这样写不行
		return UUID.randomUUID().toString() + "." + ext;
	}

	private void addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BusinessServiceImpl service = new BusinessServiceImpl();
		List<Category> category = service.getAllCategory();
		request.setAttribute("categories", category);
		request.getRequestDispatcher("/manager/addBook.jsp").forward(request,
				response);
	}

	//获得配置
	public Properties getProps(){
		Properties prop = new Properties();
		InputStream inputStream = BookServlet.class.getClassLoader().getResourceAsStream("resource.properties");
		try {
			prop.load(inputStream);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
