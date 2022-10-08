package pizza

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.flatMap.*
import cats.syntax.applicativeError.*
import com.typesafe.scalalogging.Logger
import pizza.errors.*
import pizza.interpreters.{FourElectrique, TableDeCuisine}
import pizza.models.{Ingredient, Pizza}

object Main extends IOApp {

  val logger = Logger("pizzaLogger")

  override def run(args: List[String]): IO[ExitCode] = {

    val pateAPizza = Pizza.preparerPate
    val fourFroid = FourElectrique.make[IO]
    val tableDeCuisine = new TableDeCuisine[IO]

    val pizzaProgram = for {
      _ <- IO(logger.info(s"Démarrage de la prépation de la pizza"))
      fourPrechauffe <- fourFroid.prechauffer(100) <* IO(logger.info("Préchauffage du four... OK!"))
      _ <- IO.cede
      pizzaALaTomate  <- tableDeCuisine.ajouterIngredient(pateAPizza, Ingredient("Tomate", 2))  <* IO(logger.info("➕ Ajout de 2 tomates..."))
      pizzaAuxAnchois  <- tableDeCuisine.ajouterIngredient(pizzaALaTomate, Ingredient("Anchois", 3)) <* IO(logger.info("➕ Ajout de 3 anchoix..."))
      pizzaCuite <- fourPrechauffe.cuire(pizzaAuxAnchois) <* IO(logger.info(s"Cuisson de la pizza $pizzaAuxAnchois..."))
      _ <- IO.cede
      _ <- IO(logger.info(s"La pizza a des ingrédients et a cuit correctement!"))
    } yield pizzaCuite

    pizzaProgram
      .onError({
        case FourFroidError =>
          IO(logger.error("⚠️Le four était froid, la pâte de la pizza n'a pas suffisamment levé :("))
        case AucunIngredientError =>
          IO(logger.error("⚠️Aucun ingrédient dans cette pizza !"))
        case   IngredientQuantiteZero(ingredient) =>
          IO(logger.error(s"⚠️On ne peut pas ajouter l'ingrédient ${ingredient} avec une quantité 0 !"))
      })
      .flatMap(_ => IO(logger.info(s"🍕 Voilà votre pizza! Bon appétit!")))
      .as(ExitCode.Success)
  }
}
