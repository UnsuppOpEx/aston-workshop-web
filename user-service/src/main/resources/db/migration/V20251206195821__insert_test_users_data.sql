insert into users (id, name, email, age, created_at)
values (gen_random_uuid(), 'Alice', 'alice@example.com', 25, now()),
       (gen_random_uuid(), 'Bob', 'bob@example.com', 30, now()),
       (gen_random_uuid(), 'David', 'David@example.com', 33, now()),
       (gen_random_uuid(), 'Jonn', 'Jonn@example.com', 45, now()),
       (gen_random_uuid(), 'Fred', 'Fred@example.com', 64, now());
