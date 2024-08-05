# URL
https://cofffeee.org

# Installation
 
```bash
git clone https://github.com/Ipsen87k/CofffeeeCompWeb

cd /CofffeeeCompWeb/.devcontainer

docker compose up -d --build

docker compose exec コンテナID /bin/bash

cd /workspaces/CofffeeeCompWeb/pro

mvn spring-boot:run
```