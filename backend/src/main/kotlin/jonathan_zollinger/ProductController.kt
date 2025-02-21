package jonathan_zollinger

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Controller("/product")
open class ProductController(private val productRepository: ProductRepository) {

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getProduct(id:Long): HttpResponse<Product> {
        val productOptional = productRepository.findById(id)
        return if (productOptional.isPresent) {
            HttpResponse.ok(productOptional.get())
        } else {
            HttpResponse.notFound()
        }
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getProductByName(@QueryValue name: String): HttpResponse<Product> {
        val decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8)
        val product = productRepository.findByProductName(decodedName)
        return if (null != product) {
            HttpResponse.ok(product)
        } else {
            HttpResponse.notFound()
        }
    }
}