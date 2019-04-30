#!/bin/bash
echo "Installing chat system"
read -p "Are you sure you want to install? "
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    exit 1
else
   echo "Installing"
fi
