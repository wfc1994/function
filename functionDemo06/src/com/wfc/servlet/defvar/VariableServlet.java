package com.wfc.servlet.defvar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jasper.tagplugins.jstl.core.Out;

import com.sun.xml.internal.bind.CycleRecoverable.Context;
import com.wfc.domain.Variable;


/**
 * Servlet implementation class VariableServlet
 */
@WebServlet("/VariableServlet")
public class VariableServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		boolean flag = true;
		boolean dataflag = true;
		boolean repeatflag = true;
		ServletContext context = getServletContext();   // 获得ServletContext对象

		//1.获取数据
		String varname = request.getParameter("varname");
		String varunit = request.getParameter("varunit");
		String vardescribe = request.getParameter("vardescribe");
		if((varname=="") || (varunit=="") || (vardescribe=="")) {
			dataflag = false;
		}
		Variable vartemp = (Variable) context.getAttribute("vartemp");
		if(vartemp!=null)
		{
			if((varname.equals( vartemp.getVarname())) && (varunit.equals(vartemp.getVarunit())) && (vardescribe.equals(vartemp.getVardescribe())) )
			{
				repeatflag = false;
				System.out.println(repeatflag+"刷新添加数据失败了！");
			}else {
				System.out.println("无临时数据！");
			}
		}
		
		//2.创建Variable的对象，以及List，获取数据添加到列表中
		Variable var = new Variable(varname, varunit, vardescribe);
		List<Variable> varlist = new ArrayList<Variable>(); 	
		context.setAttribute("vartemp", var);
		Integer count = (Integer)context.getAttribute("counter");
		Integer importflag = (Integer) context.getAttribute("importflag");
		if(importflag!=null)
		{
			varlist = (List<Variable>) context.getAttribute("varlist");
		}else {
			System.out.println("无导入数据!");
		}
		
		//3.判读添加的数据是否为空，如果不是再添加数据
		if(dataflag)
		{
			if(count == null){
				varlist.add(var);
				context.setAttribute("varlist", varlist);
				count = 1;
				context.setAttribute("counter", count);
			}else {
				varlist = (List<Variable>) context.getAttribute("varlist");
				for(Variable variable : varlist)
				{
					if((varname.equals( variable.getVarname())) && (varunit.equals(variable.getVarunit())) && (vardescribe.equals(variable.getVardescribe())) )
					{
						flag = false;
						System.out.println(flag+"添加重复数据失败了！");
					}
				}
				
				if((flag==true) && (importflag == 0) && (repeatflag==true))
				{
					System.out.println("添加数据成功！");
					varlist.add(var);
				}
				context.setAttribute("varlist", varlist);
				context.setAttribute("counter", count+1);
			}
			
			//4.保存每一条变量的id
			for(int i=0;i<varlist.size();i++)
			{
				varlist.get(i).setVarid(i+1);
			}
			context.setAttribute("varlist", varlist);
		}else {
			System.out.println("不能提交空数据！");
		}
		
//		System.out.println(count);
//		System.out.println(var.getVarname());
		
		//4.跳转到Variable.jsp原页面中去
		context.setAttribute("importflag", 0);
		request.getRequestDispatcher("Variable.jsp").forward(request, response);
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
