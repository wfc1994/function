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

import com.wfc.domain.Variable;
import com.wfc.domain.VariableRelation;

/**
 * Servlet implementation class DeleteVariableRelationServlet
 */
@WebServlet("/DeleteVariableRelationServlet")
public class DeleteVariableRelationServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");			
			ServletContext context = getServletContext();   // 获得ServletContext对象
			
			//1.接收参数
			int varrelid = Integer.parseInt(request.getParameter("varrelid"));
			String independentvar =  URLDecoder.decode(request.getParameter("independentvar"), "UTF-8");
			String dependentvar = URLDecoder.decode(request.getParameter("dependentvar"), "UTF-8");
			String functionname = URLDecoder.decode(request.getParameter("functionname"), "UTF-8");
			System.out.println("删除的编号是："+varrelid);
			boolean flag = false;
			
			//2.执行删除，查询varlist中是否有对应数据，如果有则通过接收的id删除数据，
			List<VariableRelation> varrelationlist = (List<VariableRelation>) context.getAttribute("varrelationlist");	
			for(VariableRelation varrel : varrelationlist)
			{
				if((independentvar.equals( varrel.getIndependentvar())) && (dependentvar.equals(varrel.getDependentvar())) && (functionname.equals(varrel.getFunctionname())) )
				{
					flag = true;
				}
			}
			
			if(flag==true)
			{
				varrelationlist.remove(varrelid-1);
			}else {
				System.out.println("删除数据失败");
			}
			
			//3.将重新生成的varrelationlist，重新生成varrelid再保存
			for(int i=0;i<varrelationlist.size();i++)
			{
				varrelationlist.get(i).setVarrelid(i+1);
			}			
			context.setAttribute("varrelationlist", varrelationlist);

			//4.跳转到列表页
			request.getRequestDispatcher("Defvarrelation.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
