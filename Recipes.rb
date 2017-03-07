require_relative 'Client.rb'
require 'unirest'

def get_recipes(params)
	name = params.has_key?("name")) ? params['name'] : ""
	ingredients = params.has_key?("ingredients") ? params['ingredients'] : ""
	user = get_user_by_token(params['accessToken'])
	diet = user.diet.nil? ? "" : user.diet 
	response = Unirest.get "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?addRecipeInformation=false&fillIngredients=false&instructionsRequired=true&limitLicense=false&number=20&offset=0&query=&ranking=1",
		headers:{
		"X-Mashape-Key" => "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi",
		"Accept" => "application/json"
		},
		parameters:{ :query => name, :diet => diet, :includeIngredients => ingredients}

	if user.preferences == 'lowCal'
		response.body.results.sort_by!(|recipe| recipe.calories)
	else
		response.body.results.each do |k|
			recipe = get_specific_recipe(k.id)
			k[:price] = recipe.pricePerServing
		end
		response.body.results.sort_by!(|recipe| recipe.pricePerServing)
	end
	response.body.results
end

def get_specific_recipe(id)
	response = Unirest.get "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/#{id}/information?includeNutrition=true",
	headers:{
	"X-Mashape-Key" => "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi",
	"Accept" => "application/json"
	}
	if response.body.status == 404
		return "Recipe ID not found"
	end
	reponse.body
end	
