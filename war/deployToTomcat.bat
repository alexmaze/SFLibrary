
@echo off
echo   开始Deploy...

D:

dir /b /s "D:\Dev\Tomcat 7.0\webapps\ROOT"|find /v "images" >list.txt


for /f "delims=" %%i in (list.txt) do (del /f /s /q "%%i")


xcopy /s /e /f /exclude:D:\Dev\EclipseWorkspace\SFLibrary\war\uncopy.txt D:\Dev\EclipseWorkspace\SFLibrary\war "D:\Dev\Tomcat 7.0\webapps\ROOT"

del /f /s /q D:\Dev\EclipseWorkspace\SFLibrary\war\list.txt

echo 完成!
echo. & pause
