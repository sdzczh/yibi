package com.yibi.orderapi.component;

import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

  
/**
 * @描述 图片上传Servlet<br>
 * @author administrator
 * @版本 v1.0.0
 * @日期 2017-6-14
 */
@SuppressWarnings("unchecked")
public class FileUploadServlet extends HttpServlet {    
    private static final long serialVersionUID = -4903483985922185852L;  

    public void doGet(HttpServletRequest request, HttpServletResponse response) {  
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);    
		if(isMultipart){    
			responseOutWithJson(response,uploadImg(request));  
		}else{
			responseOutWithJson(response, Result.toResult(ResultCode.PARAM_IS_INVALID));  
		} 
    	
    }    

    /**  
     *  
     * @Description 上传图片 
     * @param request
     */  
	public String  uploadImg( HttpServletRequest request){
        Map<String,Object> res = new HashMap<String, Object>();  
        String imageName = null;  
        //String realPath = null;
        String fileDir = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        ServletFileUpload upload = new ServletFileUpload(factory);    
        upload.setSizeMax(1024*1024*5*10);  
        upload.setHeaderEncoding("utf-8");
        boolean isFile = false;
        try {    
            List<FileItem> items = upload.parseRequest(request);    
            for(FileItem item : items){    
                if(item.isFormField()){
                    String id = item.getFieldName();    
                    if("fileDir".equals(id)){
                    	fileDir = item.getString("utf-8");
                    }
                    
                } else { //文件    
                	isFile = true;
                	if(fileDir==null){
                		return Result.toResult(ResultCode.PARAM_IS_BLANK);
                	}
                	FileType fileType = FILE.checkFileType(item.getInputStream());
            		if(fileType == FileType.JPEG || fileType == FileType.PNG || fileType == FileType.GIF || fileType == FileType.BMP ){
                		String name = item.getName();  
                		if(name.lastIndexOf(".")==-1){
                			return Result.toResult(ResultCode.FILE_TYPE_ERROR);
                		}
                		imageName = System.currentTimeMillis() + name.substring(name.lastIndexOf("."));  
                		
                		String path = ImgUtils.uploadImg(fileDir, imageName, item.getInputStream());
                		res.put("imgPath",path);
                        return Result.toResult(ResultCode.SUCCESS,res);
                		
                	}else{
                		return Result.toResult(ResultCode.FILE_TYPE_ERROR);
                	}
                }   
            } 
            if(!isFile){
            	return Result.toResult(ResultCode.FILE_TYPE_ERROR);
            }
        } catch (Exception e) {    
            e.printStackTrace();  
        }
		return Result.toResult(ResultCode.FILE_UPLOAD_ERROR);
    }  
    /**  
     * 以JSON格式输出  
     * @param response  
     */    
    protected void responseOutWithJson(HttpServletResponse response,String resultStr) {    
        //将实体对象转换为JSON Object转换    
        response.setCharacterEncoding("UTF-8");    
        response.setContentType("application/json; charset=utf-8");    
        PrintWriter out = null;    
        try {    
            out = response.getWriter();    
            out.append(resultStr);    
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            if (out != null) {    
                out.close();    
            }    
        }    
    }  
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
        this.doGet(request,response);  
    }

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				servletConfig.getServletContext());
		// TODO Auto-generated method stub
		super.init();
	}  
    
    
} 