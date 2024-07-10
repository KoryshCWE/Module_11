package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String timezoneParam = request.getParameter("timezone");
            ZoneId zoneId;


            try {
                if (timezoneParam != null && !timezoneParam.isEmpty()) {
                    zoneId = ZoneId.of(timezoneParam);
                } else {
                    zoneId = ZoneId.of("UTC");
                }
            } catch (Exception e) {
                zoneId = ZoneId.of("UTC");
            }


            LocalDateTime currentTime = LocalDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String formattedTime = currentTime.format(formatter);


            out.println("<html>");
            out.println("<head>");
            out.println("<title>Current Time</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Current Time in " + zoneId + "</h1>");
            out.println("<p>" + formattedTime + " " + zoneId + "</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}
