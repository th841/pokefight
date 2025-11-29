docker rm -f mongodb
docker run -d \
        --name mongodb \
        -p 27017:27017 \
        -v $(pwd)/config:/etc/mongo \
        mongo:7.0-jammy \
        --config /etc/mongo/mongod.conf
