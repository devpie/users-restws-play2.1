# --- First database schema
 
# --- !Ups
 
CREATE TABLE user (
  id                        SERIAL PRIMARY KEY,
  username                  VARCHAR(255) NOT NULL,
  firstname		     VARCHAR(255) NOT NULL,
  lastname		     VARCHAR(255) NOT NULL
);
 
# --- !Downs
 
DROP TABLE IF EXISTS user;