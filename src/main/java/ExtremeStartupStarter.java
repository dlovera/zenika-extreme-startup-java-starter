import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExtremeStartupStarter extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtremeStartupStarter.class);
    public static final int SERVER_PORT = 8080;

    /**
     * Main entry point
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(SERVER_PORT);
        server.setHandler(new WebAppContext("src/main/webapp", "/"));
        LOGGER.info("Server running on http://0.0.0.0:{}", SERVER_PORT);
        server.start();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = req.getParameter("q");
        if (request == null) {
            // no request
            return;
        }
        LOGGER.info("request: {}", request);
        String answer = answer(request);
        if (answer != null) {
            resp.getWriter().write(answer);
        }
    }

    private String answer(String req) {
        Matcher additionMatcher = Pattern.compile(".*what is your name").matcher(req);
        if (additionMatcher.matches()) {
            return "<YOUR NAME>";
        }
        LOGGER.info("Unrecognized question " + req);
        return null;
    }
}
