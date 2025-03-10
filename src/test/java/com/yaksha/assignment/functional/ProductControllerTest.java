package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.MasterData.asJsonString;
import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yaksha.assignment.controller.ProductController;
import com.yaksha.assignment.entity.Order;
import com.yaksha.assignment.entity.Product;
import com.yaksha.assignment.repository.OrderRepository;
import com.yaksha.assignment.repository.ProductRepository;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private OrderRepository orderRepository;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to check if the 'ProductController' class has @RestController annotation
	@Test
	public void testRestControllerAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkClassAnnotation(filePath, "RestController");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if 'createProduct' method has @PostMapping annotation
	@Test
	public void testCreateProductHasProperAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "createProduct", "PostMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if the Product class is annotated with @Entity
	@Test
	public void testProductEntityAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/entity/Product.java"; // Update path as needed

		// Check if the class is annotated with @Entity
		boolean result = JavaParserUtils.checkClassAnnotation(filePath, "Entity");

		// Assert the result using YakshaAssert
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check DELETE request to delete a product
	@Test
    public void testDeleteProduct() throws Exception {
        when(this.productRepository.existsById(eq(1L))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
                businessTestFile);
    }

	// Test to check GET request for products by price range
	@Test
	public void testGetProductsByPriceRange() throws Exception {
		List<Product> products = List.of(new Product("Product A", "Description A", 100.0),
				new Product("Product B", "Description B", 200.0));

		when(this.productRepository.findByPriceBetween(eq(100.0), eq(200.0))).thenReturn(products);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/price").param("minPrice", "100.0")
				.param("maxPrice", "200.0").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(products)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check GET request for products by name
	@Test
	public void testGetProductsByName() throws Exception {
		List<Product> products = List.of(new Product("Product A", "Description A", 100.0));

		when(this.productRepository.findByNameContaining(eq("Product"))).thenReturn(products);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/name").param("keyword", "Product")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(products)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check POST request to create a new product
	@Test
	public void testCreateProduct() throws Exception {
		Product product = new Product("Product X", "A cool product", 200.0);

		when(this.productRepository.save(any(Product.class))).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products").content(asJsonString(product))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(product)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check GET request for orders by customer ID
	@Test
	public void testGetOrdersByCustomerId() throws Exception {
		List<Order> orders = List.of(new Order(1L, 150.0), new Order(1L, 200.0));

		when(this.orderRepository.findByCustomerId(eq(1L))).thenReturn(orders);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders/customer/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(orders)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check GET request for orders by total amount greater than the given
	// value
	@Test
	public void testGetOrdersByAmount() throws Exception {
		List<Order> orders = List.of(new Order(1L, 250.0));

		when(this.orderRepository.findByTotalAmountGreaterThan(eq(200.0))).thenReturn(orders);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders/amount").param("amount", "200.0")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(orders)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check POST request to create a new order
	@Test
	public void testCreateOrder() throws Exception {
		Order order = new Order(1L, 300.0);

		when(this.orderRepository.save(any(Order.class))).thenReturn(order);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/orders").content(asJsonString(order))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(order)) ? "true" : "false"),
				businessTestFile);
	}
}
