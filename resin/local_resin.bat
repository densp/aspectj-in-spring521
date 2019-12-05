rem set JAVA_HOME=C:\Java\jdk-11.0.5.10-hotspot
set JAVA_HOME=c:\java\jdk1.8.0_201
set RESIN_HOME=c:\resin\resin-pro-4.0.62

call %JAVA_HOME%\bin\java -jar %RESIN_HOME%\lib\resin.jar -conf %~dp0\resin.4.portal.conf.xml -server main-app-server %1