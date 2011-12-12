package cl.tidev.commons.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.tidev.commons.library.export.PdfEngine;

public class document {

	public void pdf(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PdfEngine pdfEngine = new PdfEngine(request, response);
		pdfEngine.renderDocument();
		
	}
}
