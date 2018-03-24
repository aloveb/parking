# parking

**work in progress**

Quick start
-----------

To run the app stand-alone with an embedded Tomcat simply run the following command:

``mvn spring-boot:run -Drun.profiles=dev,standalone``

To create a `WAR` file that can be deployed to **SAP HANA Cloud Platform** (Tomcat 8 runtime) execute the following command:

``mvn -P neo clean package install``

> **NOTE:** If you deploy the app o **SAP HANA Cloud Platform** do not forget to specify the following JVM argument `-Dspring.profiles.active=neo`.  

> **NOTE:** As outlined in the [official online documentation](https://help.hana.ondemand.com/help/frameset.htm?e6e8ccd3bb571014b6afdc54744eef4d.html) the assembled `WAR` file MUST not include any `SLF4J` or `logback` related jar files, hence we flag the `spring-boot-starter-logging` dependency as `provided`(by the runtime).


To create a `jar` file that can be deployed to **SAP HANA Cloud Platform, Starter Edition for Cloud Foundry Services (Beta)** execute the following command:

``mvn -P cf clean package install``
