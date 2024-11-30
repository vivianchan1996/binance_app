-- Create trading price
CREATE TABLE trading_price (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               cryptoCurr VARCHAR(10) NOT NULL,
                               bidPrice DOUBLE NOT NULL,
                               askPrice DOUBLE NOT NULL,
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create transaction history
CREATE TABLE transaction_history (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     cryptoCurr VARCHAR(10) NOT NULL,
                                     action VARCHAR(10) NOT NULL,
                                     quantity DOUBLE NOT NULL,
                                     price DOUBLE NOT NULL,
                                     total DOUBLE NOT NULL,
                                     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Create user balance table
CREATE TABLE user_wallet_balance (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     currency VARCHAR(10) NOT NULL,
                                     balance DOUBLE NOT NULL,
                                     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Initialize user wallet balance
INSERT INTO user_wallet_balance (user_id, currency, balance) VALUES (1, 'USDT', 50000);
INSERT INTO user_wallet_balance (user_id, currency, balance) VALUES (1, 'BTC', 0);
INSERT INTO user_wallet_balance (user_id, currency, balance) VALUES (1, 'ETH', 0);
