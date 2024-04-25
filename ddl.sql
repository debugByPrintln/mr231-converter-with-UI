CREATE TABLE searadar.vhw_message (
    id SERIAL PRIMARY KEY,
    msgRecTime TIMESTAMP,
    course DOUBLE PRECISION,
    courseAttr VARCHAR(255),
    speed DOUBLE PRECISION,
    speedUnit VARCHAR(50)
);

CREATE TABLE searadar.rsd_message (
    id SERIAL PRIMARY KEY,
    msgRecTime TIMESTAMP,
    initialDistance DOUBLE PRECISION,
    initialBearing DOUBLE PRECISION,
    movingCircleOfDistance DOUBLE PRECISION,
    bearing DOUBLE PRECISION,
    distanceFromShip DOUBLE PRECISION,
    bearing2 DOUBLE PRECISION,
    distanceScale DOUBLE PRECISION,
    distanceUnit VARCHAR(50),
    displayOrientation VARCHAR(50),
    workingMode VARCHAR(50)
);

CREATE TABLE searadar.ttm_message (
    id SERIAL PRIMARY KEY,
    msgRecTime TIMESTAMP,
    msgTime BIGINT
    targetNumber INT,
    distance DOUBLE PRECISION,
    bearing DOUBLE PRECISION,
    course DOUBLE PRECISION,
    speed DOUBLE PRECISION,
    type VARCHAR(50) CHECK (type in ('SURFACE', 'AIR', 'UNKNOWN')),
    status VARCHAR(50) CHECK (status in ('TRACKED', 'LOST', 'UNRELIABLE_DATA')),
    iff VARCHAR(50) CHECK (iff in ('FRIEND', 'FOE', 'UNKNOWN'))
);
