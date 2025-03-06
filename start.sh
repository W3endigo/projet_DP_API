#!/bin/sh
# start.sh

# Wait for the database to be ready
./wait-for-it.sh bdd-projet:3306 --timeout=30 --strict -- echo "Database is up"

# Start the application
java -jar app.jar --spring.profiles.active=dev