package pizza.algebras

import pizza.models.Pizza

trait Four[F[_]] {
  def prechauffer(tempature: Int): F[Four[F]]
  def cuire(pizza: Pizza): F[Pizza]
}
