package org.budapest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import scala.collection.immutable.List;
import org.budapest.model.Timeline;

public class MainHandler extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
		String tag = request.getParameter("tag");
		if (tag == null) tag = "budapest";
		Hello hello = new Hello(tag);
		Client client = new Client();
		String content = "";
		List<Timeline> list = client.getTimeline("http://cink.hu/ajax/tag/" + java.net.URLEncoder.encode(tag, "UTF-8"));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
		scala.collection.Iterator iter = list.iterator();
        response.getWriter().println("<h1>" + hello.say() + "</h1>");
		while (iter.hasNext()) {
			Timeline item = (Timeline)iter.next();
			response.getWriter().println("<p><a href=" + item.permalink() + ">" + item.headline() + "</a></p>");
		}
        
    }
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new MainHandler());
 
        server.start();
        server.join();
    }
}
