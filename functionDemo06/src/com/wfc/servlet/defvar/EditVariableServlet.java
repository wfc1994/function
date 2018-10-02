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
 * 	���ڴ����޸Ķ���ı���
 * 	@author wfc
 *
 */
@WebServlet("/EditVariableServlet")
public class EditVariableServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		boolean flag = true;
		ServletContext context = getServletContext();   // ���ServletContext����
		try {
			//1.���ղ���
			int varid = Integer.parseInt(request.getParameter("varid"));
//			String varname = request.getParameter("varname");
//			String varunit = request.getParameter("varunit");
//			String vardescribe = request.getParameter("vardescribe");
			String varname =  URLDecoder.decode(request.getParameter("varname"), "UTF-8");
			String varunit = URLDecoder.decode(request.getParameter("varunit"), "UTF-8");
			String vardescribe = URLDecoder.decode(request.getParameter("vardescribe"), "UTF-8");
			System.out.println("�޸ĵı���ǣ�"+varid);
			
			//2.ִ���޸ģ���ѯvarlist���Ƿ�����ͬ���ݣ���������޸����ݡ�
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
				System.out.println("�޸�����ʧ��");
			}			
			context.setAttribute("varlist", varlist);

			//3.��ת���б�ҳ
			request.getRequestDispatcher("Variable.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
