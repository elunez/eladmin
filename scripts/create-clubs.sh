#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

echo "Trying to determine the correct club API endpoint..."

# Try different potential endpoints for club creation
ENDPOINTS=(
  "/api/club"
  "/api/sport/club"
  "/api/clubs"
)

# Get a new token first
TOKEN_RESPONSE=$(curl -s -X POST 'http://localhost:8000/auth/login' \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "admin",
    "password": "123456"
  }')

# Extract the token
NEW_TOKEN=$(echo $TOKEN_RESPONSE | grep -o '"token":"[^"]*' | sed 's/"token":"//')
echo "Using token: $NEW_TOKEN"

for ENDPOINT in "${ENDPOINTS[@]}"; do
  echo "Trying endpoint: $ENDPOINT"
  
  # Check if the endpoint exists (GET request to see if it returns 404)
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X GET "http://localhost:8000$ENDPOINT" \
    -H "Authorization: $NEW_TOKEN")
  
  echo "  Status code: $STATUS"
  
  if [ "$STATUS" != "404" ]; then
    echo "Found working endpoint: $ENDPOINT"
    
    # Create Club 1: SF Tennis Club
    echo "Creating SF Tennis Club..."
    curl -X POST "http://localhost:8000$ENDPOINT" \
      -H "Content-Type: application/json" \
      -H "Authorization: $NEW_TOKEN" \
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
    curl -X POST "http://localhost:8000$ENDPOINT" \
      -H "Content-Type: application/json" \
      -H "Authorization: $NEW_TOKEN" \
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
    curl -X POST "http://localhost:8000$ENDPOINT" \
      -H "Content-Type: application/json" \
      -H "Authorization: $NEW_TOKEN" \
      -d '{
        "name": "LA Pickleball Center",
        "description": "Southern California\'s largest dedicated pickleball facility.",
        "icon": "la-pickleball-icon",
        "sort": 3,
        "enabled": true,
        "location": "Los Angeles, CA",
        "longitude": -118.2437,
        "latitude": 34.0522
      }'
    echo
    
    # Try to list clubs to verify they were created
    echo "Listing clubs:"
    curl -X GET "http://localhost:8000$ENDPOINT" \
      -H "Content-Type: application/json" \
      -H "Authorization: $NEW_TOKEN"
    echo
    
    break
  fi
done

if [ "$STATUS" == "404" ]; then
  echo "All endpoints returned 404. The club API may not be properly deployed."
  echo "Checking if we can access the sport API:"
  curl -s -X GET "http://localhost:8000/api/sport" \
    -H "Content-Type: application/json" \
    -H "Authorization: $NEW_TOKEN"
fi
