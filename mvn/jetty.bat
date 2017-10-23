@echo off
title Eova 1.6

mvn clean compile jetty:start

echo Press any key to exit
pause
exit