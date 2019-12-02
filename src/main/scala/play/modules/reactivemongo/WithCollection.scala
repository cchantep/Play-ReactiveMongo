package play.modules.reactivemongo

import scala.concurrent.{ ExecutionContext, Future }

import reactivemongo.api.{
  Collection,
  CollectionProducer,
  DefaultDB
}

/**
 * {{{
 * import scala.concurrent.Future
 *
 * import reactivemongo.api.DefaultDB
 *
 * import reactivemongo.play.json.collection.JSONCollection
 * import play.modules.reactivemongo.WithCollection
 *
 * class MyComponent(
 *   val collectionName: String) extends WithCollection[JSONCollection] {
 *   def database: Future[DefaultDB] = ???
 * }
 * }}}
 */
trait WithCollection[C <: Collection] {
  /** Database asynchronous reference */
  def database: Future[DefaultDB]

  /** The name of the collection */
  def collectionName: String

  /** Resolve a reference to the collection specified by its name. */
  final def collection(implicit ec: ExecutionContext, cp: CollectionProducer[C]): Future[C] = database.map(_(collectionName))
}

/**
 * {{{
 * import scala.concurrent.Future
 *
 * import reactivemongo.api.DefaultDB
 *
 * import reactivemongo.play.json.collection.JSONCollection
 * import play.modules.reactivemongo.CollectionResolution
 *
 * class MyComponent extends CollectionResolution[JSONCollection]("collName") {
 *   def database: Future[DefaultDB] = ???
 * }
 * }}}
 */
abstract class CollectionResolution[C <: Collection](
    val collectionName: String) extends WithCollection[C]

