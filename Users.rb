require_relative 'Client.rb'

def get_user(email, pwd)
	user = nil
        # connect to the MySQL server
        client = create_client()
	stmt = client.prepare("SELECT * FROM User WHERE email = ? AND password = ?")
	results = stmt.execute(email, pwd)
	if (results.count == 1)
		user = results.first
	end
	user
end

def create_user(params)
	user = nil
	client = create_client()

	#check if email already exists
	count = client.prepare("SELECT COUNT(id) FROM User WHERE email = ?")
	count = count.execute(params['email'])
	if (count.count != 0)
		return "User already exists!"
	end

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
	
	#retrieve inserted user
	id = client.query("SELECT last_insert_id()")
	id = id.first['last_insert_id()']
	results = client.query("SELECT * FROM User WHERE id = '#{id}'")
	results.first
end

def update_user(params)
	user = nil
	client = create_client()

	#check if email already exists
        count = client.prepare("SELECT COUNT(id) FROM User WHERE email = ?")
        count = count.execute(params['email'])
        if (count.count == 0)
                return "User does not exist!"
        end

	stmt = client.prepare("UPDATE User
				SET name = ?, password = ?, calPerDay = ?, preferences = ?,
                                lacto_vegetarian = ?, ovo_vegetarian = ?, vegan = ?, vegetarian = ?, pescetarian = ?
                        	WHERE email = ?")
        calPerDay = (params['calPerDay'] == nil) ? 0 : params['calPerDay']
        preferences = (params['preferences'] == 'cheapest') ? 'cheapest' : 'lowcal'
        lactoVeg = (params['diet'].include? "lacto") ? 1 : 0
        ovoVeg = (params['diet'].include? "ovo") ? 1 : 0
        vegan = (params['diet'].include? "vegan") ? 1 : 0
        vegetarian = (params['diet'].include? "vegetarian") ? 1 : 0
        pescetarian = (params['diet'].include? "pescetarian") ? 1 : 0
        results = stmt.execute(params['name'], params['password'], calPerDay, preferences, lactoVeg, ovoVeg, vegan, vegetarian, pescetariani, params['email'])
        
	#retrieve updated user
	id = client.query("SELECT last_insert_id()")
        id = id.first['last_insert_id()']
        results = client.query("SELECT * FROM User WHERE id = '#{id}'")
        results.first
end
