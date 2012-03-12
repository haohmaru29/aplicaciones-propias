package cl.tidev.matrimonio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="security")
public class security {

	@RequestMapping(value="login", method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String login(HttpServletRequest request, HttpServletResponse response ) {
		String success = "false";
		
		
		return success;
	}
}
