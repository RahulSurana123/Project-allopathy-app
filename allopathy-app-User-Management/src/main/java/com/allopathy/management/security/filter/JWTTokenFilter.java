//package com.allopathy.management.security.filter;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import com.allopathy.management.service.impl.TokenServiceImpl;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class JWTTokenFilter extends BasicAuthenticationFilter {
//
//	TokenServiceImpl tokenServiceImpl;
//	
//	private UserDetailsService userDetailsService;
////	
//	public JWTTokenFilter(AuthenticationManager authenticationManager, TokenServiceImpl tokenServiceImpl,
//			UserDetailsService userDetailsService) {
//		super(authenticationManager);
//		this.tokenServiceImpl = tokenServiceImpl;
//		this.userDetailsService = userDetailsService;
//	}
////
////	@Override
////	public void doFilterInternal(HttpServletRequest request,
////			HttpServletResponse response, FilterChain chain)
////					throws IOException, ServletException {
////		Authentication authentication = getAuthentication(request);
////		log.debug("authentication processed from request is : {}",
////				authentication);
////		if (authentication == null) {
////			chain.doFilter(request, response);
////			return;
////		}
////		SecurityContextHolder.getContext().setAuthentication(authentication);
////		chain.doFilter(request, response);
////	}
////
////	private Authentication getAuthentication(HttpServletRequest request) {
////		String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
////		if (StringUtils.isNotEmpty(token)
////				&& token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
////			try {
////				log.debug("received Authorization header : {}", token);
////				token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
////				Jws<Claims> parsedToken = tokenServiceImpl
////						.parseClaimsJws(token);
////				String username = parsedToken.getBody().getSubject();
////				UserDetails userDetails = userDetailsService
////						.loadUserByUsername(username);
////				if (userDetails != null && userDetails.isAccountNonExpired()
////						&& userDetails.isAccountNonLocked()
////						&& userDetails.isEnabled()) {
////					return new PreAuthenticatedAuthenticationToken(
////							userDetails.getUsername(),
////							request.getHeader(
////									SecurityConstants.TOKEN_HEADER),
////							userDetails.getAuthorities());
////				}
////			} catch (ExpiredJwtException exception) {
////				log.error("Request to parse expired JWT : {} failed : {}",
////						token, exception.getMessage());
////			} catch (UnsupportedJwtException exception) {
////				log.error(
////						"Request to parse unsupported JWT : {} failed : {}",
////						token, exception.getMessage());
////			} catch (MalformedJwtException exception) {
////				log.error("Request to parse invalid JWT : {} failed : {}",
////						token, exception.getMessage());
////			} catch (SignatureException exception) {
////				log.error(
////						"Request to parse JWT with invalid signature : {} failed : {}",
////						token, exception.getMessage());
////			} catch (IllegalArgumentException exception) {
////				log.error(
////						"Request to parse empty or null JWT : {} failed : {}",
////						token, exception.getMessage());
////			}
////		}
////		return null;
////	}
//
//}
