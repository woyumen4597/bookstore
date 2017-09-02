package web.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;

import utils.FtpUtil;
import utils.IDUtils;
import utils.JsonUtils;
import utils.PictureResult;

@WebServlet("/pic/upload")
public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (!item.isFormField()) {
					String filename = item.getName();
					String ext = filename.substring(filename.lastIndexOf("."));
					InputStream in = item.getInputStream();
					String imageName = IDUtils.genImageName();
					// 把图片上传到ftp服务器（图片服务器）
					// 需要把ftp的参数配置到配置文件中
					// 文件在服务器的存放路径，应该使用日期分隔的目录结构
					DateTime dateTime = new DateTime();
					String filePath = dateTime.toString("/yyyy/MM/dd");
					FtpUtil.uploadFile(filePath, imageName+ext, in);
					item.delete();
					response.setContentType("application/json;charset=UTF-8");
					String imageUrl = FtpUtil.IMAGE_BASE_URL + filePath + "/" + imageName + ext;
					response.getWriter().print(JsonUtils.objectToJson(PictureResult.ok(imageUrl)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
