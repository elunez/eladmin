#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

# Create Pickleball sport
curl -X POST "http://localhost:8000/api/sport" \
  -H "Content-Type: application/json" \
  -H "Authorization: $JWT_TOKEN" \
  -d '{
    "name": "Pickleball",
    "description": "A paddle sport that combines elements of tennis, badminton, and table tennis, played with a perforated plastic ball and solid paddles.",
    "icon": "pickleball-icon",
    "sort": 3,
    "enabled": true
  }'

echo -e "\nPickleball sport created successfully!"
