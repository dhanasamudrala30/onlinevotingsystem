package org.example;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int candidateId = Integer.parseInt(request.getParameter("candidateId"));
        String username = request.getParameter("username");

        try (Connection conn = DBUtil.getConnection()) {
            String updateVoteQuery = "UPDATE candidates SET votes = votes + 1 WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(updateVoteQuery);
            ps1.setInt(1, candidateId);
            ps1.executeUpdate();

            String updateUserQuery = "UPDATE users SET has_voted = TRUE WHERE username = ?";
            PreparedStatement ps2 = conn.prepareStatement(updateUserQuery);
            ps2.setString(1, username);
            ps2.executeUpdate();

            response.getWriter().println("Vote cast successfully!");
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

