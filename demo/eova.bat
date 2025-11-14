@echo off

setlocal & pushd

rem Java run main class
set MAIN_CLASS=cn.eova.meta.RunEovaMeta

set "JAVA_OPTS=-Xms256m -Xmx1024m -Dundertow.port=9090 -Dundertow.host=0.0.0.0"
rem set "JAVA_OPTS=-Dundertow.port=80 -Dundertow.host=0.0.0.0"


set APP_BASE_PATH=%~dp0
set CP=%APP_BASE_PATH%config;%APP_BASE_PATH%lib\*
echo starting jfinal undertow
java -Xverify:none %JAVA_OPTS% -cp %CP% %MAIN_CLASS%
rem If the service is lagging, parameters can be added and output to a file: >> console.log 2>&1 &

endlocal & popd
pause