package pizza.interpreters

import cats.effect.Sync
import pizza.algebras.Four
import pizza.errors.{AucunIngredientError, FourFroidError}
import pizza.models.{Ingredient, Pizza}
import cats.data.NonEmptyList
import cats.syntax.apply.*

final class FourElectrique[F[_] : Sync] private(temperature: Int) extends Four[F] {
  override def prechauffer(temperature: Int): F[Four[F]] =
    Sync[F].blocking {
      Thread.sleep(2_000)
      new FourElectrique[F](temperature = temperature)
    }

  def ensureIngredients(pizza: Pizza): F[NonEmptyList[Ingredient]] =
    Sync[F].fromOption(NonEmptyList.fromList(pizza.getIngredients), AucunIngredientError)

  override def cuire(pizza: Pizza): F[Pizza] =
    if (temperature < 100) {
      Sync[F].raiseError(FourFroidError)
    } else {
      ensureIngredients(pizza) *>
      Sync[F].blocking {
        Thread.sleep(3_000)
        pizza.lever
      }
    }
}

object FourElectrique {
  def make[F[_] : Sync]: FourElectrique[F] = new FourElectrique[F](temperature = 0)
}
