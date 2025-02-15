<dfn>
    <dt><h1>ZIT</h1></dt>
    <dd>Zollinger Inventory Tool</dd>
</dfn><br>

This project is under development. I'm aiming to compare implementations of tech stacks when the front end strategy changes. 

### Controlled Variables
I'm wanting to maintain some baseline consistencies between each implementation.

- backend framework is micronaut-based
- build tool is to be gradle-kotlin
- backing data provided by northwind database via postgres-alpine container
- data queries are to use micronaut-data with jdbc flavor

### Shared Resources
test data is provided for all implementations in the shared-resources directory. See [shared-resources/sql](shared-resources/sql/README.md) for more details.

