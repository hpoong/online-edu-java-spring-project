package com.hopoong.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication {

	/*
	 * e-commerce 시스템으로 다향의 데이터와 높은 동시 접속자 수 환경을 구성한다.
	 *
	 * 요구사항
	 *	- 판매자는 상품을 등록할수있다.
	 *	- 판매자는 상품의 설명, 수량 등 정보를 관리 할 수 있다.
	 *	- 구매자는 상품을 구매할 수 있다.
	 *	- 구매자는 결제수단을 등록하고 주문시 사용할 수 있다.
	 *	- 구매자는 배송지를 등록하고 주문시 사용할 수 있다.
	 *	- 구매자는 완료된 주문의 상태를 조회할 수 있다.
	 *	- 구매자는 완료된 주문의 배송 상태를 조회할 수 있다.
	 *	- 구매자는 주문 내역 리스트를 볼 수 있다.
	 *	- 구매자는 회원으로 등록할 수 있다.
	 *	- 회원은 로그인을 할 수 있다.
	 *
	 * 서비스
	 *	- catalog
	 *  - payment
	 *  - order
	 *  - delivery
	 *  - member
	 */
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

}
