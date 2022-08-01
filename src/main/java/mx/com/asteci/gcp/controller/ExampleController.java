package mx.com.asteci.gcp.controller;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.checkerframework.common.reflection.qual.GetMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@GetMapping("/")
	public String unsecured() {
		return "No secrets here!\n";
	}

	@GetMapping("/topsecret")
	public String secured() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
			Jwt jwt = (Jwt) authentication.getPrincipal();
			return String.format("You are [%s] with e-mail address [%s].%n",
					jwt.getSubject(), jwt.getClaimAsString("email"));
		}
		else {
			return "Something went wrong; authentication is not provided by IAP/JWT.\n";
		}

	}

	@GetMapping("/headers")
	public Map<String, String> headers(HttpServletRequest req) {
		return Collections.list(req.getHeaderNames())
				.stream()
				.collect(Collectors.toMap(Function.identity(), req::getHeader));
	}
}
