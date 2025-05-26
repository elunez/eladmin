#!/bin/bash

# Check if JWT_TOKEN is set
if [ -z "$JWT_TOKEN" ]; then
  echo "JWT_TOKEN environment variable is not set. Please run login.sh first and export the token."
  echo "Example: export JWT_TOKEN=\"Bearer eyJhbGciOiJIUzUxMiJ9...\""
  exit 1
fi

# Add sport permissions to the admin role (ID: 1)
curl -X PUT "http://localhost:8000/api/roles" \
  -H "Content-Type: application/json" \
  -H "Authorization: $JWT_TOKEN" \
  -d '{
    "id": 1,
    "name": "超级管理员",
    "level": 1,
    "description": "超级管理员",
    "dataScope": "全部",
    "depts": [],
    "menus": [],
    "permission": "admin,sport:list,sport:add,sport:edit,sport:del,club:list,club:add,club:edit,club:del"
  }'

echo -e "\nAdded sport permissions to admin role"
