package com.skillstorm.im.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.im.daos.CompanyDAO;
import com.skillstorm.im.daos.MySQLCompanyImp;
import com.skillstorm.im.models.Company;
import com.skillstorm.im.services.URLParserService;
import com.skillstorm.im.models.NotFound;

/**
 * Our top level entity. A company should be create upon account creation,
 * and only the name can be changed. A company is retrieved each time a user logs in
 * to be able to retrieve the inventory entities under it.
 */
@WebServlet(urlPatterns = "/company/*")
public class CompanyServlet extends HttpServlet {

	private static final long serialVersionUID = -6234891161086056513L;
	CompanyDAO dao = new MySQLCompanyImp();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Company newCompany = mapper.readValue(reqBody, Company.class);
		// TODO
//		validatorService.validate(newArtist); // Could be a service
		// The id has changed (auto-incremented)
		newCompany = dao.save(newCompany);
		if (newCompany != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(newCompany));
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
			Company company = dao.findById(id);
			if (company != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(company));
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new NotFound("No company with the provided id found")));
			}
		} catch (Exception e) {
			// There wasn't an id in the url
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a company")));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Company newCompany = mapper.readValue(reqBody, Company.class);
		// TODO
		// should validate id is contained here
//		validatorService.validate(newArtist); // Could be a service
		// validate that there is an (existing?) id
		dao.update(newCompany);
		if (newCompany != null) {
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
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to find a company")));
		}
	}
	
}
