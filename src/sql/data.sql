create table systemUser(
                           _id UUID DEFAULT gen_random_uuid() NOT NULL,
                           account  VARCHAR(128) NOT NULL,
                           password VARCHAR(128),
                           name     VARCHAR(128),
                           PRIMARY KEY (_id),
                           UNIQUE (account)
);



create table goods
(
    _id UUID DEFAULT gen_random_uuid() NOT NULL,
    name        VARCHAR(128) NOT NULL,
    cr_user     UUID         NOT NULL,
    cr_datetime TIMESTAMP DEFAULT CURRENT_DATE,
    up_user     UUID         NOT NULL,
    up_datetime TIMESTAMP DEFAULT CURRENT_DATE,
    PRIMARY KEY (_id),
    FOREIGN KEY (cr_user) REFERENCES systemUser (_id),
    FOREIGN KEY (up_user) REFERENCES systemUser (_id)
);
