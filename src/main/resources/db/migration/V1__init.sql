CREATE TABLE payments (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL,
                          amount NUMERIC(19, 2) NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          method VARCHAR(50) NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE transactions (
                              id UUID PRIMARY KEY,
                              payment_id UUID NOT NULL REFERENCES payments(id) ON DELETE CASCADE,
                              type VARCHAR(50) NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_payments_user_id ON payments(user_id);
CREATE INDEX idx_transactions_payment_id ON transactions(payment_id);