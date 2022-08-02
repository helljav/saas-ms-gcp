package mx.com.asteci.gcp.controller;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample REST Controller to demonstrate IAP security header extraction.
 *
 * <p>Makes the following pages available:
 *
 * <ul>
 *   <li>{@code /} - Unsecured page.
 *   <li>{@code /topsecret} - Secured page requiring non-anonymous authentication. Prints IAP
 *       identity details.
 *   <li>{@code /headers} - Unsecured page that can be used for troubleshooting.
 * </ul>
 *
 * @since 1.1
 */
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
      return String.format(
          "You are [%s] with e-mail address [%s].%n",
          jwt.getSubject(), jwt.getClaimAsString("email"));
    } else {
      return "Something went wrong; authentication is not provided by IAP/JWT.\n";
    }
  }

  @GetMapping("/headers")
  public Map<String, String> headers(HttpServletRequest req) {
    return Collections.list(req.getHeaderNames()).stream()
        .collect(Collectors.toMap(Function.identity(), req::getHeader));
  }
}
