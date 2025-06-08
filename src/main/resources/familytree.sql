drop database familytree;
create database familytree;
use familytree;

CREATE TABLE family_info (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    family_name VARCHAR(255),
    date_created TIMESTAMP
);

CREATE TABLE person_detail (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    info JSON,
    date_created TIMESTAMP,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    relation_type VARCHAR(50),
    gender VARCHAR(10),
    member_alive BOOLEAN,
    birth_date DATE,
    marriage_date DATE,
    death_date DATE,
    link_this_member_to_a_user BIGINT,
    photo TEXT,
    avatar TEXT,
    facebook VARCHAR(255),
    twitter VARCHAR(255),
    instagram VARCHAR(255),
    email VARCHAR(255),
    home_tel VARCHAR(50),
    website VARCHAR(255),
    birth_place VARCHAR(255),
    death_place VARCHAR(255),
    profession VARCHAR(100),
    company VARCHAR(255),
    bio_notes TEXT
);

CREATE TABLE family_person_map (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    family_id BIGINT REFERENCES family_info(id),
    person_id BIGINT REFERENCES person_detail(id),
    date_added TIMESTAMP
);

CREATE TABLE person_relation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rel1 BIGINT REFERENCES person_detail(id),
    rel2 BIGINT REFERENCES person_detail(id),
    prop VARCHAR(100),
    date_created TIMESTAMP
);

CREATE TABLE member_interest (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    interest VARCHAR(255),
    personid BIGINT REFERENCES person_detail(id)
);

CREATE TABLE phone_info (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country_code VARCHAR(10),
    mob_number VARCHAR(20),
    personid BIGINT REFERENCES person_detail(id)
);

CREATE TABLE relation_desc (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    info VARCHAR(255)
);

CREATE TABLE media (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id BIGINT REFERENCES person_detail(id),
    media_type VARCHAR(50),  -- 'photo', 'video', 'document', etc.
    file_name VARCHAR(255),
    file_type VARCHAR(100),
    file_size BIGINT,
    file_url TEXT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

CREATE TABLE login_history (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES person_detail(id),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    device_info TEXT,
    status VARCHAR(20)            -- 'success', 'failed'
);

CREATE TABLE activity_log (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES person_detail(id),
    action VARCHAR(255),          -- e.g., 'view_profile', 'update_photo'
    description TEXT,
    activity_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_history (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id BIGINT REFERENCES person_detail(id),
    subject VARCHAR(255),               -- NEW: subject/title of the notification
    message TEXT,                       -- plain text message
    content TEXT,                       -- full content (could be HTML or detailed body)
    notification_type VARCHAR(50),     -- 'email', 'sms', 'push', etc.
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20)                 -- 'sent', 'failed', 'pending'
);

CREATE TABLE otp_verification (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id BIGINT REFERENCES person_detail(id),
    otp_code VARCHAR(10),
    purpose VARCHAR(50),               -- 'forgot_password', 'email_verification', 'mobile_verification'
    contact_type VARCHAR(20),          -- 'email' or 'mobile'
    contact_value VARCHAR(255),        -- email address or phone number
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    verified_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'pending'  -- 'pending', 'verified', 'expired'
);

CREATE TABLE search_history (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id BIGINT REFERENCES person_detail(id),
    search_term TEXT,
    search_filters BLOB,               -- optional: to store structured filter info
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),            -- optional: user’s IP
    device_info TEXT                   -- optional: browser or device
);

CREATE TABLE subscription_plan (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    price DECIMAL(10, 2),
    duration_days INT,  -- e.g., 30, 365
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_subscription (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id BIGINT REFERENCES person_detail(id),
    subscription_plan_id BIGINT REFERENCES subscription_plan(id),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20)  -- 'active', 'expired', 'cancelled'
);