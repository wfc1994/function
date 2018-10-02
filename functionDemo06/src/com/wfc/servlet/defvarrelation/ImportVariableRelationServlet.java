package com.wfc.servlet.defvarrelation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfc.domain.Variable;
import com.wfc.domain.VariableRelation;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ImportVariableRelationServlet
 */
@WebServlet("/ImportVariableRelationServlet")
public class ImportVariableRelationServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();   // ���ServletContext����
		context.removeAttribute("varrelationlist");
		context.setAttribute("importvlflag", 1);
		List<VariableRelation> varrelationlist = new ArrayList<VariableRelation>(); 	
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
	   			  String independentvar = job.get("independentvar").toString();
	   			  String dependentvar = job.get("dependentvar").toString();
	   			  String functionname = job.get("functionname").toString();
	   			  VariableRelation varrelation = new VariableRelation(independentvar, dependentvar, functionname);
	   			  varrelation.setVarrelid(i+1);
	   			  varrelation.setVarrelation(independentvar, dependentvar, functionname);
	   			  varrelationlist.add(varrelation);
	   		  }
	   	  	}
			
	 	    context.setAttribute("varrelationlist", varrelationlist);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			request.getRequestDispatcher("Defvarrelation.jsp").forward(request, response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
