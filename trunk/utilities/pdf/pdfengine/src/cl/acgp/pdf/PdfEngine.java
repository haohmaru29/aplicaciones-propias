package cl.acgp.pdf;

import com.lowagie.text.DocumentException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PdfEngine {

    private static final String XHTML_HEADER = "<html><head>";
    private static final String XHTML_FOOTER = "</body></html>";
    private StringBuilder urlBuffer;
    private StringBuilder includes;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public PdfEngine(HttpServletRequest request, HttpServletResponse response) {
        urlBuffer = new StringBuilder();
        this.request = request;
        this.response = response;
        urlBuffer.append(request.getScheme().concat("://"));
        urlBuffer.append(request.getServerName().concat(":"));
        urlBuffer.append(request.getServerPort());
        urlBuffer.append(request.getContextPath().concat("/"));
    }
    
    public PdfEngine(HttpServletRequest request, HttpServletResponse response, List imports) {
        urlBuffer = new StringBuilder();
        includes = new StringBuilder();
        this.request = request;
        includes = new StringBuilder("");
        this.response = response;
        urlBuffer.append(request.getScheme().concat("://"));
        urlBuffer.append(request.getServerName().concat(":"));
        urlBuffer.append(request.getServerPort());
        urlBuffer.append(request.getContextPath().concat("/"));
        //loadImports(imports);
    }
    
    private void loadImports(List imports) {
        Iterator it = imports.iterator();
        while(it.hasNext()) {
            includes.append(readURL(it.next().toString()));
        }
    }
    
    private String readURL(String imports) {
        String response = "<style type=\"text/css\">";
        try {
            URL url = new URL(imports);
            String entrada = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((entrada = in.readLine()) != null) {
                response= response + entrada;    
                System.out.println(response);
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(PdfEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response +"</style>";
    }
    

    private ServletOutputStream create(String xhtml, ServletOutputStream out) {
        ITextRenderer renderer = new ITextRenderer();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xhtml)));
            renderer.setDocument(doc, urlBuffer.toString());
            renderer.layout();
            try {
                renderer.createPDF(out);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    private String buildXHTML(String content, String path) throws IOException {
        StringBuilder sb = new StringBuilder(XHTML_HEADER);
        sb.append("").append(includes.toString()).append(content).append(XHTML_FOOTER);
        String raw = sb.toString();
        String url = path.substring(0, path.indexOf('/', "http://".length() + 1) + 1);
        raw = raw.replaceAll("src=\"/", "src=\"" + url);
        InputStream rawis = new ByteArrayInputStream(raw.getBytes("iso-8859-1"));
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setDocType("omit");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        tidy.parse(rawis, os);
        String out = new String(os.toByteArray());
        return out;
    }

    public void renderDocument() {
        String content = request.getParameter("content");
        try {
            String xhtml = buildXHTML(content, request.getRequestURL().toString());
            String nameFile = request.getParameter("title");
            if ("".equals(nameFile) || nameFile == null) {
                nameFile = "DocumentoPDF";
            }
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName=" + nameFile + ".pdf");
            ServletOutputStream outPdf = create(xhtml, response.getOutputStream());
            outPdf.flush();
            outPdf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
