package pizza.algebras

import pizza.models.{Ingredient, Pizza}

trait PlanDeTravail[F[_]] {
  def ajouterIngredient(pizza: Pizza, ingredient: Ingredient): F[Pizza]
}
