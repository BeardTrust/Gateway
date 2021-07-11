package com.beardtrust.webapp.gateway;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * The type Authorization header filter used in the authentication process.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	/**
	 * The type Config.
	 */
	public static class Config{

	}

	private final Environment environment;

	/**
	 * Instantiates a new Authorization header filter.
	 *
	 * @param environment the environment
	 */
	@Autowired
	public AuthorizationHeaderFilter(Environment environment) {
		super(Config.class);
		this.environment = environment;
	}



	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if(!request.getHeaders().containsKey("Authorization")){
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer", "");

			if(!isJwtValid(jwt))
			{
				return onError(exchange, "Invalid jwt token", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		};
	}

	/**
	 * This method is called when an error is encountered.
	 *
	 * @param exchange ServerWebExchange object from server-client interaction
	 * @param no_authorization_header String details of the error encountered
	 * @param httpStatus HttpStatus code detailing the server's response
	 * @return the response status is set to complete
	 */
	private Mono<Void> onError(ServerWebExchange exchange, String no_authorization_header, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);

		return response.setComplete();
	}

	/**
	 * This method tests to see if the JSON Web Token is valid.
	 *
	 * @param jwt JSON web token being checked
	 * @return boolean whether JSON token is valid
	 */
	private boolean isJwtValid(String jwt){
		boolean isValid = false;

		String subject = Jwts.parser()
				.setSigningKey(environment.getProperty("token.secret"))
				.parseClaimsJws(jwt)
				.getBody()
				.getSubject();

		if(subject != null & !subject.isEmpty()) isValid = true;

		return isValid;
	}
}
