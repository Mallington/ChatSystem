#!/bin/bash
read -p "Would you like to install Chat System?"
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    exit 1
else
   echo "Installing the latest version of Mallington/ChatSystem from github."
fi
