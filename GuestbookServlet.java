package guestbook;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GuestbookServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8"); // 한글 사용시 
		resp.getWriter().println("Hello, world 안녕하세요? 반갑습니다.");
	}
}
