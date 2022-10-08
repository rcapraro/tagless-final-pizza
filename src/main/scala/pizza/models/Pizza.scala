package pizza.models

import Pizza.*

case class Pizza private(private val pate: Pate, private val ingredients: List[Ingredient]) {
  def lever: Pizza = this.copy(pate = Pate(pate.epaisseur * 2))

  def ajouterIngredients(ingredients: List[Ingredient]) = this.copy(ingredients = ingredients)

  def ajouterIngredient(ingredient: Ingredient) = this.copy(ingredients = ingredients :+ ingredient)

  def getIngredients: List[Ingredient] = this.ingredients

  override def toString: String = s"avec une pâte de ${pate.epaisseur}mm et les ingrédients suivants: ${ingredients.mkString(", ")}"
}

object Pizza {
  private case class Pate(epaisseur: Int) extends AnyVal

  def preparerPate: Pizza = Pizza(Pate(epaisseur = 1), List.empty)
}

final case class Ingredient(nom: String, quantite: Int) {
  override def toString: String = s"$quantite $nom"
}

