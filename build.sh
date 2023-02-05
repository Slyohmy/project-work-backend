mvn test
if [ $? -eq 0 ]; then
    docker build . -t backend
    docker run -p 8080:8080 --name backend --link localhost -d backend
else
    echo "Tests failed, not building Docker image."
fi
chmod +x build.sh
./build.sh


