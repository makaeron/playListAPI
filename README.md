Docker Build Command:-
docker build -t playlistservice:dev .

Docker Run:-
docker run -d -p 9000:8080 -e PORT=8080 --name playlist_local --rm playlistservice:dev
