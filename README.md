# Reportum
This is weekly report

#Bug fix
1.Oracle database issue ORA-12505:
oracle.net.ns.NetException: Listener refused the connection with the following error:
ORA-12505, TNS:listener does not currently know of SID given in connect descriptor

>Sotution:
>Go to the windows machine that hosts the Oracle database server
>Go to Start -> Run -> Services.msc in windows. Locate OracleService (OracleServiceORCL or OracleServiceXE) and click on "Start" to start the oracle    database service (if not already running).
