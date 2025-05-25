# Spring Commerce
# Link video demo: https://drive.google.com/drive/folders/1lugMNHAwTF6NlT7eoglE_ulY3unCeZKP?usp=sharing

## Giới thiệu
**SpringCommerce** là một phần mềm trực tuyến đơn giản được xây dựng bằng **Java Spring Boot**. Bảo mật ở mức phương thức với mục tiêu chính của dự án này là triển khai một Sản phẩm khả dụng tối thiểu (MVP - Minimum Viable Product) thể hiện được các chức năng cơ bản của một nền tảng thương mại điện tử bao gồm hiển thị danh sách sản phẩm, tìm kiếm và lọc danh sách sản phẩm theo các tiêu chí, quản lý giỏ hàng, và đặt hàng và theo dõi trạng thái sản phẩm.
## Ứng dụng được xây dựng với
### Front-end
<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" height="40" alt="html5 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" height="40" alt="css3 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="40" alt="javascript logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/axios/axios-plain-wordmark.svg" height="40" alt="javascript logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/bootstrap/bootstrap-original.svg" height="40" alt="bootstrap logo" />      
</div>

### Back-end
<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original-wordmark.svg" height="40" alt="java logo" />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg" height="40" alt="spring logo" />
  <img width="12" />
</div>
<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mysql/mysql-original-wordmark.svg" height="40" alt="mysql logo" />
  <img width="12" />
</div>  

### Deploy
<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/amazonwebservices/amazonwebservices-original-wordmark.svg" height="40" alt="aws logo" />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/docker/docker-original-wordmark.svg" height="40" alt="docker logo" />
  <img width="12" />        
</div>

### Tools
<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/visualstudio/visualstudio-original.svg" height="40" alt="vs logo" />
  <img width="12" />        
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/intellij/intellij-original.svg" height="40" alt="intellij logo" />
  <img width="12" />     
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/github/github-original.svg" height="40" alt="github logo" />
</div>

Giới thiệu tổng quan về dự án
-----------------------------------------------------------------

# 🌿 Spring Commerce

Spring Commerce là một ứng dụng web thương mại điện tử đơn giản được phát triển bằng **Spring Boot**, cho phép người dùng duyệt và đặt hàng các sản phẩm, đồng thời cung cấp giao diện quản lý cho admin.


## 🚀 Chức năng chính

### 👤 Người dùng (Khách hàng)

- 🔐 **Đăng ký & Đăng nhập**  
  Đăng nhập và đăng ký tài khoản bằng email và mật khẩu.

- 🛍️ **Xem danh sách sản phẩm**  
  Hiển thị toàn bộ sản phẩm trong hệ thống.

- 🔎 **Tìm kiếm & Lọc sản phẩm**  
  Tìm theo tên, mô tả; lọc theo danh mục, màu sắc.

- 📄 **Xem chi tiết sản phẩm**  
  Thông tin sản phẩm gồm tên, mô tả, giá, màu, thương hiệu,...

- 🛒 **Giỏ hàng & Đặt hàng**  
  Thêm sản phẩm vào giỏ hàng và tiến hành đặt hàng.

- 💵 **Thanh toán khi nhận hàng (COD)**  
  Hình thức thanh toán tiền mặt khi giao hàng.

- 📦 **Theo dõi đơn hàng**  
  Kiểm tra trạng thái đơn hàng đã đặt.

---

### 🛠️ Quản trị viên (Admin)

- 👤 **Các chức năng của khách hàng**
  Quản lý có thể thao các các chức năng của khách hàng

- 📦 **Quản lý sản phẩm**  
  Thêm, chỉnh sửa, xóa, cập nhật thông tin sản phẩm.

- 🗂️ **Quản lý danh mục**  
  Tạo, sửa, xóa danh mục sản phẩm.

- 🚚 **Quản lý đơn hàng**  
  Xem danh sách đơn hàng và cập nhật trạng thái.

---

Mục Lục
-----------------------------------------------------------------

