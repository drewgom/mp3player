# CECS 343 Term Project

## Helpful Links
Documents are stored [here](https://drive.google.com/drive/folders/1DOkwGmKIIz9Kv6WdBLH3gYagA-zSxSuJ?usp=sharing)

## Needed Modules
### Can be found in the CECS343 Discord
- mysql-connector-java-8.0.21
- mp3gic-0.9.2.jar
### Can be found in the the BasicPlayer3.0 folder:
- jl1.0.jar
- jogg-0.0.7.jar
- jorbis-0.0.15.jar
- jspeex0.9.7.jar
- mp3spi1.9.4.jar
- mp3spi1.9.5.jar
- tritonus_share.jar
- vorbisspi1.0.2.jar
- basicplayer3.0.jar
- commons-logging-api.jar


## DB Setup
### Derby itself
- Download NetBeans if you dont already have it
- Follow the directions [here](https://web.csulb.edu/~mopkins/cecs323/netbeans.shtml) to get a DerbyDB set up locally
	- user is `root` and password is `1234`
- Go to the 'Services' tab expand the 'Databases' field
- Right click on your local DB and click 'Execute Command...'
- Copy and paste the code in `utils/db_remake_statements.sql` in to the command prompt that was created
- To the right side of the 'Connection' dropdown menu at the top of the command prompt, you should see a orange/brown database that says 'Run SQL' when you highlight over it. Click that button.
- This should *hopefully* be the completion of the DB setup, however you still need to set up the NetBeans IDE
### NetBeans Setup
- Go to 'File' -> 'New Project...'
- Under 'Categories', select 'Java with Ant' and under 'Projects' select 'Java Project with Existing Sources'. Then click 'Next'
- Select the `mp3player` repository's local folder. Then click 'Finish'
- Go to the 'Projects' tab in the left sidebar and click the project
- Right click on 'Libraries'
- Click 'Add JAR/Folder...'
- Select all the libraries listed [at the beginning of the README](https://github.com/drewgom/mp3player/blob/master/README.md#needed-modules) and add them
- Right click on 'Libraries'
- Click 'Add Library...'
- Select 'Java DB Driver' and then 'Add Library'
- Your project should now build. If not, then contact [me](https://github.com/drewgom)
### DB Navigation + Tip
- Tip: **always** make sure that the DB is running when you open NetBeans
	- Go to the 'Services' tab expand the 'Databases' field
	- Right click on your local DB and click 'Connect'
- To view the status of the DB table at any given time
	- Go to the 'Services' tab expand the 'Databases' field
	- Expand your local db
	- Expand your schema `ROOT`
	- Expand the folder 'Tables'
	- Right click on the table `SONG` and Click 'View Data'