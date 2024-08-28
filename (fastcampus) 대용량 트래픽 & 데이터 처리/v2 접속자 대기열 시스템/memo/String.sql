
set "users:1000:name" greg
set "users:1000:email" greg@naver.com

-- get
get users:1000:email
get users:1000:name

-- mget: 여러 key 조회
mget users:1000:name users:1000:email

-- setnx : key가 존재하면 저장 없으면 저장을 안함.
setnx users:1000:name greg2

-- set : key가 존재 안해도 저장.
set users:1000:name greg2

-- incr: 증가
incr counter
incr counter

--  decr: 감소
decr counter

-- incrby : 10씩 증가.
incrby counter 10

