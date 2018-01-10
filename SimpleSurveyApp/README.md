SimpleSurveyApp - a Spring Boot prototypical application
----------------

How to - wie starte und benutze ich die Anwendung?
	-> die Jar-Datei SimpleSurveyApp-0.0.1 per Kommandokonsole ausführen
	-> es sollte die Start-Prozedur des integrierten Webservers zu sehen sein
	-> wenn die Ausgabe "Started SimpleSurveyAppApplication in x.x seconds (JVM running for 6.009)" auf der Konsole zu sehen ist, ist der Webserver vollständig hochgefahren
	-> Aufruf der URL http://localhost:8080/ führt direkt zur Login-Maske
	-> bei erstmaligen Start wird ein Standard-Admin-Benutzer erstellt (Login: admin; Passwort: admin01)
	
-> Selbstverständlich können die Quellen auch per IDE eingelesen werden; dann kann der Webserver mittels SimpleSurveyAppApplication -> Run as -> Java Application 
ebenso gestartet werden	

How to - wie kann ich den Quellcode einsehen und eventuell verändern?
	-> der Source-Code kann (sofern nicht bereits aufgerufen) aus folgendem GitHub-Repository akquiriert werden:
		https://github.com/lfko/spring/tree/master/SimpleSurveyApp
	-> in einer IDE kann der Code dann importiert werden	
	-> sollte es beim build zu einem Fehler bzgl. Datenbankschema kommen, muss in der application.properties der Wert für "spring.jpa.hibernate.ddl-auto" von "update" auf "create-drop" umgestellt werden, um eine saubere Neuinstallation der Datenbank/Tabellen zu erzwingen
	
How to - wie erhalte ich Zugriff auf die H2-Datenbank?
	-> nach dem Start der Anwendung kann die Datenbank über http://localhost:8080/console aufgerufen werden
	-> Die Voreinstellung in der Login-Maske sind zu übernehmen	