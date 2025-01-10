#!/bin/bash

# Check if a number is provided as an argument
if [ -z "$1" ]; then
    echo "Usage: $0 <number>"
    exit 1
fi

# Get the number and format it as dayXX
number=$1
formatted_number=$(printf "%02d" "$number")
directory_name="day$formatted_number"

# Create the directory
mkdir -p "$directory_name"

# Create the required files in the directory
cp "template.kts" "$directory_name/Part1.kts"
touch "$directory_name/Part2.kts"

git add --all

touch "$directory_name/sample_input.txt"

url=https://adventofcode.com/2023/day/$1/input
curl -o "$directory_name/input.txt" "$url" -H 'Cookie: session=53616c7465645f5fedbdc72260e39b973cf033fc25b99d3ed8b86576ce609afd0874f17c8f3e548afe2989444b532dc8c652c6dfdac8c79ab1b53b250fc00a0b'

cd $directory_name