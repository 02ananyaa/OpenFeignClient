# Apache Superset + OpenFeign Integration in Spring Boot  

## 1. What is Apache Superset?  

Apache Superset is an **open-source data exploration and visualization tool** that allows users to create dashboards and analyze data with an interactive UI. It supports:  
- **SQL-based querying** on various databases.  
- **Custom charts, graphs, and dashboards**.  
- **Role-based access control** for security.  
- **API access** for programmatic data retrieval.  

### 1.1 Features of Apache Superset  
✅ **Interactive UI** – Create dashboards and charts easily.  
✅ **Supports multiple databases** – MySQL, PostgreSQL, BigQuery, etc.  
✅ **API Access** – Integrate with other applications (like Spring Boot).  
✅ **Scalable** – Handles large datasets efficiently.  

---

## 2. How We Integrate Apache Superset with OpenFeign in Spring Boot  

We will use **OpenFeign** (a declarative REST client in Spring Boot) to **consume Superset’s API**.  

### 2.1 Why Use OpenFeign?  
- **Easier API calls** (no need for `RestTemplate`).  
- **Built-in load balancing** (if using Eureka).  
- **Declarative approach** using interfaces.  

---

## 3. Setup Apache Superset With Docker  

#### Step 1: Clone the Superset Repository
Clone the Apache Superset GitHub repository:

``` sh
git clone https://github.com/apache/superset.git
cd superset
```

#### Step 2: Start Superset with Docker Compose
Run the following command to start Superset:

``` sh
docker-compose -f docker-compose-non-dev.yml up
```

#### Step 3: Initialize Superset
Once all containers are running, initialize the Superset database:
``` sh
docker exec -it superset_app superset db upgrade
```
Create an admin user:
``` sh
docker exec -it superset_app superset fab create-admin
```
You will be prompted to enter admin details.

Initialize Superset:
``` sh
docker exec -it superset_app superset init
```

#### Step 4 : Access Superset
Superset should now be running on http://localhost:8088
Log in using the admin credentials you created.

## 4.Create an OpenFeign Client in Spring Boot
##### 4.1 Add Dependencies
``` xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
##### 4.2 Enable Feign Clients in Spring Boot
In your main Spring Boot application class:
``` xml
@SpringBootApplication
@EnableFeignClients
public class SupersetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupersetApplication.class, args);
    }
}
```

##### 4.3 Create Feign Client to Call Superset API
SupersetFeignClient.java
``` java 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "supersetClient", url = "http://localhost:8088/api/v1")
public interface SupersetFeignClient {
    
    @GetMapping("/dashboard")
    DashboardResponse getDashboards(@RequestHeader("Authorization") String token);
}
 ```
#### 5. Get API Access Token and Use It for Guest Token
To access Superset’s API, you need an authentication token. This token will later be used to generate a guest token for frontend integration.
##### 5.1 Obtain the API Access Token
Superset requires login credentials to generate an API token. Use the following API call to authenticate and get the token:
``` java
@FeignClient(name = "supersetAuthClient", url = "http://localhost:8088/api/v1/security")
public interface SupersetAuthClient {

    @PostMapping("/login")
    AuthResponse login(@RequestBody AuthRequest authRequest);
}
```
Here’s the request payload for authentication:
``` java
{
    "username": "admin",
    "password": "admin",
    "provider": "db",
    "refresh": true
}
```
The response will contain an access token:
``` java 
{
    "access_token": "your_access_token_here"
}
```
##### 5.2 Use Access Token for Guest Token
Once you have the access token, use it to request a guest token for embedding dashboards in the frontend.

Create a Feign client for guest token retrieval:
``` java
@FeignClient(name = "supersetGuestClient", url = "http://localhost:8088/api/v1/security")
public interface SupersetGuestClient {

    @PostMapping("/guest_token")
    GuestTokenResponse getGuestToken(@RequestHeader("Authorization") String token, @RequestBody GuestTokenRequest request);
}
```
The request body should specify the user roles and dashboards:
``` java
{
    "user": {
        "username": "guest_user"
    },
    "resources": [
        {
            "id": "dashboard_id_here",
            "type": "dashboard"
        }
    ]
}
```
The API will return a guest token:
``` java
{
    "token": "your_guest_token_here"
}

```
##### 6. Use Guest Token for Frontend Integration
Now, use the guest token in your frontend application to embed Superset dashboards securely.

Example: Embedding in React
``` javascript
const supersetUrl = "http://localhost:8088/superset/dashboard/your_dashboard_id_here/";
const guestToken = "your_guest_token_here";

const iframeSrc = `${supersetUrl}?token=${guestToken}`;

return (
    <iframe
        src={iframeSrc}
        width="100%"
        height="600px"
        frameBorder="0"
    />
);

```
#### Now, your React or frontend application can securely display Superset dashboards without requiring direct user authentication.

##### 7. Need Changes or Improvements?
This setup provides a basic integration of Apache Superset with OpenFeign in Spring Boot. However, depending on your requirements, you might need to:

Secure API Calls – Store tokens securely instead of hardcoding them.
Handle Expired Tokens – Implement token refresh mechanisms.
Enhance Error Handling – Manage API failures gracefully.
Optimize Performance – Use caching or async processing for faster responses.
If you have any suggestions, improvements, or face any issues, feel free to contribute or raise an issue. 🚀

Happy coding! 😊
