# giftandgain-backend - Setting up and Running the Inventory Management App using Docker
# Prerequisites: Ensure Docker and Docker Compose are installed on your machine.

# Steps:
1. **Pulling the Application Image from Docker Hub**:
    - Run the following command to pull the `inventory-management-app` image from Docker Hub: 
      'docker pull shaowchin98/inventory-management-app:latest'

2. **Get the Docker Compose File**:
    - Obtain the `docker-compose.yml` file from git in 'shannon-inventory-management'

3.  **Starting the Services**:
    - Open a terminal or command prompt and navigate to the directory containing the `docker-compose.yml`.
    - Run the following command to start both the application and the database:
       'docker-compose up'

4. **Accessing the Application**:
    - Once the services are up and running, you can access the Inventory Management App on your web browser by navigating to:
      http://localhost:8001/giftandgain/.....

5. **(Optional)Accessing the Database**:
    - The MySQL database is accessible on port `3308` of the machine.
    - connect using any MySQL client with the following credentials:
      - **Username**: giftandgain
      - **Password**: giftandgain123
      - **Host**: localhost
      - **Port**: 3308


