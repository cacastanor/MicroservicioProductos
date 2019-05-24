trait Validator[T] {
  def validate(t: T): Option[ApiError]
}
//Pendiente cuando se cambie los datos
object CreateProductValidator extends Validator[CreateProduct] {
  def validate(createProduct: CreateProduct): Option[ApiError] =
    if (createProduct.name.isEmpty)
      Some(ApiError.emptyNameField)
    else
      None
}

