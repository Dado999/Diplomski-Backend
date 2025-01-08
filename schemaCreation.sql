-- -----------------------------------------------------
-- Schema diplomski
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS diplomski CASCADE;
CREATE SCHEMA diplomski;

-- -----------------------------------------------------
-- Table diplomski.bank
-- -----------------------------------------------------
CREATE TABLE diplomski.bank (
  id SERIAL PRIMARY KEY,
  name VARCHAR(45),
  location VARCHAR(45),
  address VARCHAR(45)
);

-- -----------------------------------------------------
-- Table diplomski.users
-- -----------------------------------------------------
CREATE TABLE diplomski.users (
  id SERIAL PRIMARY KEY,
  bank_id INTEGER NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(255) NOT NULL,
  CONSTRAINT fk_users_bank FOREIGN KEY (bank_id)
    REFERENCES diplomski.bank (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table diplomski.account_type
-- -----------------------------------------------------
CREATE TABLE diplomski.account_type (
  id SERIAL PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table diplomski.account
-- -----------------------------------------------------
CREATE TABLE diplomski.account (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  account_type_id INTEGER NOT NULL,
  account_number VARCHAR(15) NOT NULL UNIQUE,
  bank_id INTEGER NOT NULL,
  balance NUMERIC(15, 2) DEFAULT 0,
  CONSTRAINT fk_account_users FOREIGN KEY (user_id)
    REFERENCES diplomski.users (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_account_account_type FOREIGN KEY (account_type_id)
    REFERENCES diplomski.account_type (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_account_bank FOREIGN KEY (bank_id)
    REFERENCES diplomski.bank (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table diplomski.worker
-- -----------------------------------------------------
CREATE TABLE diplomski.worker (
  id SERIAL PRIMARY KEY,
  users_id INTEGER NOT NULL,
  CONSTRAINT fk_worker_users FOREIGN KEY (users_id)
    REFERENCES diplomski.users (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table diplomski.transaction
-- -----------------------------------------------------
CREATE TABLE diplomski.transaction (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  from_account_id INTEGER NOT NULL,
  to_account_id INTEGER NOT NULL,
  amount NUMERIC(15, 2) NOT NULL,
  CONSTRAINT fk_transaction_users FOREIGN KEY (user_id)
    REFERENCES diplomski.users (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaction_from_account FOREIGN KEY (from_account_id)
    REFERENCES diplomski.account (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaction_to_account FOREIGN KEY (to_account_id)
    REFERENCES diplomski.account (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- -----------------------------------------------------
-- Table diplomski.transaction_status
-- -----------------------------------------------------
CREATE TABLE diplomski.transaction_status (
  id SERIAL PRIMARY KEY,
  code VARCHAR(10) NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table diplomski.transaction_log
-- -----------------------------------------------------
CREATE TABLE diplomski.transaction_log (
  id SERIAL PRIMARY KEY,
  transaction_id INTEGER NOT NULL,
  log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status_id INTEGER NOT NULL,
  updated_by_worker_id INTEGER NOT NULL,
  CONSTRAINT fk_transaction_log_transaction FOREIGN KEY (transaction_id)
    REFERENCES diplomski.transaction (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaction_log_transaction_status FOREIGN KEY (status_id)
    REFERENCES diplomski.transaction_status (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaction_log_worker FOREIGN KEY (updated_by_worker_id)
    REFERENCES diplomski.worker (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
