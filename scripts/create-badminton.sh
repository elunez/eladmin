#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

# Create Badminton sport
curl -X POST "http://localhost:8000/api/sport" \
  -H "Content-Type: application/json" \
  -H "Authorization: $JWT_TOKEN" \
  -d '{
    "name": "Badminton",
    "description": "A racquet sport played using racquets to hit a shuttlecock across a net.",
    "icon": "badminton-icon",
    "sort": 1,
    "enabled": true
  }'

echo -e "\nBadminton sport created successfully!"
