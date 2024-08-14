Here's the updated documentation with the combined installation and startup steps:

---

# e-commerce-application

A microservices-based e-commerce platform designed for scalability and flexibility. This application leverages modern Java technologies to handle various aspects of e-commerce, including product management, customer interactions, orders, payments, and notifications.

---

### Technologies Used

- **Java 17**
- **Spring:** Web, Kafka, Validation
- **Spring Boot:** Mail, Thymeleaf, Maven, Actuator
- **Spring Cloud:** Config, Eureka, OpenFeign
- **Spring Data:** JPA, MongoDB
- **Flyway:** Core, Database (PostgreSQL)
- **PostgreSQL JDBC Driver**
- **ModelMapper, Lombok**
- **Zipkin Reporter (Brave)**
- **Micrometer Tracing (Brave)**
- **KeyCloak**

## Getting Started

1. **Clone the repository:**

    ```bash
    git clone https://github.com/akorovai/e-commerce-application.git
    ```

2. **Navigate to the repository directory:**

    ```bash
    cd e-commerce-application
    ```

3. **Start Docker containers:**

    ```bash
    docker-compose up -d
    ```

4. **Navigate to the `services` directory:**

    ```bash
    cd services
    ```

5. **Build and run the services in the following order:**

   - **Navigate to the `config-server` directory:**

       ```bash
       cd config-server
       mvn clean install
       mvn spring-boot:run
       cd ..
       ```

   - **Navigate to the `discovery-service` directory:**

       ```bash
       cd discovery-service
       mvn clean install
       mvn spring-boot:run
       cd ..
       ```

   - **Navigate to and build/run the remaining services in any order:**

       ```bash
       cd [remaining_service_directory]
       mvn clean install
       mvn spring-boot:run
       cd ..
       ```
---

### Product Service

#### Endpoints

1. **Create Product**
    - **Method:** `POST /api/v1/products`
    - **Request Body:** `ProductRequest` (with product details)
    - **Response:** `Integer` (ID of the created product)

2. **Purchase Products**
    - **Method:** `POST /api/v1/products/purchase`
    - **Request Body:** `List<ProductPurchaseRequest>` (list of products to purchase)
    - **Response:** `List<ProductPurchaseResponse>` (details of purchased products)

3. **Find Product by ID**
    - **Method:** `GET /api/v1/products/{product-id}`
    - **Path Variable:** `product-id` (ID of the product)
    - **Response:** `ProductResponse` (details of the product)

4. **Find All Products**
    - **Method:** `GET /api/v1/products`
    - **Response:** `List<ProductResponse>` (list of all products)

---

### Payment Service

#### Endpoints

1. **Create Payment**
    - **Method:** `POST /api/v1/payments`
    - **Request Body:** `PaymentRequest` (payment details)
    - **Response:** `Integer` (ID of the created payment)

---

### Order Service

#### Endpoints

1. **Create Order**
    - **Method:** `POST /api/v1/orders`
    - **Request Body:** `OrderRequest` (order details including customer ID and product list)
    - **Response:** `Integer` (ID of the created order)

2. **Find All Orders**
    - **Method:** `GET /api/v1/orders`
    - **Response:** `List<OrderResponse>` (list of all orders)

3. **Find Order by ID**
    - **Method:** `GET /api/v1/orders/{order-id}`
    - **Path Variable:** `order-id` (ID of the order)
    - **Response:** `OrderResponse` (details of the order)

---

### Customer Service

#### Endpoints

1. **Create Customer**
    - **Method:** `POST /api/v1/customers`
    - **Request Body:** `CustomerRequest` (customer details)
    - **Response:** `String` (ID of the created customer)

2. **Update Customer**
    - **Method:** `PUT /api/v1/customers`
    - **Request Body:** `CustomerRequest` (customer details to update)
    - **Response:** `Void` (status of the update)

3. **Find All Customers**
    - **Method:** `GET /api/v1/customers`
    - **Response:** `List<CustomerResponse>` (list of all customers)

4. **Check if Customer Exists**
    - **Method:** `GET /api/v1/customers/exists/{customer-id}`
    - **Path Variable:** `customer-id` (ID of the customer)
    - **Response:** `Boolean` (true if the customer exists, otherwise false)

5. **Find Customer by ID**
    - **Method:** `GET /api/v1/customers/{customer-id}`
    - **Path Variable:** `customer-id` (ID of the customer)
    - **Response:** `CustomerResponse` (details of the customer)

6. **Delete Customer**
    - **Method:** `DELETE /api/v1/customers/{customer-id}`
    - **Path Variable:** `customer-id` (ID of the customer)
    - **Response:** `Void` (status of the deletion)

---

### Notification Consumer (Kafka)

#### Kafka Listeners

1. **Consume Payment Success Notifications**
    - **Topic:** `payment-topic`
    - **Action:** Save notification to the database and send a payment success email

2. **Consume Order Confirmation Notifications**
    - **Topic:** `order-topic`
    - **Action:** Save notification to the database and send an order confirmation email

---

### Gateway Service Configuration

- **Security Configuration:** Uses OAuth2 with JWT for authentication, disables CSRF, and permits access to `/eureka/**` without authentication.
- **Routing Configuration:** Routes requests to various microservices based on path patterns.

---

### Spring Profiles and Configurations

- **Profiles:** Active profile is `native` for `config-server`.
- **Config Server:** Loads configurations from a native location.
- **Eureka Server:** Runs on port `8761`, used for service discovery.
- **Spring Cloud Config:** Configurations are overridden by system properties and imported from a config server.
- **Database and Kafka Configurations:** Define settings for PostgreSQL databases and Kafka integration.
