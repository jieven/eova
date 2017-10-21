@echo off
title Eova 1.6 Beta2

mvn clean compile jetty:start

echo 按任意键结束
pause
exit