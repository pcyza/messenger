On unix:

1. Building:
cd messenger
mvn clean install

2. Starting server:
java -jar target/messenger-0.0.1-SNAPSHOT.jar

3. Testing

-add post:
curl -H "Content-Type: application/json" -X POST -d '{"message":"<message_text>"}' -v http://localhost:8080/post/<login>

-display user posts:
curl http://localhost:8080/post/<login>

-follow another user:
curl -H "Content-Type: application/json" -X PUT -d '{"user":"<user_to_follow>"}' -v http://localhost:8080/followed/<login>

-display followed users posts
curl http://localhost:8080/followed/<login>

