INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Paris', 'France', 180.00, 95.00, 85.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Paris');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Rome', 'Italy', 150.00, 85.00, 95.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Rome');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Barcelona', 'Spain', 140.00, 80.00, 80.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Barcelona');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Amsterdam', 'Netherlands', 190.00, 100.00, 75.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Amsterdam');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Prague', 'Czech Republic', 110.00, 65.00, 70.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Prague');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Vienna', 'Austria', 160.00, 85.00, 90.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Vienna');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Budapest', 'Hungary', 100.00, 60.00, 65.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Budapest');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Lisbon', 'Portugal', 130.00, 75.00, 85.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Lisbon');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Berlin', 'Germany', 140.00, 80.00, 75.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Berlin');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Athens', 'Greece', 120.00, 70.00, 55.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Athens');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Copenhagen', 'Denmark', 200.00, 110.00, 110.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Copenhagen');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Venice', 'Italy', 170.00, 95.00, 100.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Venice');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Dubrovnik', 'Croatia', 160.00, 90.00, 130.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Dubrovnik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Reykjavik', 'Iceland', 220.00, 120.00, 150.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Reykjavik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost)
SELECT 'Zurich', 'Switzerland', 240.00, 130.00, 140.00
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Zurich');