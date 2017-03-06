require 'sinatra'
require 'json'
require_relative 'Users.rb'

get '/users/:email' do
	return_msg = {}
	if (params.key?("email") && params.key?("password"))
		result = get_user(params['email'], params['password'])
		if (result == nil)
			return_msg[:status] = 'invalid user id'
		else
			return_msg[:status] = 'success'
			return_msg[:user] = result.to_s
		end
	else
		return_msg[:status] = 'required parameters - email, password - not provided'
	end
	return_msg.to_json
end

post '/users' do
	return_msg = {}
	if (params.key?("name") && params.key?("email") && params.key?("password"))
		result = create_user(params)
		if (result.class == Hash)
	                return_msg[:user] = result
		else
			return_msg[:status] = result
		end
	else
		return_msg[:status] = 'required parameters - name, email, password - not provided'
	end
	return_msg.to_json
end

put '/users/:email' do
	return_msg = {}
	if (params.key?("email") && params.key?("password"))
		result = update_user(params['email'], params['password'])
		if (result.class == Hash)
			return_msg[:user] = result
		else
			return_msg[:status] = result
		end
	else
		return_msg[:status] = 'required parameters - email - not provided'
	end
	return_msg.to_json
end
