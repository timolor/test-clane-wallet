CREATE TABLE IF NOT EXISTS users(
    id serial PRIMARY KEY,
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    phone varchar(100) UNIQUE NULL,
    isVerified boolean,
    kyc_level_id INT NULL,
    address varchar(100) NULL,
    created_by INT NULL,
    last_modified_by INT NULL,
    date_created TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    FOREIGN KEY (kyc_level_id) REFERENCES kyc_levels(id)
);

CREATE TABLE IF NOT EXISTS wallets(
    id serial PRIMARY KEY,
    user_id INT NOT NULL,
    balance MONEY NOT NULL,
    account_number varchar(100) UNIQUE NOT NULL,
    created_by INT NULL,
    last_modified_by INT NULL,
    date_created TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS transactions(
    id serial PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    amount MONEY NOT NULL,
    payment_reference varchar(255) UNIQUE NOT NULL,
    created_by INT NULL,
    last_modified_by INT NULL,
    date_created TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS kyc_levels(
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    kyc_limit MONEY NOT NULL,
    created_by INT NULL,
    last_modified_by INT NULL,
    date_created TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL
);
