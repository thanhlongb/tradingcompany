package vn.edu.rmit.tradingcompany.controller;

import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    // READ
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    // UPDATE
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    // DELETE
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public Product deleteProduct(@RequestBody Product product) {
        return productService.deleteProduct(product);
    }
}
