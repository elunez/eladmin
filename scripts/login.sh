#!/bin/bash

# Login and get JWT token
response=$(curl -s -X POST 'http://localhost:8000/auth/login' \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "admin",
    "password": "123456"
  }')

# Extract the token
token=$(echo $response | grep -o '"token":"[^"]*' | sed 's/"token":"//')

echo "Your JWT token is: $token"
echo "To export this token as an environment variable, run:"
echo "export JWT_TOKEN=\"$token\""
