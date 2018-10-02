package com.wfc.servlet.defvar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfc.domain.Variable;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class ImporatVariableServlet
 */
@WebServlet("/ImporatVariableServlet")
public class ImporatVariableServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();   // ���ServletContext����
		context.removeAttribute("varlist");
		context.setAttribute("importflag", 1);
		List<Variable> varlist = new ArrayList<Variable>(); 	
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
	 	    String line = null;
	 	    StringBuilder str = new StringBuilder();
	 	    while ((line = br.readLine()) != null) {
	 	      str.append(line);
	 	    }
	 	    String jstr = str.toString();
	 	    JSONArray jsonarray = JSONArray.fromObject(jstr);
	 	    if(jsonarray.size()>0){
	   		  for(int i=0;i<jsonarray.size();i++){
	   			  // ���� jsonarray ���飬��ÿһ������ת�� json ����
	   			  JSONObject job = jsonarray.getJSONObject(i); 
	   			  // �õ� ÿ�������е�����ֵ
	   			  String varname = job.get("varname").toString();
	   			  String varunit = job.get("varunit").toString();
	   			  String vardescribe = job.get("vardescribe").toString();
	   			  Variable var = new Variable(varname, varunit, vardescribe);
	   			  var.setVarid(i+1);
	   			  varlist.add(var);
	   		  }
	   	  	}
			
	 	   
	 	    context.setAttribute("varlist", varlist);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			request.getRequestDispatcher("Variable.jsp").forward(request, response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
