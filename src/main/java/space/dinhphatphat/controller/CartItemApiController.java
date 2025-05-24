package space.dinhphatphat.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.dinhphatphat.service.CartItemService;

import java.util.Map;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemApiController {

    @Autowired
    private CartItemService cartItemService;
    @PostMapping("increase")
    public ResponseEntity<?> increase(@RequestBody Map<String, Integer> body) {
        int cartItemId = body.get("id");
        cartItemService.increaseQuantity(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reduce")
    public ResponseEntity<?> reduce(@RequestBody Map<String, Integer> body) {
        int cartItemId = body.get("id");
        cartItemService.reduceQuantity(cartItemId);
        return ResponseEntity.ok().build();
    }
}
