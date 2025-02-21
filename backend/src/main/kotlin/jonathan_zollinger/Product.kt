package jonathan_zollinger

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity("products")
data class Product(
    @field:Id @field:JsonProperty("product_id") val productId: Long? = null,
    @field:JsonProperty("product_name") val productName: String,
    @field:JsonProperty("supplier_id") val supplierId: Long? = null,
    @field:JsonProperty("category_id") val categoryId: Long? = null,
    @field:JsonProperty("quantity_per_unit") val quantityPerUnit: String? = null,
    @field:JsonProperty("unit_price") val unitPrice: Double? = null,
    @field:JsonProperty("units_in_stock") val unitsInStock: Int? = null,
    @field:JsonProperty("units_on_order") val unitsOnOrder: Int? = null,
    @field:JsonProperty("reorder_level") val reorderLevel: Int? = null,
    val discontinued: Int
)