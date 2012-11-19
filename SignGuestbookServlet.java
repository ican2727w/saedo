package guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//서블릿 클래스 
@SuppressWarnings("serial")
public class SignGuestbookServlet extends HttpServlet {
	
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

    //post로 넘겨받은 파라미터를 처리할 doPost 메소드입니다. 
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //작성자의 정보를 가져오고.
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        
        //파라미터로 전송받은  방명록 이름을 설정합니다. 
        String guestbookName = req.getParameter("guestbookName");
        //entity에 주어질 키를 생성하네요.
        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);

        String content = req.getParameter("content");
        Date date = new Date();
        
        //Greeting 이라는 이름과 위에서 생성한 키로 entity를 선언하고 생성합니다.
        Entity greeting = new Entity("Greeting", guestbookKey);
        
        //entity에 글쓴이, 시간, 내용을 담습니다.
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);

        //실제 datastore에 자료를 저장하는 부분입니다. 객체를 생성하고
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // 위에서 만든 entity인 greeting을 저장하네요.
        datastore.put(greeting);

        //guestbook.jsp로 리다이렉트 시킵니다. 이러면 jsp에서 보여지겠죠.
        resp.sendRedirect("/guestBook.jsp?guestbookName=" + guestbookName);
    }
}