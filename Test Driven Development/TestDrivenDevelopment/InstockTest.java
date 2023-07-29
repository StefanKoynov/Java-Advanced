package TestDrivenDevelopment;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InstockTest {

    private ProductStock instock;
    private Product product;

    @Before
    public void setUp() {
        this.instock = new Instock();
        this.product = new Product("test_product" , 13.00, 1);
    }


    @Test
    public void testAddInStockShouldContainThatProduct() {

        instock.add(product);

        Assert.assertTrue(instock.contains(product));

    }

    @Test
    public void testContainsShouldReturnFalseWhenProductIsMissing() {
        assertFalse(instock.contains(product));
    }


    @Test
    public void testCountShouldReturnTheCorrectNumberOfProducts() {
        assertEquals(0, instock.getCount());

        instock.add(product);

        assertEquals(1, instock.getCount());

        instock.add(new Product("test_two" , 10 , 13));

        assertEquals(2, instock.getCount());


    }

    @Test
    public void testFindShouldReturnTheCorrectEndProduct () {
        List<Product> products = addMultipleProducts();

        int productIndex = 3;

        Product expected = products.get(productIndex);

        Product actual = instock.find(productIndex);

        assertNotNull(actual);
        assertEquals(expected.getLabel() , actual.getLabel());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testFindWithIndexOutOfRangeShouldThrow () {
        List<Product> products = addMultipleProducts();

        instock.find(products.size());
    }

    @Test
    public void testChangeQuantityShouldUpdateTheProductQuantity () {
        instock.add(product);
        int expectedQuantity = product.getQuantity() + 10;
        instock.changeQuantity(product.getLabel(), expectedQuantity);

        assertEquals(expectedQuantity, product.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeQuantityShouldFailIfProductWithLabelIsMissing() {
        instock.changeQuantity("missing_label" , 13);

    }

    @Test
    public void testFindByLabelShouldReturnTheProductWithTheSameLabel() {
        addMultipleProducts();

        instock.add(product);

        Product actual = instock.findByLabel(product.getLabel());

        assertNotNull(actual);
        assertEquals(product.getLabel() , actual.getLabel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByLabelShouldFailWhenProductWithLabelIsMissing() {
        instock.findByLabel("missing_label");
    }



    @Test
    public void testFindFirstByAlphabeticalOrderShouldReturnCorrectNumberOfProduct() {
        addMultipleProducts();


        int expectedCount = 3;
        List<Product> actual = iterableToList(instock.findFirstByAlphabeticalOrder(expectedCount));
        assertEquals(expectedCount , actual.size());


    }

    @Test
    public void testFindFirstByAlphabeticalOrderShouldReturnTheProductsOrderedByLabel() {
        List<Product> products = addMultipleProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getLabel))
                .collect(Collectors.toList());
        int expectedCount =products.size();

        List<Product> actual = iterableToList(instock.findFirstByAlphabeticalOrder(expectedCount));

        
        assertEquals(expectedCount , actual.size());

        for (int i = 0; i < products.size(); i++) {
            String expectedLabel = products.get(i).getLabel();
            String actualLabel = actual.get(i).getLabel();
            assertEquals(expectedLabel, actualLabel);
        }



    }

    @Test
    public void testFindFirstByAlphabeticalOrderShouldReturnEmptyCollectionWhenNotEnoughProducts(){

        int size = addMultipleProducts().size();

        List<Product> products = iterableToList(instock.findFirstByAlphabeticalOrder(size + 1));

        assertEquals(0 , products.size());

    }

    @Test
    public void testFindAllInRangeShouldReturnTheCorrectRange() {
        final int beginRange = 2;
        final int endRange = 13;
        List<Product> products = addMultipleProducts()
                .stream().filter(product1 -> product1.getPrice() > beginRange && product1.getPrice() <= endRange)
                .collect(Collectors.toList());

        List<Product> actual = iterableToList(instock.findAllInRange(beginRange, endRange));

        assertEquals(products.size(), actual.size());

        boolean hasNoOutOfRangePrices = actual.stream()
                .map(Product::getPrice)
                .noneMatch(p -> p <= beginRange || p > endRange);

        assertTrue(hasNoOutOfRangePrices);
    }

    @Test
    public void testFindAllInRangeShouldReturnOrderedProductsByPriceDescending() {

        final int beginRange = 2;
        final int endRange = 13;
        List<Product> products = addMultipleProducts()
                .stream().filter(product1 -> product1.getPrice() > beginRange && product1.getPrice() <= endRange)
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());

        List<Product> actual = iterableToList(instock.findAllInRange(beginRange, endRange));

        assertEquals(products.size(), actual.size());

        for (int i = 0; i < products.size(); i++) {
            double actualPrice = actual.get(i).getPrice();
            double expectedPrice = products.get(i).getPrice();
            assertEquals(expectedPrice, actualPrice , 0.00);
        }


    }

    @Test
    public void testFindAllByPriceShouldReturnMatchingPriceProducts() {
        addMultipleProducts();

        int expectedPrice = 5;
        List<Product> products = iterableToList(instock.findAllByPrice(expectedPrice));

        for (Product p : products) {
            assertEquals(expectedPrice , p.getPrice() , 0.00);
        }
    }

    @Test
    public void testFindAllByPriceShouldReturnEmptyCollectionWheNoneMatches() {
        addMultipleProducts();

        List<Product> products = iterableToList(instock.findAllByPrice(-10));

        assertEquals(0 , products.size());


    }

    @Test
    public void testFindFirstMostExpensiveProductsShouldReturnTheCorrectMostExpensiveProducts() {

        int productsToTake = 5;
        List<Product> expectedProducts = addMultipleProducts()
                .stream().sorted(Comparator.comparing(Product::getPrice).reversed())
                .limit(productsToTake)
                .collect(Collectors.toList());

        List <Product> actual = iterableToList(instock.findFirstMostExpensiveProducts(productsToTake));

        assertEquals(expectedProducts.size() , actual.size());

        for (int i = 0; i < expectedProducts.size(); i++) {
            double expectedPrice = expectedProducts.get(i).getPrice();
            double actualPrice = actual.get(i).getPrice();
            assertEquals(expectedPrice , actualPrice , 0.00);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindFirstMostExpensiveProductsShouldFailWhenThereAreLessProductsInStock() {
        int size = addMultipleProducts().size();

        instock.findFirstMostExpensiveProducts(size + 1);
    }


    @Test
    public void testFindAllByQuantityShouldReturnMatchingProdcuts() {
        addMultipleProducts();

        int expectedQuantity = 5;
        List<Product> products = iterableToList(instock.findAllByQuantity(expectedQuantity));

        for (Product p : products) {
            assertEquals(expectedQuantity , p.getQuantity());
        }
    }

    @Test
    public void testFindAllByQuantityShouldReturnEmptyCollectionWheNoneMatches() {
        addMultipleProducts();

        List<Product> products = iterableToList(instock.findAllByQuantity(-10));

        assertEquals(0 , products.size());


    }

    @Test
    public void testIteratorShouldReturnAllTheProductsInStock() {

        List<Product> expected = addMultipleProducts();
        Iterator<Product> iterator = instock.iterator();

        List<Product> actual = new ArrayList<>();

        iterator.forEachRemaining(actual::add);

        assertEquals(expected, actual);
    }


    private List<Product> addMultipleProducts() {
        List<Product> products = List.of(
                new Product("label_3" , 5 , 4),
                new Product("label_2" , 3 , 7),
                new Product("label_1" , 10 , 7),
                new Product("label_6" , 5 , 7),
                new Product("label_7" , 3 , 7),
                new Product("label_5" , 16 , 8),
                new Product("label_4" , 2 , 7),
                new Product("label_8" , 7 , 1)
        );

        products.forEach(instock::add);
        return products;
    }

    private List<Product> iterableToList(Iterable<Product> iterable) {
        assertNotNull(iterable);
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        return products;
    }

}
