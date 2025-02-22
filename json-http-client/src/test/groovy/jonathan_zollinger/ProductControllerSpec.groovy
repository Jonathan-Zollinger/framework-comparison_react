package jonathan_zollinger

import groovy.sql.Sql
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets

@MicronautTest
@Property(name = "datasources.default.driver-class-name",
        value = "org.testcontainers.jdbc.ContainerDatabaseDriver")
@Property(name = "datasources.default.url",
        value = "jdbc:tc:postgresql:15.2-alpine:///db?TC_INITSCRIPT=sql/northwind.sql")
@Property(name = "datasources.default.username",
        value = "dummyText")
@Property(name = "datasources.default.password",
        value = "alsoDummyText")
class ProductControllerSpec extends Specification {

    @Inject
    @Client("/product")
    HttpClient client

    @Shared
    Sql sql

    void setupSpec() {
        sql = Sql.newInstance("jdbc:tc:postgresql:15.2-alpine:///db?TC_INITSCRIPT=sql/northwind.sql")
    }

    void cleanupSpec() {
        sql?.close()
    }

    @Unroll
    void "Products Controller queries ProductByID with accurate results"() {
        expect: "ID: #product_id, Product Name: #product_name"
        testGetProductById(product_id as long, HttpStatus.OK, product_name as String)

        where:
        [product_id, product_name] << sql.rows('select product_id, product_name from products')
    }

    @Unroll
    void "Products Controller queries ProductByName with accurate results"() {
        expect: "ID: #product_id, Product Name: #product_name"
        testGetProductByName(product_name as String, HttpStatus.OK, product_id as long)

        where:
        [product_id, product_name] << sql.rows('select product_id, product_name from products')
    }


    private void testGetProductByName(String name, HttpStatus expectedStatus, Long expectedId) {
        try {
            String url = UriBuilder.of("/")
                    .queryParam("name", URLEncoder.encode(name, StandardCharsets.UTF_8.toString()))
                    .build().toString()
            HttpResponse<Product> response = client.toBlocking()
                    .exchange(HttpRequest.GET(url), Product)

            assert response.status() == expectedStatus
            if (expectedStatus == HttpStatus.OK) {
                assert response.body().productId == expectedId
            }

        } catch (HttpClientResponseException e) {
            assert e.status == expectedStatus
        }

    }


    private void testGetProductById(Long id, HttpStatus expectedStatus, String expectedProductName) {
        try {
            HttpResponse<Product> response = client.toBlocking().exchange("/${id}", Product)
            assert response.status() == expectedStatus
            if (expectedStatus == HttpStatus.OK) {
                assert response.body().productName == expectedProductName
            }
        } catch (HttpClientResponseException e) {
            assert e.status == expectedStatus
        }
    }

}