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
 *	���ڴ���ɾ������ı���
 * 	@author wfc
 *
 */
@WebServlet("/DeleteVariableServlet")
public class DeleteVariableServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");			
			ServletContext context = getServletContext();   // ���ServletContext����
			
			//1.���ղ���
			int varid = Integer.parseInt(request.getParameter("varid"));
//			String varname = request.getParameter("varname");
//			String varunit = request.getParameter("varunit");
//			String vardescribe = request.getParameter("vardescribe");
			String varname =  URLDecoder.decode(request.getParameter("varname"), "UTF-8");
			String varunit = URLDecoder.decode(request.getParameter("varunit"), "UTF-8");
			String vardescribe = URLDecoder.decode(request.getParameter("vardescribe"), "UTF-8");
			System.out.println("ɾ���ı���ǣ�"+varid);
			boolean flag = false;
			
			//2.ִ��ɾ������ѯvarlist���Ƿ��ж�Ӧ���ݣ��������ͨ�����յ�idɾ�����ݣ�
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
				System.out.println("ɾ������ʧ��");
			}
			
			//3.���������ɵ�varlist����������varid�ٱ���
			for(int i=0;i<varlist.size();i++)
			{
				varlist.get(i).setVarid(i+1);
			}			
			context.setAttribute("varlist", varlist);

			//4.��ת���б�ҳ
			request.getRequestDispatcher("Variable.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
