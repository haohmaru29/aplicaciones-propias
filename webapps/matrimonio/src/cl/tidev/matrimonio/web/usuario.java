package cl.tidev.matrimonio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="usuario")
public class usuario {

	@RequestMapping(value="login", method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String login(HttpServletRequest request, HttpServletResponse response ) {
		String success = "false";
		
		
		return success;
	}
	
	
	
}
