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

//���� Ŭ���� 
@SuppressWarnings("serial")
public class SignGuestbookServlet extends HttpServlet {
	
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

    //post�� �Ѱܹ��� �Ķ���͸� ó���� doPost �޼ҵ��Դϴ�. 
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //�ۼ����� ������ ��������.
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        
        //�Ķ���ͷ� ���۹���  ���� �̸��� �����մϴ�. 
        String guestbookName = req.getParameter("guestbookName");
        //entity�� �־��� Ű�� �����ϳ׿�.
        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);

        String content = req.getParameter("content");
        Date date = new Date();
        
        //Greeting �̶�� �̸��� ������ ������ Ű�� entity�� �����ϰ� �����մϴ�.
        Entity greeting = new Entity("Greeting", guestbookKey);
        
        //entity�� �۾���, �ð�, ������ ����ϴ�.
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);

        //���� datastore�� �ڷḦ �����ϴ� �κ��Դϴ�. ��ü�� �����ϰ�
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // ������ ���� entity�� greeting�� �����ϳ׿�.
        datastore.put(greeting);

        //guestbook.jsp�� �����̷�Ʈ ��ŵ�ϴ�. �̷��� jsp���� ����������.
        resp.sendRedirect("/guestBook.jsp?guestbookName=" + guestbookName);
    }
}