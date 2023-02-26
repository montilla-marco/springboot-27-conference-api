CREATE TABLE IF NOT EXISTS attendees
(
    attendee_id  SERIAL PRIMARY KEY,
    first_name   varchar(30) NOT NULL,
    last_name    varchar(30) NOT NULL,
    title        varchar(40) NULL,
    company      varchar(50) NULL,
    email        varchar(80) NOT NULL,
    phone_number varchar(20) NULL
);

CREATE TABLE IF NOT EXISTS ticket_types
(
    ticket_type_code  varchar(1) PRIMARY KEY,
    ticket_type_name  varchar(30)  NOT NULL,
    description       varchar(100) NOT NULL,
    includes_workshop boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS pricing_categories
(
    pricing_category_code varchar(1) PRIMARY KEY,
    pricing_category_name varchar(20) NOT NULL,
    pricing_start_date    date        NOT NULL,
    pricing_end_date      date        NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_prices
(
    ticket_price_id       SERIAL PRIMARY KEY,
    ticket_type_code      varchar(1)    NOT NULL REFERENCES ticket_types (ticket_type_code),
    pricing_category_code varchar(1)    NOT NULL REFERENCES pricing_categories (pricing_category_code),
    base_price            numeric(8, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS discount_codes
(
    discount_code_id SERIAL PRIMARY KEY,
    discount_code    varchar(20)   NOT NULL,
    discount_name    varchar(30)   NOT NULL,
    discount_type    varchar(1)    NOT NULL,
    discount_amount  numeric(8, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS attendee_tickets
(
    attendee_ticket_id SERIAL PRIMARY KEY,
    attendee_id        integer       NOT NULL REFERENCES attendees (attendee_id),
    ticket_price_id    integer       NOT NULL REFERENCES ticket_prices (ticket_price_id),
    discount_code_id   integer       NULL REFERENCES discount_codes (discount_code_id),
    net_price          numeric(8, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS time_slots
(
    time_slot_id         SERIAL PRIMARY KEY,
    time_slot_date       date                   NOT NULL,
    start_time           time  					NOT NULL,
    end_time             time  					NOT NULL,
    is_keynote_time_slot boolean default false  NOT NULL
);

CREATE TABLE IF NOT EXISTS sessions
(
    session_id          integer PRIMARY KEY,
    session_name        varchar(80)   NOT NULL,
    session_description varchar(1024) NOT NULL,
    session_length      integer       NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS sessions_seq AS INTEGER START WITH 100;

CREATE TABLE IF NOT EXISTS session_schedule
(
    schedule_id  SERIAL PRIMARY KEY,
    time_slot_id integer     NOT NULL REFERENCES time_slots (time_slot_id),
    session_id   integer     NOT NULL REFERENCES sessions (session_id),
    room         varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS tags
(
    tag_id      SERIAL PRIMARY KEY,
    description varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS session_tags
(
    session_id integer NOT NULL REFERENCES sessions (session_id),
    tag_id     integer NOT NULL REFERENCES tags (tag_id)
);

CREATE TABLE IF NOT EXISTS speakers
(
    speaker_id    SERIAL PRIMARY KEY,
    first_name    varchar(30)   NOT NULL,
    last_name     varchar(30)   NOT NULL,
    title         varchar(40)   NOT NULL,
    company       varchar(50)   NOT NULL,
    speaker_bio   varchar(2000) NOT NULL,
    speaker_photo BLOB   		NULL
);

CREATE TABLE IF NOT EXISTS session_speakers
(
    session_id integer NOT NULL REFERENCES sessions (session_id),
    speaker_id integer NOT NULL REFERENCES speakers (speaker_id)
);

CREATE TABLE IF NOT EXISTS workshops
(
    workshop_id   SERIAL PRIMARY KEY,
    workshop_name varchar(60)   NOT NULL,
    description   varchar(1024) NOT NULL,
    requirements  varchar(1024) NOT NULL,
    room          varchar(30)   NOT NULL,
    capacity      integer       NOT NULL
);

CREATE TABLE IF NOT EXISTS workshop_speakers
(
    workshop_id integer NOT NULL REFERENCES workshops (workshop_id),
    speaker_id  integer NOT NULL REFERENCES speakers (speaker_id)
);

CREATE TABLE IF NOT EXISTS workshop_registrations
(
    workshop_id        integer NOT NULL REFERENCES workshops (workshop_id),
    attendee_ticket_id integer NOT NULL REFERENCES attendee_tickets (attendee_ticket_id)
);
