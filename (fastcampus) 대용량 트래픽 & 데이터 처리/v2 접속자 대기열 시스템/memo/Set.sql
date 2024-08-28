
-- sadd : Set 데이터 저장
sadd "user:100:follow" 150 130 120

-- scard : count 조회
scard user:100:follow

sadd user:100:follow 150

-- smembers: 전체 데이터 조회
smembers user:100:follow


sadd "user:200:follow" 150 130

-- sinter: 값이 중복되는 데이터 조회
sinter user:100:follow user:200:follow

-- srem : key의 value 값 삭제
srem user:100:follow 150
















