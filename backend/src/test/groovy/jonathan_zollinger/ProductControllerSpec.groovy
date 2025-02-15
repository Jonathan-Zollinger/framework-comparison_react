package jonathan_zollinger

import io.micronaut.context.annotation.Property
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import net.datafaker.Faker
import spock.lang.Specification

@MicronautTest
@Property(name = "datasources.default.driver-class-name",
        value = "org.testcontainers.jdbc.ContainerDatabaseDriver")
@Property(name = "datasources.default.url",
        value = "jdbc:tc:postgresql:15.2-alpine:///db?TC_INITSCRIPT=sql/northwind.sql")
class ProductControllerSpec extends Specification {

    @Inject
    @Client("/product")
    HttpClient client

    void 'test it works'() {
        def prodCount = new Faker().number().numberBetween(0, 30)
        when:
        HttpResponse<List<Product>> response = client.toBlocking()
                .exchange(HttpRequest.GET("/$prodCount")
                        .accept(MediaType.APPLICATION_JSON_TYPE),
                        Argument.listOf(Product.class))
        then:
        response.status() == HttpStatus.OK
        and:
        response.body().size() == prodCount.toInteger() // Assert on the list size
        response.body().forEach { prod ->
            prod.name.length() > 0
            prod.description.length() > 0
            prod.price > 0
            prod.sku.length() > 0
            prod.quantityInStock > 0
        }

    }
}
