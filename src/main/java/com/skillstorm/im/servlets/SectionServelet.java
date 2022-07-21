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
import com.skillstorm.im.daos.MySQLSectionImp;
import com.skillstorm.im.daos.SectionDAO;
import com.skillstorm.im.models.NotFound;
import com.skillstorm.im.models.Section;
import com.skillstorm.im.services.URLParserService;

@WebServlet(urlPatterns = "/section/*")
public class SectionServelet extends HttpServlet {

	private static final long serialVersionUID = 5163522458183428531L;
	SectionDAO dao = new MySQLSectionImp();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Section newSection = mapper.readValue(reqBody, Section.class);
		// TODO
//		validatorService.validate(newArtist); // Could be a service
		// The id has changed (auto-incremented)
		newSection = dao.save(newSection);
		if (newSection != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(newSection));
			resp.setStatus(201); // The default is 200
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create company")));
		}
	}
	
	/**
	 * GET retrieves sections from a warehouse id, rather than a section from its own id.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Try to extract an id
			int id = urlService.extractIdFromURL(req.getPathInfo());
			List<Section> sections = dao.findByWarehouseId(id);
			if (sections != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(sections));
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new NotFound("No sections with the provided warehouse found")));
			}
		} catch (Exception e) {
			// There wasn't an id in the url
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a section")));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Section newSection = mapper.readValue(reqBody, Section.class);
		// TODO
		// should validate id is contained here
//		validatorService.validate(newArtist); // Could be a service
		// validate that there is an (existing?) id
		dao.update(newSection);
		if (newSection != null) {
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
			int id = urlService.extractIdFromURL(req.getPathInfo());
			dao.delete(id);
			// Accepted but no content returned
			resp.setStatus(204);
		} catch (Exception e) {
			// There wasn't an id in the url
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a section")));
		}
	}

}
