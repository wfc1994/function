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
		ServletContext context = getServletContext();   // ���ServletContext����

		//1.��ȡ����
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
				System.out.println(repeatflag+"ˢ���������ʧ���ˣ�");
			}else {
				System.out.println("����ʱ���ݣ�");
			}
		}
		
		//2.����Variable�Ķ����Լ�List����ȡ������ӵ��б���
		Variable var = new Variable(varname, varunit, vardescribe);
		List<Variable> varlist = new ArrayList<Variable>(); 	
		context.setAttribute("vartemp", var);
		Integer count = (Integer)context.getAttribute("counter");
		Integer importflag = (Integer) context.getAttribute("importflag");
		if(importflag!=null)
		{
			varlist = (List<Variable>) context.getAttribute("varlist");
		}else {
			System.out.println("�޵�������!");
		}
		
		//3.�ж���ӵ������Ƿ�Ϊ�գ�����������������
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
						System.out.println(flag+"����ظ�����ʧ���ˣ�");
					}
				}
				
				if((flag==true) && (importflag == 0) && (repeatflag==true))
				{
					System.out.println("������ݳɹ���");
					varlist.add(var);
				}
				context.setAttribute("varlist", varlist);
				context.setAttribute("counter", count+1);
			}
			
			//4.����ÿһ��������id
			for(int i=0;i<varlist.size();i++)
			{
				varlist.get(i).setVarid(i+1);
			}
			context.setAttribute("varlist", varlist);
		}else {
			System.out.println("�����ύ�����ݣ�");
		}
		
//		System.out.println(count);
//		System.out.println(var.getVarname());
		
		//4.��ת��Variable.jspԭҳ����ȥ
		context.setAttribute("importflag", 0);
		request.getRequestDispatcher("Variable.jsp").forward(request, response);
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
