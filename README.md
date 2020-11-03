# A Dropwizard example
An example Dropwizard application to demonstrate how to use Dropwizard using JDBI to interact with postgresql (or any db).

Required installations
---
Precondition to start application is to have Docker installed.
Application will be built and run within Docker compose environment.
According to your operating system follow the guidelines to install Docker Desktop.

https://docs.docker.com/get-docker/

Once you have installed a Docker Desktop (Docker Toolbox is deprecated), edit you hosts file by mapping docker IP to the URL 'convey-library'.
If you use Docker Desktop then IP is 127.0.0.1 (localhost), so in hosts file you should add:
    
    127.0.0.1 convey-library
    
For Docker Toolbox users, IP starts from 198.162.99.100    

Run the application
---
Once you have up and running Docker, to run application, execute following in the root directory where docker-compose.yml file exists

`docker-compose up`


Docker compose will create 4 containers:
    
    - dropwizard-library_server_1
    - dropwizard-library_client_1
    - dropwizard-library_lb-traefic_1
    - dropwizard-library_db_1

Application is running on

http://convey-library/

To list all containers run following
`docker ps`

or if you want just ID, Name and Status of containers run following

`docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Status}}`

To stop and remove the containers run following
`docker-compose down` 

To list all images run following
`docker images ls -a`

To monitor logs of the one container run following
`docker logs -f {container_id}` - it is enough to use only just the first 3 chars of container id
