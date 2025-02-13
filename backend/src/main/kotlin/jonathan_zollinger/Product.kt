package jonathan_zollinger

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Product @JvmOverloads constructor(
    var name: String,
    var description: String? = null,
    var price: Double,
    var sku: String? = null,
    var quantityInStock: Int? = null
)