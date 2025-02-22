package jonathan_zollinger

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import jakarta.validation.constraints.NotBlank

@Suppress("kotlin:S6526")
@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class ProductRepository : PageableRepository<Product, Long> {
    abstract fun findByProductName(@NotBlank productName: String) : Product?
}