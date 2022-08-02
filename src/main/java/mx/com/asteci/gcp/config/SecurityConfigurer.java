package mx.com.asteci.gcp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;


/**
 * Sample custom {@link WebSecurityConfigurerAdapter} that applies OAuth Resource Server
 * pre-authentication, and rejects unauthenticated requests to a single page, {@code /topsecret}.
 * All other pages are unsecured.
 *
 * <p>Because of {@code spring-cloud-gcp-starter-security-iap} dependency, the secure token will be
 * retrieved from Google Cloud IAP header {@code x-goog-iap-jwt-assertion}, and not from the
 * standard OAuth Bearer header.
 *
 * @since 1.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/topsecret")
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .and()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
  }
}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//			.authorizeHttpRequests(authorize -> authorize
//				.anyRequest().authenticated()
//			);
//		return http.build();
//	}
	
	
//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter(){
//		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
//		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return bean;
//	}
//	
//	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration corsConfig = new CorsConfiguration();
//		corsConfig.setAllowedOrigins(Arrays.asList("*"));
//		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
//		corsConfig.setAllowCredentials(true);
//		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//		
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfig);
//		
//		return source;
//	}