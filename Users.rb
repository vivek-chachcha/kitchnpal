require_relative 'Client.rb'

def get_user(email)
	user = nil
        # connect to the MySQL server
        client = create_client()
	stmt = client.prepare("SELECT * FROM User WHERE email = ?")
	results = stmt.execute(email)
	if (results.count == 1)
		results.each do |row|
			user = row
		end
	end
	user
end

def create_user(params)
	user = nil
	client = create_client()
	stmt = client.prepare("INSERT INTO User (name, password, email, calPerDay, isActive, preferences,
				lacto_vegetarian, ovo_vegetarian, vegan, vegetarian, pescetarian, accessToken) VALUES
				(?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?)")
	calPerDay = (params['calPerDay'] == nil) ? 0 : params['calPerDay']
	preferences = (params['preferences'] == 'cheapest') ? 'cheapest' : 'lowcal'
	lactoVeg = (params['diet'].include? "lacto") ? 1 : 0
	ovoVeg = (params['diet'].include? "ovo") ? 1 : 0
	vegan = (params['diet'].include? "vegan") ? 1 : 0
	vegetarian = (params['diet'].include? "vegetarian") ? 1 : 0
	pescetarian = (params['diet'].include? "pescetarian") ? 1 : 0
	accessToken = (0...8).map { (65 + rand(26)).chr }.join
	results = stmt.execute(params['name'], params['password'], params['email'], calPerDay, preferences, lactoVeg, ovoVeg, vegan, vegetarian, pescetarian, accessToken)
	returns = {}
	id = client.query("SELECT last_insert_id()")
	id = id.first['last_insert_id()']
	results = client.query("SELECT * FROM User WHERE id = '#{id}'")
	results.first
end
