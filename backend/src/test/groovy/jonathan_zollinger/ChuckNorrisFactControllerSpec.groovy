package jonathan_zollinger

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class ChuckNorrisFactControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client

    void 'test it works'() {
        when:
        HttpResponse<Object> response = client.toBlocking().exchange(HttpRequest.GET("/chuck/one-liner") as HttpRequest)

        then:
        response.status() == HttpStatus.OK

        and:
        null != response.body()

    }
}
