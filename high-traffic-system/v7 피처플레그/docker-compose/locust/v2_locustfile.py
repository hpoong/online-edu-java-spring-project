from locust import FastHttpUser, task, between
import random

# 사용자 클래스 정의
class WebsiteUser(FastHttpUser):
    wait_time = between(1, 3)

    keySet = ["test", "service1", "service2", "service3", "service4"]

    @task
    def scenario(self):
        # 랜덤 인덱스와 정수 생성
        randomKeyIdx = random.randint(0, 4)
        randomKeyInt = random.randint(1, 10000)

        # 시나리오 2: 특정 조건에 따라 인덱스 변경
        if randomKeyIdx == 0 or randomKeyIdx == 1 or randomKeyIdx == 2:
            randomKeyIdx = 0

        keyId = f"{self.keySet[randomKeyIdx]}-{randomKeyInt}"

        # HTTP GET 요청 전송
        self.client.get(f"/api/v1/resolve/string/{keyId}")
