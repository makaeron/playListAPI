Docker Build Command:-
docker build -t playlistservice:dev .

Docker Run:-
docker run -d -p 9000:8080 -e PORT=8080 --name playlist_local --rm playlistservice:dev

Check for image:-
docker images ps


Running Local Postgres Spring Profiles
docker run --name playlist_localpg -e PORT=8080 -e SPRING_PROFILES_ACTIVE=localpg -p 9001:8080 -d playlistservice:dev

