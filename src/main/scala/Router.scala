import scala.util.{Failure, Success}

import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}

class ProductRouter(productRepository: ProductRepository) extends Router with Directives with ProductDirectives with ValidatorDirectives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = pathPrefix("products") {
    pathEndOrSingleSlash {
      get {
        handleWithGeneric(productRepository.all()) { products =>
          complete(products)
        }
      } ~ post {
        entity(as[CreateProduct]) { createProduct =>
          validateWith(CreateProductValidator)(createProduct) {
            handleWithGeneric(productRepository.save(createProduct)) { products =>
              complete(products)
            }
          }
        }
      }
    } 
  }
}
