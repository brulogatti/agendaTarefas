package controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GlobalFunctions {
	
	public boolean checkSession (HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
		boolean result = false;
		boolean end = false;
		HttpSession session=request.getSession();  
		Enumeration<String> attributeNames = session.getAttributeNames();
		if(attributeNames.hasMoreElements()) {
			List<String> attributes = new ArrayList<String>();
			while(end==false) {
				try{
					attributes.add(attributeNames.nextElement());
				}catch(NoSuchElementException e ) {
					end = true;
				}
			}
			for(String element : attributes) {
				if(session.getAttribute(element).toString().isEmpty()) {
					result = false;
				}else {
					result = true;
				}
			}
		}
		return result;
	}

}
