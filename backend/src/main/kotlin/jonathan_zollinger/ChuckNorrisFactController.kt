package jonathan_zollinger

import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import net.datafaker.Faker
import org.reactivestreams.Publisher

@Controller("/chuck")
class ChuckNorrisFactController {

    @Get("/one-liner")
    @Produces(MediaType.TEXT_JSON)
    @SingleResult
    fun getRandomOneLiner(faker: Faker = Faker()): Publisher<ChuckNorrisFact> {
        return Publisher<ChuckNorrisFact>{
            ChuckNorrisFact(faker.name().name(), punchLine =  faker.chuckNorris().fact().toString())
        }
    }
}