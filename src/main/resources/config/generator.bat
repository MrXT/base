@echo off
set windowfile=start.window.bat
set linuxfile=start.linux.sh
set linuxStopfile=stop.linux.sh
set jarName=%1
set dirName=%2
if exist target\%dirName%\%windowfile% del /q target\%dirName%\%windowfile%
if exist target\%dirName%\%linuxfile% del /q target\%dirName%\%linuxfile%

echo ^@echo off>> target\%dirName%\%windowfile%
echo.
echo start javaw -Dfile.encoding=utf-8 -jar %jarName%>> target\%dirName%\%windowfile%
echo.
echo echo "Sure Success? Please Scan 'logs/info.log' contains 'Started ProjectApplication'" >> target\%dirName%\%windowfile%
echo.
echo pause>> target\%dirName%\%windowfile%

echo nohup java -Dfile.encoding=utf-8 -jar %jarName% ^&^> /dev/null ^& echo "Sure Success? Please Scan 'logs/info.log' contains 'Started ProjectApplication'" >> target\%dirName%\%linuxfile%

echo cat self.pid ^|xargs kill -9 >> target\%dirName%\%linuxStopfile%