1. [Các Nguyên Tắc, Mẫu Thiết Kế](#principles-and-patterns)

2. [Kiến Trúc Thiết Kế ](#architectural-design)
    
3. [Công Nghệ Sử Dụng](#technologies-used)
    
4. [Cấu Trúc Dự Án](#project-structure)
    
5. [Sơ Đồ ERD](#erd-diagram)
    
6. [Các Endpoint API](#api-endpoints)
    
7. [Kiểm Thử Đơn Vị](#unit-tests)
    
8. [Cài Đặt Và Thiết Lập](#installation-and-setup)

1\. Các Nguyên Tắc, Mẫu Thiết Kế.
--------------------------------------------------------------------

### **Nguyên Tắc SOLID**

![markdown](https://winzone.vn/images/blog/19/blg_inline_solid_principles.png)

Nguyên tắc SOLID là tập hợp năm quy tắc thiết kế phần mềm hướng đối tượng, giúp xây dựng hệ thống dễ bảo trì, mở rộng và thích ứng với thay đổi. SOLID bao gồm năm nguyên tắc: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation và Dependency Inversion. Trong Spring Boot, SOLID tận dụng Dependency Injection và IoC để tạo mã nguồn sạch. Dưới đây là cách áp dụng các nguyên tắc này:
* **S – Single Responsibility Principle (Nguyên tắc trách nhiệm đơn lẻ):** Mỗi lớp chỉ nên đảm nhiệm một chức năng duy nhất. Ví dụ, trong Spring Boot, OrderService chỉ xử lý các nghiệp vụ liên quan đến đơn hàng, còn EmailService sẽ đảm nhận việc gửi email. Cách tách biệt này giúp mã nguồn dễ kiểm thử và bảo trì.
* **O – Open/Closed Principle (Nguyên tắc Mở/Rộng – Đóng/Sửa):** Hệ thống nên cho phép mở rộng chức năng mà không cần chỉnh sửa mã nguồn hiện tại. Giống như một máy pha cà phê có thể thêm chức năng pha trà bằng cách gắn thêm bộ lọc, thay vì phải sửa lại toàn bộ cấu trúc bên trong.
* **L – Liskov Substitution Principle (Nguyên tắc thay thế Liskov):** Các lớp con có thể được thay thế cho lớp cha mà không làm thay đổi hành vi chương trình. Ví dụ, nếu bạn cắm đèn bàn hoặc đèn cây vào cùng một ổ điện mà mọi thứ vẫn hoạt động ổn định, điều đó thể hiện nguyên tắc này được đảm bảo.

* **I – Interface Segregation Principle (Nguyên tắc phân tách giao diện):** Giao diện nên được chia nhỏ, chuyên biệt thay vì gộp nhiều chức năng không liên quan. Ví dụ, điều khiển từ xa cho TV cơ bản chỉ cần nút bật/tắt và đổi kênh; không cần thêm các nút phức tạp như chỉnh âm 3D nếu TV không hỗ trợ. Điều này giúp giao diện dễ hiểu và dễ sử dụng.
* **D – Dependency Inversion Principle (Nguyên tắc đảo ngược phụ thuộc):** Các thành phần cấp cao nên phụ thuộc vào các trừu tượng (interface), không phụ thuộc vào chi tiết cụ thể. Ví dụ, ứng dụng đặt đồ ăn chỉ quan tâm đến việc nhận món ăn, chứ không cần biết ai nấu hay nhà hàng nào cung cấp. Điều này cho phép thay đổi nhà cung cấp mà không ảnh hưởng đến phần còn lại của hệ thống.

Việc áp dụng nguyên tắc này trong dự án Spring Boot giúp hệ thống dễ dàng bảo trì, nâng cấp, giảm thiểu lỗi và tiết kiệm thời gian phát triển. Thay vì cố gắng xử lý quá nhiều tình huống đặc biệt bằng các hàm phức tạp, nhà phát triển nên sử dụng các annotation mặc định của Spring Boot và khai thác tính năng tự động cấu hình (auto-configuration) nhằm giảm thiểu tối đa cấu hình không cần thiết.

### **Mẫu Thiết Kế**
Dự án được xây dựng dựa trên các mẫu thiết kế như:
* **✅ Singleton Pattern**
Toàn bộ các bean trong ứng dụng như ProductService, OrderService, UserRepository,... được Spring quản lý mặc định theo Singleton, đảm bảo:

Không tạo nhiều instance gây tốn bộ nhớ

Duy trì trạng thái nhất quán

Dễ kiểm soát luồng xử lý

* **✅ Repository Pattern**
Sử dụng Spring Data JPA để xây dựng tầng truy xuất dữ liệu. Mỗi entity đều có một repository riêng mở rộng từ JpaRepository, giúp tách logic truy vấn khỏi nghiệp vụ.

Ví dụ:

ProductRepository: tìm sản phẩm theo category, theo giá, theo từ khóa tìm kiếm.

OrderRepository: truy xuất đơn hàng theo người dùng, theo trạng thái đơn.

* **✅ Dependency Injection (DI)**
SpringCommerce tận dụng hoàn toàn Dependency Injection để inject các bean như service, repository, config,... giúp code tách rời, dễ mock khi test và tăng khả năng mở rộng.


2\. Kiến Trúc Thiết Kế
-----------------------------
Khi phát triển các dự án phần mềm hiện đại, các kỹ thuật và kiến trúc thiết kế đóng vai trò quan trọng trong việc tối ưu hóa hệ thống. Một trong những phương pháp phổ biến là Kiến trúc phân tầng (Layered Architecture), giúp chia ứng dụng thành nhiều tầng riêng biệt như Controller, Service, Repository, giúp việc bảo trì và quản lý dễ dàng hơn.

Đối với những hệ thống cần cách ly hoàn toàn logic nghiệp vụ khỏi các chi tiết kỹ thuật như giao diện người dùng hay database, Kiến trúc Hexagonal (Ports and Adapters) cung cấp một giải pháp hiệu quả, tạo điều kiện thuận lợi để kiểm thử và thay đổi công nghệ khi cần. Bên cạnh đó, Clean Architecture cũng hướng đến việc tổ chức ứng dụng sao cho phần logic nghiệp vụ tách biệt hoàn toàn với framework, thư viện bên ngoài, giúp quá trình bảo trì và kiểm thử được tối ưu hóa.

Mỗi mô hình kiến trúc đều có những ưu điểm riêng và phù hợp với từng bối cảnh cụ thể. Trong dự án lần này, tôi đã quyết định sử dụng RESTful API làm kiến trúc chính để xây dựng hệ thống dịch vụ web, bởi tính đơn giản, linh hoạt, khả năng mở rộng cao và sự phổ biến rộng rãi của nó trong ngành phát triển phần mềm hiện nay.

### **Kiến Trúc RESTful API**
Kiến trúc RESTful API (Representational State Transfer) là sự lựa chọn phù hợp để phát triển các dịch vụ web trong dự án này. Nó giúp các ứng dụng giao tiếp với nhau một cách rõ ràng và hiệu quả thông qua giao thức HTTP, đồng thời hỗ trợ cách tiếp cận đơn giản nhưng mạnh mẽ trong việc quản lý tài nguyên.

RESTful API sử dụng các phương thức HTTP tiêu chuẩn như GET, POST, PUT, DELETE để tương tác với các tài nguyên. Mỗi tài nguyên đều có một đường dẫn URL cụ thể để định danh, ví dụ: /users để lấy danh sách người dùng hoặc /auth/login để xử lý đăng nhập. Việc định nghĩa tài nguyên một cách rõ ràng, kết hợp với các phương thức tiêu chuẩn, giúp RESTful API trở nên dễ hiểu và dễ sử dụng, tạo điều kiện thuận lợi cho việc phát triển và mở rộng hệ thống.

Với tính linh hoạt và khả năng mở rộng tốt, RESTful API đã trở thành một lựa chọn phổ biến trong lĩnh vực phát triển phần mềm hiện nay, giúp các hệ thống hoạt động một cách mạch lạc và hiệu quả.

### **Cách Thức Hoạt Động của RESTful API trong dự án**
Cụ thể, dự án của tôi được phát triển với front-end sử dụng ReactJS, trong khi back-end được xây dựng trên nền tảng Spring Boot. Hai phần này tương tác với nhau thông qua RESTful API, tạo ra một hệ thống tách biệt và linh hoạt:

1.  **Client**: (ReactJS) gửi yêu cầu tới server thông qua một API endpoint, ví dụ khi người dùng thực hiện hành động (ví dụ nhấn nút để xem thông tin sản phẩm), front-end sẽ gửi request HTTP (như GET /products) tới server.
2.  **Server**: (Spring Boot) nhận yêu cầu, xử lý (chẳng hạn như truy vấn cơ sở dữ liệu) và trả về kết quả, server với Spring Boot sẽ tiếp nhận request, thực hiện xử lý nghiệp vụ (truy vấn dữ liệu, tính toán logic) và trả về dữ liệu dạng JSON.
3.  **Client**: ReactJS nhận được dữ liệu này từ RESTful API và hiển thị lên giao diện một cách thân thiện và rõ ràng cho người dùng.

### **Lợi ích khi sử dụng RESTful API trong dự án**
Việc lựa chọn kiến trúc RESTful API không chỉ giúp xây dựng một hệ thống linh hoạt, hiện đại mà còn mang lại nhiều lợi ích quan trọng trong quá trình phát triển, mở rộng và bảo trì ứng dụng.

3\. Công Nghệ Sử Dụng
------------------------------------------------
- Java 17
- Spring Boot
- Spring Data JPA
- Thymeleaf
- MySQL
- Bootstrap
- Docker & Docker Compose

---
### Back-end: Spring Boot
Phía server được triển khai bằng Spring Boot, một nền tảng mạnh mẽ trong hệ sinh thái Java, giúp tăng tốc quá trình xây dựng các ứng dụng web có kiến trúc RESTful. Các lý do tôi chọn Spring Boot:
* **Tự động hóa cấu hình**: Hệ thống auto-configuration giúp tiết kiệm thời gian thiết lập ban đầu, như kết nối database, cấu hình servlet, hoặc dependency injection.
* **Hỗ trợ xây dựng REST API mạnh mẽ**: Với các annotation như @RestController, @RequestMapping, @PathVariable, tôi dễ dàng thiết lập các API phục vụ quản lý người dùng, sản phẩm, đơn hàng.
* **Tích hợp tầng truy xuất dữ liệu**: Tôi sử dụng Spring Data JPA để giao tiếp với cơ sở dữ liệu thông qua các interface, giảm thiểu mã SQL thủ công, đồng thời đảm bảo khả năng mở rộng cho các thao tác tìm kiếm, lọc nâng cao.
* **Khả năng mở rộng và tích hợp**:  Spring Boot hỗ trợ dễ dàng việc tích hợp với nhiều dịch vụ khác như email, thanh toán, xác thực OAuth2, Kafka, hoặc thậm chí gắn với microservices nếu mở rộng trong tương lai.

### Cơ sở dữ liệu: MySQL
Toàn bộ dữ liệu của hệ thống được lưu trữ bằng MySQL, một hệ quản trị cơ sở dữ liệu quan hệ phổ biến, đảm bảo tính ổn định, bảo mật và hiệu suất.
* **Tương thích tốt với Spring Boot**: Thông qua JPA và Hibernate, các entity Java được ánh xạ trực tiếp với bảng dữ liệu, giúp tôi thao tác dữ liệu một cách tự nhiên, giảm viết lặp SQL.
* **Tối ưu hiệu suất với index và truy vấn có điều kiện**: Cơ chế index hóa được áp dụng trên các cột truy vấn nhiều như email, product_id, giúp tăng tốc xử lý dữ liệu.
* **Đáp ứng tốt cho hệ thống vừa và lớn**: MySQL đủ linh hoạt cho hệ thống quản lý đơn hàng, người dùng, sản phẩm có thể mở rộng về sau.

### Cloud Storage & Hosting: AWS (Amazon Web Service)
Để hỗ trợ lưu trữ và triển khai, tôi lựa chọn Amazon Web Services (AWS), nền tảng điện toán đám mây phổ biến và đáng tin cậy.
* **AWS EC2 (dự kiến triển khai)**: Trong giai đoạn hoàn thiện, tôi có kế hoạch triển khai toàn bộ hệ thống lên EC2 – dịch vụ máy chủ ảo cho phép kiểm soát hoàn toàn môi trường chạy ứng dụng Spring Boot, cấu hình domain, bảo mật bằng HTTPS và giám sát hiệu suất.

### Bảo mật: Spring Security + JWT
Để đảm bảo an toàn cho toàn bộ hệ thống, tôi triển khai Spring Security kết hợp với JWT (JSON Web Token) như một giải pháp xác thực và phân quyền. Các tính năng bảo mật đã được tôi thực hiện:
* **Xác thực người dùng**: Thông tin đăng nhập được kiểm tra dựa trên cơ sở dữ liệu người dùng, kết hợp mã hóa bằng BCryptPasswordEncoder để đảm bảo mật khẩu không bị lộ.
* **Phân quyền truy cập (Authorization)**: Tôi định nghĩa các vai trò như ROLE_USER, ROLE_ADMIN, sau đó phân quyền ở từng API endpoint thông qua @PreAuthorize hoặc cấu hình HttpSecurity.
* **Xác thực bằng JWT**: Sau khi người dùng đăng nhập thành công, server sẽ trả về một JWT chứa thông tin định danh. Token này được lưu ở phía client và gửi kèm trong mỗi request tiếp theo qua header Authorization, giúp API luôn stateless và dễ mở rộng.
* **Bảo vệ API theo vai trò**: Các chức năng quan trọng như tạo/sửa/xóa sản phẩm được giới hạn chỉ dành cho admin, trong khi các thao tác mua hàng chỉ cần người dùng đã xác thực.
* **Cấu hình bảo mật linh hoạt**: Toàn bộ luồng bảo mật được định nghĩa trong class SecurityConfig, sử dụng SecurityFilterChain, AuthenticationManager, cùng các custom filter xử lý JWT.

4\. Cấu Trúc Dự Án
---------------------------------

### **Controller**
Controller là nơi xử lý các yêu cầu HTTP từ phía client. Trong Spring Boot, các lớp controller sử dụng annotation @RestController hoặc @Controller để đánh dấu và xử lý các endpoint của API. Controller nhận các yêu cầu từ phía người dùng, gọi các dịch vụ liên quan và trả về kết quả (dữ liệu hoặc thông báo).
**Chức năng:**
*   Xử lý các yêu cầu GET, POST, PUT, DELETE.
*   Gửi yêu cầu đến các service tương ứng.
*   Đảm bảo trả về các dữ liệu dưới định dạng JSON hoặc các phản hồi thích hợp.

### **Entity**
Entity là các lớp mô phỏng các bảng trong cơ sở dữ liệu. Mỗi entity đại diện cho một bảng dữ liệu trong hệ thống, và các trường trong entity tương ứng với các cột trong bảng. Các lớp entity được đánh dấu với annotation @Entity.
**Chức năng:**
*   Định nghĩa các đối tượng dữ liệu trong hệ thống.    
*   Ánh xạ các trường trong entity vào các cột trong cơ sở dữ liệu.   
*   Xử lý các mối quan hệ giữa các bảng (1-1, 1-n, n-n).   

### **Exception**
Exception bao gồm các lớp xử lý ngoại lệ (errors). Tôi đã tạo các lớp exception tùy chỉnh để xử lý các lỗi người dùng hoặc hệ thống một cách chi tiết và dễ quản lý. Các lớp này thường được đánh dấu với annotation @ResponseStatus để trả về mã trạng thái HTTP thích hợp.
**Chức năng:**
*   Xử lý các lỗi xảy ra trong quá trình xử lý yêu cầu.    
*   Cung cấp thông tin chi tiết về lỗi cho người dùng.   
*   Đảm bảo ứng dụng không bị dừng đột ngột mà luôn trả về phản hồi hợp lý.   

### **Mapper**
Mapper là các lớp dùng để chuyển đổi giữa các đối tượng khác nhau, ví dụ như từ DTO sang entity và ngược lại. Tôi đã sử dụng thư viện MapStruct hoặc các phương thức thủ công để thực hiện việc ánh xạ này.
**Chức năng:**
*   Chuyển đổi giữa các lớp DTO và entity. 
*   Giảm bớt sự lặp lại khi làm việc với các đối tượng tương tự.

### **Repository**
Repository là lớp để tương tác với cơ sở dữ liệu. Trong Spring Boot, tôi sử dụng JpaRepository hoặc CrudRepository từ Spring Data JPA để dễ dàng truy vấn và thao tác với dữ liệu. Repository giúp thực hiện các thao tác như tìm kiếm, lưu trữ và cập nhật dữ liệu.
**Chức năng:**
*   Tương tác trực tiếp với cơ sở dữ liệu. 
*   Cung cấp các phương thức mặc định để truy vấn, lưu và xóa dữ liệu.    
*   Được Spring Data JPA tự động triển khai.

### **Security**
Security chịu trách nhiệm bảo mật ứng dụng. Trong phần này, tôi đã sử dụng Spring Security để xử lý các yêu cầu về xác thực (authentication) và phân quyền (authorization). Security đảm bảo rằng chỉ người dùng có quyền mới có thể truy cập vào các tài nguyên của hệ thống.
**Chức năng:**
*   Xác thực người dùng (login/logout).   
*   Phân quyền truy cập vào các endpoint API.   
*   Bảo vệ các endpoint khỏi các mối đe dọa từ bên ngoài.

### **Service**
Service chứa logic nghiệp vụ của ứng dụng. Các lớp service thực hiện các phép toán, xử lý các yêu cầu và thực hiện các hành động cần thiết trong hệ thống. Các lớp này thường được đánh dấu với annotation @Service.
**Chức năng:**
*   Chứa logic xử lý nghiệp vụ.    
*   Tương tác với repository để truy vấn hoặc lưu trữ dữ liệu.    
*   Cung cấp các phương thức để controller gọi và trả về kết quả cho người dùng.

### **Specification**
Specification được sử dụng để xây dựng các truy vấn động trong Spring Data JPA. Nó cho phép tạo ra các truy vấn linh hoạt và phức tạp hơn mà không cần phải viết trực tiếp các câu lệnh SQL.
**Chức năng:**
*   Xây dựng các truy vấn động cho các yêu cầu tìm kiếm phức tạp.    
*   Tăng cường khả năng tái sử dụng mã và mở rộng ứng dụng.  

### **Resoures**
Trong dự án Spring Boot, thư mục resources nằm trong src/main/resources là một phần không thể thiếu, đóng vai trò là nơi chứa tài nguyên cấu hình và tệp tĩnh mà ứng dụng cần trong quá trình chạy. Các tệp trong thư mục này sẽ được đóng gói trực tiếp vào file JAR hoặc WAR khi build ứng dụng, và được Spring Boot tự động tải vào classpath.

5\. Entity Diagram
---------------------------

### **Sơ đồ quan hệ ERD**

Sơ đồ được đặt trong thư mục diagrams của repository

6\. API Enpoints
-------------------------------------
### **Các API request bằng postman được đặt trong thư mục API_Endpoints


7\. Kiểm Thử Đơn Vị (Unit Test)
------------------------------------------
Unit Test là một kỹ thuật kiểm thử nhằm đánh giá hoạt động của các thành phần nhỏ nhất trong ứng dụng, chẳng hạn như hàm hoặc phương thức, đảm bảo chúng thực thi đúng như mong đợi. Trong quá trình phát triển phần mềm, Unit Test đóng vai trò thiết yếu trong việc duy trì tính chính xác của hệ thống, phát hiện lỗi sớm và giúp việc sửa lỗi trở nên hiệu quả hơn. Không chỉ giúp đảm bảo từng phần nhỏ hoạt động đúng, Unit Test còn góp phần tăng cường sự ổn định khi tích hợp các thành phần lại với nhau, hỗ trợ bảo trì và cho phép kiểm tra nhanh những tác động khi mở rộng hoặc thay đổi ứng dụng.

# Phân tích kết quả JaCoCo Coverage (Kết quả nằm trong target/site/jacoco/index.html) Ảnh chụp có nằm trong thư mục jacoco/index.png

## Công cụ & Framework sử dụng  
- **JUnit 5**: Khung kiểm thử chính để viết và chạy test case.  
- **Mockito**: Giả lập (mock) các dependency trong tầng Service, giúp cô lập logic cần kiểm thử.  
- **Spring MockMvc**: Kiểm thử các REST endpoint ở tầng Controller, bao gồm cả các tình huống yêu cầu xác thực (sử dụng `@WithMockUser` hoặc `SecurityMockMvcRequestPostProcessors`).

## Phân tích kết quả từ ảnh báo cáo

1. **Tổng quan**  
   - Instruction coverage đạt **20%** (1917 lệnh chưa chạy trên tổng 2399).  
   - Branch coverage chỉ **16%** (158 nhánh chưa chạy trên tổng 190).  
   Do khối lượng câu lệnh và nhánh khá nhiều nên có thể hiểu.

2. **Theo package**    
   - **`.space.dinhphatphat.controller`**  
     **28%** instruction và **23%** branch.
   - **`space.dinhphatphat.service`**  
     Instruction coverage rất thấp (**3%**), branch coverage bằng 0. Cần bổ sung test.
   - **`space.dinhphatphat.config`**  
     Coverage tổng hợp ở mức **100%**, branch không áp dụng.

## Kết luận & Định hướng cải thiện  
- Tổng quan:

- Instruction coverage chỉ 20%, tức là chỉ 1/5 số lệnh được thực thi qua test → mức độ kiểm thử còn rất hạn chế.

- Branch coverage chỉ 16%, tức là phần lớn các điều kiện (if, else, switch) chưa được kiểm tra → tiềm ẩn nhiều lỗi logic chưa bị phát hiện.

- Theo từng package:

- controller: đã có test, nhưng cần thêm để bao phủ hết các logic nhánh, xử lý lỗi, session null, token sai v.v.

- service: cực kỳ thấp (3%) và branch = 0% → gần như toàn bộ lớp Service chưa có test trực tiếp.

- **Hướng cải thiện**:  
🎯 Ưu tiên số 1: Viết unit test cho lớp service
Vì service là trung tâm xử lý logic nghiệp vụ (như order, login, cart), cần được kiểm tra độc lập.

Viết test riêng cho từng method của OrderService, CartItemService, UserService, dùng @TestConfiguration hoặc @MockBean để inject các repo giả.

🎯 Ưu tiên số 2: Mở rộng test cho controller
Bao phủ các nhánh điều kiện như:

Người dùng chưa đăng nhập.

cartItemIds rỗng hoặc không tồn tại.

orderService.order() ném exception.

orderService.updateStatus() trả về null.

🧪 Tăng test case phủ lỗi (negative tests):
Test sai dữ liệu đầu vào, thiếu trường, session null, token sai, v.v.

8\. Cài Đặt và Thiết Lập
----------------------------------

- Mở Docker trên máy
- Clone repo về máy: https://github.com/DinhPhatPhat/Spring-Commerce
- Giải nén repo, mở thư mục chứa dự án.
- Build dự án springboot: mvn clean install -DskipTests
- -DskipTests là một tùy chọn để người dùng có thể bỏ qua test hoặc không.
- Chạy lệnh: docker compose up --build
- Sau khi build, dự án springcommerce sẽ có 2 container là app và mysql-db.
- Lần đầu build, database sẽ khởi tạo giá trị ban đầu, do đó có thể chậm hơn springcommerce mặc dù springcommerce đã *depend vào mysql-db, trong trường hợp lần đầu build, app chưa thể khởi động, vui lòng stop và  build lại lần 2, lần này giá trị khởi tạo database đã có nên app sẽ chạy được.


