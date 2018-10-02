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
		ServletContext context = getServletContext();   // ���ServletContext����
		try {
			//1.���ղ���
			int varrelid = Integer.parseInt(request.getParameter("varrelid"));
			String independentvar =  URLDecoder.decode(request.getParameter("independentvar"), "UTF-8");
			String dependentvar = URLDecoder.decode(request.getParameter("dependentvar"), "UTF-8");
			String functionname = URLDecoder.decode(request.getParameter("functionname"), "UTF-8");
			System.out.println("�޸ĵı���ǣ�"+varrelid);
			
			//2.ִ���޸ģ���ѯvarrelationlist���Ƿ�����ͬ���ݣ���������޸����ݡ�
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
				System.out.println("�޸�����ʧ��");
			}			
			context.setAttribute("varrelationlist", varrelationlist);

			//3.��ת���б�ҳ
			request.getRequestDispatcher("Defvarrelation.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
