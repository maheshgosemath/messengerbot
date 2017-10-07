package in.codingeek.controller;

import in.codingeek.util.MessageUtil;
import in.codingeek.util.ReaderUtil;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/callback")
public class RequestController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String mode = request.getParameter("hub.mode");
        String token = request.getParameter("hub.verify_token");
        String challenge = request.getParameter("hub.challenge");
        if("subscribe".equals(mode) &&
                "verification_token".equals(token)) {
            try (PrintWriter pw = response.getWriter()) {
                pw.append(challenge);
                pw.flush();
                pw.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        JSONObject input = ReaderUtil.getRequestParameters(request);
        if(input != null) {
            MessageUtil.sendTextResponse(input);
            MessageUtil.sendQuickResponse(input);
        }
        response.setStatus(HttpStatus.SC_OK);
    }
}
