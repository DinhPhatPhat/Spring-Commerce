# OurStories

OurStories is a web application that allows users to share and explore personal stories.

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
   git clone https://github.com/DinhPhatPhat/Our-stories.git
   ```
2. Configure the database in `application.properties`:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ourstories
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. Run the application:  
   ```bash
   mvn spring-boot:run
   ```
4. Open `http://localhost:8080` in your browser.

## Contributing

Feel free to fork and submit pull requests!

## License

MIT License.

