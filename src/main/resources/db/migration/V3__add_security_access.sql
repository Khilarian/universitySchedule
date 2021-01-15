CREATE table security_users(
id SERIAL PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(30) NOT NULL,
email VARCHAR (50) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(20) DEFAULT 'USER' NOT NULL,
status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL
);