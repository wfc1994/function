package com.wfc.servlet.defvar;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wfc.domain.Variable;


/**
 *	用于处理删除定义的变量
 * 	@author wfc
 *
 */
@WebServlet("/DeleteVariableServlet")
public class DeleteVariableServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");			
			ServletContext context = getServletContext();   // 获得ServletContext对象
			
			//1.接收参数
			int varid = Integer.parseInt(request.getParameter("varid"));
//			String varname = request.getParameter("varname");
//			String varunit = request.getParameter("varunit");
//			String vardescribe = request.getParameter("vardescribe");
			String varname =  URLDecoder.decode(request.getParameter("varname"), "UTF-8");
			String varunit = URLDecoder.decode(request.getParameter("varunit"), "UTF-8");
			String vardescribe = URLDecoder.decode(request.getParameter("vardescribe"), "UTF-8");
			System.out.println("删除的编号是："+varid);
			boolean flag = false;
			
			//2.执行删除，查询varlist中是否有对应数据，如果有则通过接收的id删除数据，
			List<Variable> varlist = (List<Variable>) context.getAttribute("varlist");	
			for(Variable variable : varlist)
			{
				if((varname.equals( variable.getVarname())) && (varunit.equals(variable.getVarunit())) && (vardescribe.equals(variable.getVardescribe())) )
				{
					flag = true;
				}
			}
			if(flag==true)
			{
				varlist.remove(varid-1);
			}else {
				System.out.println("删除数据失败");
			}
			
			//3.将重新生成的varlist，重新生成varid再保存
			for(int i=0;i<varlist.size();i++)
			{
				varlist.get(i).setVarid(i+1);
			}			
			context.setAttribute("varlist", varlist);

			//4.跳转到列表页
			request.getRequestDispatcher("Variable.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
