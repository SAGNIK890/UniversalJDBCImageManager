1. Universal JDBC Image Manager

Universal JDBC Image Manager is a Java Swing desktop application designed to simplify the visualization and management of images stored in relational databases. The application dynamically connects to JDBC-compliant databases and enables users to retrieve, preview, upload, download, search, and manage images stored as BLOB/LONGBLOB data through an intuitive graphical interface.

Unlike traditional implementations that rely on hardcoded database schemas, this application allows runtime configuration of database connection details, table names, image columns, and primary key columns, making it adaptable to different database structures without requiring code modifications.

2. Features

* Dynamic JDBC database connectivity
* Runtime database configuration
* Dynamic table selection
* Dynamic image column selection
* Dynamic primary key selection
* Image retrieval from BLOB/LONGBLOB fields
* Real-time image preview
* Upload image to database
* Download image from database
* Delete image records
* Search records by primary key
* Swing-based graphical user interface
* Executable Fat JAR deployment

3. Technologies Used

* Java
* Java Swing
* JDBC
* MySQL Connector/J
* Maven
* Spring Boot Packaging
* SQL
* File I/O

4. Screenshots

Add screenshots of:

* Database Connection Window
* Image Preview Window
* Upload Image Functionality
* Search and Retrieval Operations

5. Build

```bash
mvn clean package
```

6. Run

```bash
java -jar target/universaljdbcimagemanager-0.0.1-SNAPSHOT.jar
```

7. Use Case

This application is useful for visualizing and managing image data stored in relational databases where conventional database tools typically display only binary data rather than the actual image content.

## Author

Sagnik Bhattacharyya
