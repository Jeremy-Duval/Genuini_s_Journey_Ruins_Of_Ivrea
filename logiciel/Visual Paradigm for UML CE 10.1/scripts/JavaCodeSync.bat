@echo off
REM VPPRODUCT: VP-UML, BP-VA, DB-VA, AG, SDE-EC, SDE-VS, SDE-IJ, SDE-NB, SDE-JB, SDE-WW or SDE-JD  
if "%VPPRODUCT%" == "" (
	set VPPRODUCT=VP-UML
)

if "%HEADLESS%" == "" (
	set HEADLESS=false
)
pushd ..\bin
..\jre\bin\java -Xms256m -Xmx1024m -Djava.awt.headless=%HEADLESS% -cp ".;..\lib\vpplatform.jar;..\lib\jniwrap.jar;..\lib\winpack.jar;..\ormlib\orm.jar;..\ormlib\orm-core.jar;..\lib\lib01.jar;..\lib\lib02.jar;..\lib\lib03.jar;..\lib\lib04.jar;..\lib\lib05.jar;..\lib\lib06.jar;..\lib\lib07.jar;..\lib\lib08.jar;..\lib\lib09.jar;..\lib\lib10.jar" com.vp.cmd.JavaCodeSync -product %VPPRODUCT% %*
popd
