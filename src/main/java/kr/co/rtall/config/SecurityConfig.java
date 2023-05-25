package kr.co.rtall.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.anyRequest().authenticated()
		;
		
		http
			.formLogin()
//			.loginPage("/loginPage")
//			.defaultSuccessUrl("/")
//			.failureUrl("/login")
//			.usernameParameter("userId")
//			.passwordParameter("passwd")
//			.loginProcessingUrl("/login_prod")
//			.successHandler(new AuthenticationSuccessHandler() {
//				
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					System.out.println("authentication:" + authentication.getName());
//					response.sendRedirect("/");
//				}
//			})
//			.failureHandler(new AuthenticationFailureHandler() {
//				
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//						AuthenticationException exception) throws IOException, ServletException {
//					System.out.println("Fail:");
//					response.sendRedirect("/login");
//				}
//			})
//			.permitAll()
		;
		
		http
			.logout()
			.logoutUrl("logout")
			.logoutSuccessUrl("/login")
//			.addLogoutHandler(new LogoutHandler() {
//				
//				@Override
//				public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//					HttpSession session = request.getSession();
//					session.invalidate();
//				}
//			})
			.logoutSuccessHandler(new LogoutSuccessHandler() {
				
				@Override
				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
						throws IOException, ServletException {
					response.sendRedirect("/login");
				}
			})
//			.deleteCookies("remember-me")
		;
		
		http
			.rememberMe()
//			.rememberMeParameter("remember")
//			.tokenValiditySeconds(3600)
			.userDetailsService(userDetailsService)
		;
		
		http
			.sessionManagement()
			.maximumSessions(1) // 최대 허용가능 세션 수, -1 무제한
			.maxSessionsPreventsLogin(true) // 동시로그인 차단, false: 기존 세션 만료
			// invalidSessionUrl // 세션이 유효하지 않을때 이동 할 페이지
			.expiredUrl("/expired") // 세션이 만료된 경우 이동 할 페이지
			;
	}

	
}
