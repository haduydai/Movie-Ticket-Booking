USE movie_ticket_booking;

-- Add cinema_status column to cinemas table (OPEN/CLOSED)
ALTER TABLE cinemas
ADD COLUMN IF NOT EXISTS cinema_status ENUM('OPEN','CLOSED') DEFAULT 'OPEN';

-- Set existing rows to OPEN
UPDATE cinemas SET cinema_status = 'OPEN' WHERE cinema_status IS NULL;