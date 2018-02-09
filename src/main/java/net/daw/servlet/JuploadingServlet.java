package net.daw.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class JuploadingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String name = "";
        String strMessage = "";
        HashMap<String, String> hash = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//images//" + name));

                    } else {
                        hash.put(item.getFieldName(), item.getString());

                    }

                }
                strMessage = "<h1>File Uploaded Successfully</h1>";
                

                Iterator it = hash.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    strMessage+= e.getKey() + " " + e.getValue() + "<br/>";
                }

                strMessage += "<img src=\"" + "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + "/images/" + name + "\"  width=\"150\" /><br/>";
                strMessage += "<a href=\"" + "http://" + request.getServerName() + ":" + request.getServerPort() + "/juploading" + "\">Return</a><br/>";
                request.setAttribute("message", strMessage);
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed: " + ex);
                strMessage += "<a href=\"" + "http://" + request.getServerName() + ":" + request.getServerPort() + "/juploading" + "\">Return</a><br/>";
            }
        } else {
            request.setAttribute("message", "Only serve file upload requests");
            strMessage += "<a href=\"" + "http://" + request.getServerName() + ":" + request.getServerPort() + "/juploading" + "\">Return</a><br/>";
        }
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
