package pizza.errors

import scala.util.control.NoStackTrace

sealed trait PizzaError extends NoStackTrace
case object FourFroidError extends PizzaError
case object AucunIngredientError extends PizzaError
case class IngredientQuantiteZero(ingredient: String) extends PizzaError
