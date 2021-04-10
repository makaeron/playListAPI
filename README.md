# Playlist Service

 This service was created to expose the playlist related endpoints. Please find below acceptance criteria achieved so far.

```
* Rule: Playlist names are unique.
* Rule: Song is just the name of the song.

* When a playlist is created with a name
* Then a confirmation is returned that it was successful.
* And the playlist is empty.

* When a playlist is created with existing name
* Then a message is returned that it was unsuccessful.

* When a playlist is created without a name
* Then a message is returned that a name is required.

* Given a playlist
* When a song is added
* Then the playlist have one more song

* Given a playlist has songs
* When a song is removed
* Then the playlist have one less song

* Given a playlist has songs
* When retrieve the playlist
* Then see the songs on the playlist
```

## How to deploy on docker

### Common docker command
To check the available images
```
docker images
```
To check the available containers
```
docker ps -a
```

### Build the playlist docker image
To build the playlist docker image use the following command on terminal
```
docker build -t playlistservice:dev .
```
Use the following command to create the playlist container in local.
```
docker run -d -p 9000:8080 -e PORT=8080 --name playlist_local --rm playlistservice:dev
```
Use the following command to create and start the postgres container in local.
```
docker pull postgres
docker run --name my-postgres -e POSTGRES_PASSWORD=open -p 5432:5432 -d postgres
```
Use the following command if postgres container already exist and run the playlist service with given spring profile to communicate the local postgres database.
```
docker start my-postgres
docker run --name playlist-localpg -e PORT=8080 -e SPRING_PROFILES_ACTIVE=localpg -p 9001:8080 -d playlistservice:dev
```
Use the following command to create and run the playlist service on the docker network.
```
docker network create --driver bridge playlist-network
docker run --name playlist-pg --network playlist-network -e POSTGRES_PASSWORD=open -e POSTGRES_DB=playlist-db -d postgres
docker run --name playlist-networkpg --network playlist-network -e PORT=8080 -e SPRING_PROFILES_ACTIVE=dockerpg -p 9002:8080 -d playlistservice:dev
```

## How to deploy on cloud
To deploy the playlist service to Heroku. First create the account to Heroku (https://dashboard.heroku.com/) and add the new application. Run the following command to push the code to heroku.
```
 heroku git:remote -a [appname]
```
Back on the Heroku web dashboard for your app, go to the "Deploy" tab. Select the Container Registry deployment option and you can see the following commands
```
heroku login
heroku create
heroku git:remote -a
heroku container:login
heroku container:push web
heroku container:release web
```
You'll then need to add the environment variable to enable the heroku Spring profile.
> SPRING_PROFILES_ACTIVE=heroku


## API document
https://my-playlist-service.herokuapp.com/docs/index.html
