package dev.akorovai.ecommerce.product;

import dev.akorovai.ecommerce.category.Category;
import dev.akorovai.ecommerce.category.CategoryRepository;
import dev.akorovai.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid category ID: " + productRequest.categoryId()));

        Product product = new Product();
        product.setCategory(category);
        modelMapper.map(productRequest, product);

        return repository.save(product).getId();
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requestList) {

        var productIds = requestList.stream().map(ProductPurchaseRequest::productId).toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Invalid number of products");
        }
        var storedRequest = requestList.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < 0; i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID::" + productRequest.productId());
            }

            var newAvailabeQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailabeQuantity);
            repository.save(product);
            purchasedProducts.add(modelMapper.map(product, ProductPurchaseResponse.class));
        }
        return purchasedProducts;
    }


    @Override
    public ProductResponse findById(Integer productId) {
        return repository.findById(productId).map(product -> modelMapper.map(product, ProductResponse.class)).orElseThrow(() -> new EntityNotFoundException("Product not found with the ID::" + productId));
    }


    @Override
    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
    }
}
