require_relative 'Client.rb'
require 'unirest'
require 'uri'

def get_recipes(params)
	name = params.has_key?("name") ? params['name'] : ""
	ingredients = params.has_key?("ingredients") ? params['ingredients'] : ""
	user = get_user_by_token(params['accessToken'])
	diet = user['diet'].nil? ? "" : user['diet']
	diet = URI::encode(diet)
	response = Unirest.get "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?addRecipeInformation=true&fillIngredients=false&instructionsRequired=true&limitLicense=false&number=20&offset=0&ranking=1&minCalories=1&query=#{name}&includeIngredients=#{ingredients}&diet=#{diet}",
		headers:{
		"X-Mashape-Key" => "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi",
		"Accept" => "application/json"
		}

	if (ingredients != "")
		response.body['results'].sort_by!{|recipe| recipe['missedIngredientCount']}
	elsif (user['preferences'] == 'lowcal')
		response.body['results'].sort_by!{|recipe| recipe['calories']}
	else
		response.body['results'].sort_by!{|recipe| recipe['pricePerServing']}
	end
	response.body['results']
end

def get_specific_recipe(id)
	response = Unirest.get "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/#{id}/information?includeNutrition=true",
	headers:{
	"X-Mashape-Key" => "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi",
	"Accept" => "application/json"
	}
	if (response.body['status'] == 404)
		return "Recipe ID not found"
	end
	response.body
end	
