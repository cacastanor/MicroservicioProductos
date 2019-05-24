import java.util.UUID

import scala.concurrent.{ExecutionContext, Future}

import ProductRepository.ProductNotFound

trait ProductRepository {


  def all(): Future[Seq[Product]]

  def save(createProduct: CreateProduct): Future[Product]
}

object  ProductRepository {

  final case class  ProductNotFound(id: String) extends Exception("")
}

class InMemoryProductRepository(initialProducts: Seq[Product] = Seq.empty)(implicit ec: ExecutionContext) extends ProductRepository {

  private var products: Vector[Product] = initialProducts.toVector

  override def all(): Future[Seq[Product]] = Future.successful(products)

  override def save(createProduct: CreateProduct): Future[Product] = Future.successful {
    val product = Product(
      UUID.randomUUID().toString,
      createProduct.name,
      createProduct.pType,
      createProduct.cc,
    )
    products = products :+ product
    product
  }

}
