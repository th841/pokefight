## This app is a homework project, not a full solution
### App needs a running local mongodb
Scripts provided, start it:

```
cd etc/docker
sh local-mongo.sh
```

### Build:
Needs jdk 17
  
```
./mvnw clean install
```

Note: only contains dev build now, not for running standalone
