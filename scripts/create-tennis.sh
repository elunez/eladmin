#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

# Create Tennis sport
curl -X POST "http://localhost:8000/api/sport" \
  -H "Content-Type: application/json" \
  -H "Authorization: $JWT_TOKEN" \
  -d '{
    "name": "Tennis",
    "description": "A racquet sport played between two players or two teams of two players each.",
    "icon": "tennis-icon",
    "sort": 2,
    "enabled": true
  }'

echo -e "\nTennis sport created successfully!"
