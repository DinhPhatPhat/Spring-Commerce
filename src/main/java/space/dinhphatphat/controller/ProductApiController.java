package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.CartItem;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.Product;
import space.dinhphatphat.model.User;
import space.dinhphatphat.service.CartItemService;
import space.dinhphatphat.service.CategoryService;
import space.dinhphatphat.service.ProductService;
import space.dinhphatphat.service.UserService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getFilteredProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color
    ) {
        int pageSize = 6;
        Page<Product> productPage = productService.getFilteredProducts(
                PageRequest.of(page - 1, pageSize, Sort.by("updatedAt").descending()),
                categoryId, name, brand, color);

        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("totalPages", productPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Validated @ModelAttribute Product product,
                                           BindingResult bindingResult,
                                           @RequestParam(required = false) MultipartFile image,
                                           HttpSession session,
                                           @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null || user.getRole() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập với tư cách quản lý");
        }

        if(product.getCategory() == null || categoryService.findById(product.getCategory().getId()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng chọn danh mục sản phẩm");
        }
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        if (image != null && image.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 2MB");
        }

        try {
            System.out.println(product.getName());
            productService.create(product, image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sản phẩm mới đã được thêm");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@Validated @ModelAttribute Product product,
                                            BindingResult bindingResult,
                                            HttpSession session,
                                            @RequestParam(required = false) MultipartFile image,
                                            @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null || user.getRole() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập với tư cách quản lý");
        }
        ResponseEntity<?> errorResponse = checkBindingResult(user, bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        Product existingProduct = productService.findById(product.getId());
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy sản phẩm");
        }

        if (image != null && image .getSize() > 3 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File ảnh cần bé hơn 3MB");
        }
        try {
            existingProduct.setCategory(product.getCategory());
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setColor(product.getColor());
            existingProduct.setActive(product.isActive());
            productService.update(existingProduct, image);
            return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thành công");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id, HttpSession session,
                                                @CookieValue(value = "token", required = false) String token) {
        User user = userService.getCurrentUser(session, token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập");
        }
        Category existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy danh mục sản phẩm");
        }

        try {
            categoryService.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body("Đã xóa câu danh mục sản phẩm");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
        }
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Integer> body, HttpSession session,@CookieValue(value = "token", required = false) String token){
        User user = userService.getCurrentUser(session, token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hãy đăng nhập để mua hàng");
        }
        int id = body.get("id");
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm không tồn tại");
        }
        else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(existingProduct);
            cartItem.setUser(user);
            cartItem.setQuantity(1);
            try {
                CartItem savedCartItem = cartItemService.addCartItem(cartItem);
                if (savedCartItem != null) {
                    return ResponseEntity.status(HttpStatus.OK).body("Đã thêm sản phẩm vào giỏ hàng");
                }
                else {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lỗi hệ thống, vui lòng thử lại sau");
            }
        }
    }

    private ResponseEntity<?> checkBindingResult(User user ,BindingResult bindingResult) {
        // BindingResult store valid error, then log and return to front-end
        if (bindingResult.hasErrors()) {
            List<String> fieldOrder = List.of(
                    "name",         // Tên sản phẩm (bắt buộc, @NotBlank)
                    "price",        // Giá sản phẩm (bắt buộc, @DecimalMin)
                    "category",     // Danh mục (bắt buộc, @ManyToOne không null)
                    "brand",        // Thương hiệu
                    "color",        // Màu sắc
                    "description",  // Mô tả
                    "imagePath",    // Đường dẫn ảnh
                    "isActive"      // Trạng thái kích hoạt
            );

            List<String> errors = bindingResult.getFieldErrors().stream()
                    .sorted(Comparator.comparingInt(e -> fieldOrder.indexOf(e.getField())))
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            System.out.println(errors);
            return ResponseEntity.badRequest().body(errors.get(0));
        }
        return null;
    }
}
