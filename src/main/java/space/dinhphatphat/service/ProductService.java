package space.dinhphatphat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Category;
import space.dinhphatphat.model.Product;
import space.dinhphatphat.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public Page<Product> getFilteredProducts(Pageable pageable, Long categoryId, String name, String brand, String color) {
        return productRepository.findByFilters(categoryId, name, brand, color, pageable);
    }
    public Product create(Product Product, MultipartFile image) throws IOException {
        try{
            Product savedProduct = productRepository.save(Product);
            if(image != null){
                String imagePath = upLoadImage(image, savedProduct.getId());
                savedProduct.setImagePath(imagePath);
            }
            return productRepository.save(savedProduct);
        }
        catch(Exception e){
            return null;
        }
    }
    public String upLoadImage(MultipartFile imageFile, int productId) throws IOException {
        String uploadDir;
        String accessPath;

        uploadDir = "uploads/image/product/";
        accessPath = "/uploads/image/product/";

        return UploadService.upLoadImage(imageFile, productId, uploadDir, accessPath);
    }

    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(int categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }
    public List<Product> findByCategoryIdAndActive(int categoryId) {
        return productRepository.findByCategory_IdAndActive(categoryId, true);
    }

    public Product update(Product product, MultipartFile image) throws IOException {
        try{
            if(image != null){
                String imagePath = upLoadImage(image, product.getId());
                product.setImagePath(imagePath);
            }
            return productRepository.save(product);
        }
        catch(Exception e){
            return null;
        }
    }

    public void deleteById(int id) {
       productRepository.deleteById(id);
    }
}
