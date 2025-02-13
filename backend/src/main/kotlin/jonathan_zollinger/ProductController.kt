package jonathan_zollinger

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import net.datafaker.Faker
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@Controller("/product")
class ProductController {

    @Get("/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getProduct(@PathVariable count: Int): Publisher<List<Product>> {
        val faker = Faker()
        return Flux.range(1, count).map {
            Product (
                name = faker.commerce().productName(),
                description = faker.commerce().material(),
                price = faker.commerce().price().toDouble(),
                sku = faker.code().ean8(),
                quantityInStock = faker.number().positive()
            )
        }.collectList()
    }
}