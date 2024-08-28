-- List, Set

-- rpush
rpush stack1 100
rpush stack1 200
rpush stack1 300

lrange stack1 0 -1

-- rpop
rpop  stack1
rpop  stack1
rpop  stack1

rpush queue1 100
rpush queue1 200
rpush queue1 300

lrange queue1 0 -1

lpop queue1
lpop queue1
lpop queue1


-- ltrim
rpush queue2 100
rpush queue2 200
rpush queue2 300
rpush queue2 400
rpush queue2 500

lrange queue2 0 -1

ltrim queue2 0 2

lrange queue2 0 -1



rpush queue3 100
rpush queue3 200
rpush queue3 300

blpop queue3 5
blpop queue3 5
blpop queue3 5
blpop queue3 5


-- blpop
rpush queue3 100
rpush queue3 100
rpush queue3 100
blpop queue3 10
blpop queue3 10
blpop queue3 10









