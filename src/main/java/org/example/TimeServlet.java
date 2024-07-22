package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.TemplateEngine;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezoneParam = request.getParameter("timezone");
        ZoneId zoneId = ZoneId.of("UTC");
        boolean validTimezone = false;


        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezoneParam);
                validTimezone = true;
            } catch (Exception e) {

            }
        }


        if (!validTimezone) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        try {
                            zoneId = ZoneId.of(cookie.getValue());
                            validTimezone = true;
                        } catch (Exception e) {

                        }
                        break;
                    }
                }
            }
        }


        if (validTimezone && timezoneParam != null) {
            Cookie cookie = new Cookie("lastTimezone", timezoneParam);
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
            response.addCookie(cookie);
        }

        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        TemplateEngine templateEngine = ThymeleafConfig.getTemplateEngine();
        WebContext context = new WebContext(request, response, getServletContext());
        context.setVariable("formattedTime", formattedTime);
        context.setVariable("timezone", zoneId);

        templateEngine.process("time", context, response.getWriter());
    }
}
