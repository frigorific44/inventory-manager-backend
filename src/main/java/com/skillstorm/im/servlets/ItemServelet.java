package com.skillstorm.im.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.im.daos.MySQLItemImp;
import com.skillstorm.im.daos.ItemDAO;
import com.skillstorm.im.models.NotFound;
import com.skillstorm.im.models.Item;
import com.skillstorm.im.services.URLParserService;

@WebServlet(urlPatterns = "/item/*")
public class ItemServelet extends HttpServlet {

	private static final long serialVersionUID = 5163522458183428531L;
	ItemDAO dao = new MySQLItemImp();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Item newItem = mapper.readValue(reqBody, Item.class);
		// TODO
//		validatorService.validate(newArtist); // Could be a service
		// The id has changed (auto-incremented)
		// check for index
		newItem = dao.save(newItem);
		if (newItem != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(newItem));
			resp.setStatus(201); // The default is 200
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create company")));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Try to extract an id
			int id = urlService.extractIdFromURL(req.getPathInfo());
			List<Item> items = dao.findBySectionId(id);
			if (items != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(items));
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new NotFound("No items with the provided section found")));
			}
		} catch (Exception e) {
			// There wasn't an id in the url
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a item")));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Item newItem = mapper.readValue(reqBody, Item.class);
		// TODO
		// should validate id is contained here
//		validatorService.validate(newArtist); // Could be a service
		// validate that there is an (existing?) id
		dao.update(newItem);
		if (newItem != null) {
			// 200 is generic OK~!
			resp.setStatus(200);
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to update company")));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Try to extract an id
			int[] ids = urlService.extractIdsFromURL(req.getPathInfo());
			dao.delete(ids[0], ids[1]);
			// Accepted but no content returned
			resp.setStatus(204);
		} catch (Exception e) {
			// There wasn't an id in the url
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a item")));
		}
	}

}
