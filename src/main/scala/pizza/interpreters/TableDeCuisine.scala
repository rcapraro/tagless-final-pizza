package pizza.interpreters

import cats.ApplicativeThrow
import cats.effect.Sync
import pizza.algebras.PlanDeTravail
import pizza.errors.IngredientQuantiteZero
import pizza.models.{Ingredient, Pizza}

class TableDeCuisine[F[_] : ApplicativeThrow] extends PlanDeTravail[F] {

  override def ajouterIngredient(pizza: Pizza, ingredient: Ingredient): F[Pizza] =
    if (ingredient.quantite <= 0) {
      ApplicativeThrow[F].raiseError(IngredientQuantiteZero(ingredient.nom))
    } else {
      ApplicativeThrow[F].pure(pizza.ajouterIngredient(ingredient))
    }
}
