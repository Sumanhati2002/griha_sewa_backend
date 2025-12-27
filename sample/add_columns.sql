-- SQL Script to Add Missing Columns to listings table
-- Run this in your PostgreSQL database if the columns don't exist

-- Connect to your database first:
-- psql -U postgres -d village_connect

-- Add mobile_number column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'listings' AND column_name = 'mobile_number'
    ) THEN
        ALTER TABLE listings ADD COLUMN mobile_number VARCHAR(255);
        RAISE NOTICE 'Column mobile_number added successfully';
    ELSE
        RAISE NOTICE 'Column mobile_number already exists';
    END IF;
END $$;

-- Add service_date column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'listings' AND column_name = 'service_date'
    ) THEN
        ALTER TABLE listings ADD COLUMN service_date DATE;
        RAISE NOTICE 'Column service_date added successfully';
    ELSE
        RAISE NOTICE 'Column service_date already exists';
    END IF;
END $$;

-- Verify the columns were added
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'listings'
ORDER BY ordinal_position;
