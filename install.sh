#!/bin/bash
echo "Installing chat system"
read -p "Are you sure you want to install? " -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    exit 1
else
   echo "Installing"
fi
