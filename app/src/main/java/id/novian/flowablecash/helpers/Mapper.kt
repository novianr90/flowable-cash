package id.novian.flowablecash.helpers

interface Mapper<Model, Domain> {
    fun mapToDomain(model: Model): Domain
}