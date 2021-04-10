### Check for docker image:-
```
docker images
```

### Docker Build Command:-
```
docker build -t playlistservice:dev .
```

### Docker Run:-
```
docker run -d -p 9000:8080 -e PORT=8080 --name playlist_local --rm playlistservice:dev
```

### Running Local Postgres Spring Profile
```
docker start my-postgres
docker run --name playlist-localpg -e PORT=8080 -e SPRING_PROFILES_ACTIVE=localpg -p 9001:8080 -d playlistservice:dev
```

### Running Playlist service with Postgres on docker network
```
docker network create --driver bridge playlist-network
docker run --name playlist-pg --network playlist-network -e POSTGRES_PASSWORD=open -e POSTGRES_DB=playlist-db -d postgres
docker run --name playlist-networkpg --network playlist-network -e PORT=8080 -e SPRING_PROFILES_ACTIVE=dockerpg -p 9002:8080 -d playlistservice:dev
```

###Heroku Commands
```
heroku login
heroku create
heroku git:remote -a
heroku container:login
heroku container:push web
heroku container:release web
```
