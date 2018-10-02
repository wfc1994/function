package com.wfc.servlet.defvar;

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

/**
 * 	用于处理修改定义的变量
 * 	@author wfc
 *
 */
@WebServlet("/EditVariableServlet")
public class EditVariableServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		boolean flag = true;
		ServletContext context = getServletContext();   // 获得ServletContext对象
		try {
			//1.接收参数
			int varid = Integer.parseInt(request.getParameter("varid"));
//			String varname = request.getParameter("varname");
//			String varunit = request.getParameter("varunit");
//			String vardescribe = request.getParameter("vardescribe");
			String varname =  URLDecoder.decode(request.getParameter("varname"), "UTF-8");
			String varunit = URLDecoder.decode(request.getParameter("varunit"), "UTF-8");
			String vardescribe = URLDecoder.decode(request.getParameter("vardescribe"), "UTF-8");
			System.out.println("修改的编号是："+varid);
			
			//2.执行修改，查询varlist中是否有相同数据，如果有则不修改数据。
			List<Variable> varlist = (List<Variable>) context.getAttribute("varlist");	
			for(Variable variable : varlist)
			{
				if((varname.equals( variable.getVarname())) && (varunit.equals(variable.getVarunit())) && (vardescribe.equals(variable.getVardescribe())) )
				{
					flag = false;
				}
			}
			if(flag==true)
			{
				varlist.get(varid-1).setVarname(varname);
				varlist.get(varid-1).setVarunit(varunit);
				varlist.get(varid-1).setVardescribe(vardescribe);
			}else {
				System.out.println("修改数据失败");
			}			
			context.setAttribute("varlist", varlist);

			//3.跳转到列表页
			request.getRequestDispatcher("Variable.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
