package jonathan_zollinger

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ChuckNorrisFact(
    var submitter: String,
    var punchLine: String
    )
