package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import classes.Article;
import classes.ArticlePicture;
import services.ArticleService;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String imgId = request.getPathInfo().substring(1);

        ArticlePicture ap;
		try {			
			ap = ArticleService.GetPictureFromPictureId(Integer.parseInt(imgId));
			byte[] content = ap.content;
	        response.setContentType(getServletContext().getMimeType("jpg"));
	        response.setContentLength(content.length);
	        response.getOutputStream().write(content);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}       
    }
}

