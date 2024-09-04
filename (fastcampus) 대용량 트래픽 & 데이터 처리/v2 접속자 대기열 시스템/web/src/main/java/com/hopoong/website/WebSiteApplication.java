package com.hopoong.website;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootApplication
@Controller
public class WebSiteApplication {

	RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(WebSiteApplication.class, args);
	}


	@GetMapping("/")
	public String index(
			@RequestParam(name = "queue", defaultValue = "default") String queue,
			@RequestParam(name = "userId") Long userId,
			HttpServletRequest request
	) {

		var cookies = request.getCookies();
		var cookeName = "user-queue-%s-token".formatted(queue);

		String token = "";
		if(cookies != null) {
			var cookie = Arrays.stream(cookies).filter(f -> f.getName().equalsIgnoreCase(cookeName)).findFirst();
			token = cookie.orElse(new Cookie(cookeName, "")).getValue();
		}

		var uri = UriComponentsBuilder
					.fromUriString("http://localhost:9100")
					.path("/api/v1/queue/allowed")
					.queryParam("userId", userId)
					.queryParam("queue", queue)
					.queryParam("token", token)
					.encode()
					.build()
					.toUri();

		ResponseEntity<AllowedUserResponse> response = restTemplate.getForEntity(uri, AllowedUserResponse.class);
		if(response.getBody() == null || !response.getBody().allowed()) {
			// 대기 웹페이지로 리다이렉트
			return "redirect:http://localhost:9100/watiting-room?userId=%d&redirectUrl=%s".formatted(
					userId, "http://localhost:9000?userId=%d".formatted(userId)
			);
		}

		// 허용 상태라면 해당 페이지를 진입.
		return "index";
	}

	public  record AllowedUserResponse(Boolean allowed) { }
}

