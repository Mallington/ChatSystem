#!/bin/bash
read -p "Would you like to install Chat System? (Y/N) "
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
   echo "Exiting..."
    exit 1
else
   echo "Installing the latest version of Mallington/ChatSystem from github."
fi
