# hackthehive
A tool used for a [hacking challenge](https://hackthehive.swarmonline.com/login)
* Will attempt to log into the site using passwords until a successful one is found. Passwords that resulted in access denied are written to the `log4j2` log file, so they are not repeated on subsequent runs.
## Requirements
* Java 8+
## Usage
* Configure via the `config.properties` resource file.
* Run with `./gradlew run`
