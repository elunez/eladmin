#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

# List all sports
curl -X GET "http://localhost:8000/api/sport" \
  -H "Content-Type: application/json" \
  -H "Authorization: $JWT_TOKEN" | json_pp

echo -e "\nSports listed successfully!"
