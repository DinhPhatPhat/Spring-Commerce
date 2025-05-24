# Spring Commerce
Link video demo: https://drive.google.com/drive/folders/1lugMNHAwTF6NlT7eoglE_ulY3unCeZKP?usp=sharing


## Technologies Used

- **Spring Boot** – Backend framework  
- **Maven** – Dependency management  
- **Thymeleaf** – Templating engine  
- **MySQL** – Database  

## Setup & Installation

### Prerequisites

- Java 17+  
- Maven  
- MySQL  

### Steps

1. Clone the repository:  
   ```bash
   git clone https://github.com/DinhPhatPhat/Spring-Commerce.git
   ```
2. Configure the database in `application.properties`:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/springcommerce_db
   spring.datasource.username=root
   spring.datasource.password=root
   ```
3. Run the application:  
   ```bash
   mvn spring-boot:run
   ```
4. Open `http://localhost:8081` in your browser.

## Contributing

Feel free to fork and submit pull requests!

## License

MIT License.

