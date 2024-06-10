CREATE TABLE if not exists station (
    id SERIAL PRIMARY KEY,
    station VARCHAR(50) NOT NULL
);

CREATE TABLE if not exists "order" (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    from_station_id INT NOT NULL,
    to_station_id INT NOT NULL,
    status INT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_station_id) REFERENCES station(id),
    FOREIGN KEY (to_station_id) REFERENCES station(id)
);

insert into station(id, station) values
    (1, 'Moscow'),
    (2, 'Kazan'),
    (3, 'New York'),
    (4, 'Berlin'),
    (5, 'Peru'),
    (6, 'Crimea'),
    (7, 'Vladivostok');


