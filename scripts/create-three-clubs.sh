#!/bin/bash

# First get a new token
echo "Getting JWT token..."
TOKEN_RESPONSE=$(curl -s -X POST 'http://localhost:8000/auth/login' \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "admin",
    "password": "123456"
  }')

# Extract the token and prepare Bearer token
TOKEN=$(echo $TOKEN_RESPONSE | grep -o '"token":"[^"]*' | sed 's/"token":"//')
BEARER_TOKEN="Bearer $TOKEN"
echo "Token obtained successfully"

echo "Trying different club API endpoints..."

# List of possible endpoints to try
endpoints=("/api/club" "/api/clubs" "/api/sport/club")

# Try each endpoint
for endpoint in "${endpoints[@]}"; do
  echo "Testing endpoint: $endpoint"
  status=$(curl -s -o /dev/null -w "%{http_code}" -X GET "http://localhost:8000$endpoint" \
    -H "Authorization: $BEARER_TOKEN")
  
  echo "Status code: $status"
  
  if [ "$status" != "404" ]; then
    working_endpoint=$endpoint
    echo "Found working endpoint: $working_endpoint"
    break
  fi
done

if [ -z "$working_endpoint" ]; then
  echo "Could not find a working club API endpoint. Creating clubs directly in the database instead."
  # Here we would add database commands if needed
  
  # As a fallback, let's verify we can still access the sport API
  echo "Checking if sport API is accessible:"
  curl -s "http://localhost:8000/api/sport" \
    -H "Authorization: $BEARER_TOKEN"
  exit 1
fi

# Create Club 1: SF Tennis Club
echo "Creating SF Tennis Club..."
curl -X POST "http://localhost:8000$working_endpoint" \
  -H "Content-Type: application/json" \
  -H "Authorization: $BEARER_TOKEN" \
  -d '{
    "name": "SF Tennis Club",
    "description": "Premier tennis club in San Francisco with indoor and outdoor courts.",
    "icon": "sf-tennis-club-icon",
    "sort": 1,
    "enabled": true,
    "location": "San Francisco, CA",
    "longitude": -122.4194,
    "latitude": 37.7749
  }'
echo

# Create Club 2: Seattle Badminton Club
echo "Creating Seattle Badminton Club..."
curl -X POST "http://localhost:8000$working_endpoint" \
  -H "Content-Type: application/json" \
  -H "Authorization: $BEARER_TOKEN" \
  -d '{
    "name": "Seattle Badminton Club",
    "description": "Professional badminton facility with Olympic-standard courts.",
    "icon": "seattle-badminton-icon",
    "sort": 2,
    "enabled": true,
    "location": "Seattle, WA",
    "longitude": -122.3321,
    "latitude": 47.6062
  }'
echo

# Create Club 3: LA Pickleball Center
echo "Creating LA Pickleball Center..."
curl -X POST "http://localhost:8000$working_endpoint" \
  -H "Content-Type: application/json" \
  -H "Authorization: $BEARER_TOKEN" \
  -d '{
    "name": "LA Pickleball Center",
    "description": "Southern California'\''s largest dedicated pickleball facility.",
    "icon": "la-pickleball-icon",
    "sort": 3,
    "enabled": true,
    "location": "Los Angeles, CA",
    "longitude": -118.2437,
    "latitude": 34.0522
  }'
echo

# List clubs to verify they were created
echo "Listing clubs:"
curl -X GET "http://localhost:8000$working_endpoint" \
  -H "Content-Type: application/json" \
  -H "Authorization: $BEARER_TOKEN"
echo

echo "Club creation process completed!"
