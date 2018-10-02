package com.wfc.servlet.defvardata;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wfc.domain.tempbean;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *	����ʵ�ֵ���������ݹ���
 */
@WebServlet("/ImportVariableDataServlet")
public class ImportVariableDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * 	�ϴ����ݼ������ļ�
     */
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    	String filePath = null;
        // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
                 
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        request.setAttribute("message","�ļ��ϴ��ɹ�!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message","������Ϣ: " + ex.getMessage());
        }
        
        tempbean tb = new tempbean();
        List tbList = new ArrayList<tempbean>();
        List templist = new ArrayList<String>();
        
        //1:����workbook
        Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(new File(filePath));
			 //2:��ȡ��һ��������sheet
	        Sheet sheet=workbook.getSheet(0);
	        //3:��ȡ����
	        System.out.println("�У�"+sheet.getRows());
	        System.out.println("�У�"+sheet.getColumns());
	        
	        for(int i=0;i<sheet.getRows();i++){
	        	String tbt = "";
	            for(int j=0;j<sheet.getColumns();j++){
	                Cell cell=sheet.getCell(j,i);
	                System.out.print(cell.getContents()+" ");
	                tbt = tbt + "<td>" +cell.getContents()+ "</td>";
	            }
	            System.out.println();
                templist.add(tbt);
	        }
	        
	        //���һ�����ر���Դ
	        workbook.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} 
       
		ServletContext context = getServletContext();   // ���ServletContext����
		context.setAttribute("templist", templist);
       
        
        
        // ��ת�� message.jsp
        request.getServletContext().getRequestDispatcher("/Defvardata.jsp").forward(request, response);
    }
}

