#OSM Boundary Service für Köln

##Überblick

Dieser Service liefert die Umrisse für Köln für die Stadtteile, die Stadtbezirke und die Stadt selber im geoJson Format. Grundlage hierfür sind die OSM Daten. Der Service gibt für bereits erstellte Datensätze die abgespeicherten Ergebnisse zurück. 

##Funktionsweise

Der Service ist in Java geschrieben. Er läßt sich mit maven bauen.
- checkout git
- mvn clean install

Die Antwort ist ein geoJson-Datenstruktur. Die Beispielhafte Einbindung des Services in Leaflet ist in index.html umgesetzt.

Die Installation erfolgt als Webapplikation in einen Application-Server. Ich habe ihn nur auf Basis von Tomcat entwickelt. Aus diesem Grund wird die Verwendung eines Tomcat vorgeschlagen. 

Folgende REST-Endpoints stehen zur Verfügung:
- /boundariesosmkoeln/service/&lt;level&gt;; Gibt die Umrisse der Stadtteile von Köln zurück mit throshold 10
- /boundariesosmkoeln/service/&lt;level&gt;/&lt;threshold&gt;; Gibt die Umrisse der Stadtteile von Köln zurück mit angegebenem threshold 
- /boundariesosmkoeln/service/generate; Erstellt den Umriss

Der Service erlaubt es die Umrisse zu vereinfachen, mit dem Ergebnis, dass der Datenumfang der zu ladenden Daten sich verringert. Die Vereinfachung erfolgt auf Datenbankebene und wird durch anhängen des threshold angefordert. Dabei steht die Zahl für Auflösung; z.B.
- /boundariesosmkoeln/service/stadteile/100 vereinfacht auf 100m Genauigkeit
- /boundariesosmkoeln/service/stadteile/1000 vereinfacht auf 1000m Genauigkeit

Die Generalisierung wird mit dem Douglas-Peucker Algorithmus, auf Basis von PostGis umgesetzt. Dieser verwirft jeden Punkt, dess Entferung vom Segment geringer ist, als der übergebene Schwellwert. Das Problem hierbei ist generell, dass benachbarte Polygone entweder nicht mehr direkt benachbart sind, oder sich an einigen Stellen überlappen.

Um dies zu verhindern, wird die Anfrage in Anlehnung an [An example showing how to simplify a multipolygon layer, keeping topology between objects](https://trac.osgeo.org/postgis/wiki/UsersWikiSimplifyPreserveTopology) umgesetzt.

##Definition der DB-Verbindung

Der Service benötigt ggf. eine postgresql-Datenbank. Die Verbindung wird über jndi im Tomcat definiert. Hierfür ist folgender Eintrag in der context.xml des Tomcat notwendig:

&gt;Resource 
	name="jdbc/postgresql" 
	auth="Container" 
	type="javax.sql.DataSource"
	username="username" 
	password="password" 
	driverClassName="org.postgresql.Driver"
	url="jdbc:postgresql://server:5432/dbname" 
	maxTotal="25" 
	maxIdle="10"
	validationQuery="select 1" /&lt;

## DB-Abfragen

Die Abfragen selber werden per prepared Statement durchgeführt. Die Definition der Statements erfolgt als resource und wird von der jeweiligen AskFor-Klasse aus den resources geladen. 

##admin_level

Dabei steht der admin_level für folgende Aufteilung in Deutschland:
1 - nicht verwendet
2 - deutsche Aussengrenze
3 - nicht verwendet
4 - Bundesland
5 - Regierungsbezirk
6 - Landkreis / Kreis / kreisfreie Stadt / Stadtkreis 
7 - Amtsgemeinde, Verwaltungsgemeinschaft
8 - Stadt, Gemeinde
9 - Stadtbezirk / Gemeindeteil mit Selbstverwaltung 
10 - Stadtteil / Gemeindeteil ohne Selbstverwaltung

## mögliche Abfragen

Die Abfragen gegen die Datenbank sind recht zeitaufwändig. Z.B. benötigt bei mir die Abfrage auf die Stadtteile in Köln ca 10 Minuten. Um die Zeit abzukürzen, sind dieser Webanwendung bereits berechnete GeoJson Strings hinterlegt, die im Normalfall ausgeliefert werden. Durch den Endpunkt "generate" (Beispiel: /boundariesosmkoeln/service/generate) wird die Erstellung des Polygons gegen die Datenbank in jedem Fall ausgeführt. Mit Übergabeparametern können spezielle Polygon berechnet werden (Beispiel: /boundariesosmkoeln/service/generate?level=stadtteile&threshold=250). Das Ergebnis dieser Berechnung wird im Filesystem abgelegt. Der Ort dafür wird in der applicatin.properties for Start des Servers eingetragen und als Ergebnis des Service zurückgegeben.

Bereits berechnet Daten sind in diesem Projekt auf github hinterlegt und können so direkt verwendet werden.
