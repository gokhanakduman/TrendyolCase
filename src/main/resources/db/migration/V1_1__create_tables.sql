CREATE TABLE IF NOT EXISTS product_link_access
(
    id SERIAL PRIMARY KEY,
    content_id varchar(32) NOT NULL,
    campaign_id varchar(32) DEFAULT NULL,
    merchant_id varchar(32) DEFAULT NULL,
    request_uri varchar(255) NOT NULL,
    response_uri varchar(255) NOT NULL,
    request_date timestamp default now() NOT NULL,
    request_source varchar(20) default 'WEB' NOT NULL
);

CREATE TABLE IF NOT EXISTS search_link_access
(
    id SERIAL PRIMARY KEY,
    query varchar(255) NOT NULL,
    request_uri varchar(255) NOT NULL,
    response_uri varchar(255) NOT NULL,
    request_date timestamp default now() NOT NULL,
    request_source varchar(20) default 'WEB' NOT NULL
);

CREATE TABLE IF NOT EXISTS default_link_access
(
    id SERIAL PRIMARY KEY,
    request_path varchar(255) NOT NULL,
    request_uri varchar(255) NOT NULL,
    response_uri varchar(255) NOT NULL,
    request_date timestamp default now() NOT NULL,
    request_source varchar(20) default 'WEB' NOT NULL
);

INSERT INTO search_link_access(query, request_uri, response_uri,  request_source) VALUES('a','a','a','a');