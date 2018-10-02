package com.wfc.servlet.defvarrelation;


import java.io.IOException;
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

/**
 * Servlet implementation class DefunctionServlet
 */
@WebServlet("/DefunctionServlet")
public class DefVariableRelationServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		boolean dataflag = true;
		boolean flag = true;
		boolean repeatflag = true;
		ServletContext context = getServletContext();   // ���ServletContext����

		//1.��ȡ����
		String independentvar = request.getParameter("independentvar");
		String dependentvar = request.getParameter("dependentvar");
		String functionname = request.getParameter("functionname");
		if((independentvar=="") || (dependentvar=="") || (functionname=="")) {
			dataflag = false;
		}
		VariableRelation varrelationtemp = (VariableRelation) context.getAttribute("varrelationtemp");
		if(varrelationtemp!=null)
		{
			if( (independentvar.equals( varrelationtemp.getIndependentvar())) && (dependentvar.equals(varrelationtemp.getDependentvar())) && (functionname.equals(varrelationtemp.getFunctionname())) )
			{
				repeatflag = false;
				System.out.println(repeatflag+"ˢ���������ʧ���ˣ�");
			}else {
				System.out.println("����ʱ���ݣ�");
			}
		}
		
		//2.����VariableRelation�Ķ����Լ�List����ȡ������ӵ��б���
		VariableRelation varrelation = new VariableRelation(independentvar, dependentvar, functionname);
		varrelation.setVarrelation(independentvar, dependentvar, functionname);
		List<VariableRelation> varrelationlist = new ArrayList<VariableRelation>();
		context.setAttribute("varrelationtemp", varrelation);
		Integer funcount = (Integer)context.getAttribute("funcount");
		Integer importvlflag = (Integer) context.getAttribute("importvlflag");
		if(importvlflag!=null){
			varrelationlist = (List<VariableRelation>) context.getAttribute("varrelationlist");
		}else {
			System.out.println("�޵�������!");
		}
		
		//3.�ж���ӵ������Ƿ�Ϊ�գ�����������������
		if(dataflag)
		{
			if(funcount == null){
			varrelationlist.add(varrelation);
			context.setAttribute("varrelationlist", varrelationlist);
			funcount = 1;
			context.setAttribute("funcount", funcount);
			}else {
				varrelationlist = (List<VariableRelation>) context.getAttribute("varrelationlist");
				for(VariableRelation varrel : varrelationlist)
				{
					if((independentvar.equals( varrel.getIndependentvar())) && (dependentvar.equals(varrel.getDependentvar())) && (functionname.equals(varrel.getFunctionname())) )
					{
						flag = false;
						System.out.println(flag+"����ظ�����ʧ���ˣ�");
					}
				}
						
				if((flag==true) && (importvlflag==0) && (repeatflag==true))
				{
					System.out.println("������ݳɹ���");
					varrelationlist.add(varrelation);
				}
				
				context.setAttribute("varrelationlist", varrelationlist);
				context.setAttribute("funcount", funcount+1);
			}
					
			//4.����ÿһ��������id
			for(int i=0;i<varrelationlist.size();i++)
			{
				varrelationlist.get(i).setVarrelid(i+1);
			}
			context.setAttribute("varrelationlist", varrelationlist);
		}else {
			System.out.println("�����ύ�����ݣ�");
		}
				
		//4.��ת��Defvarrelation.jspԭҳ����ȥ
		context.setAttribute("importvlflag", 0);
		request.getRequestDispatcher("Defvarrelation.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
