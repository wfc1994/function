package com.wfc.servlet.defvarrelation;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfc.domain.VariableRelation;

/**
 * Servlet implementation class EditVariableRelationServlet
 */
@WebServlet("/EditVariableRelationServlet")
public class EditVariableRelationServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		boolean flag = true;
		ServletContext context = getServletContext();   // 获得ServletContext对象
		try {
			//1.接收参数
			int varrelid = Integer.parseInt(request.getParameter("varrelid"));
			String independentvar =  URLDecoder.decode(request.getParameter("independentvar"), "UTF-8");
			String dependentvar = URLDecoder.decode(request.getParameter("dependentvar"), "UTF-8");
			String functionname = URLDecoder.decode(request.getParameter("functionname"), "UTF-8");
			System.out.println("修改的编号是："+varrelid);
			
			//2.执行修改，查询varrelationlist中是否有相同数据，如果有则不修改数据。
			List<VariableRelation> varrelationlist = (List<VariableRelation>) context.getAttribute("varrelationlist");	
			for(VariableRelation varrel : varrelationlist)
			{
				if((independentvar.equals( varrel.getIndependentvar())) && (dependentvar.equals(varrel.getDependentvar())) && (functionname.equals(varrel.getFunctionname())) )
				{
					flag = false;
				}
			}
			if(flag==true)
			{
				varrelationlist.get(varrelid-1).setIndependentvar(independentvar);
				varrelationlist.get(varrelid-1).setDependentvar(dependentvar);
				varrelationlist.get(varrelid-1).setFunctionname(functionname);
				varrelationlist.get(varrelid-1).setVarrelation(independentvar, dependentvar, functionname);
			}else {
				System.out.println("修改数据失败");
			}			
			context.setAttribute("varrelationlist", varrelationlist);

			//3.跳转到列表页
			request.getRequestDispatcher("Defvarrelation.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
